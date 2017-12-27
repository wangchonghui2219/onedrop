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
import com.dlwx.onedrop.bean.SubOrderBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/16/016.
 */

public class SubOrderAdapter extends BaseFastAdapter {
    private  List<SubOrderBean.BodyBean.InfoBean.ListBean> list;

    public SubOrderAdapter(Context ctx,  List<SubOrderBean.BodyBean.InfoBean.ListBean> list) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_suborder, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        SubOrderBean.BodyBean.InfoBean.ListBean listBean = list.get(position);
        vh.tv_name.setText(listBean.getTitle());
        vh.tv_price.setText("￥"+listBean.getPrice());
        vh.tv_num.setText("x"+listBean.getNum());
        Glide.with(ctx).load(listBean.getTitle_pic()).into(vh.iv_pic);
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_name;
        public TextView tv_num;
        public TextView tv_price;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
            this.tv_price = (TextView) rootView.findViewById(R.id.tv_price);
        }

    }
}
