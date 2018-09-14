package cj.studio.netos.module.desktop.region;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cj.studio.netos.R;
import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.thirty.BottomNavigationViewEx;
import cj.studio.netos.module.desktop.IDesktopRegion;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsRegion extends Fragment implements IDesktopRegion {


    public NewsRegion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.region_news, container, false);
    }

    @Override
    public void input(Frame frame, IServiceProvider site) {

    }

    @Override
    public String name() {
        return "news";
    }

    @Override
    public void renderTo(IViewport viewport, Activity on, IServiceProvider site) {
        viewport.setToolbarInfo("新闻",false,on);
    }

    @Override
    public boolean onToolbarMenuInstall(MenuInflater menuInflater, Menu menu) {
        return false;
    }

    @Override
    public boolean onToolbarMenuSelected(MenuItem item, IServiceProvider site) {
        return false;
    }

    @Override
    public BottomNavigationView.OnNavigationItemSelectedListener onResetNavigationMenu(BottomNavigationViewEx bottomNavigationView,final Activity on) {
        bottomNavigationView.clearMenu();
        bottomNavigationView.getMenu().add("test");
        return null;
    }

    @Override
    public boolean isBottomNavigationViewVisibility() {
        return true;
    }
}
