package com.dlwx.onedrop.fragments;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.SetBanner;
import com.dlwx.onedrop.ClassSelete;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.activitys.CityListActivity;
import com.dlwx.onedrop.activitys.MessageActivity;
import com.dlwx.onedrop.activitys.ProductDescActivity;
import com.dlwx.onedrop.adapter.HomeAdapter;
import com.dlwx.onedrop.base.MyApplication;
import com.dlwx.onedrop.bean.ClassIdBean;
import com.dlwx.onedrop.bean.MessNumBean;
import com.dlwx.onedrop.utiles.AmapUtils;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment implements AmapUtils.LocationListener, View.OnClickListener {


    @BindView(R.id.lv_list)
    ListView lv_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private List<ClassIdBean.BodyBean.ListBean> list;
    private ViewHolder vh;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_home;

    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        refreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadmore(true);//是否启用上拉加载功能
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshLayout.setEnableAutoLoadmore(true);//是否启用列表惯性滑动到底部时自动加载更多
        View headView = LayoutInflater.from(ctx).inflate(R.layout.home_title, null);
        vh = new ViewHolder(headView);
        lv_list.addHeaderView(headView);
    }

    @Override
    protected void initDate() {

        getHomeDate();


    }
    private int HttpType = 0;
    private void getHomeDate() {
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        mPreenter.fetch(map, true, HtttpUrlUtils.ClassID, "class" + MyApplication.classpos + 1);

    }
    /**
     * 获取消息数量
     */
    private void getNumber() {
        HttpType = 2;
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        mPreenter.fetch(map,true, HtttpUrlUtils.MessNum,"");

    }

    @Override
    protected void initListener() {
        vh.btn_class1.setOnClickListener(HomeFragment.this);
        vh.btn_class2.setOnClickListener(HomeFragment.this);
        vh.btn_class3.setOnClickListener(HomeFragment.this);
        vh.btn_class4.setOnClickListener(HomeFragment.this);
        vh.iv_message.setOnClickListener(this);
        vh.ll_seletcity.setOnClickListener(this);
        AmapUtils.setLocationListener(this);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void getResultAdd(String city) {
        vh.tv_city.setText(city);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch("分类" + s);
        Gson gson = new Gson();

        if (HttpType == 1) {
            getNumber();
            ClassIdBean classIdBean = gson.fromJson(s, ClassIdBean.class);
            if (classIdBean.getCode() == 200) {
                ClassIdBean.BodyBean body = classIdBean.getBody();
                List<String> listeBanner = new ArrayList<>();
                List<ClassIdBean.BodyBean.BannerBean> banner = body.getBanner();
                for (int i = 0; i < banner.size(); i++) {
                    listeBanner.add(banner.get(i).getBanner_img());
                }
                SetBanner.startBanner(ctx, vh.banner, listeBanner);
                list = body.getList();
                lv_list.setOnItemClickListener(itemClickListener);
                lv_list.setAdapter(new HomeAdapter(ctx, list));
            }
        }else{
            MessNumBean messNumBean = gson.fromJson(s, MessNumBean.class);
            if (messNumBean.getCode() == 200) {
                MessNumBean.BodyBean body = messNumBean.getBody();
                if (body.getSys_msgnum()==0) {

                    if (body.getOrder_msgnum()==0) {

                        vh.iv_reddot.setVisibility(View.GONE);
                    }else{
                        vh.iv_reddot.setVisibility(View.VISIBLE);
                    }
                }else{
                   vh.iv_reddot.setVisibility(View.VISIBLE);
                }

            }else{
                Toast.makeText(ctx, messNumBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_seletcity:
                startActivityForResult(new Intent(ctx, CityListActivity.class), 1);
                break;
            case R.id.btn_class1:
                ClassSelete.seleteListener.seletepos(0);
                break;
            case R.id.btn_class2:
                ClassSelete.seleteListener.seletepos(1);
                break;
            case R.id.btn_class3:
                ClassSelete.seleteListener.seletepos(2);
                break;
            case R.id.btn_class4:
                ClassSelete.seleteListener.seletepos(3);
                break;
            case R.id.iv_message:
                startActivity(new Intent(ctx, MessageActivity.class));
                break;

        }
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ClassIdBean.BodyBean.ListBean listBean = list.get(i - 1);
            String productid = listBean.getProductid();
            Intent intent = new Intent(ctx, ProductDescActivity.class);
            intent.putExtra("productid", productid);
            startActivity(intent);
        }
    };

    private class ViewHolder {
        public View rootView;
        public Banner banner;
        public TextView tv_city;
        public LinearLayout ll_seletcity;
        public ImageView iv_reddot;
        public ImageView iv_message;
        public Button btn_class1;
        public Button btn_class2;
        public Button btn_class3;
        public Button btn_class4;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.banner = (Banner) rootView.findViewById(R.id.banner);
            this.tv_city = (TextView) rootView.findViewById(R.id.tv_city);
            this.ll_seletcity = (LinearLayout) rootView.findViewById(R.id.ll_seletcity);
            this.iv_reddot = (ImageView) rootView.findViewById(R.id.iv_reddot);
            this.iv_message = (ImageView) rootView.findViewById(R.id.iv_message);
            this.btn_class1 = (Button) rootView.findViewById(R.id.btn_class1);
            this.btn_class2 = (Button) rootView.findViewById(R.id.btn_class2);
            this.btn_class3 = (Button) rootView.findViewById(R.id.btn_class3);
            this.btn_class4 = (Button) rootView.findViewById(R.id.btn_class4);

        }

    }
}
