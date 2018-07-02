package cj.studio.netos.framework;

import android.webkit.WebView;

import java.util.Set;

import cj.studio.netos.framework.isite.IWebsite;

/**
 * Created by caroceanjofers on 2018/2/25.
 */

public interface INetAreaProgramContainer extends IModule{
    /**
     * 创建浏览器
     * @param webView
     * @return
     */
    IWebsite createDesktop(WebView webView);

    /**
     * 创建web应用
     * @param webView
     * @return
     */
    IWebsite createWebApp(WebView webView);
    IWebsite createWebSite(WebView webView);
    void removeWebsite(IWebsite browser);
    void close();
    void removeWebsite(String name);
    IWebsite getWebsite(String name);
    Set<String> enunWebsiteKeys();
    IWebsite getDesktop();
}
