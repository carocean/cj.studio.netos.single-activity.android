package cj.studio.netos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.ICellsap;
import cj.studio.netos.framework.IDesktopRegion;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.INeuron;
import cj.studio.netos.framework.ISelection;
import cj.studio.netos.framework.ISurfaceHost;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.IWindow;
import cj.studio.netos.framework.IWorkbench;
import cj.studio.netos.framework.util.OverflowToolbarMenu;
import cj.studio.netos.module.DesktopModule;
import cj.studio.netos.module.GeoMicroblogModule;
import cj.studio.netos.module.MarketModule;
import cj.studio.netos.module.NetflowModule;
import cj.studio.netos.viewport.DesktopViewport;
import cj.studio.netos.viewport.ModuleViewport;
import cj.studio.netos.viewport.WidgetViewport;

public class NetosActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        IModule desktop = new DesktopModule();
        workbench.addModule(desktop);
        IModule geo = new GeoMicroblogModule();
        workbench.addModule(geo);
        IModule netflow = new NetflowModule();
        workbench.addModule(netflow);
        IModule market = new MarketModule();
        workbench.addModule(market);

        ISurfaceHost host = workbench.host();
        IViewport desktopViewport = new DesktopViewport();
        host.addViewport(desktopViewport);
        IViewport moudleViewport = new ModuleViewport();
        host.addViewport(moudleViewport);
        IViewport widgetViewport = new WidgetViewport();
        host.addViewport(widgetViewport);
        workbench.render(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        INeuron neuron = ((INeuron) this.getApplication());
        IWorkbench workbench = neuron.cell().getService(IWorkbench.class);

        ISelection selection = workbench.getService("$.workbench.selection");
        IWindow window = selection.selectedModule();
        if (window == null) {
            return super.onCreateOptionsMenu(menu);
        }
        if(selection.selectedRegion()!=null&&selection.selectedWidget()==null){
            IDesktopRegion region=selection.selectedRegion();

            MenuInflater menuInflater = getMenuInflater();
            return window.onToolbarMenuInstall(menuInflater, menu);
        }
        if(selection.selectedWidget()!=null){
            window=selection.selectedWidget();
        }
        ISurfaceHost host = workbench.getService("$.workbench.host");
        IViewport viewport = host.viewport(window.viewport());
        if (viewport != null && viewport.isShowToolbarMenuIcon()) {
            OverflowToolbarMenu.showIcon(menu);
        }
        MenuInflater menuInflater = getMenuInflater();
        return window.onToolbarMenuInstall(menuInflater, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        INeuron neuron = ((INeuron) this.getApplication());
        IWorkbench workbench = neuron.cell().getService(IWorkbench.class);
        ISelection selection = workbench.getService("$.workbench.selection");
        IWindow window = selection.selectedModule();
        if (window == null) {
            return super.onOptionsItemSelected(item);
        }
        if(selection.selectedRegion()!=null&&selection.selectedWidget()==null){
            IDesktopRegion region=selection.selectedRegion();
            ICell cell = neuron.cell();
            return window.onToolbarMenuSelected(item, cell);
        }
        if(selection.selectedWidget()!=null){
            window=selection.selectedWidget();
        }
        ICell cell = neuron.cell();
        return window.onToolbarMenuSelected(item, cell);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            INeuron neuron = ((INeuron) this.getApplication());
            IWorkbench workbench = neuron.cell().getService(IWorkbench.class);
            workbench.finish(this);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
