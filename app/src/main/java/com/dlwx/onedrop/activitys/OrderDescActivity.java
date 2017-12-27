package com.dlwx.onedrop.activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.AliPayBean;
import com.dlwx.onedrop.bean.MyOrderDescBean;
import com.dlwx.onedrop.bean.ResuleBean;
import com.dlwx.onedrop.utiles.AlPay.AliPayUtiles;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 订单详情
 */
public class OrderDescActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_totalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.ll_product)
    LinearLayout ll_product;
    private String orderid;
    private int status;
    private MyOrderDescBean.BodyBean body;
    private String productid;
    private PayViewHolder payViewHolder;
    private String order_num;


    @Override
    protected void initView() {
        orderid = getIntent().getStringExtra("orderid");
        order_num = getIntent().getStringExtra("order_num");
        setContentView(R.layout.activity_order_desc);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.orderdesc);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getDesc();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.ll_product, R.id.btn_submit})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_product://跳转商品详情
                intent = new Intent(ctx, ProductDescActivity.class);
                intent.putExtra("productid", productid);
                startActivity(intent);
                break;
            case R.id.btn_submit://去评价/确认收货
                switch (status) {
                    case 0:
                        showPayPopu();
                        break;
                    case 2:
                        tvType.setText("订单待发货");
                        btnSubmit.setVisibility(View.GONE);
                        break;
                    case 3:
                       shouhuo();
                        break;
                    case 4:
//                        intent = new Intent(ctx, EvaluateActivity.class);
//                        intent.putExtra("productid", productid);
//                        startActivity(intent);
                        break;
                }
                break;
        }
    }

    /**
     * 收货
     */
    private void shouhuo() {
        HttpType = 3;

        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("orderid", orderid);
        mPreenter.fetch(map, true, HtttpUrlUtils.Shouhuo, "");
    }

    /**
     * 获取详情
     */
    private void getDesc() {
        HttpType = 1;
        wch("order:"+orderid);
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        if (TextUtils.isEmpty(order_num)) {
            btnSubmit.setVisibility(View.VISIBLE);
            map.put("orderid", orderid);
        }else{
            map.put("order_num", order_num);

        }
        mPreenter.fetch(map, true, HtttpUrlUtils.OrderDesc, HtttpUrlUtils.OrderDesc + Token);

    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {

            orderDescData(s, gson);
        } else if (HttpType == 2) {//去付款
            toPay(s, gson);
        }else if (HttpType == 3) {
            ResuleBean resuleBean = gson.fromJson(s, ResuleBean.class);
            if (resuleBean.getCode() == 200) {
                finish();
            }
            Toast.makeText(ctx, resuleBean.getResult(), Toast.LENGTH_SHORT).show();
        }

    }

    private void toPay(String s, Gson gson) {
        if (paytype == 1) {
            AliPayBean aliPayBean = gson.fromJson(s, AliPayBean.class);
            if (aliPayBean.getCode() == 200) {
                String body = aliPayBean.getBody();
                AliPayUtiles aliPayUtiles = new AliPayUtiles((Activity) ctx, body);
                aliPayUtiles.startPay();
                aliPayUtiles.setOnResultListener(new AliPayUtiles.PayResultOnListener() {
                    @Override
                    public void getResult(Boolean result) {
                        if (result) {
                            finish();
                            startActivity(new Intent(ctx, PaySuccessActivity.class));
                        }
                    }
                });

            } else {

                Toast.makeText(ctx, aliPayBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void orderDescData(String s, Gson gson) {
        MyOrderDescBean myOrderDescBean = gson.fromJson(s, MyOrderDescBean.class);
        if (myOrderDescBean.getCode() == 200) {
            body = myOrderDescBean.getBody();
            status = body.getStatus();
            switch (status) {
                case 0:
                    tvType.setText("订单待付款");
                    btnSubmit.setText("去付款");
                    break;
                case 1:
                    tvType.setText("订单待发货");
                    btnSubmit.setVisibility(View.GONE);
                    break;
                case 2:
                    tvType.setText("订单已发货");
                    btnSubmit.setText("收货");
                    break;
                case 3:
                    tvType.setText("订单已发货");
                    btnSubmit.setVisibility(View.GONE);
                    btnSubmit.setText("去评价");
                    break;
            }
            tvDate.setText(body.getCreatedtime());
            MyOrderDescBean.BodyBean.ProductBean product = body.getProduct();

            MyOrderDescBean.BodyBean.ProductBean.ListBean listBean = product.getList().get(0);
            productid = listBean.getProductid();
            wch("productid:"+productid);
            tvDesc.setText(listBean.getTitle());
            tvPrice.setText(listBean.getPrice() + "元");
            tvNum.setText("x" + listBean.getNum());
            Glide.with(ctx).load(listBean.getTitle_pic()).into(ivPic);
            double toatl = listBean.getPrice() * listBean.getNum();
            DecimalFormat df = new DecimalFormat("#.00");
            toatl = Double.parseDouble(df.format(toatl));
            tvTotalPrice.setText(toatl + "元");

            tvName.setText(body.getAddr_name());
            tvPhone.setText(body.getAddr_tel());
            tvAddress.setText(body.getAddr_addr());
            tvCode.setText(body.getProduct_orderid());

        } else {
            Toast.makeText(ctx, myOrderDescBean.getResult(), Toast.LENGTH_SHORT).show();

        }
    }

    private int paytype = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_aliPay:
                paytype = 1;
                payViewHolder.cb_alicheck.setChecked(true);
                payViewHolder.cb_wxcheck.setChecked(false);
                break;
            case R.id.ll_wxPay:
                paytype = 2;
                payViewHolder.cb_alicheck.setChecked(false);
                payViewHolder.cb_wxcheck.setChecked(true);
                break;
            case R.id.btn_pay:
                HttpType = 2;
                Map<String, String> map = new HashMap<>();
                map.put("token", Token);
                map.put("orderid", orderid);
                map.put("paytype", paytype + "");
                mPreenter.fetch(map, false, HtttpUrlUtils.ToPay, "");


                break;

        }
    }

    /**
     * 显示支付方式
     */
    private void showPayPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_pay, null);
        payViewHolder = new PayViewHolder(view);
        PopupWindow popu = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popu.setFocusable(true);
        popu.setOutsideTouchable(true);
        backgroundAlpha(0.7f);
        popu.showAtLocation(tvAddress, Gravity.BOTTOM, 0, 0);
        popu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        payViewHolder.btn_pay.setOnClickListener(this);
        payViewHolder.ll_aliPay.setOnClickListener(this);
        payViewHolder.ll_wxPay.setOnClickListener(this);
    }

    private class PayViewHolder {
        public View rootView;
        public CheckBox cb_alicheck;
        public LinearLayout ll_aliPay;
        public CheckBox cb_wxcheck;
        public LinearLayout ll_wxPay;
        public Button btn_pay;

        public PayViewHolder(View rootView) {
            this.rootView = rootView;
            this.cb_alicheck = (CheckBox) rootView.findViewById(R.id.cb_alicheck);
            this.ll_aliPay = (LinearLayout) rootView.findViewById(R.id.ll_aliPay);
            this.cb_wxcheck = (CheckBox) rootView.findViewById(R.id.cb_wxcheck);
            this.ll_wxPay = (LinearLayout) rootView.findViewById(R.id.ll_wxPay);
            this.btn_pay = (Button) rootView.findViewById(R.id.btn_pay);
        }

    }
}
