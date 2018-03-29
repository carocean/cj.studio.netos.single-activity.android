package cj.studio.netos.portal;

import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.ISelection;

/**
 * Created by caroceanjofers on 2018/2/24.
 */

public class Selection implements ISelection {
    ICell cell;
    IModule selected;
    public Selection(ICell cell) {
        this.cell=cell;
    }

    @Override
    public void onselected(IModule selected) {
        this.selected=selected;
    }
    @Override
    public IModule selected() {
        return selected;
    }
}
