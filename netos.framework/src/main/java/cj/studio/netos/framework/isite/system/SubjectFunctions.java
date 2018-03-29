package cj.studio.netos.framework.isite.system;

import android.webkit.JavascriptInterface;

import cj.studio.netos.framework.isite.ISubjectFunctions;

/**
 * Created by caroceanjofers on 2018/3/12.
 */

public  class SubjectFunctions extends Object implements ISubjectFunctions {
    String principal;
    String nick;
    String faceBase64;
    String roles;
    @JavascriptInterface
    @Override
    public String principal() {
        return principal;
    }
    public void principal(String principal) {
        this.principal= principal;
    }
    @JavascriptInterface
    @Override
    public String nick() {
        return nick;
    }
    @JavascriptInterface
    @Override
    public String faceBase64() {
        return faceBase64;
    }
    @JavascriptInterface
    @Override
    public String roles() {
        return roles;
    }
}
