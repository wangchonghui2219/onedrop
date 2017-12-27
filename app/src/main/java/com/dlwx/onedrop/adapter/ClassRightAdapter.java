package com.dlwx.onedrop.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.ClassIdBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13/013.
 */

public class ClassRightAdapter extends BaseFastAdapter {
    private List<ClassIdBean.BodyBean.ListBean> list;

    public ClassRightAdapter(Context ctx, List<ClassIdBean.BodyBean.ListBean> list) {
        super(ctx);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_calass, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        CardView.LayoutParams layoutParams = (CardView.LayoutParams) vh.iv_pic.getLayoutParams();
        layoutParams.width = widthPixels/7*5;
        layoutParams.height = widthPixels/7*5/2;
        vh.iv_pic.setLayoutParams(layoutParams);
        ClassIdBean.BodyBean.ListBean listBean = list.get(position);
        Glide.with(ctx).load(listBean.getTitle_pic()).into(vh.iv_pic);
        vh.tv_title.setText(listBean.getTitle());
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_title;
        public CardView cardview;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_title = rootView.findViewById(R.id.tv_title);
            this.cardview = rootView.findViewById(R.id.cardview);
        }

    }
}
