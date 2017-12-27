package com.dlwx.onedrop.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.BankListBean;
import com.dlwx.onedrop.bean.ResuleBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.dlwx.onedrop.utiles.SpUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 添加银行卡
 */
public class AddBankActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_banknumber)
    TextView etBanknumber;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private List<BankListBean.BodyBean> bankList;
    private String bankID;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_add_bank);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            initTabBar(toolBar);
            tvTitle.setText(R.string.bankcarbinding);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({ R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                submit();

                break;
        }
    }

    /**
     * 添加银行卡
     */
    private void submit() {
        String name = etName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(ctx, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String number = etBanknumber.getText().toString();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(ctx, "银行卡号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpType = 2;
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("hold_name",name);

        map.put("bank_number",number);
        mPreenter.fetch(map,false,HtttpUrlUtils.AddBank,"");

    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {
            BankListBean bankListBean = gson.fromJson(s, BankListBean.class);
            if (bankListBean.getCode() == 200) {
                bankList = bankListBean.getBody();
                String [] names = new String[bankList.size()];
                for (int i = 0; i < bankList.size(); i++) {
                    names[i] = bankList.get(i).getBank_name();
                }
            }else{
                Toast.makeText(ctx, bankListBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }else{
            ResuleBean resuleBean = gson.fromJson(s, ResuleBean.class);
            if (resuleBean.getCode() == 200) {

                String is_tx_passwrord = sp.getString(SpUtils.is_tx_passwrord, "");
                if (!is_tx_passwrord.equals("1")) {
                    startActivity(new Intent(ctx, SetPayPWdActivity.class));
                }
                finish();
            }
            Toast.makeText(ctx, resuleBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

}
