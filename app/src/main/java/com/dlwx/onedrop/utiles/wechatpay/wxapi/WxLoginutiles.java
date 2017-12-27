package com.dlwx.onedrop.utiles.wechatpay.wxapi;

import android.content.Context;
import android.widget.Toast;

import com.dlwx.onedrop.wxapi.AllInterface;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2017/9/6/006.
 */

public class WxLoginutiles {
    private IWXAPI api;
    private static final String APP_ID ="wxd9d9719bda9d3dc0";
    private Context ctx;
    public WxLoginutiles(Context ctx) {
        this.ctx = ctx;
        regToWx();
    }

    private void regToWx() {
        /**
         * 注册微信
         */

        //通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(ctx, APP_ID, true);
        //讲应用的appid注册到微信
        api.registerApp(APP_ID);

    }

    public void loginWx(final Context ctx){
        if (api != null && api.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            api.sendReq(req);
            AllInterface.setWxLoginListener(new AllInterface.WxLoginListener() {
                @Override
                public void loginListener(boolean isSucc) {
                   if (isSucc) {

                    }else{
                       Toast.makeText(ctx, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(ctx, "您尚未安装微信", Toast.LENGTH_SHORT).show();
        }
    }

}
