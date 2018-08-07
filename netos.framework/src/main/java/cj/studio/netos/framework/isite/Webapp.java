package cj.studio.netos.framework.isite;

import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.IServiceProvider;

/**
 * - 页面资源可以完全在本地，因此需要安装
 * - 可以远程服务:远程服务需要处理资源请求，因此实现起来较为复杂，第二版时再去实现吧
 * - app的本地路径：netarea/local/应用名/
 * - website缓存路径:netarea/网域名/website名/
 *
 * Created by caroceanjofers on 2018/3/19.
 */

public class Webapp extends Website implements IWebsite {

    public Webapp(WebView webView, IServiceProvider site) {
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

    @Override
    protected WebViewClient createWebViewClient() {
        return new BaseWebViewClient();
    }

    @Override
    protected WebChromeClient createWebChromeClient() {
        return new WebChromeClient();
    }

    @Override
    public void input(Frame frame, ICell cell) {
        super.input(frame, cell);
    }


    class BaseWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            //可过url协议以区分本地资源调用或远程调用
            return false;//当返回false时，则表示在当前的webview里打开
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.loadUrl("javascript:imports.title(document.title);");
            view.loadUrl("javascript:var arr = document.getElementsByName('$.isite.name');imports.name(arr.length>0?arr[0].title:'');delete arr;");
            view.loadUrl("javascript:var arr = document.getElementsByName('$.isite.company');imports.company(arr.length>0?arr[0].title:'');delete arr;");
            view.loadUrl("javascript:var arr = document.getElementsByName('$.isite.version');imports.version(arr.length>0?arr[0].title:'');delete arr;");
            view.loadUrl("javascript:var arr = document.getElementsByName('$.isite.group');$.imports.group(arr.length>0?arr[0].title:'');delete arr;");
            view.loadUrl("javascript:var arr = document.getElementsByName('$.isite.description');imports.description(arr.length>0?arr[0].title:'');delete arr;");
        }
    }

    class ChomeWebViewClient extends WebChromeClient {
        //当网页调用prompt()来弹出prompt弹出框前回调，用以拦截prompt()函数
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return false;
        }

        //当网页调用alert()来弹出alert弹出框前回调，用以拦截alert()函数
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return false;
        }

        //当网页调用confirm()来弹出confirm弹出框前回调，用以拦截confirm()函数
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return false;
        }

        //打印 console 信息
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }

        //通知程序当前页面加载进度
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

    }
}
