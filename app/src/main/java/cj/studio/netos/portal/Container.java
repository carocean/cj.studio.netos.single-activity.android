package cj.studio.netos.portal;

import android.widget.FrameLayout;

import cj.studio.netos.R;
import cj.studio.netos.framework.IContainer;

/**
 * Created by caroceanjofers on 2018/2/24.
 */

public class Container implements IContainer {
    FrameLayout containerui;
    public Container(FrameLayout containerui) {
        this.containerui=containerui;
    }

    @Override
    public int viewId() {
        return R.id.container;
    }
}
