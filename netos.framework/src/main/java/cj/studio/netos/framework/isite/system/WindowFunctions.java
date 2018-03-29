package cj.studio.netos.framework.isite.system;

import android.util.Log;
import android.webkit.JavascriptInterface;

import cj.studio.netos.framework.isite.ISiteInfoFunctions;
import cj.studio.netos.framework.isite.ISensorFunctions;
import cj.studio.netos.framework.isite.ISubjectFunctions;
import cj.studio.netos.framework.isite.IWindowFunctions;

/**
 * Created by caroceanjofers on 2018/3/9.
 */

public class WindowFunctions extends Object implements IWindowFunctions {

    private final ISensorFunctions sensors;
    private final ISubjectFunctions subject;
    public WindowFunctions(SubjectFunctions subject, ISensorFunctions sensors) {
        this.subject=subject;
        this.sensors=sensors;
    }

    @JavascriptInterface
    @Override
    public ISubjectFunctions subject() {
        return subject;
    }
    @JavascriptInterface
    @Override
    public ISensorFunctions sensors() {
        return sensors;
    }
    @JavascriptInterface
    @Override
    public Object require(String jspackage) {
        return null;
    }
    @JavascriptInterface
    @Override
    public void print(String v){
        Log.i("netos",v);
    }

}
