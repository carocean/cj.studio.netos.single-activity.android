package cj.studio.netos.framework;

import android.app.Activity;

import cj.studio.netos.framework.view.CJBottomNavigationView;

/**
 * 视口是与设备相关的一个矩形区域<br>
 * 也就是显示模板
 */
public interface IViewport {
    int layout();

    CJBottomNavigationView setNavigation(INavigation navigation, Activity on);

    void setToolbarInfo(String title, boolean displayHomeAsUpEnabled, Activity on);

    boolean isShowToolbarMenuIcon();

    int displayViewId();

    CJBottomNavigationView navigationView(Activity on);
    boolean isBottomNavigationViewVisibility();
}
