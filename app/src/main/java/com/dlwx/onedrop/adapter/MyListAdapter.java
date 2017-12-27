package com.dlwx.onedrop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.onedrop.R;

/**
 * Created by Administrator on 2017/11/8/008.
 */

public class MyListAdapter extends BaseFastAdapter {
    private String[] listNames;
    private int [] pics;
    public MyListAdapter(Context ctx,String[] listNames,int [] pics) {
        super(ctx);
        this.listNames = listNames;
        this.pics = pics;
    }

    @Override
    public int getCount() {
        return listNames.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.my_list, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
           vh = (ViewHolder) convertView.getTag();
        }
            vh.tv_name.setText(listNames[position]);
        Glide.with(ctx).load(pics[position]).into(vh.iv_pic);
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_name;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }
}
