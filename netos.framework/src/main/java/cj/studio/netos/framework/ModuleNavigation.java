package cj.studio.netos.framework;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

class ModuleNavigation implements INavigation {
    IServiceProvider site;
    OnNavigationItemSelectedEvent event;

    public ModuleNavigation(IServiceProvider site) {
        this.site = site;
    }

    @Override
    public void navigate(String navigateable) {
        IModule module = site.getService("$.module." + navigateable);
        ISelection selection = site.getService("$.workbench.selection");
        if(selection.selectedModule()!=null&&selection.selectedModule().equals(module)){
            return;
        }
        ISurfaceHost host = site.getService("$.workbench.host");

        host.showWindow(module);
        selection.setSelectedModule(module);
    }

    @Override
    public BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedEvent(Activity on) {
        if (event != null) {
            return event;
        }
        event = new OnNavigationItemSelectedEvent(on);
        return event;
    }

    class OnNavigationItemSelectedEvent implements BottomNavigationView.OnNavigationItemSelectedListener {
        Activity on;
        public OnNavigationItemSelectedEvent( Activity on) {
            this.on=on;
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            try {
                String name=on.getResources().getResourceName(item.getItemId());
                int pos=name.indexOf("/");
                if(pos>-1){
                    name=name.substring(pos+1,name.length());
                }
                navigate(name);
//                item.setChecked(true);
                return true;
            } catch (Exception e) {
                Log.e("Nav", e.getMessage());
                return false;
            }
        }
    }
}
