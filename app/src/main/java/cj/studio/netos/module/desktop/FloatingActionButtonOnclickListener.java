package cj.studio.netos.module.desktop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pedaily.yc.ycdialoglib.bottomLayout.BottomDialogFragment;
import com.pedaily.yc.ycdialoglib.bottomMenu.CustomBottomDialog;
import com.pedaily.yc.ycdialoglib.bottomMenu.CustomItem;
import com.pedaily.yc.ycdialoglib.bottomMenu.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import cj.studio.netos.R;
import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.ICellsap;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.IServiceProvider;
import cj.studio.netos.framework.thirty.BottomNavigationViewEx;
import cj.studio.netos.framework.util.ForbiddenNavigationAnimation;
import cj.studio.netos.framework.util.WindowUtil;
import q.rorbin.badgeview.Badge;

public class FloatingActionButtonOnclickListener implements View.OnClickListener {
    private final IServiceProvider site;
    AppCompatActivity on;
    INavigation navigation;

    public FloatingActionButtonOnclickListener(Activity on, IServiceProvider site) {
        this.on = (AppCompatActivity) on;
        this.site = site;
    }

    @Override
    public void onClick(View v) {
        BottomDialogFragment dialog = BottomDialogFragment.create(on.getSupportFragmentManager());
        BottomDialogFragment.ViewListener viewListener = new MyViewListener(on, dialog, site);
        dialog.setViewListener(viewListener);
        dialog.setLayoutRes(R.layout.dialog_bottom)
                .setDimAmount(0.5f)
                .setTag("BottomDialog")
                .setCancelOutside(true)
                .setHeight(WindowUtil.getScreenHeight(on) / 2 - 180)
                .show();
    }

    public void setNavigation(INavigation navigation) {
        this.navigation = navigation;
    }

    class MyViewListener extends Dialog implements BottomDialogFragment.ViewListener {
        private final ICellsap cellsap;
        AppCompatActivity on;
        List<CustomItem> items;
        BottomDialogFragment dialog;

        public MyViewListener(AppCompatActivity on, BottomDialogFragment dialog, IServiceProvider site) {
            super(on.getApplication());
            this.on = on;
            items = new ArrayList<>();
            this.dialog = dialog;
            this.cellsap = site.getService(ICellsap.class);
        }

        @SuppressLint("RestrictedApi")
        void inflateMenu(int menu) {
            MenuInflater menuInflater = new SupportMenuInflater(getContext());
            MenuBuilder menuBuilder = new MenuBuilder(getContext());
            menuInflater.inflate(menu, menuBuilder);

            for (int i = 0; i < menuBuilder.size(); i++) {
                MenuItem menuItem = menuBuilder.getItem(i);
                items.add(new CustomItem(menuItem.getItemId(), menuItem.getTitle().toString(), menuItem.getIcon()));
            }

        }

        @Override
        public void bindView(View v) {
            String principal = cellsap.principal();

            TextView textView = v.findViewById(R.id.tv_title);
            textView.setText(String.format("%s 的 netos", principal));

            final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.popup_recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(on));
            inflateMenu(R.menu.desktop_popup);

            DesktopPopupAdapter mAdapter = new DesktopPopupAdapter(on, items, CustomBottomDialog.VERTICAL);
            mAdapter.setItemClick(new OnItemClickListener() {
                @Override
                public void click(CustomItem item) {
                    Log.i("切换desktop region", item.getTitle());
                    if (navigation == null) {
                        return;
                    }
                    String name = on.getResources().getResourceName(item.getId());
                    int spliter = name.indexOf("/");
                    if (spliter > -1) {
                        name = name.substring(spliter + 1, name.length());
                    }
                    if(navigation.navigate(name)){
                        dialog.dismiss();
                    }
                }
            });

            recyclerView.setAdapter(mAdapter);

            final BottomNavigationViewEx bottomNavigationView = v.findViewById(R.id.popup_navigation);
            ForbiddenNavigationAnimation.disableShiftMode(bottomNavigationView);
            if (bottomNavigationView.getMenu().size() > 0) {
                bottomNavigationView.setIconTintList(0, on.getResources().getColorStateList(R.color.colorGray));
                bottomNavigationView.setTextTintList(0, on.getResources().getColorStateList(R.color.colorGray));
                bottomNavigationView.setBadgeAt(0, 25, 20, 0, on, new Badge.OnDragStateChangedListener() {
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                        if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState)
                            Toast.makeText(on, R.string.tips_badge_removed, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            final INavigation navigation = site.getService("$.workbench.navigation");
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Log.i("CustomItem", item.getTitle() + "");
                    int pos = bottomNavigationView.getMenuItemPosition(item);
                    if (pos == 0) {
                        bottomNavigationView.setIconTintList(0, on.getResources().getColorStateList(R.color.selector_item_primary_color));
                        bottomNavigationView.setTextTintList(0, on.getResources().getColorStateList(R.color.selector_item_primary_color));
                    }
                    String name = on.getResources().getResourceName(item.getItemId());
                    int spliter = name.indexOf("/");
                    if (spliter > -1) {
                        name = name.substring(spliter + 1, name.length());
                    }
                    if(navigation.navigate(name)) {
                        dialog.dismiss();
                    }
                    return true;
                }
            });
        }
    }
}