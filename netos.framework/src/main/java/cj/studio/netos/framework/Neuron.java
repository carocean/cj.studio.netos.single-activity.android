package cj.studio.netos.framework;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cj.studio.netos.framework.os.IReciever;
import cj.studio.netos.framework.os.ISender;
import cj.studio.netos.framework.os.INetOSMPusherBinder;
import cj.studio.netos.framework.os.NetOSMPusher;
import cj.studio.netos.framework.util.StringUtil;

/**
 * Created by caroceanjofers on 2018/1/19.
 */

public final class Neuron extends Application implements INeuron, IServiceProvider {


    private ICell cell;
    private ICellsap sap;
    NetOSServiceConnection conn;
    ISender senderToPusher;
    boolean isonline;
    boolean isconnected;
    IServiceProvider provider;

    @Override
    public void onCreate() {super.registerActivityLifecycleCallbacks(new Lifecycle(this));
        super.onCreate();
        refresh();
    }

    @Override
    public boolean isConnected() {
        return isconnected;
    }

    @Override
    public void onTerminate() {
        exit();
        super.onTerminate();
    }

    @Override
    public boolean isOnline() {
        return isonline;
    }

    public ICellsap cellsap() {
        if (sap == null) {
            sap = new Cellsap(this);
        }
        return sap;
    }

    @Override
    public IServiceProvider provider() {
        return this;
    }

    @Override
    public void refresh() {
        provider = new SystemServiceProvider();
        cell = cell();

        if (senderToPusher == null) {
            senderToPusher = new NetOSAppSender(cell);
        }
        if (conn == null) {
            conn = new NetOSServiceConnection();
        }

        if (!cellsap().checkIdentity()) {
//            throw new RuntimeException("检查本地身份失败，netos.app必须正确登录一次，以获取身份信息到本地");
            cell.refresh();
            return;
        }

        reconnect();

        cell.refresh();
    }

    @Override
    public void reconnect() {
        if (!cellsap().checkIdentity()) {
            throw new RuntimeException("检查本地身份失败，netos.app必须正确登录一次，以获取身份信息到本地");
        }

        if (senderToPusher == null) {
            senderToPusher = new NetOSAppSender(cell);
        }
        if (conn == null) {
            conn = new NetOSServiceConnection();
        }


        Intent intent = new Intent(this, NetOSMPusher.class);

        if (isconnected) {
            unbindService(conn);
            stopService(intent);
            isconnected = false;
        }

        intent.putExtra("addresslist", sap.remoteAddressList());
        intent.putExtra("domain", String.format("tcp://%s.com", sap.principal()));
        intent.putExtra("token", sap.token());

        startService(intent);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        isconnected = true;
    }

    @Override
    public ICell cell() {
        if (cell == null) {
            cell = new Workbench(this);
        }
        return cell;
    }

    @Override
    public void exit() {
        cell.close();
        Intent intent = new Intent(this, NetOSMPusher.class);
        unbindService(conn);
        stopService(intent);
    }

    @Override
    public Object getService(String name) {
        if (INeuron.class.getName().equals(name)) {
            return this;
        }
        if (ICellsap.class.getName().equals(name)) {
            return this.cellsap();
        }
        if (Application.class.getName().equals(name)) {
            return this;
        }
        if (ICell.class.getName().equals(name)) {
            return this.cell;
        }
        if ("$.mpusher.sender".equals(name)) {
            return this.senderToPusher;
        }

        return provider.getService(name);
    }

    @Override
    public <T> T getService(Class<T> clazz) {
        if (clazz.isAssignableFrom(INeuron.class)) {
            return (T) this;
        }
        if (clazz.isAssignableFrom(ICellsap.class)) {
            return (T) this.cellsap();
        }
        if (clazz.isAssignableFrom(Application.class)) {
            return (T) this;
        }
        if (clazz.isAssignableFrom(IWorkbench.class)) {
            return (T) this.cell;
        }
        return this.provider.getService(clazz);
    }

    class NetOSServiceConnection implements ServiceConnection {

        @Override
        public void onBindingDied(ComponentName name) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("onServiceDisconnected", ".....");
            isconnected = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("onServiceConnected", ".....");
            INetOSMPusherBinder binder = (INetOSMPusherBinder) service;
            binder.bindNetOSApp(senderToPusher, new NetOSAppReciever());
            isconnected = true;
        }
    }

    class NetOSAppSender implements ISender {
        IReciever reciever;
        ICell cell;

        public NetOSAppSender(ICell cell) {
            this.cell = cell;
        }

        @Override
        public void set(IReciever reciever) {
            this.reciever = reciever;
        }

        @Override
        public void send(Frame frame) {
            //reciever==null是netos.app未与mpusher绑定
            if (reciever == null) {
                doMPusherUnstart(frame);
                return;
            }
            reciever.recieve(frame);
        }

        private void doMPusherUnstart(Frame frame) {
            //将侦缓存到本地磁盘，在建立与mpusher的连接后自动发送
        }
    }

    class NetOSAppReciever implements IReciever {
        @Override
        public void recieve(Frame frame) {
            if ("GATEWAY/1.0".equals(frame.protocol())) {
                doGateway(frame);
                return;
            }
            String moduleName = frame.rootName();
            if (StringUtil.isEmpty(moduleName)) {
                throw new RuntimeException("侦缺少发送的模块目标");
            }
            IDendrite dendrite = cell.dendrite(moduleName);
            if (dendrite == null) {
                throw new RuntimeException("应用中不存在模块:" + moduleName);
            }
            dendrite.input(frame, cell);
        }

        private void doGateway(Frame frame) {
            String url = frame.url();
            if (url.startsWith("/handshake")) {

                //向各模块广播上线通知
                Set<String> set = cell.listDendriteName();
                for (String key : set) {
                    IDendrite dendrite = cell.dendrite(key);
                    Frame f = new Frame("online / mpusher/1.0");
                    dendrite.input(f, cell);
                }
                isonline = true;
                return;
            }
            if ("tryconnect".equals(frame.command())&&url.startsWith("/error")) {
                Log.e("tryconnect",frame.head("Message"));
                return;
            }
            if (url.startsWith("/disconnect")) {

                Set<String> set = cell.listDendriteName();
                for (String key : set) {
                    IDendrite dendrite = cell.dendrite(key);
                    Frame f = new Frame("offline / mpusher/1.0");
                    dendrite.input(f, cell);
                }
                isonline = false;
                return;
            }
        }
    }

    class Lifecycle implements ActivityLifecycleCallbacks {
        INeuron neuron;

        public Lifecycle(INeuron neuron) {
            this.neuron = neuron;
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (activity instanceof IModule) {
                Log.i("found module", ((IModule) activity).name());
                neuron.cell().getService(IWorkbench.class).addModule((IModule) activity);
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (activity instanceof IModule) {
                Log.i("Destroy module", ((IModule) activity).name());
                neuron.cell().getService(IWorkbench.class).removeModule(((IModule) activity).name());
            }
        }
    }

    class SystemServiceProvider implements IServiceProvider {
        String label;
        public SystemServiceProvider() {
            label="$.android.";
        }

        @Override
        public Object getService(String name) {
            int pos=name.indexOf(label);
            if (pos==0) {//访问系统服务
                String sname=name.substring(label.length(),name.length());
                return getSystemService(sname);
            }
            return null;
        }

        @Override
        public <T> T getService(Class<T> clazz) {

            return null;
        }
    }
}
