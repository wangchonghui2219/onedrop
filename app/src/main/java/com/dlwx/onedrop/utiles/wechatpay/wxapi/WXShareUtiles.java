package com.dlwx.onedrop.utiles.wechatpay.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.dlwx.baselib.utiles.Utils;
import com.dlwx.onedrop.R;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * Created by Administrator on 2017/4/17/017.
 */

public class WXShareUtiles {
    private IWXAPI api;
    private static final String APP_ID ="wxd9d9719bda9d3dc0";
    private Context ctx;
    public WXShareUtiles(Context ctx) {
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
    /**
     * 分享网页类型的
     */
    public void share2weixin(int style,Bitmap bmp,String url) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "木香茗";
        msg.description = "快来注册加入我们吧";
        try
        {
//            Bitmap bmp = BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.icon_logo);
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
            thumbBmp.recycle();
            msg.setThumbImage(thumbBmp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;

        req.scene = style  == 1? SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    /**
     * 文本类型分享
     */

    public void wxTextShare(int style,String mess){
        if (!api.isWXAppInstalled()) {
            Toast.makeText(ctx, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        //初始化WXText对象
        WXTextObject textObject = new WXTextObject();
        textObject.text = mess;

        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = textObject;
        mediaMessage.description = "快来注册加入我们吧";

        //构造一个req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = mediaMessage;
        req.scene = style  == 1? SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    /**
     *
     * @param style 1 为好友2为朋友圈
     */
    public void wxImgShare(final int style,Bitmap bmp){
        if (!api.isWXAppInstalled()) {
            Toast.makeText(ctx, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }
//        Bitmap bmp = BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.icon_logo);
        WXImageObject imageObject = new WXImageObject(bmp);
        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = imageObject;


        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
//                bmp.recycle();//使用之后回收
        message.thumbData = Utils.bmpToByteArray(thumbBmp,true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "img";
        req.message = message;
        req.scene = style  == 1? SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    /**
     * 分享音乐
     * @param style
     */
    public void wxMusicShare(int style){
        if (!api.isWXAppInstalled()) {
            Toast.makeText(ctx, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        WXMusicObject musicObject = new WXMusicObject();
        musicObject.musicUrl = "";

        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = musicObject;
        mediaMessage.description = "音乐描述";
        mediaMessage.title = "音乐标题";

        Bitmap bmp = BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_launcher);
        mediaMessage.thumbData = Utils.bmpToByteArray(bmp);
        //构造一个req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = mediaMessage;
        req.scene = style  == 1? SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    /**
     * 视频描述
     * @param style
     */
    public void wxVideoShare(int style){
        if (!api.isWXAppInstalled()) {
            Toast.makeText(ctx, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        WXVideoObject videoObject = new WXVideoObject();
        videoObject.videoUrl = "";
        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = videoObject;
        mediaMessage.description = "视频描述";
        mediaMessage.title = "视频标题";
        Bitmap bmp = BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_launcher);
        mediaMessage.thumbData = Utils.bmpToByteArray(bmp);
        //构造一个req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = mediaMessage;
        req.scene = style  == 1? SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }


}
