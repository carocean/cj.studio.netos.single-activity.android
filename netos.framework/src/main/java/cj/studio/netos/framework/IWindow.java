package cj.studio.netos.framework;

import android.app.Activity;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cj.studio.netos.framework.thirty.BottomNavigationViewEx;

public interface IWindow {
    String name();

    int viewport();

    void renderTo(IViewport viewport, Activity on, IServiceProvider site);

    boolean onToolbarMenuInstall(MenuInflater menuInflater, Menu menu);

    boolean onToolbarMenuSelected(MenuItem item, IServiceProvider site);
    BottomNavigationView.OnNavigationItemSelectedListener onResetNavigationMenu(BottomNavigationViewEx bottomNavigationView, Activity on);

    boolean isBottomNavigationViewVisibility();
}
