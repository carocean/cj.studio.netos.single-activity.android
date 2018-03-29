package cj.studio.netos;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cj.studio.netos.module.Geoarea_module;
import cj.studio.netos.module.MessageModule;
import cj.studio.netos.module.MineModule;
import cj.studio.netos.module.NetOSModule;
import cj.studio.netos.module.NetflowModule;
import cj.studio.netos.framework.ICellsap;
import cj.studio.netos.framework.IContainer;
import cj.studio.netos.framework.IDownSlideRegion;
import cj.studio.netos.framework.IHistory;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.INetAreaWorkbench;
import cj.studio.netos.framework.INetOSResource;
import cj.studio.netos.framework.INeuron;
import cj.studio.netos.framework.INetOSPortal;
import cj.studio.netos.framework.ISelection;
import cj.studio.netos.framework.ISlideButton;
import cj.studio.netos.framework.ITitlebar;
import cj.studio.netos.framework.IWorkbench;
import cj.studio.netos.portal.Container;
import cj.studio.netos.portal.Navigation;
import cj.studio.netos.portal.Selection;
import cj.studio.netos.portal.Titlebar;

public class NetOSActivity extends FragmentActivity implements INetOSPortal, INetOSResource {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_netos);

        INeuron neuron = ((INeuron) this.getApplication());


        if (!neuron.isOnline()) {
            ICellsap sap = neuron.provider().getService(ICellsap.class);
            sap.empty();
            if (!sap.checkIdentity()) {
                //跳转到登录界面，并更新cellsap
                sap.remoteAddressList(new String[]{"tcp://192.168.10.75:7002"});
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


    @Override
    public IContainer container() {
        FrameLayout containerui = findViewById(R.id.container);
        IContainer container = new Container(containerui);
        return container;
    }

    @Override
    public ITitlebar titlebar() {
        TextView tvui = findViewById(R.id.module_title);

        return new Titlebar(tvui);
    }


    @Override
    public ISlideButton slideButton() {
        return null;
    }

    @Override
    public List<IModule> modules() {
        ArrayList<IModule> list = new ArrayList<>();
        list.add(new MessageModule());
        list.add(new Geoarea_module());
        list.add(new NetflowModule());
        list.add(new NetOSModule());
        list.add(new MineModule());
        return list;
    }

    @Override
    public INavigation navigation() {
        RadioGroup navui = findViewById(R.id.module_navigation);
        FragmentManager fm = getSupportFragmentManager();
        return new Navigation(navui, fm);
    }

    @Override
    public ISelection selection() {
        INeuron neuron = ((INeuron) this.getApplication());
        ISelection selection=new Selection(neuron.cell());
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
}
