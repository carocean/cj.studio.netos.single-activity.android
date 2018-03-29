package cj.studio.netos.portal.netarea;

import android.support.v4.app.Fragment;

import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.INetAreaSelection;

/**
 * Created by caroceanjofers on 2018/2/27.
 */

public class NetAreaSelection implements INetAreaSelection {
    IModule selected;
    @Override
    public IModule selected() {
        return selected;
    }

    @Override
    public void select(IModule selected) {
       this. selected=selected;
    }
}
