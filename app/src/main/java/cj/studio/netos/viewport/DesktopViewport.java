package cj.studio.netos.viewport;

import android.app.Activity;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cj.studio.netos.R;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.util.ForbiddenNavigationAnimation;

public class DesktopViewport implements IViewport {
    public DesktopViewport() {
    }

    @Override
    public int layout() {
        return R.layout.layout_desktop_viewport;
    }

    @Override
    public BottomNavigationView setNavigation(INavigation navigation, Activity on) {
        BottomNavigationView view = on.findViewById(R.id.module_navigation);

        ForbiddenNavigationAnimation.disableShiftMode(view);
        view.setOnNavigationItemSelectedListener(navigation.onNavigationItemSelectedEvent(on));

        return view;
    }

    @Override
    public int displayViewId() {
        return R.id.desktop_display;
    }

    @Override
    public void setTitle(String title, Activity on) {
       Toolbar toolbar= on.findViewById(R.id.toolbar);
        if(toolbar!=null) {
            toolbar.setTitle(title);
            AppCompatActivity appon=(AppCompatActivity)on;
            appon.setSupportActionBar(toolbar);
        }
        CollapsingToolbarLayout toolbarLayout= on.findViewById(R.id.toolbar_layout);
        if(toolbarLayout!=null){
            toolbarLayout.setTitle(title);
        }
    }

    @Override
    public boolean isShowMenuIcon() {
        return true;
    }
}
