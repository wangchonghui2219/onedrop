package com.dlwx.onedrop.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.dlwx.baselib.utiles.LogUtiles;
import com.dlwx.baselib.utiles.SpUtiles;
import com.dlwx.onedrop.ClassSelete;
import com.dlwx.onedrop.adapter.ClassAdapter;
import com.dlwx.onedrop.service.MyReceiver;
import com.dlwx.onedrop.utiles.CityJson;
import com.dlwx.onedrop.utiles.SpUtils;
import com.lzy.okgo.OkGo;
import com.tencent.tauth.Tencent;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 *
 */

public class MyApplication extends Application {

    public static CityJson.SanJiLianDBean sanJiLianDBean;
    public static int classpos;
    public static ClassSelete.ClassSeleteListener classSeleteListener;
    public static ClassAdapter.ClassTitleAdapter classTitleAdapter;
    public static String Token = "cdfa71855d2457511adb007d5c6c3eee";
    public static Tencent mTencent;
    public static String ServPhone = "18637051978";//客服电话
    public static String ServQQ = "852946656";
    public static String HeadPic;
    @SuppressLint("NewApi")
    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
        SharedPreferences sp = getSharedPreferences(SpUtiles.SP_Mode, MODE_PRIVATE);
        Token = sp.getString(SpUtils.TOKEN, "");
        HeadPic = sp.getString(SpUtils.header_pic,"");
        LogUtiles.LogI(Token);
        sanJiLianDBean = CityJson.initJsonData(getApplicationContext());
        mTencent = Tencent.createInstance("1106599006",this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JPushInterface.setLatestNotificationNumber(this, 3);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        MyReceiver mReceiver = new MyReceiver();
        registerReceiver(mReceiver, intentFilter);
        String user_id = sp.getString(SpUtils.User_ID,"");
        if (user_id != null) {

            Set<String> tags = new HashSet<>();
            tags.add(user_id);
            JPushInterface.setTags(getApplicationContext(),1,tags);
        }
    }


}
