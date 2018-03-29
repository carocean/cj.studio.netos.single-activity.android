package cj.studio.netos.framework.netarea;

import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.INetAreaProgramContainer;
import cj.studio.netos.framework.INetosProgramTemplate;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.isite.IBrowserable;
import cj.studio.netos.framework.isite.IWebCore;

/**
 * Created by caroceanjofers on 2018/2/25.
 */

class NetAreaProgramContainer implements INetAreaProgramContainer {
    IServiceProvider site;
    List<IBrowserable> browsers;

    public NetAreaProgramContainer(INetosProgramTemplate template, IServiceProvider site) {
        this.site = site;
        this.browsers = new ArrayList<>();
    }

    protected IWebCore webCore() {
        return (IWebCore) site.getService("$.workbench.webcore");
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public void input(Frame frame, ICell cell) {

    }

    @Override
    public int cnameId() {
        return 0;
    }

    @Override
    public IBrowserable createBrowser(WebView webView) {
        IBrowserable browserable = webCore().createBrowser(webView);
        browsers.add(browserable);
        return browserable;
    }

    @Override
    public IBrowserable createWebSite(WebView webView) {
        IBrowserable browserable = webCore().createWebSite(webView);
        browsers.add(browserable);
        return browserable;
    }

    @Override
    public IBrowserable createWebApp(WebView webView) {
        IBrowserable browserable = webCore().createWebApp(webView);
        browsers.add(browserable);
        return browserable;
    }

    @Override
    public void removeBrowser(IBrowserable browser) {
        browsers.remove(browser);
    }

    @Override
    public void close() {
        browsers.clear();
    }
}
