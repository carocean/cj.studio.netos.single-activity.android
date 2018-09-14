package cj.studio.netos.module;

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
import android.widget.Toast;

import cj.studio.netos.R;
import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.thirty.BottomNavigationViewEx;
import cj.studio.netos.module.desktop.region.GeomicroblogOnNavigationSelectListener;
import q.rorbin.badgeview.Badge;


public class GeoMicroblogModule extends Fragment implements IModule {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.module_geomicroblog, container, false);

        return view;
    }
    @Override
    public boolean isBottomNavigationViewVisibility() {
        return true;
    }

    @Override
    public void input(Frame frame, IServiceProvider site) {

    }
    @Override
    public String name() {
        return "geomicroblog";
    }
    @Override
    public int viewport() {
        return R.layout.viewport_module;
    }

    @Override
    public void renderTo(IViewport viewport, Activity on, IServiceProvider site) {
        viewport.setToolbarInfo("地微",true, on);
    }

    @Override
    public BottomNavigationView.OnNavigationItemSelectedListener onResetNavigationMenu(BottomNavigationViewEx bottomNavigationView, final Activity on) {
        bottomNavigationView.getMenu().clear();//擦除全局菜单
        bottomNavigationView.inflateMenu(R.menu.navigation_geo);
        bottomNavigationView.enableAnimation(true);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.enableItemShiftingMode(false);
        if (bottomNavigationView.getMenu().size() > 0) {
            bottomNavigationView.setIconTintList(0, on.getResources().getColorStateList(R.color.colorGray));
            bottomNavigationView.setTextTintList(0, on.getResources().getColorStateList(R.color.colorGray));
            bottomNavigationView.setBadgeAt(0, 49, 20, 0, on, new Badge.OnDragStateChangedListener() {
                @Override
                public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                    if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState)
                        Toast.makeText(on, R.string.tips_badge_removed, Toast.LENGTH_SHORT).show();
                }
            });
        }
        GeomicroblogOnNavigationSelectListener listener = new GeomicroblogOnNavigationSelectListener(bottomNavigationView, on.getResources());
        return listener;
    }

    @Override
    public boolean onToolbarMenuInstall(MenuInflater menuInflater, Menu menu) {
        menu.clear();
        menuInflater.inflate(R.menu.module_geomicroblog,menu);
        return true;
    }

    @Override
    public boolean onToolbarMenuSelected(MenuItem item, IServiceProvider site) {
        INavigation navigation= site.getService("$.workbench.navigation");
        switch (item.getItemId()) {
            case android.R.id.home:
                navigation.navigate("desktop");
                break;
        }
        return true;
    }


}
