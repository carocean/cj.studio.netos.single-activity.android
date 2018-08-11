package cj.studio.netos.module;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cj.studio.netos.R;
import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.IAxon;
import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.IModule;
import cj.studio.netos.framework.INavigation;
import cj.studio.netos.framework.IViewport;


public class NetflowModule extends Fragment implements IModule {

    private List initData() {
        List mDatas = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,1,2,3,4,5,6));
        return mDatas;
    }
    @Override
    public boolean isBottomNavigationViewVisibility() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_netflow_module, container, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.channels_recycler);
        mRecyclerView.setAdapter(new MyRecyclerAdapter(this.getContext(), initData()));
//        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);


        Log.i("-------",mRecyclerView.getAdapter().getItemCount()+"");

        return view;
    }

    @Override
    public void input(Frame frame, ICell cell) {
        IAxon axon=cell.axon();
        Frame f=new Frame("test /test/ netos/1.0");
        axon.output("netos.mpusher",f);
    }

    @Override
    public String name() {
        return "netflow";
    }

    @Override
    public int viewport() {
        return R.layout.layout_module_viewport;
    }

    @Override
    public void renderTo(IViewport viewport, Activity on, ICell cell) {
        viewport.setToolbarInfo("网流",true, on);
    }

    @Override
    public boolean onToolbarMenuInstall(MenuInflater menuInflater, Menu menu) {
        return true;
    }

    @Override
    public boolean onToolbarMenuSelected(MenuItem item, ICell cell) {
        INavigation navigation=cell.getService("$.workbench.navigation");
        switch (item.getItemId()) {
            case android.R.id.home:
                navigation.navigate("desktop");
                break;
        }
        return true;
    }

    public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyHolder> {

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
        public void onBindViewHolder(MyHolder holder, int position) {
            // TODO Auto-generated method stub
            holder.textView.setText(mDatas.get(position)+"--");
        }

        @Override
        // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
        public MyHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
            // 填充布局
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_netflow, null);
            MyHolder holder = new MyHolder(view);
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
