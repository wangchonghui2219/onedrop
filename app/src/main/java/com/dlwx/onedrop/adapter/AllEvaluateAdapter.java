package com.dlwx.onedrop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.AllEvaluteBean;
import com.example.xlhratingbar_lib.XLHRatingBar;

import java.util.List;

/**
 * Created by Administrator on 2017/11/16/016.
 */

public class AllEvaluateAdapter extends BaseFastAdapter {
    private List<AllEvaluteBean.BodyBean.ListBean> list;
    public AllEvaluateAdapter(Context ctx,List<AllEvaluteBean.BodyBean.ListBean> list) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_evaluat, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        AllEvaluteBean.BodyBean.ListBean listBean = list.get(position);
        vh.tv_nickname.setText(listBean.getUser_nickname());
        vh.tv_date.setText(listBean.getCreate_time());
        vh.ev_ratingBar.setCountNum(listBean.getRate_level());
        List<String> pingjia_img = listBean.getPingjia_img();
        if (pingjia_img != null && pingjia_img.size() == 0) {
            vh.gv_list.setVisibility(View.GONE);
        }else{
            vh.gv_list.setVisibility(View.VISIBLE);
            vh.gv_list.setAdapter(new PicAdapter(ctx,pingjia_img));
        }
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_nickname;
        public TextView tv_date;
        public XLHRatingBar ev_ratingBar;
        public TextView tv_content;
        public GridView gv_list;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_nickname = (TextView) rootView.findViewById(R.id.tv_nickname);
            this.tv_date = (TextView) rootView.findViewById(R.id.tv_date);
            this.ev_ratingBar = (XLHRatingBar) rootView.findViewById(R.id.ev_ratingBar);
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            this.gv_list = (GridView) rootView.findViewById(R.id.gv_list);
        }

    }
}
