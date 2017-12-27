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
import com.dlwx.onedrop.bean.MyOrderBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/11/011.
 */

public class MyOrderItemAdar extends BaseFastAdapter {
    private List<MyOrderBean.BodyBean.ProductBean.ListBean> list;
    public MyOrderItemAdar(Context ctx, List<MyOrderBean.BodyBean.ProductBean.ListBean> list) {
        super(ctx);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_order_item, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        MyOrderBean.BodyBean.ProductBean.ListBean listBean = list.get(position);
        vh.tv_desc.setText(listBean.getTitle());
        Glide.with(ctx).load(listBean.getTitle_pic()).into(vh.iv_pic);
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_desc;
        public ImageView iv_pic;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_desc = (TextView) rootView.findViewById(R.id.tv_desc);
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
        }

    }
}
