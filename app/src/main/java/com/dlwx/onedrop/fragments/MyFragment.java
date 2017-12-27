package com.dlwx.onedrop.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.CircleImageView;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.activitys.AboutWeActivity;
import com.dlwx.onedrop.activitys.InviteFriendsActivity;
import com.dlwx.onedrop.activitys.LoginActivity;
import com.dlwx.onedrop.activitys.MyLevelActivity;
import com.dlwx.onedrop.activitys.MyOrderActivity;
import com.dlwx.onedrop.activitys.MyTeamListActivity;
import com.dlwx.onedrop.activitys.MyWalletActivity;
import com.dlwx.onedrop.activitys.SettActivity;
import com.dlwx.onedrop.adapter.MyListAdapter;
import com.dlwx.onedrop.bean.ResuleBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.dlwx.onedrop.utiles.SpUtils;
import com.dlwx.onedrop.wxapi.QQUtiles;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的
 */
public class MyFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    @BindView(R.id.lv_list)
    ListView lvList;
    Unbinder unbinder;

    private String[] listNames;
    private ViewHolder vh;
    private AlertDialog show;
    private int[] pics = {R.mipmap.icon_wdjibie, R.mipmap.icon_wdyaoqing, R.mipmap.icon_wdqianbao, R.mipmap.icon_wdtuandui,
            R.mipmap.icon_dingdan, R.mipmap.icon_dingdan, R.mipmap.icon_wdtuichu
    };

    @Override
    public int getLayoutID() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        View headview = LayoutInflater.from(ctx).inflate(R.layout.my_head, null);
        vh = new ViewHolder(headview);
        lvList.addHeaderView(headview);
    }

    @Override
    protected void initDate() {
        listNames = getResources().getStringArray(R.array.my_list_name);
        lvList.setAdapter(new MyListAdapter(ctx, listNames, pics));
    }

    @Override
    public void onResume() {
        super.onResume();
        String head_pic = sp.getString(SpUtils.header_pic, "");
        String nickName = sp.getString(SpUtils.nickname, "");
        if (!TextUtils.isEmpty(head_pic)) {
            Glide.with(ctx).load(head_pic).into(vh.iv_head);
            vh.tv_nickname.setText(nickName);
        }
    }

    @Override
    protected void initListener() {
        vh.ll_head.setOnClickListener(this);
        vh.tv_sett.setOnClickListener(this);
        lvList.setOnItemClickListener(this);
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


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_head:
//                intent = new Intent(ctx, LoginActivity.class);
//                startActivity(intent);
                break;
            case R.id.tv_sett:
                startActivity(new Intent(ctx, SettActivity.class));
                break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 1://我的级别
                startActivity(new Intent(ctx, MyLevelActivity.class));
                break;
            case 2://邀请好友
                startActivity(new Intent(ctx, InviteFriendsActivity.class));
                break;
            case 3://我的钱包
                startActivity(new Intent(ctx, MyWalletActivity.class));
                break;
            case 4://我的团队
                startActivity(new Intent(ctx, MyTeamListActivity.class));
                break;
            case 5://我的订单
                startActivity(new Intent(ctx, MyOrderActivity.class));
                break;
            case 6://关于我们
                startActivity(new Intent(ctx, AboutWeActivity.class));
                break;
            case 7://退出登录
                logOut();
                break;
            case 8://信息修改

                break;

        }
    }
    private class ViewHolder {
        public View rootView;
        public CircleImageView iv_head;
        public TextView tv_nickname;
        public LinearLayout ll_head;
        public TextView tv_sett;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_sett = rootView.findViewById(R.id.tv_sett);
            this.iv_head = (CircleImageView) rootView.findViewById(R.id.iv_head);
            this.tv_nickname = (TextView) rootView.findViewById(R.id.tv_nickname);
            this.ll_head = (LinearLayout) rootView.findViewById(R.id.ll_head);
        }

    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void showData(String s) {
        disLoading();
        Gson gson = new Gson();
        wch("版本更新" + s);
   if (HttpType == 3) {//退出登录
            ResuleBean resuleBean = gson.fromJson(s, ResuleBean.class);
            if (resuleBean.getCode() == 200) {
                String isQQ = sp.getString(SpUtils.isqq, "");
                if (isQQ.equals("1")) {
                    new QQUtiles().logout(ctx);
                }
                sp.edit().putString(SpUtils.TOKEN, "").commit();
                sp.edit().putString(SpUtils.telephone, "").commit();
                sp.edit().putString(SpUtils.nickname, "").commit();
                sp.edit().putString(SpUtils.header_pic, "").commit();
                sp.edit().putString(SpUtils.isqq, "").commit();
                sp.edit().putString(SpUtils.is_tx_passwrord, "").commit();
                startActivity(new Intent(ctx, LoginActivity.class));
            }
        }
    }
    private int HttpType;
    /**
     * 退出登录
     */
    private void logOut(){
        Map<String,String> map = new HashMap<>();
        HttpType = 3;
        mPreenter.fetch(map,true,HtttpUrlUtils.LogOut,"");
    }
}
