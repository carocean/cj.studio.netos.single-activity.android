package cj.studio.netos.module.desktop.region;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cj.studio.netos.R;
import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.thirty.BottomNavigationViewEx;
import cj.studio.netos.module.desktop.IDesktopRegion;
import q.rorbin.badgeview.Badge;


public class MessagerRegion extends Fragment implements IDesktopRegion {
    private List initData() {
        List mDatas = new ArrayList<String>(Arrays.asList("网域桌面模拟AppleStore首页", "所有信息均由应用发布", "为不当中国人 台留学生向蔡英文筹钱告挪威", "网域桌面模拟AppleStore首页", "所有信息均由应用发布", "为不当中国人 台留学生向蔡英文筹钱告挪威", "网域桌面模拟AppleStore首页", "所有信息均由应用发布", "为不当中国人 台留学生向蔡英文筹钱告挪威", "网域桌面模拟AppleStore首页", "所有信息均由应用发布", "为不当中国人 台留学生向蔡英文筹钱告挪威"));
        return mDatas;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //如果不指定attachToRoot=false或inflater.inflate(R.layout.region_message,null)，则报此子fragment在父view中已存在的错误，该错误产生时其parent
        //ViewGroup parent = (ViewGroup) view.getParent();parent显示为不为null,必须parent显示为不为null为null时才不报错
        //以上错误的真实原因是：attachToRoot默认为true，会自动将当前fragment挂载到root上，所以再inflate相应的view上时会报其父视图中已存在此fragment的错误，因此attachToRoot必须设为false
        View view = inflater.inflate(R.layout.region_message, container, false);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.message_recycler);
        mRecyclerView.setAdapter(new MessagerRecyclerAdapter(this.getContext(), initData()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void input(Frame frame, IServiceProvider site) {

    }

    @Override
    public String name() {
        return "messager";
    }


    @Override
    public void renderTo(IViewport viewport, Activity on, IServiceProvider site) {
        viewport.setToolbarInfo("消息",false,on);
    }

    @Override
    public boolean onToolbarMenuInstall(MenuInflater menuInflater, Menu menu) {
        return true;
    }

    @Override
    public boolean onToolbarMenuSelected(MenuItem item, IServiceProvider site) {
//        INavigation navigation=site.getService("$.region.navigation");
//        navigation.navigate("news");
        return true;
    }

    @Override
    public BottomNavigationView.OnNavigationItemSelectedListener onResetNavigationMenu(BottomNavigationViewEx bottomNavigationView,final Activity on) {
        bottomNavigationView.clearMenu();
        bottomNavigationView.inflateMenu(R.menu.navigation_message);

        if (bottomNavigationView.getMenu().size() > 0) {
            MenuView.ItemView view=bottomNavigationView.getBottomNavigationItemView(0);
            Log.i("pos 0",view.getItemData()+"");
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
        return listener;
    }

    @Override
    public boolean isBottomNavigationViewVisibility() {
        return true;
    }
}
