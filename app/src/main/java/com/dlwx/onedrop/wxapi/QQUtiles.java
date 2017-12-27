package com.dlwx.onedrop.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dlwx.baselib.utiles.LogUtiles;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.base.MyApplication;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.dlwx.onedrop.base.MyApplication.mTencent;


/**
 * Created by Administrator on 2017/9/1/001.
 */

public class QQUtiles {
    private Context ctx;
    private int CallBackType = 1;
    public void shareQzone(final Context ctx, final String mess)
    {
        this.ctx = ctx;


                CallBackType = 1;
                Bundle bundle = new Bundle();
                //这条分享消息被好友点击后的跳转URL。
                bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, "木香茗");// 标题
                bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "快来注册加入我们吧");// 摘要
                bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, mess);// 内容地址
                bundle.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, MyApplication.HeadPic);
                //网络图片地址　　
                bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "木香茗");// 应用名称
                bundle.putString(QzoneShare.SHARE_TO_QQ_EXT_INT, "快来注册加入我们吧");
                bundle.putInt(QzoneShare.SHARE_TO_QQ_IMAGE_URL, R.mipmap.icon_logo);
                ArrayList<String> imgUrlList = new ArrayList<>();
                imgUrlList.add(MyApplication.HeadPic);
                bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
                mTencent.shareToQzone((Activity) ctx, bundle , uiListener);




    }

    public void shareQQ(Context ctx, String mess){
        this.ctx = ctx;
        CallBackType = 1;
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "木香茗");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "快来注册加入我们吧");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  mess);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,MyApplication.HeadPic);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "木香茗");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, R.string.app_name);
        mTencent.shareToQQ((Activity) ctx, params , uiListener);
    }
    /**
     * 分享纯图片
     * @param ctx
     */
    public void shareImageQQ(final Context ctx, String url){
        this.ctx = ctx;
        OkGo.<File>get(url)
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(Response<File> response) {
                        Log.i("wch",response.body().getAbsolutePath());
                        CallBackType = 1;
                        final Bundle params = new Bundle();
                        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);

                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, response.body().getAbsolutePath());
                        ArrayList<String> imgUrlList = new ArrayList<>();
//                        imgUrlList.add(response.body().getAbsolutePath());
//                        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imgUrlList);// 图片地址
                        mTencent.shareToQQ((Activity) ctx, params , uiListener);
                    }
                });
    }
    /**
     * 分享纯图片到空间
     * @param ctx
     */
    public void shareImageQQZone(final Context ctx, String url){
        this.ctx = ctx;
        OkGo.<File>get(url)
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(Response<File> response) {
                        CallBackType = 1;
                        Bundle bundle = new Bundle();
                        //这条分享消息被好友点击后的跳转URL。
                        bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                        bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, "淘优赚");// 标题
                        bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "点击跳转下载连接");// 摘要
                        bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://120.27.14.79/A_plan/apk/share.html");// 内容地址
                        bundle.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, "http://120.27.14.79/A_plan/apk/share.png ");
                        //网络图片地址　　
                        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "淘优赚");// 应用名称
                        bundle.putString(QzoneShare.SHARE_TO_QQ_EXT_INT, "其它附加功能");
                        ArrayList<String> imgUrlList = new ArrayList<>();
                        imgUrlList.add("http://120.27.14.79/A_plan/apk/share.png");
                        bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
                        mTencent.shareToQzone((Activity) ctx, bundle , uiListener);
                    }
                });
    }

    /**
     * 登录
     * @param ctx
     */
    public void LoginQQ(Context ctx){
        if (!mTencent.isSessionValid())
        {
            CallBackType = 2;
            mTencent.login((Activity) ctx, "all", uiListener);
        }else{

        }
    }

    public void shareText(Context ctx, String mess){
        this.ctx = ctx;
        if (isQQClientAvailable(ctx)) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, mess);
            sendIntent.setType("text/plain");
            //sendIntent.setPackage("com.tencent.mobileqq");
            List<ResolveInfo> list= getShareTargets(ctx);
//        for (int i = 0; i < list.size(); i++) {
//            String packageName = list.get(i).activityInfo.packageName;
//            Log.i("wch",packageName);
//        }
            try {
                sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
//            Intent chooserIntent = Intent.createChooser(sendIntent, "选择分享途径");
//            if (chooserIntent == null) {
//                return;
//            }
                ctx.startActivity(sendIntent);
            } catch (Exception e) {
                ctx.startActivity(sendIntent);
            }
        }else{
            Toast.makeText(ctx, "您还没有安装QQ,请先安装QQ客户端", Toast.LENGTH_SHORT).show();
        }
       
    }
    /* 获得支持ACTION_SEND的应用列表 */
    private List<ResolveInfo> getShareTargets(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        PackageManager pm = context.getPackageManager();
        return pm.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
    }
    /**
     * 退出QQ
     * @param ctx
     */
    public void logout(Context ctx)
    {
        this.ctx = ctx;
        mTencent.logout(ctx);
    }
    private static String openId;
    public IUiListener uiListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            org.json.JSONObject obj = (org.json.JSONObject) o;
            Log.i("wch", "#onComplete " + obj);
            LogUtiles.LogI(o+"object");
            if (CallBackType == 1) {

                if (CallBackListenerUtiles.callBackListener != null) {

                    CallBackListenerUtiles.callBackListener.setComplete();
                }
            }else{
                getUserMess((JSONObject) o);

            }

        }
        @Override
        public void onError(UiError uiError) {
            Log.i("wch",uiError.errorCode+"错误码");
            String errorMessage = uiError.errorMessage;
            Log.i("wch",errorMessage);
            String errorDetail = uiError.errorDetail;
            Log.i("wch",errorDetail+"dasdas");
        }

        @Override
        public void onCancel() {
            Log.i("wch", "#onCancel 取消");
        }
    };

    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
               String pn = pinfo.get(i).packageName;
//                LogUtiles.LogE("pn = "+pn);
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 登陆成功 获取用户信息
     * @param o
     */
    private void getUserMess(JSONObject o) {
        JSONObject jsonObject = o;
        //获取用户的openid
        try {
            openId = jsonObject.getString("openid");
            String expirs = jsonObject.getString("expires_in");
            String access_token = jsonObject.getString("access_token");
            mTencent.setOpenId(openId);
            mTencent.setAccessToken(access_token, expirs);
            QQToken qqToken = mTencent.getQQToken();
            UserInfo userInfo = new UserInfo(ctx, qqToken);
            LogUtiles.LogI(openId);
            userInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    if (o == null) {
                        return;
                    }

                    JSONObject json = (JSONObject) o;
                    try {
                        int ret = json.getInt("ret");
                        //获取用户信息
                       String nickname = json.getString("nickname");
                        String gender = json.getString("gender");
                        LogUtiles.LogI(gender);
                        if (gender.equals("男")) {
                            gender = "1";
                        }else{
                            gender = "2";
                        }
                       String picUrl = json.getString("figureurl_qq_2");
                        if (CallBackListenerUtiles.callBackListener != null) {

                            CallBackListenerUtiles.callBackListener.getUserInfo(nickname,picUrl,openId,gender);
                        }

                    } catch (JSONException e) {
                        LogUtiles.LogI(e.getMessage());
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(UiError uiError) {
                    String errorMessage = uiError.errorMessage;
                    LogUtiles.LogI(errorMessage);
                }

                @Override
                public void onCancel() {

                }
            });
        } catch (JSONException e) {
            String message = e.getMessage();
            LogUtiles.LogI(message);
            e.printStackTrace();
        }
    }
}
