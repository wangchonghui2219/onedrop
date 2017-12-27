package com.dlwx.onedrop.activitys;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.adapter.MyTeamAdapter;
import com.dlwx.onedrop.bean.TeamCenBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.header.FalsifyHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 我团队
 */
public class MyTeamActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_entry)
    TextView tvEntry;
    @BindView(R.id.ll_entry)
    LinearLayout llEntry;
    private String type;
    private String title;

    @Override
    protected void initView() {
        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        setContentView(R.layout.activity_my_team);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(title);
        tvName.setText(title);
        if (type.equals("2")) {
            rl_title.setVisibility(View.GONE);
        }
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

    public void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("token", Token);
        mPreenter.fetch(map, true, HtttpUrlUtils.MyTeamCenter, HtttpUrlUtils.MyTeamCenter + type);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        TeamCenBean teamCenBean = gson.fromJson(s, TeamCenBean.class);
        if (teamCenBean.getCode() == 200) {
            TeamCenBean.BodyBean body = teamCenBean.getBody();

            if (body.getTotal_num() != 0) {
                tvNumber.setText(body.getTotal_num() + "人（团队总金额：" + body.getTotal_price() + ")");
                llEntry.setVisibility(View.GONE);
                tvEntry.setText("人数为空");
            } else {
                llEntry.setVisibility(View.VISIBLE);
                refreshLayout.setVisibility(View.GONE);
                tvEntry.setText("人数为空");
            }

            List<TeamCenBean.BodyBean.PersonInfoBean> person_info = body.getPerson_info();
            MyTeamAdapter adapter = new MyTeamAdapter(ctx, person_info);
            lvList.setAdapter(adapter);
        } else {
            llEntry.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
            tvEntry.setText("人数为空");
            Toast.makeText(ctx, teamCenBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }


}
