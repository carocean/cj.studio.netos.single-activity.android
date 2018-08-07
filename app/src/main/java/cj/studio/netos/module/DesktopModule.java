package cj.studio.netos.module;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pedaily.yc.ycdialoglib.bottomMenu.CustomBottomDialog;
import com.pedaily.yc.ycdialoglib.bottomMenu.CustomItem;
import com.pedaily.yc.ycdialoglib.bottomMenu.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cj.studio.netos.R;
import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.IAxon;
import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.ICellsap;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.IViewport;


public class DesktopModule extends Fragment implements IModule {

    private List initData() {
        List mDatas = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,1,2,3,4,5,6));
        return mDatas;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_desktop_module, container, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.message_recycler);
        mRecyclerView.setAdapter(new DesktopModule.MyRecyclerAdapter(this.getContext(), initData()));
        return view;
    }


    @Override
    public void input(Frame frame, ICell cell) {
        IAxon axon = cell.axon();
        Frame f = new Frame("test /test/ netos/1.0");
        axon.output("netos.mpusher", f);
    }

    @Override
    public String name() {
        return "desktop";
    }

    @Override
    public int viewport() {
        return R.layout.layout_desktop_viewport;
    }

    @Override
    public void renderTo(IViewport viewport, Activity on, ICell cell) {
        ICellsap cellsap= cell.getService(ICellsap.class);

        viewport.setTitle("消息", on);
        FloatingActionButton fab=on.findViewById(R.id.desktop_fab);
//        fab.setBackgroundColor(0);
//        fab.setBackgroundResource(R.drawable.face);
        fab.setImageResource(R.mipmap.ic_face);
        fab.setOnClickListener(new MyOnclickListener(on,cellsap));
    }

    @Override
    public boolean onViewportMenuInstall(MenuInflater menuInflater, Menu menu) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_desktop, menu);
        return true;
    }

    @Override
    public boolean onViewportMenuSelected(MenuItem item) {
        return true;
    }

    class MyOnclickListener implements View.OnClickListener{
        private final ICellsap cellsap;
        Activity on;
        public  MyOnclickListener(Activity on, ICellsap cellsap){
            this.on=on;
            this.cellsap=cellsap;
        }
        @Override
        public void onClick(View v) {
            String principal=cellsap.principal();
            new CustomBottomDialog(on)
                    .title("我的桌面")
                    .setCancel(true,"取消")
                    .orientation(CustomBottomDialog.VERTICAL)
                    .inflateMenu(R.menu.menu_desktop_popup, new OnItemClickListener() {
                        @Override
                        public void click(CustomItem item) {

                        }
                    })
                    .show();
        }

    }
    public class MyRecyclerAdapter extends RecyclerView.Adapter<DesktopModule.MyRecyclerAdapter.MyHolder> {

        private Context mContext;
        private List<Integer> mDatas;

        public MyRecyclerAdapter(Context context, List<Integer> datas) {
            super();
            this.mContext = context;
            this.mDatas = datas;
        }

        @Override
        public int getItemCount() {
            // TODO Auto-generated method stub
            return mDatas.size();
        }

        @Override
        // 填充onCreateViewHolder方法返回的holder中的控件
        public void onBindViewHolder(DesktopModule.MyRecyclerAdapter.MyHolder holder, int position) {
            // TODO Auto-generated method stub
            holder.textView.setText(mDatas.get(position)+"--");
        }

        @Override
        // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
        public DesktopModule.MyRecyclerAdapter.MyHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
            // 填充布局
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_netflow_recycleritem, null);
            DesktopModule.MyRecyclerAdapter.MyHolder holder = new DesktopModule.MyRecyclerAdapter.MyHolder(view);
            return holder;
        }

        // 定义内部类继承ViewHolder
        class MyHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            public MyHolder(View view) {
                super(view);
                textView = (TextView) view.findViewById(R.id.test_textView);
            }

        }


    }
}
