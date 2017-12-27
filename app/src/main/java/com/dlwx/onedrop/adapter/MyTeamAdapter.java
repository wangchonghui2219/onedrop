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
import com.dlwx.onedrop.bean.TeamCenBean;

import java.util.List;

/**
 * 我的团队
 */

public class MyTeamAdapter extends BaseFastAdapter {
    private List<TeamCenBean.BodyBean.PersonInfoBean> person_info;
    public MyTeamAdapter(Context ctx,List<TeamCenBean.BodyBean.PersonInfoBean> person_info) {
        super(ctx);
        this.person_info = person_info;
    }

    @Override
    public int getCount() {
        return person_info.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_myteam, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        TeamCenBean.BodyBean.PersonInfoBean personInfoBean = person_info.get(position);
        vh.tv_name.setText(personInfoBean.getNickname());
        vh.tv_number.setText(personInfoBean.getExten_code());
        Glide.with(ctx).load(personInfoBean.getHeader_pic()).into(vh.iv_pic);
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_name;
        public TextView tv_number;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_number = (TextView) rootView.findViewById(R.id.tv_number);
        }

    }
}
