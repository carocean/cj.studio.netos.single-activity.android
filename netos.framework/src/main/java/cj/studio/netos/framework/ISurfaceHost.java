package cj.studio.netos.framework;

import android.app.Activity;

public interface ISurfaceHost {
    void bindUI(Activity activity);

    void addViewport(IViewport viewport);

    void showWindow(IWindow window);

    IViewport viewport(int viewport);

    Object owner();
}
