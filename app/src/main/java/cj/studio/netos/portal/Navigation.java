package cj.studio.netos.portal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioGroup;

import java.util.List;

import cj.studio.netos.R;
import cj.studio.netos.framework.IContainer;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.INetOSResource;
import cj.studio.netos.framework.ISelection;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.ITitlebar;
import cj.studio.netos.framework.IWorkbench;

/**
 * Created by caroceanjofers on 2018/2/24.
 */

public class Navigation implements INavigation, RadioGroup.OnCheckedChangeListener {
    RadioGroup navui;
    private IServiceProvider provider;
    FragmentManager fm;
    List<IModule> modules;

    public Navigation(RadioGroup navui, FragmentManager fm) {
        this.navui = navui;
        this.fm = fm;
    }

    @Override
    public void init(IServiceProvider provider, List<IModule> moduleList) {
        this.provider = provider;
        navui.setOnCheckedChangeListener(this);
        this.modules = moduleList;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.radio_msg://
                naviToModule("message");
                break;
            case R.id.radio_geoarea://
                naviToModule("geoarea");
                break;
            case R.id.radio_netflow://
                naviToModule("netflow");
                break;
            case R.id.radio_netos://
                naviToModule("netos");
                break;
            case R.id.radio_mine://
                naviToModule("mine");
                break;
        }

    }

    /**
     */
    @Override
    public void naviToModule(String mod_name) {
        ITitlebar tb = (ITitlebar) provider.getService("$.workbench.titlebar");
        IContainer container = (IContainer) provider.getService("$.workbench.container");
        ISelection selection = (ISelection) provider.getService("$.workbench.selection");
        INetOSResource resource = (INetOSResource) provider.getService("$.workbench.resource");
        IWorkbench wb = (IWorkbench) provider.getService(IWorkbench.class);

        //遍历集合
        if (modules == null) return;

        FragmentTransaction fragmentTransaction =
                fm.beginTransaction();
        IModule selected = null;
        for (IModule module : modules) {
            if(!(module instanceof Fragment)){
                continue;
            }
            Fragment f = (Fragment) module;

            if (mod_name.equals(module.name())) {
                if (f.isAdded()) {
                    //如果这个fragment已经被事务添加,显示
                    fragmentTransaction.show(f);
                } else {
                    //如果这个fragment没有被事务添加过,添加
                    fragmentTransaction.add(container.viewId(), f);
                }
//                fragmentTransaction.addToBackStack(null);
                selected = module;
            } else {
                //隐藏fragment
                if (f.isAdded()) {
                    //如果这个fragment已经被事务添加,隐藏
                    fragmentTransaction.hide(f);

                }
            }
        }

        if (selected != null) {
            if(selection!=null){
                String text=resource.getNetOSString(selected.cnameId());
                tb.setText(text);
                selection.onselected(selected);
            }
        }
        //提交事务
        fragmentTransaction.commit();


    }
}
