package com.dlwx.onedrop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.MyCommissBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/11/011.
 */

public class CommissionAdapter extends BaseFastAdapter {
    private List<MyCommissBean.BodyBean> body;
    public CommissionAdapter(Context ctx, List<MyCommissBean.BodyBean> body) {
        super(ctx);
        this.body = body;
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_commiss, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        MyCommissBean.BodyBean bodyBean = body.get(position);
        vh.tv_date.setText(bodyBean.getCreatedtime());
        vh.tv_mony.setText("+"+bodyBean.getChange_price());
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_date;
        public TextView tv_mony;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_date = (TextView) rootView.findViewById(R.id.tv_date);
            this.tv_mony = (TextView) rootView.findViewById(R.id.tv_mony);
        }

    }
}
