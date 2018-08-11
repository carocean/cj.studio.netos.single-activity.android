package cj.studio.netos.module;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cj.studio.netos.R;
import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.IAxon;
import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.IViewport;


public class MarketModule extends Fragment implements IModule {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_market_module, container, false);
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
        return R.layout.layout_module_viewport;
    }

    @Override
    public void renderTo(IViewport viewport, Activity on, ICell cell) {
        viewport.setToolbarInfo("市场",true, on);
    }

    @Override
    public boolean onToolbarMenuInstall(MenuInflater menuInflater, Menu menu) {
        return true;
    }

    @Override
    public boolean onToolbarMenuSelected(MenuItem item, ICell cell) {
        INavigation navigation=cell.getService("$.workbench.navigation");
        switch (item.getItemId()) {
            case android.R.id.home:
                navigation.navigate("desktop");
                break;
        }
        return true;
    }

    @Override
    public void input(Frame frame, ICell cell) {
        IAxon axon = cell.axon();
        Frame f = new Frame("test /test/ netos/1.0");
        axon.output("netos.mpusher", f);
    }


}
