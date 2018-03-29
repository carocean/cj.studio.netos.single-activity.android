package cj.studio.netos.framework.isite;

import android.webkit.WebView;

import cj.studio.netos.framework.IServiceProvider;

/**
 * Created by caroceanjofers on 2018/3/19.
 */

public class WebApp extends Browser implements IBrowserable {

    public WebApp(WebView webView, IServiceProvider site) {
        super(webView, site);
    }

    @Override
    protected void initSettings(WebView webView) {
        super.initSettings(webView);
    }

    @Override
    protected void injectService(IServiceProvider site,WebView webView) {
        super.injectService(site,webView);
    }
}
