package com.dlwx.onedrop.utiles;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.base.MyApplication;
import com.dlwx.onedrop.utiles.wechatpay.wxapi.WXShareUtiles;
import com.dlwx.onedrop.wxapi.CallBackListenerUtiles;
import com.dlwx.onedrop.wxapi.QQUtiles;

/**
 * Created by Administrator on 2017/11/10/010.
 */

public class ShareDialogUtils extends AlertDialog.Builder implements View.OnClickListener {

    private final ViewHolder vh;
    private Context ctx;
    private final AlertDialog show;
    private String qr_codepic;
    public ShareDialogUtils(Context context,String qr_codepic) {
        super(context);
        this.ctx = context;
        this.qr_codepic = qr_codepic;
        View view = LayoutInflater.from(context).inflate(R.layout.dia_share, null);
        vh = new ViewHolder(view);
        vh.ll_QQ.setOnClickListener(this);
        vh.ll_Zone.setOnClickListener(this);
        vh.ll_pyq.setOnClickListener(this);
        vh.ll_Wx.setOnClickListener(this);
        CallBackListenerUtiles.setCallBackListener(listenerUtiles);
        this.setView(view);
        show = this.show();
    }

    @Override
    public void onClick(View view) {
        QQUtiles qqUtiles = new QQUtiles();
        final WXShareUtiles shareUtiles = new WXShareUtiles(ctx);

        switch (view.getId()) {
            case R.id.ll_QQ:
                qqUtiles.shareQQ(ctx,qr_codepic);
                break;
            case R.id.ll_Zone:
                qqUtiles.shareQzone(ctx,qr_codepic);

                break;
            case R.id.ll_pyq:
                Glide.with(ctx).asBitmap().load(MyApplication.HeadPic).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        shareUtiles.share2weixin(2,resource,qr_codepic);
                    }
                });

                break;
            case R.id.ll_Wx:
                Glide.with(ctx).asBitmap().load(MyApplication.HeadPic).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        shareUtiles.share2weixin(1,resource,qr_codepic);
                    }
                });

                break;

        }
    }
    private CallBackListenerUtiles.ShareCallBackListener listenerUtiles
            = new CallBackListenerUtiles.ShareCallBackListener(){
        @Override
        public void setComplete() {
            //分享成功
            show.dismiss();
        }

        @Override
        public void getUserInfo(String nickName, String imagePic, String openID,String gender) {

        }
    };
    private class ViewHolder {
        public View rootView;
        public LinearLayout ll_QQ;
        public LinearLayout ll_Zone;
        public LinearLayout ll_pyq;
        public LinearLayout ll_Wx;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ll_QQ = (LinearLayout) rootView.findViewById(R.id.ll_QQ);
            this.ll_Zone = (LinearLayout) rootView.findViewById(R.id.ll_Zone);
            this.ll_pyq = (LinearLayout) rootView.findViewById(R.id.ll_pyq);
            this.ll_Wx = (LinearLayout) rootView.findViewById(R.id.ll_Wx);
        }

    }
}
