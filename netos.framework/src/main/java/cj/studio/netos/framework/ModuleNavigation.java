package cj.studio.netos.framework;

import android.app.Activity;
import android.util.Log;
import android.widget.RadioGroup;

class ModuleNavigation implements INavigation {
    IServiceProvider site;
    OnNavigationItemSelectedEvent event;

    public ModuleNavigation(IServiceProvider site) {
        this.site = site;
    }

    @Override
    public boolean navigate(String navigateable) {
        IModule module = site.getService("$.module." + navigateable);
        ISelection selection = site.getService("$.workbench.selection");
        if(selection.selectedModule()!=null&&selection.selectedModule().equals(module)&&selection.selectedWidget()==null){
            return false;
        }
        ISurfaceHost host = site.getService("$.workbench.host");

        host.showWindow(module);

        IHistory history = site.getService("$.workbench.history");
        if(selection.selectedModule()!=null) {
            Memento memento = new Memento(String.format("/%s", selection.selectedModule().name()));
            history.redo(memento);
        }

        selection.setSelectedModule(module);

        return true;
    }

    @Override
    public RadioGroup.OnCheckedChangeListener onNavigationItemSelectedEvent(Activity on) {
        if (event != null) {
            return event;
        }
        event = new OnNavigationItemSelectedEvent(on);
        return event;
    }

    class OnNavigationItemSelectedEvent implements RadioGroup.OnCheckedChangeListener {
        Activity on;
        public OnNavigationItemSelectedEvent( Activity on) {
            this.on=on;
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            try {
                String name=on.getResources().getResourceName(checkedId);
                int pos=name.indexOf("/");
                if(pos>-1){
                    name=name.substring(pos+1,name.length());
                }
                if(navigate(name)) {
//                item.setCheckedItem(true);
                    return ;
                }else{
                    return ;
                }
            } catch (Exception e) {
                Log.e("Nav", e.getMessage());
                return ;
            }
        }


    }
}
