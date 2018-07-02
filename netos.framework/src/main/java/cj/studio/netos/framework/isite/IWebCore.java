package cj.studio.netos.framework.isite;

import android.webkit.WebView;

/**
 * Created by caroceanjofers on 2018/3/9.
 */

public interface IWebCore {
    /**
     * 创建浏览器
     * @param webView
     * @return
     */
    IWebsite createWebsite(WebView webView);

    /**
     * 创建web应用
     * @param webView
     * @return
     */
    IWebsite createWebApp(WebView webView);
    void close();
}
