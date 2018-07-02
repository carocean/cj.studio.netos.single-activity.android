package cj.studio.netos.framework;

import android.app.Activity;

/**
 * Created by caroceanjofers on 2018/2/6.
 */

public interface ISelection {
    void onRefreshMenu(IRefreshMenuCallback refreshMenu);
    void onselected(IModule selected,int selectedMenu);
    int selectedMenu();
    IModule selectedModule();
    void refreshMenu();
}
