package com.dlwx.onedrop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.BankCardListBean;

import java.util.List;

/**
 * 银行卡列表
 */

public class BankListAdapter extends BaseRecrviewAdapter {
    private List<BankCardListBean.BodyBean> body;
    public BankListAdapter(Context ctx,List<BankCardListBean.BodyBean> body) {
        super(ctx);
        this.body = body;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_bank, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BankCardListBean.BodyBean bodyBean = body.get(position);
        ((ViewHolder)holder).tv_name.setText(bodyBean.getBank_name());
        ((ViewHolder)holder).tv_type.setText(bodyBean.getBank_type());
        ((ViewHolder)holder).tv_number.setText(bodyBean.getBank_number());

    }

    @Override
    public int getItemCount() {
        return body.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_name;
        public TextView tv_type;
        public TextView tv_number;
        public LinearLayout ll_head;
        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_type = (TextView) rootView.findViewById(R.id.tv_type);
            this.tv_number = (TextView) rootView.findViewById(R.id.tv_number);
            this.ll_head = rootView.findViewById(R.id.ll_head);
        }

    }
}
