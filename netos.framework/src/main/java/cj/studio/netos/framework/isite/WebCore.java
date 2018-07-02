package cj.studio.netos.framework.isite;

import android.webkit.WebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cj.studio.netos.framework.ICellsap;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.isite.system.SubjectFunctions;
import cj.studio.netos.framework.isite.system.SensorFunctions;
import cj.studio.netos.framework.isite.system.WindowFunctions;

/**
 * Created by caroceanjofers on 2018/3/9.
 */

public class WebCore implements IWebCore, IServiceProvider {
    private IServiceProvider site;
    private Map<String, Object> registry;
    private List<IWebsite> Websites;

    public WebCore(IServiceProvider site) {
        this.site = site;
        registry=new HashMap<>();
        Websites = new ArrayList<>();
        registerServices();
    }

    @Override
    public IWebsite createWebsite(WebView webView) {
        IWebsite browserable = new Website(webView, this);
        Websites.add(browserable);
        return browserable;
    }

    @Override
    public IWebsite createWebApp(WebView webView) {
        IWebsite browserable = new Webapp(webView, this);
        Websites.add(browserable);
        return browserable;
    }



    @Override
    public void close() {
        for (IWebsite b : Websites) {
            b.close();
        }
        registry.clear();
        Websites.clear();
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
