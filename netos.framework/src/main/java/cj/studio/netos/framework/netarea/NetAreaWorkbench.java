package cj.studio.netos.framework.netarea;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.INetAreaAppboard;
import cj.studio.netos.framework.INetAreaDesktop;
import cj.studio.netos.framework.INetAreaMutuboard;
import cj.studio.netos.framework.INetAreaNavigation;
import cj.studio.netos.framework.INetAreaPortal;
import cj.studio.netos.framework.INetAreaSelection;
import cj.studio.netos.framework.INetAreaTitlebar;
import cj.studio.netos.framework.INetAreaWorkbench;
import cj.studio.netos.framework.INetAreaWorkspace;
import cj.studio.netos.framework.INetAreaProgramContainer;
import cj.studio.netos.framework.INetosProgram;
import cj.studio.netos.framework.INetosProgramTemplate;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.IServiceSite;
import cj.studio.netos.framework.ServiceSite;

/**
 * Created by caroceanjofers on 2018/2/3.
 */

public class NetAreaWorkbench implements INetAreaWorkbench, IModule {
    IServiceSite site;
    Map<String,IModule> modules;
    public NetAreaWorkbench(IServiceProvider parent) {
        this.site = new ServiceSite(parent);
        this.modules=new HashMap<>();
    }


    @Override
    public String name() {
        return "netarea";
    }

    @Override
    public void input(Frame frame, ICell cell) {
        //根据workspace中安装的应用名启动应用或进去服务等等功能
        String relat=frame.relativeUrl();
        frame.url(relat);
        String mod_name="";
        if("/".equals(relat)){//网域的根就是桌面
            IModule indexModule=(IModule)site.getService("$.netarea.desktop");
            if(indexModule!= null) {
                indexModule.input(frame, cell);
            }
            return;
        }
        int pos=relat.lastIndexOf("/");
        if(pos>-1){
            mod_name=relat.substring(1,pos);
        }else{
            mod_name=relat.substring(1,relat.length());
        }
        if(!modules.containsKey(mod_name)){
            Log.e("netos","模块不存在："+mod_name);
            return;
        }
        IModule module=modules.get(mod_name);
        module.input(frame,cell);
    }




    @Override
    public INetosProgram openDesktop() {
        return null;
    }

    @Override
    public INetosProgram openProgram(String url) {
        return null;
    }

    @Override
    public void goDesktop() {

    }

    @Override
    public String[] listProgramUrl() {
        return new String[0];
    }

    @Override
    public void closeAllProgram() {

    }

    @Override
    public void close() {
        site.dispose();
    }

    @Override
    public Object getService(String name) {
        return site.getService(name);
    }

    @Override
    public <T> T getService(Class<T> clazz) {
        return site.getService(clazz);
    }

    @Override
    public void renderTo(INetAreaPortal portal, INetAreaWorkspace workspace) {
        IServiceProvider provider=this;

        INetAreaTitlebar titlebar=portal.titlebar();//标题栏是网域内所有模块共同的格式
        INetAreaNavigation navigation=portal.navigation();
        INetAreaSelection selection=portal.selection();
        INetAreaDesktop desktop=portal.desktop();
        INetAreaMutuboard mutuboard=portal.mutuboard();//互动板
        INetAreaAppboard appboard=portal.appboard();//应用板
        INetosProgramTemplate template=portal.programTemplate();//程序模板，它用于创建实体并运行微应用

        INetAreaProgramContainer container=new NetAreaProgramContainer(template,this);

        navigation.init(provider);

        site.addService("$.netarea.workspace", workspace);
        site.addService("$.netarea.toolbar", titlebar);
        site.addService("$.netarea.navigation", navigation);
        site.addService("$.netarea.selection", selection);
        site.addService("$.netarea.desktop", desktop);
        site.addService("$.netarea.mutuboard", mutuboard);
        site.addService("$.netarea.appboard", appboard);
        site.addService("$.netarea.program.display", container);

        modules.put(desktop.name(),desktop);
        modules.put(mutuboard.name(),mutuboard);
        modules.put(appboard.name(),appboard);
        modules.put(container.name(),container);


        //加载数据
        desktop.load(this);
        appboard.load(workspace);
        mutuboard.load(workspace);

        navigation.naviToDesktop();
    }

}
