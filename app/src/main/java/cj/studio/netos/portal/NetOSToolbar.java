package cj.studio.netos.portal;


import android.support.v7.widget.Toolbar;

import cj.studio.netos.framework.IToolbar;

/**
 * Created by caroceanjofers on 2018/2/24.
 */

public class NetOSToolbar implements IToolbar {
    Toolbar toolbar;
    public NetOSToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    @Override
    public void setText(int text) {
        toolbar.setTitle(text);
    }

    @Override
    public void setText(CharSequence text) {
        toolbar.setTitle(text);
    }
}
