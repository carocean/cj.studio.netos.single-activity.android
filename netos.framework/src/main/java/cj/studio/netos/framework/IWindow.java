package cj.studio.netos.framework;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public interface IWindow {
    String name();

    int viewport();

    void renderTo(IViewport viewport, Activity on, ICell cell);

    boolean onToolbarMenuInstall(MenuInflater menuInflater, Menu menu);

    boolean onToolbarMenuSelected(MenuItem item, ICell cell);

    boolean isBottomNavigationViewVisibility();
}
