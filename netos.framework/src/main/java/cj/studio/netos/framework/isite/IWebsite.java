package cj.studio.netos.framework.isite;

import android.webkit.WebView;

import cj.studio.netos.framework.IModule;

/**
 * Created by caroceanjofers on 2018/3/9.
 */

public interface IWebsite extends IModule{
    ISiteInfoFunctions getBrowserinfo();
    void close();
    WebView displayer();
}
