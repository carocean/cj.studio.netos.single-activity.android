package cj.studio.netos.portal;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cj.studio.netos.R;
import cj.studio.netos.framework.IDisplay;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.INetOSResource;
import cj.studio.netos.framework.ISelection;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.IToolbar;
import cj.studio.netos.framework.IWorkbench;

/**
 * Created by caroceanjofers on 2018/2/24.
 */

public class Navigation implements INavigation, BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView navui;
    private IServiceProvider provider;
    FragmentManager fm;
    Map<String, IModule> modules;

    public Navigation(BottomNavigationView navui, FragmentManager fm) {
        this.navui = navui;
        this.fm = fm;
    }

    @Override
    public void init(IServiceProvider provider, List<IModule> moduleList) {
        modules=new HashMap<>();
        this.provider = provider;
        navui.setOnNavigationItemSelectedListener(this);
        for (IModule m : moduleList) {
            modules.put(String.format("navigation_%s", m.name()), m);
        }

    }


    /**
     */
    @Override
    public void naviToModule(@NonNull MenuItem item) {
        IToolbar toolbar = (IToolbar) provider.getService("$.workbench.toolbar");
        IDisplay display = (IDisplay) provider.getService("$.workbench.display");
        ISelection selection = (ISelection) provider.getService("$.workbench.selection");
        INetOSResource resource = (INetOSResource) provider.getService("$.workbench.resource");
        IWorkbench wb = (IWorkbench) provider.getService(IWorkbench.class);

        if (modules == null) return;
        String id=resource.getNetOSAnyResourceName(item.getItemId());
        int pos=id.indexOf("/");
        if(pos>-1){
            id=id.substring(pos+1,id.length());
        }
        IModule module = modules.get(id);
        if (module == null) return;


        IModule selectedModule = null;

        if (!(module instanceof Fragment)) {
            return;
        }
        Fragment f = (Fragment) module;
        FragmentTransaction fragmentTransaction =
                fm.beginTransaction();
        try {
            if (selection.selectedModule() != null) {
                fragmentTransaction.hide((Fragment) selection.selectedModule());
            }
            if (f.isAdded()) {
                //如果这个fragment已经被事务添加,显示
                fragmentTransaction.show(f);
            } else {
                //如果这个fragment没有被事务添加过,添加
                fragmentTransaction.add(display.viewId(), f);
            }
//                fragmentTransaction.addToBackStack(null);
            //提交事务
            fragmentTransaction.commit();
            selectedModule = module;
        }catch (Exception e){
            Log.e("Navigation",e.getMessage());
        }

        if (selection != null) {
            if(selectedModule.name()=="desktop"){
                toolbar.setText(R.string.app_name);
            }else {
                toolbar.setText(item.getTitle());
            }
            String menuid=String.format("menu_%s",module.name());
            int selectedMenuId=resource.getIdentifier(menuid,"menu");
            selection.onselected(selectedModule,selectedMenuId);
            selection.refreshMenu();
        }


    }
    @Override
    public  void naviToDesktop(){
        IToolbar toolbar = (IToolbar) provider.getService("$.workbench.toolbar");
        IDisplay display = (IDisplay) provider.getService("$.workbench.display");
        ISelection selection = (ISelection) provider.getService("$.workbench.selection");
        INetOSResource resource = (INetOSResource) provider.getService("$.workbench.resource");
        IWorkbench wb = (IWorkbench) provider.getService(IWorkbench.class);

        //遍历集合
        if (modules == null) return;
        String id="navigation_desktop";
        IModule module = modules.get(id);
        if (module == null) return;
        FragmentTransaction fragmentTransaction =
                fm.beginTransaction();
        IModule selectedModule = null;

        if (!(module instanceof Fragment)) {
            return;
        }
        Fragment f = (Fragment) module;

        if(selection.selectedModule()!=null){
            fragmentTransaction.hide((Fragment)selection.selectedModule());
        }
        if (f.isAdded()) {
            //如果这个fragment已经被事务添加,显示
            fragmentTransaction.show(f);
        } else {
            //如果这个fragment没有被事务添加过,添加
            fragmentTransaction.add(display.viewId(), f);
        }
//                fragmentTransaction.addToBackStack(null);
        selectedModule = module;


        if (selection != null) {
            if(selectedModule.name()=="desktop"){
                toolbar.setText(R.string.app_name);
            }
            String menuid=String.format("menu_%s",module.name());
            int selectedMenuId=resource.getIdentifier(menuid,"menu");
            selection.onselected(selectedModule,selectedMenuId);
            selection.refreshMenu();
        }
        //提交事务
        fragmentTransaction.commit();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        try {
            naviToModule(item);
            return true;
        } catch (Exception e) {
            Log.e("Nav", e.getMessage());
            return false;
        }
    }
}
