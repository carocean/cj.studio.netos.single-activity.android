package cj.studio.netos.framework.isite.system;

import android.webkit.JavascriptInterface;

import cj.studio.netos.framework.isite.ISiteInfoFunctions;

/**
 * Created by caroceanjofers on 2018/3/20.
 */

public class SiteInfoFunctions implements ISiteInfoFunctions {
    String title;
    String name;
    String version;
    String company;
    String description;
    private String group;

    @JavascriptInterface
    @Override
    public String title() {
        return title;
    }
    @JavascriptInterface
    @Override
    public String name() {
        return name;
    }
    @JavascriptInterface
    @Override
    public String version() {
        return version;
    }
    @JavascriptInterface
    @Override
    public String group() {
        return group;
    }

    @JavascriptInterface
    @Override
    public String company() {
        return company;
    }
    @JavascriptInterface
    @Override
    public String description() {
        return description;
    }
    @JavascriptInterface
    @Override
    public void title(String title) {
        this.title=title;
    }
    @JavascriptInterface
    @Override
    public void name(String name) {
        this.name=name;
    }
    @JavascriptInterface
    @Override
    public void version(String version) {
        this.version=version;
    }
    @JavascriptInterface
    @Override
    public void group(String group) {
        this.group=group;
    }

    @JavascriptInterface
    @Override
    public void company(String company) {
        this.company=company;
    }
    @JavascriptInterface
    @Override
    public void description(String description) {
       this.description=description;
    }
}
