package cj.studio.netos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cj.studio.netos.framework.ICellsap;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.INeuron;
import cj.studio.netos.framework.ISelection;
import cj.studio.netos.framework.ISurfaceHost;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.IWorkbench;
import cj.studio.netos.framework.util.OverflowToolbarMenu;
import cj.studio.netos.module.DesktopModule;
import cj.studio.netos.module.GeoMicroblogModule;
import cj.studio.netos.module.MarketModule;
import cj.studio.netos.module.NetflowModule;
import cj.studio.netos.viewport.DesktopViewport;
import cj.studio.netos.viewport.ModuleViewport;

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

        ISurfaceHost host=workbench.host();
        IViewport desktopViewport=new DesktopViewport();
        host.addViewport(desktopViewport);
        IViewport moudleViewport=new ModuleViewport();
        host.addViewport(moudleViewport);
        workbench.render(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        INeuron neuron = ((INeuron) this.getApplication());
        IWorkbench workbench = neuron.cell().getService(IWorkbench.class);

        ISelection selection = workbench.getService("$.workbench.selection");
        IModule module = selection.selectedModule();
        if (module == null) {
            return super.onCreateOptionsMenu(menu);
        }
        ISurfaceHost host = workbench.getService("$.workbench.host");
        IViewport viewport = host.viewport(module.viewport());
        if (viewport != null && viewport.isShowMenuIcon()) {
            OverflowToolbarMenu.showIcon(menu);
        }
        MenuInflater menuInflater = getMenuInflater();
        return module.onViewportMenuInstall(menuInflater, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        INeuron neuron = ((INeuron) this.getApplication());
        IWorkbench workbench = neuron.cell().getService(IWorkbench.class);
        ISelection selection = workbench.getService("$.workbench.selection");
        IModule module = selection.selectedModule();
        if (module == null) {
            return super.onOptionsItemSelected(item);
        }
        return module.onViewportMenuSelected(item);
    }

}
