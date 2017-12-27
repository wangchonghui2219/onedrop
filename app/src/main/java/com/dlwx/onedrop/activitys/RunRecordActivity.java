package com.dlwx.onedrop.activitys;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.adapter.RunRecordAdapter;
import com.dlwx.onedrop.bean.MyCommissBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 流水记录
 */
public class RunRecordActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_entry)
    TextView tvEntry;
    @BindView(R.id.ll_entry)
    LinearLayout llEntry;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_run_record);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.run);
        refreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadmore(true);//是否启用上拉加载功能
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshLayout.setEnableAutoLoadmore(true);//是否启用列表惯性滑动到底部时自动加载更多
        getData();

    }

    @Override
    protected void initListener() {
        refreshLayout.setRefreshHeader(new WaterDropHeader(ctx));
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

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("type", "2");
        mPreenter.fetch(map, true, HtttpUrlUtils.consume_record, HtttpUrlUtils.consume_record + Token);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        MyCommissBean myCommissBean = gson.fromJson(s, MyCommissBean.class);
        if (myCommissBean.getCode() == 200) {
            List<MyCommissBean.BodyBean> body = myCommissBean.getBody();
            if (body != null) {
                RunRecordAdapter adapter = new RunRecordAdapter(ctx, body);
                lvList.setAdapter(adapter);
                llEntry.setVisibility(View.GONE);
                refreshLayout.setVisibility(View.VISIBLE);

            } else {
                llEntry.setVisibility(View.VISIBLE);
                tvEntry.setText("流水纪录为空");
                refreshLayout.setVisibility(View.GONE);
            }

        } else {
            Toast.makeText(ctx, myCommissBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

}
