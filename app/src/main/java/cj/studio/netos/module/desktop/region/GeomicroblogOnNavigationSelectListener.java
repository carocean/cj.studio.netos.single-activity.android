package cj.studio.netos.module.desktop.region;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import cj.studio.netos.R;
import cj.studio.netos.framework.thirty.BottomNavigationViewEx;

public class GeomicroblogOnNavigationSelectListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        BottomNavigationViewEx bottomNavigationView;
        Resources resources;
        public GeomicroblogOnNavigationSelectListener(BottomNavigationViewEx bottomNavigationView, Resources resources) {
            this.bottomNavigationView=bottomNavigationView;
            this.resources=resources;
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int pos=bottomNavigationView.getMenuItemPosition(item);
            if(pos==0){
                bottomNavigationView.setIconTintList(0,resources.getColorStateList(R.color.selector_item_primary_color));
                bottomNavigationView.setTextTintList(0,resources.getColorStateList(R.color.selector_item_primary_color));
            }
            String name=resources.getResourceName(item.getItemId());
            int spliter=name.indexOf("/");
            if(spliter>-1){
                name=name.substring(spliter+1,name.length());
            }
            Log.i("test", "......." + item.getTitle()+"..."+name);
            return true;
        }
    }