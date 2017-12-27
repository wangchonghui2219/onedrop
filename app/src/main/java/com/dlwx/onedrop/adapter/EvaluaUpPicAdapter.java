package com.dlwx.onedrop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.onedrop.R;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/11/11/011.
 */

public class EvaluaUpPicAdapter extends BaseFastAdapter {
    private List<File> pics;
    public EvaluaUpPicAdapter(Context ctx,List<File> pics) {
        super(ctx);
        this.pics = pics;
    }

    @Override
    public int getCount() {
        return pics.size()+1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_uppic, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        if (position == pics.size()) {
            if (pics.size() >=3) {
                Glide.with(ctx).load("dasdsadas").into(vh.iv_addpic);
            }else{

                Glide.with(ctx).load(R.mipmap.icon_tianjia).into(vh.iv_addpic);
            }
        }else{
            Glide.with(ctx).load(pics.get(position)).into(vh.iv_addpic);
        }
        return convertView;
    }

   private class ViewHolder {
        public View rootView;
        public ImageView iv_addpic;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_addpic = (ImageView) rootView.findViewById(R.id.iv_addpic);
        }

    }
}
