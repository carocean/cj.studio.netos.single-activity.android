package cj.studio.netos.framework;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.List;

import cj.studio.netos.framework.view.CJBottomNavigationView;

class WidgetNavigation implements INavigation {
    IServiceProvider site;

    public WidgetNavigation(IServiceProvider site) {
        this.site = site;
    }

    @Override
    public boolean navigate(String navigateable) {
        ISelection selection = site.getService("$.workbench.selection");
        ISurfaceHost host = site.getService("$.workbench.host");
        IModule module = selection.selectedModule();//widget所在的当前的模块
        IWidget widget = null;
        if ("desktop".equals(module.name())) {
            widget = selection.selectedRegion() == null ? null : selection.selectedRegion().widget(navigateable);
        } else {
            widget = module.widget(navigateable);
        }
        if (widget == null) {
            Log.e("WidgetNavigation", "widget不存在：" + navigateable);
            return false;
        }
        IViewport viewport = site.getService("$.viewport." + widget.viewport());
        if (viewport == null) {
            Log.e("WidgetNavigation", "widget在系统中未有viewport：" + navigateable);
            return false;
        }
        int layout = viewport.layout();
        Activity on = (Activity) host.owner();
        if (selection.getCurrentViewport() == null || (viewport.layout() != selection.getCurrentViewport().layout())) {
            //在actitity更换布局会导致先前添加的fragment无法显示，这发生在再切换回来时fragment将会一直是hide状态和isAdded，且无论如何也show不出来,且fragment.oncreateview的viewgroup为空
            //在更换布局前必须移除fragment，否则之后就移除不掉了
            removePrevFragmentOnChangeLayout(selection, on);
            //换布局
            on.setContentView(layout);
            INavigation navigation = site.getService("$.workbench.navigation");

            CJBottomNavigationView view = viewport.setNavigation(navigation, on);

        }


        CJBottomNavigationView view = viewport.navigationView(on);
        if (view != null) {
            if (viewport.isBottomNavigationViewVisibility()) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
            if (widget.isBottomNavigationViewVisibility()) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
            CJBottomNavigationView.OnCheckedChangeListener onNavigationItemSelectedListener = widget.onResetNavigationMenu(view, on);
            if (onNavigationItemSelectedListener != null) {
                view.setOnCheckedChangeListener(onNavigationItemSelectedListener);
            }
        }
        widget.onViewport(viewport, on, site);

        on.invalidateOptionsMenu();

        switchFragment(viewport, widget, on);

        IHistory history = site.getService("$.workbench.history");
        if(selection.selectedWidget() != null){
            if ("desktop".equals(module.name())) {
                if (selection.selectedRegion() != null) {
                    if(String.format("/%s",selection.selectedWidget().name()).equals(navigateable)){
                        Memento memento = new Memento(String.format("/%s/%s.region", module.name(), selection.selectedRegion().name()));
                        history.redo(memento);
                    }else{
                        Memento memento = new Memento(String.format("/%s/%s.region/%s", module.name(), selection.selectedRegion().name(), selection.selectedWidget().name()));
                        history.redo(memento);
                    }

                }
            } else {
                Memento memento = new Memento(String.format("/%s/%s", module.name(), selection.selectedWidget().name()));
                history.redo(memento);
            }
        }else{
            if ("desktop".equals(module.name())) {
                if (selection.selectedRegion() != null) {
                    Memento memento = new Memento(String.format("/%s/%s.region", module.name(), selection.selectedRegion().name()));
                    history.redo(memento);
                }
            } else {
                Memento memento = new Memento(String.format("/%s", module.name()));
                history.redo(memento);
            }
        }

        selection.setSelectedWidget(widget);
        selection.setCurrentViewport(viewport);

        return true;
    }

    private void switchFragment(IViewport viewport, IWidget widget, Activity on) {
        Fragment fragment = (Fragment) widget;
        FragmentManager manager = ((AppCompatActivity) on).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        try {
//            if (fragment.isAdded()) {
//                if (fragment.isHidden()) {
//                    transaction.show(fragment);
//                }
//            } else {
//                transaction.add(viewport.displayViewId(), fragment).show(fragment);
//            }
            //使fragment每次都执行oncreateView
            transaction.replace(viewport.displayViewId(),fragment).show(fragment);
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            Log.e("Host", e.getMessage());
        }
    }

    @Override
    public CJBottomNavigationView.OnCheckedChangeListener onNavigationItemSelectedEvent(Activity on) {
        return null;
    }

    private void removePrevFragmentOnChangeLayout(ISelection selection, Activity activity) {
        Fragment fragment = (Fragment) selection.selectedModule();
        if (fragment == null || !fragment.isAdded()) {
            return;
        }
        FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        try {
            transaction.remove(fragment);

            if (selection.selectedRegion() != null) {
                transaction.remove((Fragment) selection.selectedRegion());
            }
            List<Fragment> list=manager.getFragments();
            for(int i=0;i<list.size();i++){
                Fragment f=list.get(i);
                if(f.isAdded()) {
                    transaction.remove(f);
                }
            }
            transaction.commit();
        } catch (Exception e) {
            Log.e("Host", e.getMessage());
        }
    }
}
