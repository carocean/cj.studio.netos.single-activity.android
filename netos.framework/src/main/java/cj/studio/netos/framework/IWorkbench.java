package cj.studio.netos.framework;

import android.app.Activity;
import android.webkit.WebView;

/**
 * Created by caroceanjofers on 2018/1/18.
 */

public interface IWorkbench extends ICell {
    void renderTo(INetOSPortal portal);
    WebView createWebView(Activity parent);

    //js与java交互：http://blog.csdn.net/carson_ho/article/details/64904691
    WebView createWebview();
    IDendrite createDendrite(ISynapsis synapsis);

    void addModule(IModule module);

    void removeModule(String name);

    IModule getModule(String name);

    boolean containsModule(String name);
    void addService(String name, Object service);
    void removeService(String name);


}
