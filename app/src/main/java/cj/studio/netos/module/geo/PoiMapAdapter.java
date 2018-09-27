package cj.studio.netos.module.geo;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.d.lib.xrv.adapter.CommonAdapter;
import com.d.lib.xrv.adapter.CommonHolder;

import java.util.List;

import cj.studio.netos.R;

/**
 * POI地图
 * Created by D on 2017/7/19.
 */
public class PoiMapAdapter extends CommonAdapter<PoiModel> {

    public PoiMapAdapter(Context context, List<PoiModel> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(final int position, final CommonHolder holder, final PoiModel item) {
        holder.setText(R.id.tv_name, item.label_title);
        holder.setText(R.id.tv_address, "30公里" + " | " + item.address);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "click at: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
