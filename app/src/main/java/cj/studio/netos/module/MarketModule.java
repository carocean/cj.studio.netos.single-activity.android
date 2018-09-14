package cj.studio.netos.module;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cj.studio.netos.R;
import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.IAxon;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.thirty.BottomNavigationViewEx;
import cj.studio.netos.module.adapter.MarketGroupListAdapter;
import cj.studio.netos.ui.RecycleViewDivider;


public class MarketModule extends Fragment implements IModule {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.module_market, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.market_recycler);
        RecyclerView.Adapter adapter=new MarketGroupListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(this.getContext(), DividerItemDecoration.HORIZONTAL,30,getResources().getColor(R.color.gray_ececec)));
        return view;
    }



    @Override
    public boolean isBottomNavigationViewVisibility() {
        return false;
    }

    @Override
    public String name() {
        return "market";
    }


    @Override
    public int viewport() {
        return R.layout.viewport_module;
    }

    @Override
    public void renderTo(IViewport viewport, Activity on, IServiceProvider site) {
        viewport.setToolbarInfo("商机",true, on);
    }
    @Override
    public BottomNavigationView.OnNavigationItemSelectedListener onResetNavigationMenu(BottomNavigationViewEx bottomNavigationView, Activity on) {
        return null;
    }
    @Override
    public boolean onToolbarMenuInstall(MenuInflater menuInflater, Menu menu) {
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

    @Override
    public void input(Frame frame, IServiceProvider site) {
        IAxon axon = site.getService(IAxon.class);
        Frame f = new Frame("test /test/ netos/1.0");
        axon.output("netos.mpusher", f);
    }




}
