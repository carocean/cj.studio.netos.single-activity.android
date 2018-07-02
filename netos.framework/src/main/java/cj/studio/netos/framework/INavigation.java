package cj.studio.netos.framework;

import android.support.annotation.NonNull;
import android.view.MenuItem;

import java.util.List;

/**
 * Created by caroceanjofers on 2018/2/3.
 */

public interface INavigation {
    void init(IServiceProvider provider, List<IModule> moduleList);
    void naviToModule(@NonNull MenuItem item);
    void naviToDesktop();
}
