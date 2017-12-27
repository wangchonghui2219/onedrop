package com.dlwx.onedrop.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.HtmlBean;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 加载超文本
 */
public class HtmlActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_smalltitle)
    TextView tv_smalltitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_pic)
    ImageView iv_pic;
    private String url;
    private String title;
    private String id;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        id = intent.getStringExtra("id");
        setContentView(R.layout.activity_html);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(title);
        getData();

    }

    @Override
    protected void initListener() {

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
        HtmlBean htmlBean = gson.fromJson(s, HtmlBean.class);
        if (htmlBean.getCode() == 200) {
            HtmlBean.BodyBean body = htmlBean.getBody();
            String info = body.getInfo();
            Spanned spanned = Html.fromHtml(info);
            Spanned spanned1 = Html.fromHtml(String.valueOf(spanned));
            tvContent.setText(spanned1);
            tv_smalltitle.setText(body.getTitle());
            if (!TextUtils.isEmpty(body.getTitle_pic())) {
                Glide.with(ctx).load(body.getTitle_pic()).into(iv_pic);
            }else{
                iv_pic.setVisibility(View.GONE);
            }
        }else{
            Toast.makeText(ctx, htmlBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    public void getData() {
        Map<String,String> map = new HashMap<>();
        if (!TextUtils.isEmpty(id)) {
            map.put("id",id);
        }
        mPreenter.fetch(map,true,url,url);
    }
}
