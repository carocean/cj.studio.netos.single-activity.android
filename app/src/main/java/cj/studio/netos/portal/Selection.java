package cj.studio.netos.portal;

import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.IRefreshMenuCallback;
import cj.studio.netos.framework.ISelection;

/**
 * Created by caroceanjofers on 2018/2/24.
 */

public class Selection implements ISelection {
    ICell cell;
    IModule selectedModule;
    int selectedMenu=-1;
    IRefreshMenuCallback refreshMenu;
    public Selection(ICell cell) {
        this.cell=cell;
    }

    @Override
    public void onRefreshMenu(IRefreshMenuCallback refreshMenu) {
        this.refreshMenu=refreshMenu;
    }

    @Override
    public void onselected(IModule selectedModule, int selectedMenu) {
        this.selectedModule =selectedModule;
        this.selectedMenu=selectedMenu;
    }
    @Override
    public IModule selectedModule() {
        return selectedModule;
    }

    @Override
    public void refreshMenu() {
        refreshMenu.refreshMenu(this);
    }

    @Override
    public int selectedMenu() {
        return selectedMenu;
    }
}
