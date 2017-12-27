package com.dlwx.onedrop.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.adapter.MyOrderAdapter;
import com.dlwx.onedrop.bean.MyOrderBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.header.FalsifyHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 我的订单
 */
public class MyOrderActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, MyOrderAdapter.StartActivityInterface {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rb_allorder)
    RadioButton rbAllorder;
    @BindView(R.id.rb_receive)
    RadioButton rbReceive;
    @BindView(R.id.rb_unreceive)
    RadioButton rbUnreceive;
    @BindView(R.id.rg_cont)
    RadioGroup rgCont;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_entry)
    TextView tvEntry;
    @BindView(R.id.ll_entry)
    LinearLayout llEntry;
    private MyOrderAdapter myOrderAdapter;
    private MyOrderBean myOrderBean;
    private List<MyOrderBean.BodyBean> body = new ArrayList<>();

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.myorder);
        refreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadmore(true);//是否启用上拉加载功能
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshLayout.setEnableAutoLoadmore(true);//是否启用列表惯性滑动到底部时自动加载更多
        rbAllorder.setChecked(true);
        myOrderAdapter = new MyOrderAdapter(ctx, body);
        lvList.setAdapter(myOrderAdapter);
        getData("");

    }


    @Override
    protected void initListener() {
        rgCont.setOnCheckedChangeListener(this);
        myOrderAdapter.setActivityInterface(this);
        refreshLayout.setRefreshHeader(new FalsifyHeader(ctx));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new FalsifyFooter(ctx));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);

            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();

            }
        });
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_allorder://全部订单
                getData("");
                break;
            case R.id.rb_receive://已收货
                getData("1");
                break;
            case R.id.rb_unreceive://未收货
                getData("2");
                break;

        }

    }

    @Override
    public void start(int position, int i) {
        List<MyOrderBean.BodyBean> body = myOrderBean.getBody();
        MyOrderBean.BodyBean bodyBean = body.get(position);
        Intent intent = new Intent(ctx, OrderDescActivity.class);
        intent.putExtra("orderid", bodyBean.getOrderid());
        startActivity(intent);
    }

    /**
     * 获取我的订单
     */
    private void getData(String status) {
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("status", status);
        mPreenter.fetch(map, true, HtttpUrlUtils.MyOrderData, HtttpUrlUtils.MyOrderData + Token);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        myOrderBean = gson.fromJson(s, MyOrderBean.class);
        if (myOrderBean.getCode() == 200) {
            body.clear();
            if (myOrderBean.getBody() != null) {
                body.addAll(myOrderBean.getBody());
                myOrderAdapter.notifyDataSetChanged();
                llEntry.setVisibility(View.GONE);
            } else {
                body.clear();
                llEntry.setVisibility(View.VISIBLE);
                refreshLayout.setVisibility(View.GONE);
                tvEntry.setText("数据为空");
                myOrderAdapter.notifyDataSetChanged();

            }
        } else {
            llEntry.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
            tvEntry.setText("数据为空");
            Toast.makeText(ctx, myOrderBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

}
