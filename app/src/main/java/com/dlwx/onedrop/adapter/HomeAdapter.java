package com.dlwx.onedrop.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.ClassIdBean;
import com.dlwx.onedrop.views.ZQImageViewRoundOval;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15/015.
 */

public class HomeAdapter extends BaseFastAdapter {
    private List<ClassIdBean.BodyBean.ListBean> list;
    public HomeAdapter(Context ctx,List<ClassIdBean.BodyBean.ListBean> list) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_home, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) vh.iv_pic.getLayoutParams();
        layoutParams.width = widthPixels;
        layoutParams.height = widthPixels/2;
        vh.iv_pic.setLayoutParams(layoutParams);
        vh.iv_pic.setType(ZQImageViewRoundOval.TYPE_ROUND);
        vh.iv_pic.setRoundRadius(20);//矩形凹行大小
        ClassIdBean.BodyBean.ListBean listBean = list.get(position);
        Glide.with(ctx).load(listBean.getTitle_pic()).into(vh.iv_pic);
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public ZQImageViewRoundOval iv_pic;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ZQImageViewRoundOval) rootView.findViewById(R.id.iv_pic);
        }

    }
}
