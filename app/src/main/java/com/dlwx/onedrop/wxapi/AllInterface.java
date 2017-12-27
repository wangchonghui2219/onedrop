package com.dlwx.onedrop.wxapi;

/**
 * Created by Administrator on 2017/7/13/013.
 */

public class AllInterface {
    public interface DeleteListener {
        void deleteResute(int position);
    }
    public static DeleteListener deleteListen;
    public static void setDelete(DeleteListener deleteListener){
        deleteListen = deleteListener;
    }
    /**
     * 微信登录回调
     */
      public interface WxLoginListener{
        void loginListener(boolean isSucc);
    }
    public static WxLoginListener wxLoginListen;
    public static void setWxLoginListener(WxLoginListener wxLoginListener){
        wxLoginListen = wxLoginListener;
    }
    //**支付成功回调
    public interface PayResultListener{
     void result();
    }
    public static PayResultListener payResultListener;


    public static void setPayResultListener(PayResultListener payResultListener) {
        AllInterface.payResultListener = payResultListener;
    }
}
