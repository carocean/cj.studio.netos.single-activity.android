package cj.studio.netos.module.desktop;

import android.app.Activity;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.thirty.BottomNavigationViewEx;

public class RegionNavigation implements INavigation {
    FragmentManager manager;
    int desktop_region;
    IRegionSelection selection;
    IViewport viewport;
    IServiceProvider site;
    Activity on;
    public RegionNavigation(IViewport viewport, IServiceProvider site, Activity on, int desktop_region) {
        this.desktop_region=desktop_region;
        this.selection=site.getService("$.region.selection");
        this.manager=site.getService(FragmentManager.class);
        this.viewport=viewport;
        this.site=site;
        this.on=on;
    }

    @Override
    public boolean navigate(String navigateable) {
        IDesktopRegion region=site.getService(String.format("$.region.nameis.%s",navigateable));
        if(region==null){
            return false;
        }
        BottomNavigationViewEx bottomNavigationView=viewport.navigationView(on);
        BottomNavigationView.OnNavigationItemSelectedListener listene=region.onResetNavigationMenu(bottomNavigationView,on);
//        if(listene!=null) {//注掉，不论listene是不是null都设置，就把之前的bottomNavigationView事件给冲掉
            bottomNavigationView.setOnNavigationItemSelectedListener(listene);
//        }
        if(region.isBottomNavigationViewVisibility()){
            bottomNavigationView.setVisibility(View.VISIBLE);
        }else{
            bottomNavigationView.setVisibility(View.GONE);
        }

        region.renderTo(viewport,on,site);
        on.invalidateOptionsMenu();
        switchFragment(desktop_region,region,selection);
        selection.setSelectRegion(region);
        return true;
    }
    private void switchFragment(int containerViewId, IDesktopRegion region, IRegionSelection selection) {
        Fragment fragment = (Fragment) region;
        FragmentTransaction transaction = manager.beginTransaction();
        try {
            Fragment prev = (Fragment) selection.selectRegion();
            if (prev != null) {
                transaction.hide(prev);
            }
            if (fragment.isAdded()) {
                transaction.show(fragment);
            } else {
                transaction.add(containerViewId, fragment).show(fragment);
            }
            transaction.commit();
        } catch (Exception e) {
            Log.e("Host", e.getMessage());
        }
    }
    @Override
    public BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedEvent(Activity on) {
        return null;
    }
}
