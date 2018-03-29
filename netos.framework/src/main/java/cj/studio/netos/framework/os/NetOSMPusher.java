package cj.studio.netos.framework.os;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.NetException;
import cj.studio.netos.framework.net.IConnection;
import cj.studio.netos.framework.net.TcpConnection;

/**
 * Created by caroceanjofers on 2018/1/22.
 */

public class NetOSMPusher extends IntentService {
    private IConnection connection;
    private NetOSMPusherBinder binder;
    private ISender mpushSender;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public NetOSMPusher() {
        super("cj.netos.mpusher");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        connection = new TcpConnection();
        mpushSender = new MPusherSender();
        binder = new NetOSMPusherBinder(mpushSender);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (connection.isConnected()) {
            connection.disconnect();
        }
        String domain = intent.getStringExtra("domain");
        String token = intent.getStringExtra("token");
        String[] addresslist = intent.getStringArrayExtra("addresslist");


        try {
            connection.tryconnect(domain, token, addresslist);
        } catch (NetException e) {
            if(mpushSender!=null){
                Frame err=new Frame("tryconnect /error GATEWAY/1.0");
                err.head("Message",e.getMessage());
                mpushSender.send(err);
                return;
            }
            throw new RuntimeException(e);
        }

        try {
            connection.loop(this.mpushSender);
        } catch (NetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroy() {
        connection.disconnect();
        this.binder = null;
        this.mpushSender = null;
        super.onDestroy();
    }


    public class NetOSMPusherBinder extends Binder implements INetOSMPusherBinder {
        ISender mpushSender;

        public NetOSMPusherBinder(ISender mpushSender) {
            this.mpushSender = mpushSender;
        }

        @Override
        public void bindNetOSApp(ISender netosappsender, IReciever netosappreciever) {
            mpushSender.set(netosappreciever);
            netosappsender.set(new MPusherReciever());
        }
    }

    class MPusherSender implements ISender {
        IReciever reciever;

        @Override
        public void send(Frame frame) {
            //reciever==null是netos.app未与mpusher绑定
            if (reciever == null) {
                doAppUnrun(frame);
                return;
            }
            reciever.recieve(frame);
        }

        private void doAppUnrun(Frame frame) {
            //将侦缓存到本地磁盘，并发提醒到通知版和应用消息提示符，当app与它建立连接后则发给app，并清除提醒
        }

        @Override
        public void set(IReciever reciever) {
            this.reciever = reciever;
        }
    }

    class MPusherReciever implements IReciever {
        @Override
        public void recieve(Frame frame) {
            Log.i("MPusher recieve", frame.toString());
            switch (frame.protocol()){
                case "NETOS/1.0":
                    try {
                        connection.send(frame);
                    } catch (NetException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "NOTI/1.0"://到系统通知栏
                    break;
                case "TIPS/1.0"://到应用消息提醒位
                    break;
            }
        }
    }
}
