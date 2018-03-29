package cj.studio.netos.framework.isite;

import android.webkit.WebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cj.studio.netos.framework.ICellsap;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.isite.system.SiteInfoFunctions;
import cj.studio.netos.framework.isite.system.SubjectFunctions;
import cj.studio.netos.framework.isite.system.SensorFunctions;
import cj.studio.netos.framework.isite.system.WindowFunctions;

/**
 * Created by caroceanjofers on 2018/3/9.
 */

public class WebCore implements IWebCore, IServiceProvider {
    private IServiceProvider site;
    private Map<String, Object> registry;
    private List<IBrowserable> browsers;

    public WebCore(IServiceProvider site) {
        this.site = site;
        registry=new HashMap<>();
        browsers = new ArrayList<>();
        registerServices();
    }

    @Override
    public IBrowserable createBrowser(WebView webView) {
        IBrowserable browserable = new Browser(webView, this);
        browsers.add(browserable);
        return browserable;
    }

    @Override
    public IBrowserable createWebApp(WebView webView) {
        IBrowserable browserable = new WebApp(webView, this);
        browsers.add(browserable);
        return browserable;
    }

    @Override
    public IBrowserable createWebSite(WebView webView) {
        IBrowserable browserable = new WebSite(webView, this);
        browsers.add(browserable);
        return browserable;
    }

    @Override
    public void close() {
        for (IBrowserable b : browsers) {
            b.close();
        }
        registry.clear();
        browsers.clear();
        site = null;
    }


    @Override
    public Object getService(String name) {
        if ("$.system.names".equals(name)) {
            return registry.keySet();
        }
        if (registry.containsKey(name)) return registry.get(name);

        return site.getService(name);
    }

    @Override
    public <T> T getService(Class<T> clazz) {
        return site.getService(clazz);
    }

    protected void registerServices() {
        //netos系统、传感器、本地设备、环境这几类服务


        ISensorFunctions sensors=new SensorFunctions();
        SubjectFunctions subject=new SubjectFunctions();


        ICellsap sap=(ICellsap)site.getService(ICellsap.class);
        subject.principal(sap.principal());

        IWindowFunctions win = new WindowFunctions(subject,sensors);
        registry.put("$", win);
    }
}
