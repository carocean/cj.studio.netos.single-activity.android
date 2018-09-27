package cj.studio.netos.module.desktop.region;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cj.studio.netos.R;
import cj.studio.netos.framework.ILocation;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.IWidget;
import cj.studio.netos.framework.view.CJBottomNavigationView;

public class MessagerWidget extends Fragment implements IWidget {
    @Override
    public String name() {
        return "messagerWidget";
    }

    @Override
    public int viewport() {
        return R.layout.viewport_widget;
    }

    @Override
    public void onViewport(IViewport viewport, Activity on, IServiceProvider site) {
        viewport.setToolbarInfo("消息", true, on);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messager_viewer, container, false);
        return view;
    }

    @Override
    public boolean onToolbarMenuInstall(MenuInflater menuInflater, Menu menu) {
        menu.clear();
        return true;
    }

    @Override
    public boolean onToolbarMenuSelected(MenuItem item, IServiceProvider site) {
        ILocation location = site.getService("$.workbench.location");
        switch (item.getItemId()) {
            case android.R.id.home:
                location.locate("/desktop");
                break;
        }
        return true;
    }

    @Override
    public CJBottomNavigationView.OnCheckedChangeListener onResetNavigationMenu(CJBottomNavigationView view, Activity on) {
        return null;
    }

    @Override
    public boolean isBottomNavigationViewVisibility() {
        return false;
    }
}
