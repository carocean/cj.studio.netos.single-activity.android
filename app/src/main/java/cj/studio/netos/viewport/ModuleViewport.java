package cj.studio.netos.viewport;

import android.app.Activity;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cj.studio.netos.R;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.util.ForbiddenNavigationAnimation;

public class ModuleViewport implements IViewport {
    BottomNavigationView view;
    public ModuleViewport() {
    }

    @Override
    public int layout() {
        return R.layout.layout_module_viewport;
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
    public BottomNavigationView setNavigation(INavigation navigation, Activity on) {
        BottomNavigationView view= on.findViewById(R.id.module_navigation);
        ForbiddenNavigationAnimation.disableShiftMode(view);
        view.setOnNavigationItemSelectedListener(navigation.onNavigationItemSelectedEvent(on));
        this.view=view;
        return view;
    }
    @Override
    public BottomNavigationView navigationView() {
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
