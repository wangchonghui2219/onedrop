package com.dlwx.onedrop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.onedrop.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/25/025.
 */

public class PicAdapter extends BaseFastAdapter {
    private List<String> pingjia_img;
    public PicAdapter(Context ctx,List<String> pingjia_img) {
        super(ctx);
        this.pingjia_img = pingjia_img;
    }

    @Override
    public int getCount() {
        return pingjia_img.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_pic, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Glide.with(ctx).load(pingjia_img.get(position)).into(vh.iv_pic);
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
        }

    }
}
