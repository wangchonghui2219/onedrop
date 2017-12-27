package com.dlwx.onedrop.fragments;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.activitys.SubmitOrderActivity;
import com.dlwx.onedrop.adapter.ShopCatAdapter;
import com.dlwx.onedrop.bean.ResuleBean;
import com.dlwx.onedrop.bean.ShopCatBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 购物车
 */
public class ShopCatFragment extends BaseFragment {
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.ll_entry)
    LinearLayout llEntry;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.cb_allcheck)
    CheckBox cbAllcheck;
    @BindView(R.id.tv_toatl)
    TextView tvToatl;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    Unbinder unbinder;
    private List<ShopCatBean.BodyBean.ListBean> list = new ArrayList<>();
    private ShopCatAdapter shopCatAdapter;
    private String cartId;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_shop_cat;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initDate() {
        shopCatAdapter = new ShopCatAdapter(ctx, list);
        lvList.setAdapter(shopCatAdapter);
        getCatS();

    }



    @Override
    protected void initListener() {
        cbAllcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = ((CheckBox) v).isChecked();
                double toatl = 0.0;
                if (isChecked) {

                    for (int i = 0; i < list.size(); i++) {
                        ShopCatBean.BodyBean.ListBean listBean = list.get(i);
                        listBean.setCheck(isChecked);
                        toatl += listBean.getPrice() * listBean.getNum();
                    }
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        ShopCatBean.BodyBean.ListBean listBean = list.get(i);
                        listBean.setCheck(isChecked);
                        toatl = 0.0;
                    }
                }
                DecimalFormat df = new DecimalFormat("#.00");
                toatl = Double.parseDouble(df.format(toatl));
                tvToatl.setText("￥" + toatl);
                shopCatAdapter.notifyDataSetChanged();
            }
        });

        shopCatAdapter.setOnCheckListener(new ShopCatAdapter.OnCheckListener() {

            @Override
            public void getResult() {
                double toatl = 0.0;
                int itemNum = 0;
                for (int i = 0; i < list.size(); i++) {
                    ShopCatBean.BodyBean.ListBean listBean = list.get(i);
                    if (listBean.isCheck()) {
                        toatl += listBean.getPrice() * listBean.getNum();
                        itemNum++;
                    }
                }
                DecimalFormat df = new DecimalFormat("#.00");
                toatl = Double.parseDouble(df.format(toatl));
                if (itemNum == list.size()) {
                    cbAllcheck.setChecked(true);
                } else {
                    cbAllcheck.setChecked(false);
                }
                tvToatl.setText("￥" + toatl);
            }

            @Override
            public void setNum(String cartId, int num) {
                revampNum(cartId,num);
            }

            @Override
            public void delete(String cartId,int pos) {
                position = pos;

                deleteProduct(cartId);
            }
        });

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShopCatBean.BodyBean.ListBean listBean = list.get(i);
                boolean check = listBean.isCheck();
                listBean.setCheck(!check);
                shopCatAdapter.seleteCheck();
            }
        });

    }




    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private boolean isEditext;//是否处于编辑状态
    @OnClick({R.id.tv_edit, R.id.btn_submit, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                if (isEditext) {
                    isEditext = !isEditext;
                    tvEdit.setText("编辑");
                    btnDelete.setVisibility(View.GONE);
                    shopCatAdapter.setEdiState(isEditext);
                } else {
                    isEditext = !isEditext;
                    tvEdit.setText("完成");
                    btnDelete.setVisibility(View.VISIBLE);
                    shopCatAdapter.setEdiState(isEditext);
                }
                break;
            case R.id.btn_submit:
                cartId = "";
                for (int i = 0; i < list.size(); i++) {
                    ShopCatBean.BodyBean.ListBean listBean = list.get(i);
                    boolean check = listBean.isCheck();
                    if (check) {

                        if (TextUtils.isEmpty(cartId)) {
                            cartId = listBean.getCarid();
                        }else{
                            cartId = cartId +","+listBean.getCarid();
                        }
                    }

                }
                Intent intent = new Intent(ctx, SubmitOrderActivity.class);
                intent.putExtra("ordertype","cat");
                intent.putExtra("carid", cartId);
                startActivity(intent);

                break;
            case R.id.btn_delete:
               cartId = "";
                for (int i = 0; i < list.size(); i++) {
                    ShopCatBean.BodyBean.ListBean listBean = list.get(i);
                    boolean check = listBean.isCheck();
                    if (check) {

                        if (TextUtils.isEmpty(cartId)) {
                            cartId = listBean.getCarid();
                        }else{
                            cartId = cartId +","+listBean.getCarid();
                        }
                    }

                }
                position = -1;
                deleteProduct(cartId);
                break;
        }
    }
    private int HttpType;
    /**
     * 获取购物车列表数据
     */
    private void getCatS() {
        HttpType = 1;
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        mPreenter.fetch(map,true, HtttpUrlUtils.shopcats,HtttpUrlUtils.shopcats+Token);
    }

    /**
     * 删除商品
     * @param cartId
     */
    private void deleteProduct(String cartId) {
        wch(cartId);
        HttpType = 2;
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("carid",cartId);
        mPreenter.fetch(map,true,HtttpUrlUtils.DeleteCat,"");
    }
    /**
     * 修改商品数量
     * @param cartId
     * @param num
     */
    private void revampNum(String cartId, int num) {
        OkGo.<String>get(HtttpUrlUtils.RevampCatNum)
                .params("token",Token)
                .params("carid",cartId)
                .params("num",num)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        Gson gson = new Gson();
                        ResuleBean resuleBean = gson.fromJson(s, ResuleBean.class);
                        if (resuleBean.getCode() == 200) {

                        }else{
                            Toast.makeText(ctx, resuleBean.getResult(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(ctx, "网络连接失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void showData(String s) {
        disLoading();
        Gson gson = new Gson();
        if (HttpType == 1) {

            ShopCatBean shopCatBean = gson.fromJson(s, ShopCatBean.class);
            if (shopCatBean.getCode() == 200) {
                list.clear();
                ShopCatBean.BodyBean body = shopCatBean.getBody();
                list.addAll(body.getList());
                if (list.size() == 0) {
                    llEntry.setVisibility(View.VISIBLE);
                }
//            tvToatl.setText("￥"+body.getTotal_price());
                shopCatAdapter.notifyDataSetChanged();
            }else{

            }
        }else if (HttpType == 2) {
            ResuleBean resuleBean = gson.fromJson(s, ResuleBean.class);
            if (resuleBean.getCode() == 200) {
                if (position >= 0) {

                    list.remove(position);

                }else{
                    list.clear();
                    llEntry.setVisibility(View.VISIBLE);

                }
                shopCatAdapter.notifyDataSetChanged();
                cbAllcheck.setChecked(false);
                tvToatl.setText("￥0.0");
            }
            Toast.makeText(ctx, resuleBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }
    private int position;

}
