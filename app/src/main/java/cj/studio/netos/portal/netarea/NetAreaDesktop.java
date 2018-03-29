package cj.studio.netos.portal.netarea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import cj.studio.netos.R;
import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.INetAreaDesktop;
import cj.studio.netos.framework.INetAreaProgramContainer;
import cj.studio.netos.framework.INetAreaWorkspace;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.isite.IBrowserable;
import cj.studio.netos.framework.isite.IWebCore;

/**
 * Created by caroceanjofers on 2018/2/26.
 */

public class NetAreaDesktop extends Fragment implements INetAreaDesktop {
    private IServiceProvider site;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_netarea_desktop, container, false);
        WebView webView = view.findViewById(R.id.desktop_region);
        INetAreaProgramContainer programContainer = (INetAreaProgramContainer) site.getService("$.netarea.program.container");

        IBrowserable webSite = programContainer.createWebSite(webView);

        webSite.displayer().loadUrl("file:///android_asset/index.html");

        return view;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public void input(Frame frame, ICell cell) {

    }

    @Override
    public int cnameId() {
        return 0;
    }

    @Override
    public void load(IServiceProvider site) {
        this.site = site;
        INetAreaWorkspace workspace = (INetAreaWorkspace) site.getService("$.netarea.workspace");
    }

}
