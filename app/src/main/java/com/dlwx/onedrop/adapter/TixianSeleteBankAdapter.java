package com.dlwx.onedrop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.BankCardListBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/11/011.
 */

public class TixianSeleteBankAdapter extends BaseFastAdapter {
    private List<BankCardListBean.BodyBean> body;
    public TixianSeleteBankAdapter(Context ctx,List<BankCardListBean.BodyBean> body) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_banklist, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_name.setText(body.get(position).getBank_name());
        vh.tv_name_num.setText(body.get(position).getName_num());
        if (position == i) {
            vh.iv_cleck.setVisibility(View.VISIBLE);
        }else{
            vh.iv_cleck.setVisibility(View.GONE);
        }
        return convertView;
    }
    private int i;
    public void setUp(int i) {
        this.i = i;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_name;
        public TextView tv_name_num;
        public ImageView iv_cleck;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_name_num = (TextView) rootView.findViewById(R.id.tv_name_num);
            this.iv_cleck = (ImageView) rootView.findViewById(R.id.iv_cleck);
        }

    }
}
