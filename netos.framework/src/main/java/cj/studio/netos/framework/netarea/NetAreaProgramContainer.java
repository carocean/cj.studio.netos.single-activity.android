package cj.studio.netos.framework.netarea;

import android.webkit.WebView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.INetAreaProgramContainer;
import cj.studio.netos.framework.INetosProgramTemplate;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.isite.IWebsite;
import cj.studio.netos.framework.isite.IWebCore;

/**
 * Created by caroceanjofers on 2018/2/25.
 */

class NetAreaProgramContainer implements INetAreaProgramContainer {
    IServiceProvider site;
    Map<String, IWebsite> webapps;
    IWebsite desktop;
    public NetAreaProgramContainer(INetosProgramTemplate template, IServiceProvider site) {
        this.site = site;
        this.webapps = new HashMap<>();
    }

    public IWebsite getDesktop() {
        return desktop;
    }

    protected IWebCore webCore() {
        return (IWebCore) site.getService("$.workbench.webcore");
    }

    @Override
    public String name() {
        return "app";
    }

    @Override
    public void input(Frame frame, ICell cell) {
        //路由到相应的webapp或桌面上
    }



    @Override
    public IWebsite createDesktop(WebView webView) {
        if(desktop!=null){
            throw new RuntimeException("网域已有桌面");
        }
        IWebsite browserable = webCore().createWebsite(webView);
        desktop=browserable;
        return browserable;
    }


    @Override
    public IWebsite createWebApp(WebView webView) {
        IWebsite browserable = webCore().createWebApp(webView);
        webapps.put(browserable.getBrowserinfo().name(), browserable);
        return browserable;
    }
    //比如模拟浏览器
    @Override
    public IWebsite createWebSite(WebView webView) {
        IWebsite browserable = webCore().createWebsite(webView);
        webapps.put(browserable.getBrowserinfo().name(), browserable);
        return browserable;
    }
    @Override
    public void removeWebsite(IWebsite browser) {
        webapps.remove(browser);
    }

    @Override
    public void removeWebsite(String name) {
        webapps.remove(name);
    }

    @Override
    public IWebsite getWebsite(String name) {
        return webapps.get(name);
    }

    @Override
    public Set<String> enunWebsiteKeys() {
        return webapps.keySet();
    }

    @Override
    public void close() {
        webapps.clear();
        desktop.close();
        desktop=null;
    }
}
