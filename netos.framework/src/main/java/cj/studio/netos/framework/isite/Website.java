package cj.studio.netos.framework.isite;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Set;

import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.IWidget;
import cj.studio.netos.framework.isite.system.SiteInfoFunctions;
import cj.studio.netos.framework.view.CJBottomNavigationView;

/**
 * - 页面和资源在云端
 * - 可以远程服务:远程服务需要处理资源请求，因此实现起来较为复杂，第二版时再去实现吧
 * - website缓存路径:netarea/网域名/website名/
 * Created by caroceanjofers on 2018/3/10.
 */
/*
- 请求网站的门户展区：http://sitename/display
 */
class Website implements IWebsite {
    private WebView webView;
    private IServiceProvider site;
    ISiteInfoFunctions browserinfo;

    public Website(WebView webView, IServiceProvider site) {
        this.webView = webView;
        this.site = site;
        init();
    }

    @Override
    public CJBottomNavigationView.OnCheckedChangeListener onResetNavigationMenu(CJBottomNavigationView bottomNavigationView, Activity on) {
        return null;
    }

    @Override
    public boolean isBottomNavigationViewVisibility() {
        return true;
    }

    private void init() {
        initSettings(webView);
        // WebView.setWebViewClient(new WebViewClient(){
        // 拦截webview的各类事件，比如做网页进度条:onPageStarted会在WebView开始加载网页时调用，onPageFinished会在加载结束时调用
        //http://blog.csdn.net/harvic880925/article/details/51523983
        //shouldOverrideUrlLoading拦截 url 跳转,在里边添加点击链接跳转或者操作，实现保持在同一窗口内打开
        webView.setWebViewClient(createWebViewClient());
        webView.setWebChromeClient(createWebChromeClient());

        browserinfo = new SiteInfoFunctions();
        injectService(site, webView);
    }

    public ISiteInfoFunctions getBrowserinfo() {
        return browserinfo;
    }

    protected WebViewClient createWebViewClient() {
        return new BaseWebViewClient();
    }

    protected WebChromeClient createWebChromeClient() {
        return new ChomeWebViewClient();
    }

    protected void initSettings(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLightTouchEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
//        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置充许js弹窗,然后在ChomeWebViewClient拦截

    }

    @SuppressLint("JavascriptInterface")
    protected void injectService(IServiceProvider site, WebView webView) {
        Set<String> set = (Set<String>) site.getService("$.system.names");
        for (String name : set) {
            Object service = site.getService(name);
            if (service == null) continue;
            //这些服务必须添加注解方法：@addJavascriptInterface 该方法在17级别以上才有，因此17级以下的使用第三方控件：http://blog.csdn.net/qq_31715429/article/details/50972559
            //17以前的版本占有率很低，可以先不考虑支持
            webView.addJavascriptInterface(service, name);
        }
        webView.addJavascriptInterface(browserinfo, "imports");
    }

    @Override
    public String name() {
        return browserinfo.name();
    }

    @Override
    public int viewport() {
        return 0;
    }

    @Override
    public void onViewport(IViewport viewport, Activity on, IServiceProvider site) {

    }

    @Override
    public boolean onToolbarMenuInstall(MenuInflater menuInflater, Menu menu) {
        return false;
    }

    @Override
    public boolean onToolbarMenuSelected(MenuItem item, IServiceProvider site) {
        return false;
    }


    @Override
    public void input(Frame frame, IServiceProvider site) {
        //输出到js中
        String json=frame.toJson();
//        webView.loadData();
        webView.loadUrl(String.format("javascript:(function(){var json=%s;var jobj=eval(json);$.input(jobj);})()",json));
    }

    @Override
    public IWidget widget(String navigateable) {
        return null;
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
