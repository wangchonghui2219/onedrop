package com.dlwx.onedrop.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.MessListBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/17/017.
 */

public class SysMessAdapter extends BaseFastAdapter {
    private List<MessListBean.BodyBean> body;

    public SysMessAdapter(Context ctx, List<MessListBean.BodyBean> body) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_sysmess, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        MessListBean.BodyBean bodyBean = body.get(position);
        vh.tv_date.setText(bodyBean.getCreatedtime());
        Spanned spanned = Html.fromHtml(bodyBean.getInfo());
        Spanned spanned1 = Html.fromHtml(String.valueOf(spanned));
        vh.tv_content.setText(spanned1);
        vh.tv_title.setText(bodyBean.getTitle());

        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_date;
        public TextView tv_title;
        public TextView tv_content;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_date = (TextView) rootView.findViewById(R.id.tv_date);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
        }

    }
}
