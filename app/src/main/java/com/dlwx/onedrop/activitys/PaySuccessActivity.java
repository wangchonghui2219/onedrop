package com.dlwx.onedrop.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.InviteCodeBean;
import com.dlwx.onedrop.bean.PaySuccessBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.dlwx.onedrop.utiles.ShareDialogUtils;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 支付成功
 */
public class PaySuccessActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.btn_share)
    Button btnShare;
    @BindView(R.id.tv_content)
    TextView tv_content;
    private String qr_url;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_pay_success);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.paysuccess);
        HttpType = 1;
        Map<String,String> map = new HashMap<>();
        mPreenter.fetch(map,true, HtttpUrlUtils.PaySuccess,"");
    }
    private void getShareDate() {
        HttpType = 2;
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        mPreenter.fetch(map,true, HtttpUrlUtils.GetCode,"");
    }
    @Override
    protected void initListener() {
        ivPic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (bitmap != null) {
                    String s = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", "description");
                    Toast.makeText(ctx, "保存成功", Toast.LENGTH_SHORT).show();
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(s))));
                }


                return false;
            }
        });
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.btn_share)
    public void onViewClicked() {
        if (TextUtils.isEmpty( qr_url)) {
            return;
        }
        new ShareDialogUtils(ctx,qr_url);
    }

    private Bitmap bitmap;
    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {
            PaySuccessBean paySuccessBean = gson.fromJson(s, PaySuccessBean.class);
            if (paySuccessBean.getCode() == 200) {
                PaySuccessBean.BodyBean body = paySuccessBean.getBody();
                tv_content.setText(body.getInfo());
                showDialogWin(body.getInfo());
                getShareDate();
            }else{

            }
        }else {
            InviteCodeBean inviteCodeBean = gson.fromJson(s, InviteCodeBean.class);
            if (inviteCodeBean.getCode() == 200) {
                InviteCodeBean.BodyBean body = inviteCodeBean.getBody();
                qr_url = body.getQr_url();
                Glide.with(ctx).asBitmap().load(body.getQr_codepic()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        ivPic.setImageBitmap(resource);
                        bitmap = resource;
                    }
                });

            }
        }
    }

    private void showDialogWin(String content) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_pay_succ,null);
        PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        TextView tv_content = view.findViewById(R.id.tv_content);
        tv_content.setText(content);
        backgroundAlpha(0.5f);
        popupWindow.showAtLocation(tvTitle, Gravity.CENTER,0,0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }
}
