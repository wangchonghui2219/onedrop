package com.dlwx.onedrop.activitys;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.InviteCodeBean;
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
 * 邀请好友
 */
public class InviteFriendsActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.btn_share)
    Button btnShare;

    private String qr_url;
    private InviteCodeBean.BodyBean body;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_invite_friends);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.invitefriend);
        getData();
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        mPreenter.fetch(map, true, HtttpUrlUtils.GetCode, "");
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
    private ContentResolver cr = new ContentResolver(ctx) {
        @Nullable
        @Override
        public String[] getStreamTypes(@NonNull Uri url, @NonNull String mimeTypeFilter) {
            return super.getStreamTypes(url, mimeTypeFilter);
        }

    };
    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    private Bitmap bitmap;
    @Override
    public void showData(String s) {
        disLoading();
        Gson gson = new Gson();
        InviteCodeBean inviteCodeBean = gson.fromJson(s, InviteCodeBean.class);
        if (inviteCodeBean.getCode() == 200) {
            body = inviteCodeBean.getBody();
            qr_url = body.getQr_url();
            Glide.with(ctx).asBitmap().load(body.getQr_codepic()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    ivPic.setImageBitmap(resource);
                    bitmap = resource;
                }
            });
        } else {

        }
    }
    @OnClick({ R.id.btn_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_pic:
                
                
                break;
            case R.id.btn_share:
                if (body.getIs_share() == 2) {
                    Toast.makeText(ctx, R.string.shareString, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(qr_url)) {
                    return;
                }
                new ShareDialogUtils(ctx,qr_url);
                break;
        }
    }
}
