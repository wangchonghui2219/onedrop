package com.dlwx.onedrop.utiles.wechatpay;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.dlwx.onedrop.bean.WXPAtResultBean;
import com.dlwx.onedrop.utiles.wechatpay.wxapi.WXPayResultListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * Created by Administrator on 2017/4/14/014.
 */

public class WeChatPayUtiles {
    private Context ctx;
    private static String APP_ID = "wxe10e77f4bbd13789";
    private IWXAPI wxapi;

    public WeChatPayUtiles(Context ctx) {
        super();
        this.ctx = ctx;
            regToWx();
        }

    /**
     * 注册微信
     */
    private void regToWx() {
        //通过WXAPIFactory工厂，获取IWXAPI的实例
        wxapi = WXAPIFactory.createWXAPI(ctx, APP_ID, true);
        //讲应用的appid注册到微信
        wxapi.registerApp(APP_ID);
    }
    /**
     * 微信支付
     *
     * @param
     */
    public void WXPay(WXPAtResultBean.BodyBean info) {
        PayReq req = new PayReq();
        req.appId = info.getAppid();
        req.partnerId = info.getPartnerid();
        req.prepayId = info.getPrepayid();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = info.getNoncestr();
        req.timeStamp = info.getTimestamp();
        req.sign = info.getSign();
        if (wxapi.isWXAppInstalled()) {
            Log.i("wch","wwwwwww");
            wxapi.sendReq(req);
        } else {

            Toast.makeText(ctx, "没有安装微信", Toast.LENGTH_SHORT).show();
        }
    }
    public static WXPayResultListener resultLis;
    public void setResult(WXPayResultListener resultListener) {
        resultLis = resultListener;
    }
}
