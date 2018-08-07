package cj.studio.netos.framework;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import java.util.List;

/**
 * Created by caroceanjofers on 2018/2/3.
 */

public interface INavigation {
    void navigate(String navigateable);

    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedEvent(Activity on);
}
