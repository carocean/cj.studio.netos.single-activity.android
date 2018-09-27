package cj.studio.netos.framework;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cj.studio.netos.framework.view.CJBottomNavigationView;

public interface IDesktopRegion  {
    void input(Frame frame, IServiceProvider site);
    String name();
    void onViewport(IViewport viewport, Activity on, IServiceProvider site);

    boolean onToolbarMenuInstall(MenuInflater menuInflater, Menu menu);

    boolean onToolbarMenuSelected(MenuItem item, IServiceProvider site);
    CJBottomNavigationView.OnCheckedChangeListener onResetNavigationMenu(CJBottomNavigationView bottomNavigationView, Activity on);

    boolean isBottomNavigationViewVisibility();
    IWidget widget(String navigateable);
}
