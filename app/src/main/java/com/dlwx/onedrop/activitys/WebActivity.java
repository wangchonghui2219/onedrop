package com.dlwx.onedrop.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.LoadWEBUtiles;
import com.dlwx.onedrop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView titleTxt;
    @BindView(R.id.web_view)
    WebView webView;
    private String url;
    private String title;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        LoadWEBUtiles webUtiles = new LoadWEBUtiles(ctx);

        webUtiles.setListViewData(url, webView, null);
//        webView.loadUrl();
    }

    @Override
    protected void initData() {
        initTabBar(toolbar);
        titleTxt.setText(title);

    }

    @Override
    protected void initListener() {
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

}
