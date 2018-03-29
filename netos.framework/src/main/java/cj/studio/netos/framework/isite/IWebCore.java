package cj.studio.netos.framework.isite;

import android.webkit.WebView;

import java.util.List;

/**
 * Created by caroceanjofers on 2018/3/9.
 */

public interface IWebCore {
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
    void close();
}
