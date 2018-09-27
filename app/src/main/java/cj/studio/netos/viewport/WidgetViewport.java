package cj.studio.netos.viewport;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cj.studio.netos.R;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.view.CJBottomNavigationView;

public class WidgetViewport implements IViewport {
    public WidgetViewport() {
    }

    @Override
    public int layout() {
        return R.layout.viewport_widget;
    }

    @Override
    public int displayViewId() {
        return R.id.widget_display;
    }

    @Override
    public boolean isBottomNavigationViewVisibility() {
        return true;
    }

    @Override
    public CJBottomNavigationView setNavigation(INavigation navigation, Activity on) {
        CJBottomNavigationView view= on.findViewById(R.id.module_navigation);
        view.setOnCheckedChangeListener(navigation.onNavigationItemSelectedEvent(on));
        return view;
    }
    @Override
    public CJBottomNavigationView navigationView(Activity on) {
        CJBottomNavigationView view=on.findViewById(R.id.module_navigation);
        return view;
    }

    @Override
    public boolean isShowToolbarMenuIcon() {
        return true;
    }

    @Override
    public void setToolbarInfo(String title, boolean displayHomeAsUpEnabled, Activity on) {
        Toolbar toolbar= on.findViewById(R.id.widget_toolbar);
        if(toolbar!=null) {
            toolbar.setTitle(title);
            AppCompatActivity appon=(AppCompatActivity)on;
            appon.setSupportActionBar(toolbar);
            appon.getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
        }
    }
}
