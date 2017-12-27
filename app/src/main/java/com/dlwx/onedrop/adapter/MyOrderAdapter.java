package com.dlwx.onedrop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.MyOrderBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/11/011.
 */

public class MyOrderAdapter extends BaseFastAdapter {
    private List<MyOrderBean.BodyBean> body;
    public MyOrderAdapter(Context ctx,List<MyOrderBean.BodyBean> body) {
        super(ctx);
        this.body = body;
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.iem_base_order, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        MyOrderBean.BodyBean bodyBean = body.get(position);
        vh.tv_date.setText(bodyBean.getCreatedtime());
        vh.tv_ordercode.setText("订单编号："+bodyBean.getProduct_orderid());
        MyOrderBean.BodyBean.ProductBean product = bodyBean.getProduct();
        List<MyOrderBean.BodyBean.ProductBean.ListBean> list = product.getList();
        vh.lv_list.setAdapter(new MyOrderItemAdar(ctx,list));
        final int status = bodyBean.getStatus();
        if (status == 0) {
            vh.tv_type.setText("订单待付款");
        }else if (status == 1) {
            vh.tv_type.setText("订单待发货");
        }else if (status == 1) {
            vh.tv_type.setText("订单已发货");
        }else if (status == 1) {
            vh.tv_type.setText("订单待评价");
        }




        vh.lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (activityInterface!=null)
                    activityInterface.start(position,i);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_type;
        public TextView tv_date;
        public MyListView lv_list;
        public TextView tv_ordercode;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_type = (TextView) rootView.findViewById(R.id.tv_type);
            this.tv_date = (TextView) rootView.findViewById(R.id.tv_date);
            this.lv_list = (MyListView) rootView.findViewById(R.id.lv_list);
            this.tv_ordercode = (TextView) rootView.findViewById(R.id.tv_ordercode);
        }

    }

    public interface StartActivityInterface{
        void start(int position,int i);
    }
    private StartActivityInterface activityInterface;

    public void setActivityInterface(StartActivityInterface activityInterface){
        this.activityInterface = activityInterface;
    }

}
