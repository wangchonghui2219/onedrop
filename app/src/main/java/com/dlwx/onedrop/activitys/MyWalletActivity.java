package com.dlwx.onedrop.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.CircleImageView;
import com.dlwx.baselib.view.PswInputView;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.adapter.MyListAdapter;
import com.dlwx.onedrop.adapter.TixianSeleteBankAdapter;
import com.dlwx.onedrop.bean.BankCardListBean;
import com.dlwx.onedrop.bean.MyYueBean;
import com.dlwx.onedrop.bean.ResuleBean;
import com.dlwx.onedrop.bean.TiXianBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.dlwx.onedrop.utiles.SpUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 我的钱包
 */
public class MyWalletActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.tv_money)
    TextView tv_money;
    private int[] pics = {R.mipmap.icon_qbtx,R.mipmap.icon_qbbangding,R.mipmap.icon_qbyongjin,R.mipmap.icon_qbtxjl,R.mipmap.icon_qbliushui};
    private AlertDialog banklistdia;
    private String bank_id;
    private String user_money;
    private String money;
    private AlertDialog pwdshow;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_wallet);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        getSupportActionBar().setHomeAsUpIndicator(com.dlwx.baselib.R.mipmap.icon_jiantoubaise);
        tvTitle.setText(R.string.mywallet);
        Glide.with(ctx).load(sp.getString(SpUtils.header_pic,"")).into(ivHead);
        String[] listNames = getResources().getStringArray(R.array.my_wallet);
        lvList.setAdapter(new MyListAdapter(ctx, listNames,pics));

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyYuE();
    }

    @Override
    protected void initListener() {
        lvList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0://提现
                String is_tx_passwrord = sp.getString(SpUtils.is_tx_passwrord, "");
                if (is_tx_passwrord.equals("1"))
                getCardList();
                else
                startActivity(new Intent(ctx,SetPayPWdActivity.class));

                break;
            case 1://绑定银行卡
                startActivity(new Intent(ctx,BinDingCardActivity.class));
                break;
            case 2://我的佣金
                startActivity(new Intent(ctx,MyCommissionActivity.class));

                break;
            case 3://提现纪录
                startActivity(new Intent(ctx,TiXianRecordActivity.class));
                break;
            case 4://流水记录
                startActivity(new Intent(ctx,RunRecordActivity.class));
                break;

        }
    }

    /**
     * 帐号余额
     */
    private void getMyYuE() {
        HttpType = 4;
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        mPreenter.fetch(map,true,HtttpUrlUtils.GetYE,"");

    }
    private void getCardList() {
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        mPreenter.fetch(map, true, HtttpUrlUtils.BankCardList, "");
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {

            getBankList(s, gson);
        }else if(HttpType == 2){
            ResuleBean resuleBean = gson.fromJson(s, ResuleBean.class);
            if (resuleBean.getCode() == 200) {
                pwdshow.dismiss();
                HttpType = 3;
                Map<String,String> map = new HashMap<>();
                map.put("bank_id",bank_id);
                map.put("token",Token);
                if (All == 1) {
                    map.put("tx_price",user_money);
                }else{

                    map.put("tx_price",money);
                }
                mPreenter.fetch(map,false,HtttpUrlUtils.startTixian,"");
            }
            Toast.makeText(ctx, resuleBean.getResult(), Toast.LENGTH_SHORT).show();

        }else if(HttpType == 3){

            TiXianBean tiXianBean = gson.fromJson(s, TiXianBean.class);
            if (tiXianBean.getCode() == 200) {
                TiXianBean.BodyBean body = tiXianBean.getBody();
                showDiaResult(body);
            }
            Toast.makeText(ctx, tiXianBean.getResult(), Toast.LENGTH_SHORT).show();

//
        }else if (HttpType == 4) {
            MyYueBean myYueBean = gson.fromJson(s, MyYueBean.class);
            if (myYueBean.getCode() == 200) {
                user_money = myYueBean.getBody().getUse_price();
                tv_money.setText(user_money);
            }else{
                Toast.makeText(ctx, myYueBean.getResult(), Toast.LENGTH_SHORT).show();
            }


        }

    }private void getBankList(String s, Gson gson) {
        BankCardListBean bankCardListBean = gson.fromJson(s, BankCardListBean.class);
        if (bankCardListBean.getCode() == 200) {
            List<BankCardListBean.BodyBean> body = bankCardListBean.getBody();
            if (body == null & body.size() == 0) {

            } else {
                startTiXian(body);
            }
        }else if (bankCardListBean.getCode() == 204) {
            startActivity(new Intent(ctx,BinDingCardActivity.class));
        }
            Toast.makeText(ctx, bankCardListBean.getResult(), Toast.LENGTH_SHORT).show();

    }

    /**
     * 开始提现
     */
    private void startTiXian(final List<BankCardListBean.BodyBean> body) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_tixian1,null);
        builder.setCancelable(true);
        builder.setView(view);
        ListView lv_list = view.findViewById(R.id.lv_list);
        Button btn_complete = view.findViewById(R.id.btn_complete);
        final TixianSeleteBankAdapter tixianSeleteBankAdapter = new TixianSeleteBankAdapter(ctx, body);
        lv_list.setAdapter(tixianSeleteBankAdapter);
        banklistdia = builder.show();
        bank_id = body.get(0).getBank_id();
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tixianSeleteBankAdapter.setUp(i);
                bank_id = body.get(i).getBank_id();
            }
        });
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                banklistdia.dismiss();

                showMoneyDia();
            }
        });
    }

    /**
     * 显示输入密码框窗体
     */
    private void showPwdDia() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_pwd,null);
        builder.setCancelable(true);
        builder.setView(view);
        ImageView ivClose =  view.findViewById(R.id.iv_close);
        PswInputView pswView = view.findViewById(R.id.pswView);
        TextView tv_forgetPwd = view.findViewById(R.id.tv_forgetPwd);
        pwdshow = builder.show();
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwdshow.dismiss();
            }
        });
            pswView.setInputCallBack(new PswInputView.InputCallBack() {
                @Override
                public void onInputFinish(String result) {
                    //密码校验
                    HttpType = 2;
                    Map<String,String> map = new HashMap<>();
                    map.put("token",Token);
                    map.put("tx_password",result);
                    mPreenter.fetch(map,false,HtttpUrlUtils.TiXianPwdCheck,"");

                }
            });
        tv_forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctx, ForgitPwdActivity.class).putExtra("title", "忘记提现密码"));
            }
        });
    }

    /**
     * 显示输入金额窗体
     */
    private void showMoneyDia() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_money,null);
        builder.setCancelable(true);
        builder.setView(view);
        Button btn_submit = view.findViewById(R.id.btn_submit);
        final EditText etMoney = view.findViewById(R.id.et_money);
        TextView tv_tixian = view.findViewById(R.id.tv_tixian);
        TextView tv_totalmoney = view.findViewById(R.id.tv_totalmoney);
        tv_totalmoney.setText("当前钱包余额"+user_money+"元");
        final AlertDialog moneyshow = builder.show();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                money = etMoney.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    Toast.makeText(MyWalletActivity.this, "提现金额不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                showPwdDia();
                moneyshow.dismiss();


            }
        });
        tv_tixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moneyshow.dismiss();
                All = 1;
                showPwdDia();


            }
        });

    }
    private int All = 0;
    /**
     * 显示提现信息
     */
    private void showDiaResult(TiXianBean.BodyBean body) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_mess,null);
        TextView tv_date = view.findViewById(R.id.tv_date);
        TextView tv_bakName = view.findViewById(R.id.tv_bakName);
        TextView tv_money = view.findViewById(R.id.tv_money);
        Button btn_complete = view.findViewById(R.id.btn_complete);
        tv_date.setText(body.getTime());
        tv_bakName.setText(body.getBank());
        tv_money.setText("￥"+body.getTx_price());
        builder.setCancelable(true);
        builder.setView(view);
        final AlertDialog show = builder.show();
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
                startActivity(new Intent(ctx,CheckActivity.class));
            }
        });
    }


}
