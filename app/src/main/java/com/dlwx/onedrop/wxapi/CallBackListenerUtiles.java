package com.dlwx.onedrop.wxapi;

/**
 * Created by Administrator on 2017/9/4/004.
 */

public class CallBackListenerUtiles {

    public interface ShareCallBackListener{

        void setComplete();
        void getUserInfo(String nickName, String imagePic, String openID,String gender);

    }

    public static ShareCallBackListener callBackListener;
    public static void setCallBackListener(ShareCallBackListener callBackListener1){
        callBackListener = callBackListener1;
    }
}
