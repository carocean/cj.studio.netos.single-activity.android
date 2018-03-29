package cj.studio.netos;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.INetAreaAppboard;
import cj.studio.netos.framework.INetAreaDesktop;
import cj.studio.netos.framework.INetAreaMutuboard;
import cj.studio.netos.framework.INetAreaNavigation;
import cj.studio.netos.framework.INetAreaPortal;
import cj.studio.netos.framework.INetAreaSelection;
import cj.studio.netos.framework.INetAreaTitlebar;
import cj.studio.netos.framework.INetAreaWorkbench;
import cj.studio.netos.framework.INetAreaWorkspace;
import cj.studio.netos.framework.INetosProgramTemplate;
import cj.studio.netos.framework.INeuron;
import cj.studio.netos.framework.netarea.NetAreaWorkspace;
import cj.studio.netos.framework.util.StringUtil;
import cj.studio.netos.portal.netarea.NetAreaAppboard;
import cj.studio.netos.portal.netarea.NetAreaDesktop;
import cj.studio.netos.portal.netarea.NetAreaMutuboard;
import cj.studio.netos.portal.netarea.NetAreaNavigation;
import cj.studio.netos.portal.netarea.NetAreaSelection;
import cj.studio.netos.portal.netarea.NetAreaTitlebar;
import cj.studio.netos.portal.netarea.NetosProgramTemplate;

public class NetAreaActivity extends FragmentActivity implements INetAreaPortal {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_area);

        INeuron neuron = (INeuron) getApplication();
        ICell cell = neuron.cell();
        INetAreaWorkbench workbench = (INetAreaWorkbench) cell.getService("$.workbench.netarea");
        if (workbench != null) {
            String url = getIntent().getStringExtra("url");
            if (StringUtil.isEmpty(url)) {
                throw new RuntimeException("未指定网域地址");
            }
            INetAreaWorkspace workspace = NetAreaWorkspace.open(url);
            workbench.renderTo(this, workspace);
        }
    }

    @Override
    public void finish() {
        INeuron neuron = (INeuron) getApplication();
        ICell cell = neuron.cell();
        INetAreaWorkbench workbench = (INetAreaWorkbench) cell.getService("$.workbench.netarea");
        workbench.close();
        super.finish();
    }

    @Override
    public INetAreaDesktop desktop() {
        return new NetAreaDesktop();
    }

    @Override
    public INetAreaMutuboard mutuboard() {
        return new NetAreaMutuboard();
    }

    @Override
    public INetAreaAppboard appboard() {
        return new NetAreaAppboard();
    }

    @Override
    public INetosProgramTemplate programTemplate() {
        return new NetosProgramTemplate();
    }

    @Override
    public INetAreaNavigation navigation() {
        FragmentManager fm = getSupportFragmentManager();
        return new NetAreaNavigation(fm);
    }

    @Override
    public INetAreaSelection selection() {
        return new NetAreaSelection();
    }

    @Override
    public INetAreaTitlebar titlebar() {
        return new NetAreaTitlebar();
    }
}
