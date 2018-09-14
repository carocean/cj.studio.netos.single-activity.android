package cj.studio.netos.viewport;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cj.studio.netos.R;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.thirty.BottomNavigationViewEx;
import cj.studio.netos.framework.util.ForbiddenNavigationAnimation;

public class ModuleViewport implements IViewport {
    public ModuleViewport() {
    }

    @Override
    public int layout() {
        return R.layout.viewport_module;
    }

    @Override
    public int displayViewId() {
        return R.id.module_display;
    }

    @Override
    public boolean isBottomNavigationViewVisibility() {
        return true;
    }

    @Override
    public BottomNavigationViewEx setNavigation(INavigation navigation, Activity on) {
        BottomNavigationViewEx view= on.findViewById(R.id.module_navigation);
        ForbiddenNavigationAnimation.disableShiftMode(view);
        view.setOnNavigationItemSelectedListener(navigation.onNavigationItemSelectedEvent(on));
        return view;
    }
    @Override
    public BottomNavigationViewEx navigationView(Activity on) {
        BottomNavigationViewEx view=on.findViewById(R.id.module_navigation);
        return view;
    }

    @Override
    public boolean isShowToolbarMenuIcon() {
        return true;
    }

    @Override
    public void setToolbarInfo(String title, boolean displayHomeAsUpEnabled, Activity on) {
        Toolbar toolbar= on.findViewById(R.id.module_toolbar);
        if(toolbar!=null) {
            toolbar.setTitle(title);
            AppCompatActivity appon=(AppCompatActivity)on;
            appon.setSupportActionBar(toolbar);
            appon.getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
        }
    }
}
