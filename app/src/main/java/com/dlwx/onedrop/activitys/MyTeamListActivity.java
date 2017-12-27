package com.dlwx.onedrop.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.adapter.HelpListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的团队分类
 */
public class MyTeamListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    private String[] names;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_team_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.myteam);
        names = getResources().getStringArray(R.array.myteamlist);
        lvList.setAdapter(new HelpListAdapter(ctx, names));
    }

    @Override
    protected void initListener() {
        lvList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(ctx, MyTeamActivity.class);
        switch (i) {
            case 0://团队总人数
                intent.putExtra("type", "1");
                intent.putExtra("title", names[i]);
                startActivity(intent);
                break;
            case 1://我的上级
                intent.putExtra("type", "2");
                intent.putExtra("title", names[i]);
                startActivity(intent);
                break;
            case 2://我的一级下属
                intent.putExtra("type", "3");
                intent.putExtra("title", names[i]);
                startActivity(intent);
                break;
            case 3://我的二级下属
                intent.putExtra("type", "4");
                intent.putExtra("title", names[i]);
                startActivity(intent);
                break;
            case 4://我的三级下属
                intent.putExtra("type", "5");
                intent.putExtra("title", names[i]);
                startActivity(intent);
                break;

        }
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

}
