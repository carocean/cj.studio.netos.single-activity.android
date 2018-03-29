package cj.studio.netos.framework.isite;

import android.annotation.SuppressLint;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Map;
import java.util.Set;

import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.isite.system.SiteInfoFunctions;

/**
 * Created by caroceanjofers on 2018/3/10.
 */

class Browser implements IBrowserable {
    private WebView webView;
    private IServiceProvider site;
    ISiteInfoFunctions browserinfo;
    public Browser(WebView webView, IServiceProvider site) {
        this.webView = webView;
        this.site = site;
        init();
    }


    private void init() {
        initSettings(webView);
        // WebView.setWebViewClient(new WebViewClient(){
        // 拦截webview的各类事件，比如做网页进度条:onPageStarted会在WebView开始加载网页时调用，onPageFinished会在加载结束时调用
        //http://blog.csdn.net/harvic880925/article/details/51523983
        //shouldOverrideUrlLoading拦截 url 跳转,在里边添加点击链接跳转或者操作，实现保持在同一窗口内打开
        webView.setWebViewClient(createWebViewClient());
        webView.setWebChromeClient(createWebChromeClient());

        injectService(site,webView);
    }
    protected WebViewClient createWebViewClient(){
        return new BaseWebViewClient();
    }
    protected WebChromeClient createWebChromeClient(){
        return new ChomeWebViewClient();
    }
    protected void initSettings(WebView webView){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLightTouchEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
//        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置充许js弹窗,然后在ChomeWebViewClient拦截
    }
    @SuppressLint("JavascriptInterface")
    protected void injectService(IServiceProvider site,WebView webView){
        Set<String> set = (Set<String>) site.getService("$.system.names");
        for (String name : set) {
            Object service = site.getService(name);
            if (service == null) continue;
            //这些服务必须添加注解方法：@addJavascriptInterface 该方法在17级别以上才有，因此17级以下的使用第三方控件：http://blog.csdn.net/qq_31715429/article/details/50972559
            //17以前的版本占有率很低，可以先不考虑支持
            webView.addJavascriptInterface(service, name);
        }
//        displayer.evaluateJavascript();
        browserinfo=new SiteInfoFunctions();
        webView.addJavascriptInterface(browserinfo, "$");
    }
    @Override
    public void close() {
        webView.destroy();
    }

    @Override
    public final WebView displayer() {
        return webView;
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
            view.loadUrl("javascript:$.isite().title(document.title);");
            view.loadUrl("javascript:var arr = document.getElementsByName('$.isite.name');$.isite().name(arr.length>0?arr[0].title:'');delete arr;");
            view.loadUrl("javascript:var arr = document.getElementsByName('$.isite.company');$.isite().name(arr.length>0?arr[0].title:'');delete arr;");
            view.loadUrl("javascript:var arr = document.getElementsByName('$.isite.version');$.isite().name(arr.length>0?arr[0].title:'');delete arr;");
            view.loadUrl("javascript:var arr = document.getElementsByName('$.isite.group');$.isite().name(arr.length>0?arr[0].title:'');delete arr;");
            view.loadUrl("javascript:var arr = document.getElementsByName('$.isite.description');$.isite().name(arr.length>0?arr[0].title:'');delete arr;");
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
