package com.dlwx.onedrop.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.base.MainActivity;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 引导页
 */
public class GuideActivity extends BaseActivity {
    @BindView(R.id.viewpage)
    ViewPager viewpage;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;
    private int [] pics = {R.mipmap.icon_gui1,R.mipmap.icon_gui2,R.mipmap.icon_gui3};
    @Override
    protected void initView() {
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            immersionBar.statusBarDarkFont(false);
            viewpage.setAdapter(new MyPagerAdapter());
        pageIndicatorView.setViewPager(viewpage);
        pageIndicatorView.setAnimationType(AnimationType.WORM);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1002);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }
    private class MyPagerAdapter extends  PagerAdapter{
        @Override
        public int getCount() {
            return pics.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = (ImageView) LinearLayout.inflate(GuideActivity.this,R.layout.ite_page,null);
            Glide.with(ctx).load(pics[position]).into(imageView);
            container.addView(imageView);
            if (position == 2) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (position == 2) {
                            startActivity(new Intent(ctx,MainActivity.class));
                            finish();
                        }

                    }
                });
            }
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
