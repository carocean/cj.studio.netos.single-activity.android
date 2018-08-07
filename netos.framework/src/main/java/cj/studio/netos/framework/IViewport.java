package cj.studio.netos.framework;

import android.app.Activity;
import android.support.design.widget.BottomNavigationView;
import android.view.View;

/**
 * 视口是与设备相关的一个矩形区域<br>
 * 也就是显示模板
 */
public interface IViewport {
    int layout();

    BottomNavigationView setNavigation(INavigation navigation, Activity on);

    void setTitle(String title, Activity on);

    boolean isShowMenuIcon();

    int displayViewId();
}
