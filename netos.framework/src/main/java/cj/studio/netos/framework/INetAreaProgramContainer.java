package cj.studio.netos.framework;

import android.webkit.WebView;

import cj.studio.netos.framework.isite.IBrowserable;

/**
 * Created by caroceanjofers on 2018/2/25.
 */

public interface INetAreaProgramContainer extends IModule{
    /**
     * 创建浏览器
     * @param webView
     * @return
     */
    IBrowserable createBrowser(WebView webView);
    /**
     * 创建网站
     * @param webView
     * @return
     */
    IBrowserable createWebSite(WebView webView);
    /**
     * 创建web应用
     * @param webView
     * @return
     */
    IBrowserable createWebApp(WebView webView);
    void removeBrowser(IBrowserable browser);
    void close();
}
