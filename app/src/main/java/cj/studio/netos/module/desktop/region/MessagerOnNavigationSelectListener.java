package cj.studio.netos.module.desktop.region;

import android.content.res.Resources;
import android.util.Log;
import android.widget.RadioGroup;

import cj.studio.netos.framework.view.BadgeRadioButton;
import cj.studio.netos.framework.view.CJBottomNavigationView;

public class MessagerOnNavigationSelectListener implements CJBottomNavigationView.OnCheckedChangeListener {
    Resources resources;

    public MessagerOnNavigationSelectListener( Resources resources) {
        this.resources = resources;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        String name = resources.getResourceName(checkedId);
        int spliter = name.indexOf("/");
        if (spliter > -1) {
            name = name.substring(spliter + 1, name.length());
        }
        Log.i("test", "......." + "..." + name);
        CJBottomNavigationView view=(CJBottomNavigationView)group;
        if(view.getChildCount()>0){
            BadgeRadioButton badgeRadioButton=(BadgeRadioButton)view.getChildAt(0);
            badgeRadioButton.setBadgeNumber(-1);
        }
    }


}