package cj.studio.netos.module;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cj.studio.netos.R;
import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.IAxon;
import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.thirty.BottomNavigationViewEx;
import cj.studio.netos.module.desktop.FloatingActionButtonOnclickListener;
import cj.studio.netos.module.desktop.IDesktopRegion;
import cj.studio.netos.module.desktop.region.MessagerRecyclerAdapter;
import cj.studio.netos.module.desktop.region.MessagerOnNavigationSelectListener;
import q.rorbin.badgeview.Badge;


public class DesktopModule extends Fragment implements IModule {
    Map<String,IDesktopRegion> regionMap;
    private List initData() {
        List mDatas = new ArrayList<String>(Arrays.asList("网域桌面模拟AppleStore首页", "所有信息均由应用发布", "为不当中国人 台留学生向蔡英文筹钱告挪威", "网域桌面模拟AppleStore首页", "所有信息均由应用发布", "为不当中国人 台留学生向蔡英文筹钱告挪威", "网域桌面模拟AppleStore首页", "所有信息均由应用发布", "为不当中国人 台留学生向蔡英文筹钱告挪威", "网域桌面模拟AppleStore首页", "所有信息均由应用发布", "为不当中国人 台留学生向蔡英文筹钱告挪威"));
        return mDatas;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        regionMap=new HashMap<>();
        registerDesktopRegion();

        View view = inflater.inflate(R.layout.fragment_desktop_module, container, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.message_recycler);
        mRecyclerView.setAdapter(new MessagerRecyclerAdapter(this.getContext(), initData()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    private void registerDesktopRegion() {

    }


    @Override
    public void input(Frame frame, ICell cell) {
        IAxon axon = cell.axon();
        Frame f = new Frame("test /test/ netos/1.0");
        axon.output("netos.mpusher", f);
    }

    @Override
    public String name() {
        return "desktop";
    }

    @Override
    public int viewport() {
        return R.layout.layout_desktop_viewport;
    }

    @Override
    public void renderTo(IViewport viewport, final Activity on, ICell cell) {
        viewport.setToolbarInfo("消息",false, on);
        FloatingActionButton fab = on.findViewById(R.id.desktop_fab);
//        fab.setBackgroundColor(0);
//        fab.setBackgroundResource(R.drawable.face);
        fab.setImageResource(R.mipmap.ic_face);
        fab.setOnClickListener(new FloatingActionButtonOnclickListener(on, cell));

        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) viewport.navigationView();
        if (bottomNavigationView != null) {
            bottomNavigationView.getMenu().clear();
            bottomNavigationView.inflateMenu(R.menu.navigation_message);
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
            MessagerOnNavigationSelectListener listener = new MessagerOnNavigationSelectListener(bottomNavigationView, on.getResources());
            bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        }
    }

    @Override
    public boolean isBottomNavigationViewVisibility() {
        return true;
    }

    @Override
    public boolean onToolbarMenuInstall(MenuInflater menuInflater, Menu menu) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_desktop, menu);
        return true;
    }

    @Override
    public boolean onToolbarMenuSelected(MenuItem item, ICell cell) {
        return true;
    }


}
