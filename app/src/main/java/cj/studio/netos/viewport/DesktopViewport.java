package cj.studio.netos.viewport;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cj.studio.netos.R;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.view.CJBottomNavigationView;

public class DesktopViewport implements IViewport {
    public DesktopViewport() {
    }

    @Override
    public int layout() {
        return R.layout.viewport_desktop;
    }

    @Override
    public CJBottomNavigationView setNavigation(INavigation navigation, Activity on) {
        CJBottomNavigationView view = on.findViewById(R.id.module_navigation);
        if(view==null)return null;
        view.setOnCheckedChangeListener(navigation.onNavigationItemSelectedEvent(on));
        return view;
    }
    @Override
    public CJBottomNavigationView navigationView( Activity on) {

        return on.findViewById(R.id.module_navigation);
    }

    @Override
    public boolean isBottomNavigationViewVisibility() {
        return true;
    }

    @Override
    public int displayViewId() {
        return R.id.desktop_display;
    }

    @Override
    public void setToolbarInfo(String title, boolean displayHomeAsUpEnabled, Activity on) {
       Toolbar toolbar= on.findViewById(R.id.toolbar);
        if(toolbar!=null) {
            toolbar.setTitle(title);
            AppCompatActivity appon=(AppCompatActivity)on;
            appon.setSupportActionBar(toolbar);
            appon.getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
        }
        CollapsingToolbarLayout toolbarLayout= on.findViewById(R.id.toolbar_layout);
        if(toolbarLayout!=null){
            toolbarLayout.setTitle(title);
        }
    }

    @Override
    public boolean isShowToolbarMenuIcon() {
        return true;
    }
}
