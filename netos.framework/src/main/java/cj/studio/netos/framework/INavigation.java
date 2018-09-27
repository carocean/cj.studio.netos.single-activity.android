package cj.studio.netos.framework;

import android.app.Activity;

import cj.studio.netos.framework.view.CJBottomNavigationView;

/**
 * Created by caroceanjofers on 2018/2/3.
 */

public interface INavigation {
    boolean navigate(String navigateable);

    CJBottomNavigationView.OnCheckedChangeListener onNavigationItemSelectedEvent(Activity on);
}
