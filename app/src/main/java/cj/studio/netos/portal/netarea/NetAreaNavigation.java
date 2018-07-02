package cj.studio.netos.portal.netarea;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import cj.studio.netos.R;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.INetAreaNavigation;
import cj.studio.netos.framework.INetAreaSelection;
import cj.studio.netos.framework.IServiceProvider;

/**
 * 互动板、应用板、桌面、系统、应用窗口的导航
 * Created by caroceanjofers on 2018/2/26.
 */

public class NetAreaNavigation implements INetAreaNavigation {
    FragmentManager fm;
    private IServiceProvider site;

    public NetAreaNavigation(FragmentManager fm) {
        this.fm = fm;
    }

    @Override
    public void init(IServiceProvider provider) {
        this.site = provider;
    }

    @Override
    public void naviToDesktop() {
        INetAreaSelection selection=(INetAreaSelection)site.getService("$.netarea.selection");
        Fragment selectFrag=(Fragment) selection.selected();
        Fragment desktop = (Fragment) site.getService("$.netarea.desktop");
        //开启事务
        FragmentTransaction fragmentTransaction =
                fm.beginTransaction();
        //隐藏选中的模块
        if (selectFrag!=null&&selectFrag.isAdded()) {
            //如果这个fragment已经被事务添加,隐藏
            fragmentTransaction.hide(selectFrag);
        }
        //显示fragment
        if (desktop.isAdded()) {
            //如果这个fragment已经被事务添加,显示
            fragmentTransaction.show(desktop);
        } else {
            //如果这个fragment没有被事务添加过,添加
            fragmentTransaction.add(R.id.netarea_container, desktop);
        }

        selection.select((IModule)desktop);

        //提交事务
        fragmentTransaction.commit();
    }

    @Override
    public void naviToMutuboard() {

    }

    @Override
    public void naviToAppboard() {

    }

    @Override
    public void openProgram(String url) {

    }

    @Override
    public void naviToSiteService(String sid) {

    }
}
