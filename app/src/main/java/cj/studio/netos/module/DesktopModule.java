package cj.studio.netos.module;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import cj.studio.netos.R;
import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.IAxon;
import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.IServiceSite;
import cj.studio.netos.framework.IViewport;
import cj.studio.netos.framework.thirty.BottomNavigationViewEx;
import cj.studio.netos.module.desktop.FloatingActionButtonOnclickListener;
import cj.studio.netos.module.desktop.IDesktopRegion;
import cj.studio.netos.module.desktop.IRegionSelection;
import cj.studio.netos.module.desktop.RegionNavigation;
import cj.studio.netos.module.desktop.RegionSelection;
import cj.studio.netos.module.desktop.region.MessagerRegion;
import cj.studio.netos.module.desktop.region.NewsRegion;


public class DesktopModule extends Fragment implements IModule {
    Map<String, IDesktopRegion> regionMap;
    IRegionSelection selection;
    FloatingActionButtonOnclickListener floatingActionButtonOnclickListener;
    IViewport viewport;
    IServiceProvider site;
    INavigation navigation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //如果不指定attachToRoot=false或inflater.inflate(R.layout.region_message,null)，则报此子fragment在父view中已存在的错误，该错误产生时其parent
        //ViewGroup parent = (ViewGroup) view.getParent();parent显示为不为null,必须parent显示为不为null为null时才不报错
        //以上错误的真实原因是：attachToRoot默认为true，会自动将当前fragment挂载到root上，所以再inflate相应的view上时会报其父视图中已存在此fragment的错误，因此attachToRoot必须设为false
        View view = inflater.inflate(R.layout.module_desktop, container, false);

        navigation = new RegionNavigation(viewport, site, getActivity(),  R.id.desktop_region);
        navigation.navigate("messager");
        floatingActionButtonOnclickListener.setNavigation(navigation);
        return view;
    }

    private void registerDesktopRegion() {
        MessagerRegion messagerRegion = new MessagerRegion();
        NewsRegion newsRegion = new NewsRegion();
        regionMap.put(messagerRegion.name(), messagerRegion);
        regionMap.put(newsRegion.name(), newsRegion);
    }


    @Override
    public void input(Frame frame, IServiceProvider site) {
        IAxon axon = site.getService(IAxon.class);
        String relpath = frame.relativePath();
        IDesktopRegion region = regionMap.get(relpath);
        if (region != null) {
            region.input(frame, site);
        }
        Frame f = new Frame("test /test/ netos/1.0");
        axon.output("netos.mpusher", f);
    }

    @Override
    public String name() {
        return "desktop";
    }

    @Override
    public int viewport() {
        return R.layout.viewport_desktop;
    }

    @Override
    public void renderTo(IViewport viewport, final Activity on, IServiceProvider site) {
        this.viewport = viewport;
        if(this.site==null) {
            this.site = new DesktopServiceProvider(site);
        }
        selection = new RegionSelection();

        regionMap = new HashMap<>();
        registerDesktopRegion();

        FloatingActionButton fab = on.findViewById(R.id.desktop_fab);
//        fab.setBackgroundColor(0);
//        fab.setBackgroundResource(R.drawable.face);
        fab.setImageResource(R.mipmap.ic_face);
        floatingActionButtonOnclickListener = new FloatingActionButtonOnclickListener(on, site);
        fab.setOnClickListener(floatingActionButtonOnclickListener);
    }

    @Override
    public BottomNavigationView.OnNavigationItemSelectedListener onResetNavigationMenu(BottomNavigationViewEx bottomNavigationView, final Activity on) {
//        bottomNavigationView.getMenu().clearMenu();//擦除全局菜单
//        bottomNavigationView.inflateMenu(R.menu.navigation_message);
        return null;
    }

    @Override
    public boolean isBottomNavigationViewVisibility() {
        return true;
    }

    @Override
    public boolean onToolbarMenuInstall(MenuInflater menuInflater, Menu menu) {
        menu.clear();
        menuInflater.inflate(R.menu.module_desktop, menu);
        IDesktopRegion region = selection.selectRegion();
        if (region != null) {
            return region.onToolbarMenuInstall(menuInflater, menu);
        }
        return true;
    }

    @Override
    public boolean onToolbarMenuSelected(MenuItem item, IServiceProvider site) {
        IDesktopRegion region = selection.selectRegion();
        if (region != null) {
            if(this.site==null){
                this.site=new DesktopServiceProvider(site);
            }
            return region.onToolbarMenuSelected(item, this.site);
        }
        return true;
    }

    class DesktopServiceProvider implements IServiceProvider{
        IServiceProvider parent;
        public DesktopServiceProvider(IServiceProvider parent){
            this.parent=parent;
        }


        @Override
        public <T> T getService(String name) {
            if("$.region.selection".equals(name)){
                return (T)selection;
            }
            if("$.region.navigation".equals(name)){
                return (T)navigation;
            }
            if(name.startsWith("$.region.nameis.")){
                name=name.substring("$.region.nameis.".length(),name.length());
                return (T)regionMap.get(name);
            }
            return parent.getService(name);
        }

        @Override
        public <T> T getService(Class<T> clazz) {
            if(clazz.isAssignableFrom(FragmentManager.class)){
                return (T)getChildFragmentManager();
            }
            return parent.getService(clazz);
        }
    }

}
