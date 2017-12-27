package com.dlwx.onedrop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.ShopCatBean;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购物车列表
 */

public class ShopCatAdapter extends BaseFastAdapter {
    public Map<String, String> map;
    public List<ShopCatBean.BodyBean.ListBean> list;

    public ShopCatAdapter(Context ctx, List<ShopCatBean.BodyBean.ListBean> list) {
        super(ctx);
        this.list = list;
        map = new HashMap<>();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_shop_cat, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final ShopCatBean.BodyBean.ListBean listBean = list.get(position);
        Glide.with(ctx).load(listBean.getTitle_pic()).into(vh.ivPic);
        vh.tvName.setText(listBean.getTitle());
        vh.tvType.setText(listBean.getClassname());
        vh.tvPrice.setText("￥" + listBean.getPrice());
        vh.tvNums.setText("x"+listBean.getNum());
        vh.tvNum.setText(listBean.getNum()+"");
        double totalPrice = listBean.getNum() * listBean.getPrice();
        DecimalFormat df = new DecimalFormat("#.00");
        totalPrice = Double.parseDouble(df.format(totalPrice));
        vh.tv_totalprice.setText("￥"+(totalPrice));
        vh.cbCheck.setChecked(listBean.isCheck());
        if (isEditext) {
            vh.llEdit.setVisibility(View.VISIBLE);
            vh.btn_delete.setVisibility(View.VISIBLE);
            vh.tvNums.setVisibility(View.GONE);
        }else{
            vh.llEdit.setVisibility(View.GONE);
            vh.btn_delete.setVisibility(View.GONE);
            vh.tvNums.setVisibility(View.VISIBLE);
        }
        vh.cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                listBean.setCheck(checked);
                if (onChecklistener != null) {
                    onChecklistener.getResult();
                }
            }
        });
        vh.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean.setNum(listBean.getNum()+1);
                map.put(listBean.getCarid(),listBean.getNum()+"");
                notifyDataSetChanged();
                if (onChecklistener != null) {
                    onChecklistener.setNum(listBean.getCarid(),listBean.getNum());
                }
            }
        });
        vh.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listBean.getNum()==1) {
                    listBean.setNum(1);

                }else{
                    listBean.setNum(listBean.getNum()-1);

                }
                map.put(listBean.getCarid(),listBean.getNum()+"");
                if (onChecklistener != null) {
                    onChecklistener.setNum(listBean.getCarid(),listBean.getNum());
                }
                notifyDataSetChanged();

            }
        });
        vh.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onChecklistener != null) {
                   onChecklistener.delete(listBean.getCarid(),position);
                }
            }
        });
        return convertView;
    }

    private boolean isEditext;

    /**
     * 编辑
     *
     * @param isEditext
     */
    public void setEdiState(boolean isEditext) {
        this.isEditext = isEditext;
        notifyDataSetChanged();
    }

    public void seleteCheck() {
        if (onChecklistener != null) {
            onChecklistener.getResult();
        }
        notifyDataSetChanged();
    }

    class ViewHolder {
        @BindView(R.id.cb_check)
        CheckBox cbCheck;
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.iv_minus)
        ImageView ivMinus;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.iv_add)
        ImageView ivAdd;
        @BindView(R.id.ll_edit)
        LinearLayout llEdit;
        @BindView(R.id.tv_nums)
        TextView tvNums;
        @BindView(R.id.tv_totalprice)
        TextView tv_totalprice;
        @BindView(R.id.btn_delete)
        Button btn_delete;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private OnCheckListener onChecklistener;

    public interface OnCheckListener {
        void getResult();
        void setNum(String cartId,int num);
        void delete(String cartId,int pos);
    }

    public void setOnCheckListener(OnCheckListener onChecklistener) {
        this.onChecklistener = onChecklistener;
    }
}
