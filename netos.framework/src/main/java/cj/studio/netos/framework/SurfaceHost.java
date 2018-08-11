package cj.studio.netos.framework;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import cj.studio.netos.framework.util.WindowStatusManager;

public class SurfaceHost implements ISurfaceHost {
    Activity activity;
    IServiceProvider site;
    Map<Integer, IViewport> viewports;

    public SurfaceHost(IServiceProvider site) {
        this.site = site;
        viewports = new HashMap<>();
    }

    @Override
    public void bindUI(Activity activity) {
        this.activity = activity;
        WindowStatusManager.seat(activity);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
    }

    @Override
    public void addViewport(IViewport viewport) {
        viewports.put(viewport.layout(), viewport);
    }

    @Override
    public IViewport viewport(int viewport) {
        return viewports.get(viewport);
    }

    @Override
    public void showWindow(IWindow window) {
        if (!(window instanceof Fragment)) {
            Log.e("host", String.format("window:%s 不是Fragment", window.name()));
            return;
        }
        ISelection selection = site.getService("$.workbench.selection");
        int layout = window.viewport();

        IViewport viewport = viewports.get(layout);
        if (viewport == null) {
            Log.e("host", "模块的viewport为空:" + window.name());
            return;
        }
        //在actitity更换布局会导致先前添加的fragment无法显示，这发生在再切换回来时fragment将会一直是hide状态，且无论如何也show不出来,因此只能移除
        removePrevFragmentOnChangeLayout(selection, activity);
        if (selection.selectedModule() == null || layout != selection.selectedModule().viewport()) {
            //换布局
            activity.setContentView(layout);
            INavigation navigation = site.getService("$.workbench.navigation");

            BottomNavigationView view = viewport.setNavigation(navigation, activity);

            //由于导航是新加到activity上的，所以要选中当前的模块
            if (view != null) {
                int id = activity.getResources().getIdentifier(window.name(), "id", activity.getPackageName());
                if (id > 0) {
                    MenuItem item = view.getMenu().findItem(id);
                    item.setChecked(true);
                }

                if(viewport.isBottomNavigationViewVisibility()) {
                    view.setVisibility(View.VISIBLE);
                }else{
                    view.setVisibility(View.INVISIBLE);
                }
                if(window.isBottomNavigationViewVisibility()) {
                    view.setVisibility(View.VISIBLE);
                }else{
                    view.setVisibility(View.INVISIBLE);
                }

            }
        }

        int containerViewId = viewport.displayViewId();
        if (containerViewId == 0) {
            Log.e("Log", window.name() + "的viewport缺少定义containerViewId");
            return;
        }
        ICell cell=site.getService(IWorkbench.class);
        window.renderTo(viewport, activity,cell);
        activity.invalidateOptionsMenu();

        //切换fragment
        switchFragment(containerViewId,window,selection,activity);
    }

    private void switchFragment(int containerViewId, IWindow window, ISelection selection, Activity activity) {
        Fragment fragment = (Fragment) window;
        FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        try {
            Fragment prev = (Fragment) selection.selectedModule();
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

    private void removePrevFragmentOnChangeLayout(ISelection selection, Activity activity) {
        Fragment prev = (Fragment) selection.selectedModule();
        if (prev == null) {
            return;
        }
        FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        try {
            transaction.remove(prev);
            transaction.commit();
        } catch (Exception e) {
            Log.e("Host", e.getMessage());
        }
    }
}
