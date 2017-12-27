package com.dlwx.onedrop.activitys;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.ResuleBean;
import com.dlwx.onedrop.utiles.AddressPopuWindow;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.dlwx.onedrop.views.SeleteAddressInter;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 新建收货地址
 */
public class NewAddressActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
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
    @BindView(R.id.cb_check)
    CheckBox cbCheck;
    private String uaid;
    private String title;

    @Override
    protected void initView() {
        title = getIntent().getStringExtra("title");
        uaid = getIntent().getStringExtra("uaid");
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        province = getIntent().getStringExtra("province");
        city = getIntent().getStringExtra("city");
        area = getIntent().getStringExtra("area");
        String addr = getIntent().getStringExtra("addr");
        String isdefault = getIntent().getStringExtra("isdefault");
        setContentView(R.layout.activity_new_address);
        ButterKnife.bind(this);

        if (!TextUtils.isEmpty(uaid)) {
            etName.setText(name);
            etPhone.setText(phone);
            tvAddress.setText(province+city+area);
            etDescaddress.setText(addr);
            if (isdefault.equals("1")) {
                cbCheck.setChecked(true);
            }
        }

    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(title);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }
    @OnClick({R.id.tv_save, R.id.tv_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                save();
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
        String isdefault = "";
        if (cbCheck.isChecked()) {
            isdefault = "1";
        } else {
            isdefault = "2";
        }
        wch("token:"+Token+"\n"+"name:"+name+"\n"+"tel:"+phone+"\n"+"\n"+"province:"+province+"\n"+
            "city:"+city+"\n"+"area:"+area+"\n"+"addr:"+descAddress+"\n"+"isdefault:"+isdefault+"\n"+"uaid:"+uaid
        );
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("name", name);
        map.put("tel", phone);
        map.put("province", province);
        map.put("city", city);
        map.put("area", area);
        map.put("addr", descAddress);
        map.put("isdefault", isdefault);
        if (!TextUtils.isEmpty(uaid)) {
            map.put("uaid", uaid);
        }
        mPreenter.fetch(map, false, HtttpUrlUtils.AddAddress, "");

    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        ResuleBean resuleBean = gson.fromJson(s, ResuleBean.class);
        if (resuleBean.getCode() == 200) {
            finish();
        }
        Toast.makeText(ctx, resuleBean.getResult(), Toast.LENGTH_SHORT).show();

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
}
