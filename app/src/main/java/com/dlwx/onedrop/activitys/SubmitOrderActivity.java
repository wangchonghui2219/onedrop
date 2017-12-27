package com.dlwx.onedrop.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.adapter.SubOrderAdapter;
import com.dlwx.onedrop.bean.AddreListBean;
import com.dlwx.onedrop.bean.AliPayBean;
import com.dlwx.onedrop.bean.OrderBean;
import com.dlwx.onedrop.bean.SubOrderBean;
import com.dlwx.onedrop.bean.WXPAtResultBean;
import com.dlwx.onedrop.utiles.AlPay.AliPayUtiles;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.dlwx.onedrop.utiles.wechatpay.WeChatPayUtiles;
import com.dlwx.onedrop.utiles.wechatpay.wxapi.WXPayResultListener;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 提交订单
 */
public class SubmitOrderActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rl_entry)
    RelativeLayout rlEntry;
    @BindView(R.id.tv_seleteAddress)
    TextView tvSeleteAddress;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_descaddress)
    TextView tvDescaddress;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.tv_totalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.ll_noentry)
    LinearLayout ll_noentry;
    private PayViewHolder payViewHolder;
    private int num;
    private String ordertype;
    private String productid;
    private String carid;
    private String uaid;
    private double totalprice;
    private String orderid;
    private SubOrderBean.BodyBean.AddrBean addr;
    private PopupWindow paypopu;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        num = intent.getIntExtra("num",1);
        ordertype = intent.getStringExtra("ordertype");
        productid = intent.getStringExtra("productid");
        carid = intent.getStringExtra("carid");
        setContentView(R.layout.activity_submit_order);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText("提交订单");
            getOrderData();

    }

    @Override
    protected void initListener() {

    }
    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.rl_entry, R.id.tv_seleteAddress, R.id.tv_edit, R.id.btn_submit})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_entry:
                intent = new Intent(ctx, ShippingAddressActivity.class);
                intent.putExtra("type","order");
                startActivityForResult(intent,1);
                break;
            case R.id.tv_seleteAddress:

                break;
            case R.id.tv_edit:
//                intent = new Intent(ctx, ShippingAddressActivity.class);
//                intent.putExtra("type","order");
//                startActivityForResult(intent,1);

                intent = new Intent(ctx,NewAddressActivity.class);
                intent.putExtra("title","地址编辑");
                intent.putExtra("uaid",addr.getUaid());
                intent.putExtra("name",addr.getName());
                intent.putExtra("phone",addr.getTel());
                intent.putExtra("province",addr.getProvince());
                intent.putExtra("city",addr.getCity());
                intent.putExtra("area",addr.getArea());
                intent.putExtra("addr",addr.getAddr());
                intent.putExtra("isdefault",isdefault);
               startActivityForResult(intent,2);
                break;
            case R.id.btn_submit:
                submitOrder();
                break;
        }
    }
    private String isdefault = "1";
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

                HttpType = 3;
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
        paypopu = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        paypopu.setFocusable(true);
        paypopu.setOutsideTouchable(true);
        paypopu.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.7f);
        paypopu.showAtLocation(tvAddress, Gravity.BOTTOM, 0, 0);
        paypopu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        payViewHolder.btn_pay.setOnClickListener(this);
        payViewHolder.ll_aliPay.setOnClickListener(this);
        payViewHolder.ll_wxPay.setOnClickListener(this);

    }


    public void getOrderData() {
        HttpType = 1;
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        if (ordertype.equals("cat")) {
            map.put("carid",carid);
        }else{

            map.put("productid",productid);
            map.put("num",num+"");
        }

        mPreenter.fetch(map,true, HtttpUrlUtils.AffOrder,"");

    }
    /**
     * 提交订单
     */
    private void submitOrder() {
        if (TextUtils.isEmpty(uaid)) {
            Toast.makeText(ctx, "收货地址不能为空", Toast.LENGTH_SHORT).show();
            vibrator.vibrate(50);
            return;
        }
        HttpType = 2;
        wch(Token+";"+carid+";"+totalprice);
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        if (ordertype.equals("cat")) {
            map.put("carid",carid);
        }else{
            map.put("productid",productid);
            map.put("num",num+"");
        }
        map.put("totalprice",totalprice+"");
        map.put("uaid",uaid);
        mPreenter.fetch(map,false,HtttpUrlUtils.submitOrder,"");
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {
            addressAndsorder(s, gson);
        }else if (HttpType == 2) {//生成订单
            OrderBean orderBean = gson.fromJson(s, OrderBean.class);
            if (orderBean.getCode() == 200) {
                OrderBean.BodyBean body = orderBean.getBody();
                orderid = body.getOrderid();
                showPayPopu();
            }else{
                Toast.makeText(ctx, orderBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }else if (HttpType == 3) {
            toPay(s, gson);
        }
    }
    private void toPay(String s, Gson gson) {
        if (paytype == 1) {//阿里支付
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
                        }else{
//                            finish();
//                            startActivity(new Intent(ctx,MyOrderActivity.class));
                        }
                    }
                });

            } else {

                Toast.makeText(ctx, aliPayBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }else{//微信支付
            WXPAtResultBean wxpAtResultBean = gson.fromJson(s, WXPAtResultBean.class);
            if (wxpAtResultBean.getCode() == 200) {
                WeChatPayUtiles weChatPayUtiles = new WeChatPayUtiles(ctx);
                weChatPayUtiles.WXPay(wxpAtResultBean.getBody());
                weChatPayUtiles.setResult(new WXPayResultListener() {
                    @Override
                    public void setResult(boolean result) {
                        if (result) {
                            finish();
                            startActivity(new Intent(ctx, PaySuccessActivity.class));
                        }
                    }
                });
            }else{
                Toast.makeText(ctx, wxpAtResultBean.getResult(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * 收货地址和订单列表数据
     * @param s
     * @param gson
     */
    private void addressAndsorder(String s, Gson gson) {
        SubOrderBean subOrderBean = gson.fromJson(s, SubOrderBean.class);
        if (subOrderBean.getCode() == 200) {
            SubOrderBean.BodyBean body = subOrderBean.getBody();
            if (body.getAddr() == null) {
                rlEntry.setVisibility(View.VISIBLE);
                ll_noentry.setVisibility(View.GONE);

            }else{
                rlEntry.setVisibility(View.GONE);
                ll_noentry.setVisibility(View.VISIBLE);
                addr = body.getAddr();
                uaid = addr.getUaid();
                tvName.setText(addr.getName());
                tvPhone.setText(addr.getTel());
                tvAddress.setText(addr.getProvince()+ addr.getCity()+ addr.getArea());
                tvDescaddress.setText(addr.getAddr());
            }
            SubOrderBean.BodyBean.InfoBean info = body.getInfo();
            totalprice = info.getTotalprice();
            tvTotalPrice.setText("￥"+totalprice);
            List<SubOrderBean.BodyBean.InfoBean.ListBean> list = info.getList();
            lvList.setAdapter(new SubOrderAdapter(ctx,list));
        }else{

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        wch(requestCode+resultCode);
         switch (resultCode){
                    case 1:
                        if (data != null) {
                            AddreListBean.BodyBean bodyBean =
                                    (AddreListBean.BodyBean) data.getSerializableExtra("address");
                            rlEntry.setVisibility(View.GONE);
                            ll_noentry.setVisibility(View.VISIBLE);
                            uaid = bodyBean.getUaid();
                            tvName.setText(bodyBean.getName());
                            tvPhone.setText(bodyBean.getTel());
                            tvAddress.setText(bodyBean.getProvince()+bodyBean.getCity()+bodyBean.getArea());
                            tvDescaddress.setText(bodyBean.getAddr());
                            isdefault = bodyBean.getIsdefault();
                            addr.setName(bodyBean.getName());
                            addr.setTel(bodyBean.getTel());
                            addr.setProvince(bodyBean.getProvince());
                            addr.setCity(bodyBean.getCity());
                            addr.setArea(bodyBean.getArea());
                            addr.setAddr(bodyBean.getAddr());
                        }
                        break;
                        default:
                            getOrderData();
                            break;
                }
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
