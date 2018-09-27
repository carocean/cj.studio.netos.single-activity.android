package cj.studio.netos.module.desktop;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import cj.studio.netos.framework.IDesktopRegion;
import cj.studio.netos.framework.IHistory;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.IRegionSelection;
import cj.studio.netos.framework.ISelection;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.ISurfaceHost;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.Memento;
import cj.studio.netos.framework.view.CJBottomNavigationView;

public class RegionNavigation implements INavigation {
    FragmentManager manager;
    int desktop_region;
    IRegionSelection selection;
    IViewport viewport;
    IServiceProvider site;
    Activity on;
    public RegionNavigation(IViewport viewport, IServiceProvider site,  int desktop_region) {
        this.desktop_region=desktop_region;
        this.selection=site.getService("$.region.selection");
        this.manager=site.getService(FragmentManager.class);
        this.viewport=viewport;
        this.site=site;
       ISurfaceHost host= site.getService("$.workbench.host");
       this.on=(Activity)host.owner();
    }

    @Override
    public boolean navigate(String navigateable) {
        IDesktopRegion region=site.getService(String.format("$.region.nameis.%s",navigateable));
        if(region==null){
            return false;
        }
        ISelection sel=site.getService("$.workbench.selection");


        CJBottomNavigationView bottomNavigationView=viewport.navigationView(on);
        CJBottomNavigationView.OnCheckedChangeListener listene=region.onResetNavigationMenu(bottomNavigationView,on);
//        if(listene!=null) {//注掉，不论listene是不是null都设置，就把之前的bottomNavigationView事件给冲掉
            bottomNavigationView.setOnCheckedChangeListener(listene);
//        }
        if(region.isBottomNavigationViewVisibility()){
            bottomNavigationView.setVisibility(View.VISIBLE);
        }else{
            bottomNavigationView.setVisibility(View.GONE);
        }

        region.onViewport(viewport,on,site);
        on.invalidateOptionsMenu();

        switchFragment(desktop_region,region);
        sel.setCurrentViewport(viewport);

        IHistory history = site.getService("$.workbench.history");
        if("messager".equals(navigateable)){
            history.clear();
        }else {
            if (selection.selectRegion() != null) {
                Memento memento = new Memento(String.format("/desktop/%s.region", selection.selectRegion().name()));
                history.redo(memento);
            }
        }

        selection.setSelectRegion(region);
        sel.setSelectedRegion(region);
        return true;
    }
    private void switchFragment(int containerViewId, IDesktopRegion region) {
        Fragment fragment = (Fragment) region;
        FragmentTransaction transaction = manager.beginTransaction();
        try {
//            Fragment prev = (Fragment) selection.selectRegion();
//            if (prev != null) {
//                transaction.hide(prev);
//            }
//            if (fragment.isAdded()) {
//                if(fragment.isHidden()) {
//                    transaction.show(fragment);
//                }
//            } else {
//                transaction.add(containerViewId, fragment).show(fragment);
//            }
            //使fragment每次都执行oncreateView
            transaction.replace(containerViewId,fragment).show(fragment);
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            Log.e("Host", e.getMessage());
        }
    }
    @Override
    public CJBottomNavigationView.OnCheckedChangeListener onNavigationItemSelectedEvent(Activity on) {
        return null;
    }

}
