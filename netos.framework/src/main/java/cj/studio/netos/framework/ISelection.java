package cj.studio.netos.framework;

import android.app.Activity;

/**
 * Created by caroceanjofers on 2018/2/6.
 */

public interface ISelection {
    IModule selectedModule();

    void setSelectedModule(IModule selectedModule);
}
