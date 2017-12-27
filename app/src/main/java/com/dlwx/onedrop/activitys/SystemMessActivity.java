package com.dlwx.onedrop.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.adapter.SysMessAdapter;
import com.dlwx.onedrop.bean.MessListBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 系统消息
 */
public class SystemMessActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.tv_entry)
    TextView tvEntry;
    @BindView(R.id.ll_entry)
    LinearLayout llEntry;

    private String title;
    private String type;
    private List<MessListBean.BodyBean> body;


    @Override
    protected void initView() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        type = intent.getStringExtra("type");
        setContentView(R.layout.activity_system_mess);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(title);
        Map<String, String> map = new HashMap<>();
        wch("Token:" + Token);
        map.put("token", Token);
        map.put("type", type);
        mPreenter.fetch(map, true, HtttpUrlUtils.SysMess, HtttpUrlUtils.SysMess + "1" + Token);

    }

    @Override
    protected void initListener() {
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if ("1".equals(type)) {

                    Intent intent = new Intent(ctx, SystemMessCenActivity.class);
                    intent.putExtra("id", body.get(i).getId());
                    intent.putExtra("title","系统消息详情");
                    startActivity(intent);
                } else {
                    String order_num = body.get(i).getOrder_num();
                    if (TextUtils.isEmpty(order_num)) {
                        Intent intent = new Intent(ctx, SystemMessCenActivity.class);
                        intent.putExtra("id", body.get(i).getId());
                        intent.putExtra("title","提现消息详情");
                        startActivity(intent);
                    }else{

                        Intent intent = new Intent(ctx, OrderDescActivity.class);
                        intent.putExtra("order_num", order_num);
                        startActivity(intent);
                    }
                }
            }
        });
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


        MessListBean messListBean = gson.fromJson(s, MessListBean.class);
        if (messListBean.getCode() == 200) {
            body = messListBean.getBody();

            if (body.size() == 0) {
                llEntry.setVisibility(View.VISIBLE);
                tvEntry.setText(title+"为空");

            }else{
                lvList.setAdapter(new SysMessAdapter(ctx, body));
                llEntry.setVisibility(View.GONE);
            }
        } else {
            llEntry.setVisibility(View.VISIBLE);
            tvEntry.setText(title+"为空");
        }
    }
}
