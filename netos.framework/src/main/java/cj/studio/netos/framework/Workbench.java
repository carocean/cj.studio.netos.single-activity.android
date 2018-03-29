package cj.studio.netos.framework;

import android.app.Activity;
import android.app.Application;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cj.studio.netos.framework.isite.IWebCore;
import cj.studio.netos.framework.isite.WebCore;
import cj.studio.netos.framework.netarea.NetAreaWorkbench;
import cj.studio.netos.framework.os.ISender;

/**
 * Created by caroceanjofers on 2018/1/19.
 */

public final class Workbench implements IWorkbench, ICell {
    private IAxon axon;
    private Map<String, IModule> moduleMap;//模块容器，另外还有：微应用容器（微应用需要安装）。而微网站仅需要安装微网站模板，一个模板支撑无数企业的移动门户
    private Map<String, IDendrite> dendriteMap;//key是突触名即模块名

    private IProfile profile;

    private IServiceSite site;

    public Workbench(IServiceProvider parent) {

        this.site = new ServiceSite(parent);
    }


    @Override
    public WebView createWebView(Activity parent) {
        WebView view = new WebView(parent);//webview必须在ui线程下创建

        return view;
    }

    @Override
    public void close() {
        moduleMap.clear();
        site.dispose();
        dendriteMap.clear();
    }

    @Override
    public void addService(String name, Object service) {
        site.addService(name, service);
    }

    @Override
    public void removeService(String name) {
        site.removeService(name);
    }

    @Override
    public IAxon axon() {
        return axon;
    }

    @Override
    public IDendrite dendrite(String moduleName) {
        return dendriteMap.get(moduleName);
    }

    @Override
    public Set<String> listDendriteName() {
        return dendriteMap.keySet();
    }

    @Override
    public IProfile profile() {
        return profile;
    }

    @Override
    public IDevice forDevice(String name) {
        return (IDevice) site.getService(name);
    }

    @Override
    public MobileInfo info() {
        return (MobileInfo) site.getService("mobile.info");
    }

    @Override
    public IStorage storage() {
        return (IStorage) site.getService(IStorage.class);
    }

    //js与java交互：http://blog.csdn.net/carson_ho/article/details/64904691
    //该方法是给模块开发者使用的
    //js引擎应该作为服务提供，因为它不但要应用到模块开发者，对于netport也是要的，但是js的权限是不同的，因此要有两套实现
    public void initWebview(WebView webview) {
        WebSettings webSettings = webview.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

    }

    //该方法是给模块开发者使用的，在开发netport模块时就要用到它
    @Override
    public WebView createWebview() {
        Application app = (Application) site.getService(Application.class);
        WebView webview = new WebView(app.getApplicationContext());
        initWebview(webview);
        return webview;
    }

    @Override
    public IDendrite createDendrite(ISynapsis synapsis) {
        String name = synapsis.bindModule().name();
        if (dendriteMap.containsKey(name)) {
            throw new RuntimeException("   ");
        }
        IDendrite dendrite = new Dendrite(synapsis);
        dendriteMap.put(name, dendrite);
        return dendrite;
    }

    @Override
    public void addModule(IModule module) {

        Synapsis synapsis = new Synapsis(module);

        moduleMap.put(module.name(), module);
        createDendrite(synapsis);
    }

    @Override
    public void removeModule(String name) {
        moduleMap.remove(name);
        IDendrite dendrite = dendriteMap.get(name);
        if (dendrite != null) {
            dendrite.dispose();
        }
    }

    @Override
    public IModule getModule(String name) {
        return moduleMap.get(name);
    }

    @Override
    public boolean containsModule(String name) {
        return moduleMap.containsKey(name);
    }


    @Override
    public Object getService(String name) {
        if ("$.workbench".equals(name)) {
            return this;
        }
        if ("$.net.http".equals(name)) {
            return site.getService(name);//http通讯服务
        }
        if (moduleMap.containsKey(name)) {
            return moduleMap.get(name);
        }
        return site.getService(name);
    }

    @Override
    public <T> T getService(Class<T> clazz) {
        if (clazz.isAssignableFrom(IWorkbench.class)) {
            return (T) this;
        }

        if (clazz.isAssignableFrom(IModule.class)) {
            return (T) moduleMap.values();
        }


        return site.getService(clazz);
    }


    @Override
    public boolean isOnline() {
        INeuron neuron = (INeuron) site.getService(INeuron.class);
        return neuron.isOnline();
    }

    @Override
    public boolean isConnected() {
        INeuron neuron = (INeuron) site.getService(INeuron.class);
        return neuron.isConnected();
    }

    @Override
    public void refresh() {

        Application app = site.getService(Application.class);
//        Content content =app.getApplicationContext();
        //根据app获得以下信息并初始化

        this.moduleMap = new HashMap<>();
        this.dendriteMap = new HashMap<>();

        registerServices();

        //初始化pins给轴突
        IHillock hillock = new Hillock();

        ISender toMPusherSender = (ISender) site.getService("$.mpusher.sender");
        IMessagePin tompusher = new MPusherPin(toMPusherSender);
        hillock.addPin(tompusher);
        axon = new Axon(hillock);


    }

    protected void registerServices() {
    }


    @Override
    public void renderTo(INetOSPortal portal) {

        IWebCore webCore = new WebCore(this);
        INetOSResource resource = portal.resource();
        INavigation navigation = portal.navigation();
        ISelection selection = portal.selection();
        ISlideButton slideButton = portal.slideButton();
        ITitlebar titlebar = portal.titlebar();
        IContainer container = portal.container();
        IHistory history = portal.history();
        List<IModule> moduleList = portal.modules();
        IDownSlideRegion downSlideRegion = portal.downSlideRegion();
        INetAreaWorkbench netAreaWorkbench = new NetAreaWorkbench(this);


        navigation.init(this, moduleList);


        site.addService("$.workbench.resource", resource);
        site.addService("$.workbench.history", history);
        site.addService("$.workbench.navigation", navigation);
        site.addService("$.workbench.selection", selection);//当前操作在哪个模块上
        site.addService("$.workbench.slidebutton", slideButton);
        site.addService("$.workbench.titlebar", titlebar);
        site.addService("$.workbench.downslideregion", downSlideRegion);
        site.addService("$.workbench.container", container);
        site.addService("$.workbench.netarea", netAreaWorkbench);//打开、关闭等
        site.addService("$.workbench.webcore", webCore);//浏览器、微站、微应等支持

        if (moduleList != null) {
            for (IModule m : moduleList) {
                addModule(m);
            }
            addModule(netAreaWorkbench);
            navigation.naviToModule("message");
        }

    }


    class MPusherPin implements IMessagePin {
        ISender toMPusher;

        public MPusherPin(ISender toMPusher) {
            this.toMPusher = toMPusher;
        }

        @Override
        public String name() {
            return "netos.mpusher";
        }

        @Override
        public void output(Frame frame) {
            if (toMPusher == null) {
                return;
            }
            toMPusher.send(frame);
        }
    }
}
