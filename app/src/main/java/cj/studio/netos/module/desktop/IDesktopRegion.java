package cj.studio.netos.module.desktop;

import android.app.Activity;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.thirty.BottomNavigationViewEx;

public interface IDesktopRegion  {
    void input(Frame frame, IServiceProvider site);
    String name();
    void renderTo(IViewport viewport, Activity on, IServiceProvider site);

    boolean onToolbarMenuInstall(MenuInflater menuInflater, Menu menu);

    boolean onToolbarMenuSelected(MenuItem item, IServiceProvider site);
    BottomNavigationView.OnNavigationItemSelectedListener onResetNavigationMenu(BottomNavigationViewEx bottomNavigationView, Activity on);

    boolean isBottomNavigationViewVisibility();
}
