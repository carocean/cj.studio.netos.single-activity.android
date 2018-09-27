package cj.studio.netos.module.desktop.region;

import android.content.res.Resources;
import android.util.Log;
import android.widget.RadioGroup;

import cj.studio.netos.framework.view.CJBottomNavigationView;

public class GeomicroblogOnNavigationSelectListener implements CJBottomNavigationView.OnCheckedChangeListener {
    Resources resources;

    public GeomicroblogOnNavigationSelectListener(Resources resources) {
        this.resources = resources;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        CJBottomNavigationView bottomNavigationView=(CJBottomNavigationView)group;
        String name = resources.getResourceName(checkedId);
        int spliter = name.indexOf("/");
        if (spliter > -1) {
            name = name.substring(spliter + 1, name.length());
        }
        Log.i("test", "......." +  "..." + name);
        return ;
    }
}