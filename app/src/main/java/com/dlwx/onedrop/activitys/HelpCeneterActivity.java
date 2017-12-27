package com.dlwx.onedrop.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.adapter.HelpListAdapter;
import com.dlwx.onedrop.bean.AboutWeBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 帮助中心
 */
public class HelpCeneterActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    private List<AboutWeBean.BodyBean> body;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_help_ceneter);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.helpcent);
        getData();

    }



    @Override
    protected void initListener() {
        lvList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AboutWeBean.BodyBean bodyBean = body.get(i);
        Intent intent = new Intent(ctx,HtmlActivity.class);
        intent.putExtra("title",bodyBean.getTitle());
        intent.putExtra("url",HtttpUrlUtils.HelpCenDesc);
        intent.putExtra("id",bodyBean.getId());
        startActivity(intent);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    /**
     * 联网获取列表数据
     */
    private void getData() {
        Map<String,String> map = new HashMap<>();
        mPreenter.fetch(map,true, HtttpUrlUtils.HelpCenterList,HtttpUrlUtils.HelpCenterList);

    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        AboutWeBean aboutWeBean = gson.fromJson(s, AboutWeBean.class);
        if (aboutWeBean.getCode() == 200) {
            body = aboutWeBean.getBody();
            String[] names = new String[body.size()];;
            for (int i = 0; i < body.size() ; i++) {
                names[i] = body.get(i).getTitle();

            }
            lvList.setAdapter(new HelpListAdapter(ctx,names));
        }else{
            Toast.makeText(ctx, aboutWeBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }
}
