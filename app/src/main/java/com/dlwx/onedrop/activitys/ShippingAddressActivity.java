package com.dlwx.onedrop.activitys;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.AddreListBean;
import com.dlwx.onedrop.bean.ResuleBean;
import com.dlwx.onedrop.utiles.AddressPopuWindow;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.dlwx.onedrop.views.SeleteAddressInter;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 收货地址
 */
public class ShippingAddressActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.ll_entry)
    RelativeLayout llEntry;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_descaddress)
    EditText etDescaddress;
    @BindView(R.id.ll_edit)
    LinearLayout llEdit;
    private List<AddreListBean.BodyBean> body;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_shipping_address);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.address);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.btn_add,R.id.tv_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                llEntry.setVisibility(View.GONE);
                llEdit.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_address:
                //判断隐藏软键盘是否弹出
                if(getWindow().getAttributes().softInputMode== WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
                    //隐藏软键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                }
                showCityPopu();
                break;
        }
    }


    /**
     * 获取收货地址列表
     */
    private void getData() {
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        mPreenter.fetch(map, true, HtttpUrlUtils.AddressList, HtttpUrlUtils.AddressList + Token);
    }
    private String 	uaid;
    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {

            addressList(s, gson);
        } else {
            ResuleBean resuleBean = gson.fromJson(s, ResuleBean.class);
            if (resuleBean.getCode() == 200) {
               getData();
            }
            Toast.makeText(ctx, resuleBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addressList(String s, Gson gson) {
        AddreListBean addreListBean = gson.fromJson(s, AddreListBean.class);
        if (addreListBean.getCode() == 200) {
            body = addreListBean.getBody();
            if (body == null || body.size() == 0) {
                llEntry.setVisibility(View.VISIBLE);
                llEdit.setVisibility(View.GONE);
                return;
            }
            llEntry.setVisibility(View.GONE);
            llEdit.setVisibility(View.VISIBLE);
            province = body.get(0).getProvince();
            city = body.get(0).getCity();
            area = body.get(0).getArea();
            uaid = body.get(0).getUaid();
            etName.setText( body.get(0).getName());
            etPhone.setText( body.get(0).getTel());
            tvAddress.setText( body.get(0).getProvince()+ body.get(0).getCity()+ body.get(0).getArea());
            etDescaddress.setText( body.get(0).getAddr());

        } else {
            Toast.makeText(ctx, addreListBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        save();
    }
    private void save() {
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            vibrator.vibrate(50);
            Toast.makeText(ctx, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            vibrator.vibrate(50);
            Toast.makeText(ctx, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String address = tvAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address) && address.equals("省份、城市、区县")) {
            vibrator.vibrate(50);
            Toast.makeText(ctx, "地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String descAddress = etDescaddress.getText().toString().trim();
        if (TextUtils.isEmpty(descAddress)) {
            vibrator.vibrate(50);
            Toast.makeText(ctx, "详细地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        wch("token:"+Token+"\n"+"name:"+name+"\n"+"tel:"+phone+"\n"+"\n"+"province:"+province+"\n"+
                "city:"+city+"\n"+"area:"+area+"\n"+"addr:"+descAddress+"\n"+"isdefault:"
        );
        HttpType = 2;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("name", name);
        map.put("tel", phone);
        map.put("province", province);
        map.put("city", city);
        map.put("area", area);
        map.put("addr", descAddress);
        if (!TextUtils.isEmpty(uaid)) {

            map.put("uaid",uaid);
        }
        mPreenter.fetch(map, false, HtttpUrlUtils.AddAddress, "");

    }

    private String province = "";
    private String city = "";
    private String area = "";

    /**
     * 显示城市窗体
     */
    private void showCityPopu() {
        AddressPopuWindow.showWindow(ctx, tvAddress);
        AddressPopuWindow.setData(new SeleteAddressInter() {
            @Override
            public void setAddressSeleteData(String pro, String c, String a) {
                province = pro;
                city = c;
                area = a;
                tvAddress.setText(pro + c + a);
            }
        });
    }

//    private AddressAdapter.HandleListener handleListener = new AddressAdapter.HandleListener() {
//        @Override
//        public void edit(int i) {
//            AddreListBean.BodyBean bodyBean = body.get(i);
//            Intent intent = new Intent(ctx,NewAddressActivity.class);
//            intent.putExtra("title","编辑地址");
//            intent.putExtra("uaid",bodyBean.getUaid());
//            intent.putExtra("name",bodyBean.getName());
//            intent.putExtra("phone",bodyBean.getTel());
//            intent.putExtra("province",bodyBean.getProvince());
//            intent.putExtra("city",bodyBean.getCity());
//            intent.putExtra("area",bodyBean.getArea());
//            intent.putExtra("addr",bodyBean.getAddr());
//            intent.putExtra("isdefault",bodyBean.getIsdefault());
//            startActivity(intent);
//        }
//
//        @Override
//        public void delete(int i) {
//            HttpType = 2;
//            AddreListBean.BodyBean bodyBean = body.get(i);
//            Map<String,String> map = new HashMap<>();
//            map.put("token",Token);
//            map.put("uaid",bodyBean.getUaid());
//            mPreenter.fetch(map,true,HtttpUrlUtils.DeleteAdd,"");
//        }
//
//        @Override
//        public void setIsDefuult(boolean isDefuult,int pos) {
//            HttpType = 3;
//            AddreListBean.BodyBean bodyBean = body.get(pos);
//            Map<String,String> map = new HashMap<>();
//            map.put("token",Token);
//            map.put("uaid",bodyBean.getUaid());
//            mPreenter.fetch(map,true,HtttpUrlUtils.Set_Default,"");
//        }
//    };
}
