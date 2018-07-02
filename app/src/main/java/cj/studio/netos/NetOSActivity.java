package cj.studio.netos;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cj.studio.netos.framework.ICellsap;
import cj.studio.netos.framework.IDisplay;
import cj.studio.netos.framework.IDownSlideRegion;
import cj.studio.netos.framework.IHistory;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.INetAreaWorkbench;
import cj.studio.netos.framework.INetOSPortal;
import cj.studio.netos.framework.INetOSResource;
import cj.studio.netos.framework.INeuron;
import cj.studio.netos.framework.IRefreshMenuCallback;
import cj.studio.netos.framework.ISelection;
import cj.studio.netos.framework.ISlideButton;
import cj.studio.netos.framework.IToolbar;
import cj.studio.netos.framework.IWorkbench;
import cj.studio.netos.module.DesktopModule;
import cj.studio.netos.module.GeoMicroblogModule;
import cj.studio.netos.module.MarketModule;
import cj.studio.netos.module.NetAreaModule;
import cj.studio.netos.module.NetflowModule;
import cj.studio.netos.portal.Display;
import cj.studio.netos.portal.Navigation;
import cj.studio.netos.portal.NetOSToolbar;
import cj.studio.netos.portal.Selection;
import cj.studio.netos.util.ForbiddenNavigationAnimation;
import cj.studio.netos.util.OverflowToolbarMenu;

public class NetosActivity extends AppCompatActivity implements INetOSPortal, INetOSResource, IRefreshMenuCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setStatusBar();
        BottomNavigationView navui = findViewById(R.id.module_navigation);
        ForbiddenNavigationAnimation.disableShiftMode(navui);

        INeuron neuron = ((INeuron) this.getApplication());


        if (!neuron.isOnline()) {
            ICellsap sap = neuron.provider().getService(ICellsap.class);
            sap.empty();
            if (!sap.checkIdentity()) {
                //跳转到登录界面，并更新cellsap
                sap.remoteAddressList(new String[]{"tcp://192.168.1.102:7002"});
                sap.principal("cj");
                sap.token("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0Y3A6Ly9jai5jb20iLCJpc3MiOiJ0Y3BTZXJ2ZXIiLCJwd2QiOiIxMSIsImlhdCI6MTUxNjQyMjc3NiwianRpIjoiMmJjOTliNjAtMTM4YS00YmUxLTk1Y2EtMjViZDAyZDgxYjhlIn0.uUk1CE5xY_k22yGHO8sLGFqnPinB31VvDqH9Rg5bwa8");
            }
            if (!neuron.isConnected()) {
                neuron.reconnect();
            }
        }

        IWorkbench workbench = neuron.cell().getService(IWorkbench.class);
        workbench.renderTo(this);
//        super.startActivity(super.getIntent());
    }
    /**
     * 利用反射获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    private void setStatusBar() {
        //设置 paddingTop
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.setPadding(0, getStatusBarHeight(), 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 以上直接设置状态栏颜色
            this.getWindow().setStatusBarColor(Color.parseColor("#3F51B5"));
        } else {
            //根布局添加占位状态栏
            ViewGroup decorView = (ViewGroup) this.getWindow().getDecorView();
            View statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight());
            statusBarView.setBackgroundColor(Color.parseColor("#3F51B5"));
            decorView.addView(statusBarView, lp);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        OverflowToolbarMenu.showIcon(menu);
        menu.clear();
        INeuron neuron = ((INeuron) this.getApplication());
        ISelection selection = (ISelection) neuron.cell().getService("$.workbench.selection");
        IModule m = selection.selectedModule();
        if (m == null || (selection.selectedMenu() <= 0 && "desktop".equals(m.name()))) {
            getMenuInflater().inflate(R.menu.menu_desktop, menu);
        } else {
            if (selection.selectedMenu() > 0) {
                getMenuInflater().inflate(selection.selectedMenu(), menu);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void refreshMenu(ISelection selection) {
        invalidateOptionsMenu();
    }

    @Override
    public IDisplay display() {
        IDisplay container = new Display(R.id.display);
        return container;
    }

    @Override
    public IToolbar toolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        return new NetOSToolbar(toolbar);
    }


    @Override
    public ISlideButton slideButton() {
        return null;
    }

    @Override
    public List<IModule> modules() {
        ArrayList<IModule> list = new ArrayList<>();
        list.add(new DesktopModule());
        list.add(new GeoMicroblogModule());
        list.add(new NetflowModule());
        list.add(new MarketModule());
        list.add(new NetAreaModule());
        return list;
    }

    @Override
    public INavigation navigation() {
        BottomNavigationView navui = findViewById(R.id.module_navigation);
        ForbiddenNavigationAnimation.disableShiftMode(navui);
        FragmentManager fm = getSupportFragmentManager();
        return new Navigation(navui, fm);
    }

    @Override
    public ISelection selection() {
        INeuron neuron = ((INeuron) this.getApplication());
        ISelection selection = new Selection(neuron.cell());
        selection.onRefreshMenu(this);
        return selection;
    }

    @Override
    public IDownSlideRegion downSlideRegion() {
        return null;
    }

    @Override
    public IHistory history() {
        return null;
    }

    @Override
    public INetAreaWorkbench netArea() {

        return null;
    }

    @Override
    public INetOSResource resource() {
        return this;
    }

    @Override
    public String getNetOSString(int resid) {
        return getString(resid);
    }

    @Override
    public String getNetOSAnyResourceName(int resid) {

        return getResources().getResourceName(resid);
    }

    @Override
    public int getIdentifier(String name, String defType) {
        return getResources().getIdentifier(name, defType, getPackageName());
    }
}
