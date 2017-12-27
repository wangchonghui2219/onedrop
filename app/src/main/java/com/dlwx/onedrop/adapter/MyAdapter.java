package com.dlwx.onedrop.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.onedrop.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8/008.
 */

public class MyAdapter extends BaseFastAdapter {
    private List<String> privates;
    private int checkPosi;

    public MyAdapter(Context ctx, List<String> privates) {
        super(ctx);
        this.privates = privates;
    }

    @Override
    public int getCount() {
        return privates.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_hot, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (position == checkPosi) {

            vh.tv_name.setBackgroundResource(R.drawable.shap_hotcity_check_bg);
            vh.tv_name.setTextColor(ContextCompat.getColor(ctx,R.color.white));
        }else{
            vh.tv_name.setBackgroundResource(R.drawable.shape_hotcity_bg);
            vh.tv_name.setTextColor(ContextCompat.getColor(ctx,R.color.black));

        }
        String hotCity = privates.get(position);
        vh.tv_name.setText(hotCity);
        return convertView;
    }

    public void setCheckpos(int position) {
        this.checkPosi = position;
        notifyDataSetChanged();
    }

    private   class ViewHolder {
        public View rootView;
        public TextView tv_name;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }
}
