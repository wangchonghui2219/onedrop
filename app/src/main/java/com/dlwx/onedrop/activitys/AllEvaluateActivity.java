package com.dlwx.onedrop.activitys;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.adapter.AllEvaluateAdapter;
import com.dlwx.onedrop.bean.AllEvaluteBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全部评价
 */
public class AllEvaluateActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.iv_entry)
    ImageView iv_entry;
    private String productid;

    @Override
    protected void initView() {
        productid = getIntent().getStringExtra("productid");
        setContentView(R.layout.activity_all_evaluate);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            initTabBar(toolBar);
            tvTitle.setText("全部评价");
            getData();
    }



    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }
    private void getData() {
        Map<String,String> map = new HashMap<>();
        map.put("productid",productid);
        mPreenter.fetch(map,true, HtttpUrlUtils.AllEcaluate,HtttpUrlUtils.AllEcaluate+productid);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        AllEvaluteBean allEvaluteBean = gson.fromJson(s, AllEvaluteBean.class);
        if (allEvaluteBean.getCode() == 200) {
            AllEvaluteBean.BodyBean body = allEvaluteBean.getBody();
            List<AllEvaluteBean.BodyBean.ListBean> list = body.getList();
            if (list.size() == 0) {
                iv_entry.setVisibility(View.VISIBLE);
            }
            lvList.setAdapter(new AllEvaluateAdapter(ctx,list));

        }else{

                iv_entry.setVisibility(View.VISIBLE);

        }
    }
}
