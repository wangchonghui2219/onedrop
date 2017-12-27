package com.dlwx.onedrop.base;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.BottomNavigationViewHelper;
import com.dlwx.onedrop.ClassSelete;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.activitys.LoginActivity;
import com.dlwx.onedrop.fragments.ClassFragment;
import com.dlwx.onedrop.fragments.EarningFragment;
import com.dlwx.onedrop.fragments.HomeFragment;
import com.dlwx.onedrop.fragments.MyFragment;
import com.dlwx.onedrop.service.ExampleUtil;
import com.dlwx.onedrop.utiles.AmapUtils;
import com.dlwx.onedrop.utiles.SpUtils;
import com.dlwx.onedrop.views.ProductDescToCat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.onedrop.base.MyApplication.classSeleteListener;
import static com.dlwx.onedrop.base.MyApplication.classTitleAdapter;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener

        ,ClassSelete.ClassSeleteListener {


    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.bottom_navigation_container)
    BottomNavigationView bottomNavigationContainer;
    private List<Fragment> fragments = new ArrayList<>();
    private String token;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sp.edit().putString(SpUtils.First,"1").commit();
    }

    @Override
    protected void initData() {
        if (ContextCompat.checkSelfPermission(ctx, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) ctx, new String[]{
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    1000);
        }
        registerMessageReceiver();
        fragments.add(new HomeFragment());
        fragments.add(new ClassFragment());
        fragments.add(new EarningFragment());
        fragments.add(new MyFragment());
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationContainer);
        bottomNavigationContainer.setOnNavigationItemSelectedListener(this);
        changeFragment(0);
        /**
         * 开启高德定位
         *
         *
         */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1002);
        } else {

            AmapUtils.initialization(getApplicationContext());
        }
    }

    @Override
    protected void initListener() {
        classSeleteListener = this;
        ClassSelete.setSeleteListener(classSeleteListener);
        ProductDescToCat.setDescToCatInterface(descToCatInterface);
    }

    @Override
    protected void onResume() {
        super.onResume();
        token = sp.getString(SpUtils.TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            bottomNavigationContainer.setSelectedItemId( R.id.item_home);
        }else{
            int selectedItemId = bottomNavigationContainer.getSelectedItemId();
            bottomNavigationContainer.setSelectedItemId(selectedItemId);
        }
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    private Fragment fragment;
    private FragmentTransaction transaction;
    private Fragment lastFragment;

    private void changeFragment(int i) {

        transaction = getSupportFragmentManager().beginTransaction();
        // 上一个不为空 隐藏上一个
        if (lastFragment != null&& lastFragment != fragments.get(i)) {
//            transaction.hide(lastFragment);
            transaction.remove(lastFragment);
        }

        fragment = fragments.get(i);
        // fragment不能重复添加 // 添加过 显示 没有添加过 就隐藏
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.fl_content, fragment);
        }
        transaction.commitAllowingStateLoss();
        lastFragment = fragment;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        MyApplication.classpos = 0;
        switch (item.getItemId()) {
            case R.id.item_home://首页
                changeFragment(0);
                break;
            case R.id.item_class://分类
                changeFragment(1);
                break;
            case R.id.item_shopcat://收益
                token = sp.getString(SpUtils.TOKEN, "");
                if (TextUtils.isEmpty(token)) {
                    startActivity(new Intent(ctx, LoginActivity.class));

                }else{

                    changeFragment(2);
                }
                break;
            case R.id.item_my://我的
                token = sp.getString(SpUtils.TOKEN, "");
                if (TextUtils.isEmpty(token)) {
                    startActivity(new Intent(ctx, LoginActivity.class));

                }else{

                    changeFragment(3);
                }
                break;

        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1002:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale((Activity) ctx, permissions[i]);
                        if (showRequestPermission) {//
                            ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1002);
                            return;
                        } else {
                            Toast.makeText(this, "权限已经禁止", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                AmapUtils.initialization(getApplicationContext());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         switch (resultCode){
                    case 1:
                        String city = data.getStringExtra("city");
                        AmapUtils.locationListener.getResultAdd(city);
                        break;
                }
    }

    @Override
    public void seletepos(int i) {
//        changeFragment(1);
        bottomNavigationContainer.setSelectedItemId( R.id.item_class);
        MyApplication.classpos = i;
        if (MyApplication.classTitleAdapter != null) {
            classTitleAdapter.notifyDataSetChanged();
        }
    }
    private ProductDescToCat.ProductDescToCatInterface descToCatInterface = new ProductDescToCat.ProductDescToCatInterface() {
        @Override
        public void start() {
//                changeFragment(2);
//            bottomNavigationContainer.setSelectedItemId( R.id.item_shopcat);
            }
    };


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }
    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("wch","接收到了广播");
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }

                }
            } catch (Exception e){
            }
        }
    }
}
