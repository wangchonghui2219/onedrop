package com.dlwx.onedrop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.AddreListBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13/013.
 */

public class AddressAdapter extends BaseFastAdapter{
    private List<AddreListBean.BodyBean> body;
    public AddressAdapter(Context ctx,List<AddreListBean.BodyBean> body ) {
        super(ctx);
        this.body = body;
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_address, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final AddreListBean.BodyBean bodyBean = body.get(position);
        vh.tv_name.setText(bodyBean.getName());
        vh.tv_phone.setText(bodyBean.getTel());
        vh.tv_address.setText(bodyBean.getProvince()+bodyBean.getCity()+bodyBean.getArea());
        vh.tv_descaddress.setText(bodyBean.getAddr());
        if (bodyBean.getIsdefault().equals("1")) {
            vh.cb_check.setChecked(true);
        }else{
            vh.cb_check.setChecked(false);
        }
        vh.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleListener.edit(position);
            }
        });
        vh.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleListener.delete(position);
            }
        });
        vh.cb_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bodyBean.getIsdefault().equals("1")) {
                    boolean cb =  ((CheckBox)view).isChecked();
                    handleListener.setIsDefuult(cb,position);
                }else{
                    vh.cb_check.setChecked(true);
                }

            }
        });
        return convertView;
    }

   private class ViewHolder {
        public View rootView;
        public TextView tv_name;
        public TextView tv_phone;
        public TextView tv_address;
        public TextView tv_descaddress;
        public TextView btn_edit;
        public TextView btn_delete;
        public CheckBox cb_check;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_phone = (TextView) rootView.findViewById(R.id.tv_phone);
            this.tv_address = (TextView) rootView.findViewById(R.id.tv_address);
            this.tv_descaddress = (TextView) rootView.findViewById(R.id.tv_descaddress);
            this.btn_edit = (TextView) rootView.findViewById(R.id.btn_edit);
            this.btn_delete = (TextView) rootView.findViewById(R.id.btn_delete);
            this.cb_check = rootView.findViewById(R.id.cb_check);
        }

    }
    private HandleListener handleListener;
    public interface HandleListener{
        void edit(int i);
        void delete(int i);
        void setIsDefuult(boolean isDefuult,int pos);
    }
    public void setHandleListener(HandleListener handleListener){
        this.handleListener = handleListener;
    }


}
