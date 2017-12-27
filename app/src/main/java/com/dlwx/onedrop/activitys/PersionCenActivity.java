package com.dlwx.onedrop.activitys;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.UploadPicUtiles;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.PersionMessBean;
import com.dlwx.onedrop.bean.ResuleBean;
import com.dlwx.onedrop.bean.UpPicBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.dlwx.onedrop.utiles.SpUtils;
import com.dlwx.onedrop.views.ZQImageViewRoundOval;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.baselib.utiles.UploadPicUtiles.openAlbum;
import static com.dlwx.baselib.utiles.UploadPicUtiles.opencamera;
import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 个人资料
 */
public class PersionCenActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.rl_nickName)
    RelativeLayout rlNickName;
    @BindView(R.id.rl_gender)
    RelativeLayout rlGender;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.iv_head)
    ZQImageViewRoundOval ivHead;
    private ViewHolder vh;
    private AlertDialog show;
    private String is_qq;
    private String typeid;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_persion_cen);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.persionmess);
        is_qq = sp.getString(SpUtils.isqq, "");
        ivHead.setType(ZQImageViewRoundOval.TYPE_ROUND);
        ivHead.setRoundRadius(10);//矩形凹行大小
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getData() {
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        mPreenter.fetch(map, true, HtttpUrlUtils.GetPersionMEss, Token + HtttpUrlUtils.GetPersionMEss);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.rl_head, R.id.rl_nickName, R.id.rl_gender})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_head:
                showType = 1;

                if (is_qq.equals("3")) {

                    showDialogWindow("修改头像", "相册", "拍照");
                } else {
                    Toast.makeText(ctx, "第三方登录不可修改", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_nickName:
                if (is_qq.equals("3")) {
                    //TODO
                    Intent intent = new Intent(ctx,RevampNickNameActivit.class);
                    startActivityForResult(intent,1001);
                } else {
                    Toast.makeText(ctx, "第三方登录不可修改", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.rl_gender:
                showType = 2;
                if (is_qq.equals("3")) {
                    //TODO
                    showDialogWindow("修改性别", "男", "女");
                } else {
                    Toast.makeText(ctx, "第三方登录不可修改", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void showDialogWindow(String titname, String left, String right) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_persion, null);
        vh = new ViewHolder(view);
        vh.tv_titlename.setText(titname);
        vh.tv_left.setText(left);
        vh.tv_right.setText(right);
        builder.setView(view);
        builder.setCancelable(true);
        show = builder.show();
        vh.tv_right.setOnClickListener(this);
        vh.tv_left.setOnClickListener(this);
    }

    private int showType;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                if (showType == 1) {//相册
                    if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, 102);
                    } else {

                        UploadPicUtiles.isCamera = false;
                        openAlbum((Activity) ctx, "com.dlwx.onedrop");

                    }
                } else {//男
                    typeid = "1";
                    tvGender.setText("男");
                    upRevampMess("3");
                }
                show.dismiss();
                break;
            case R.id.tv_right:
                if (showType == 1) {//拍照
                    if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.CAMERA}, 101);
                    } else {
                        UploadPicUtiles.isCamera = true;

                        opencamera((Activity) ctx, "com.dlwx.onedrop");
                    }
                } else {//女
                    tvGender.setText("女");
                    typeid = "2";
                    upRevampMess("3");
                }
                show.dismiss();

                break;
        }
    }

    @Override
    public void showData(String s) {
        disLoading();
        Gson gson = new Gson();
        if (HttpType == 1) {
            PersionMessBean persionMessBean = gson.fromJson(s, PersionMessBean.class);
            if (persionMessBean.getCode() == 200) {
                PersionMessBean.BodyBean body = persionMessBean.getBody();
                tvNickname.setText(body.getNickname());
                tvGender.setText(body.getSex());
                Glide.with(ctx).load(body.getHeader_pic()).into(ivHead);
            } else {
                Toast.makeText(ctx, persionMessBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }else{
            ResuleBean resuleBean = gson.fromJson(s, ResuleBean.class);
            if (resuleBean.getCode() == 200) {

            }else{

            }
            Toast.makeText(ctx, resuleBean.getResult(), Toast.LENGTH_SHORT).show();
        }
        wch("个人信息：" + s);

    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_titlename;
        public TextView tv_left;
        public TextView tv_right;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_titlename = (TextView) rootView.findViewById(R.id.tv_titlename);
            this.tv_left = (TextView) rootView.findViewById(R.id.tv_left);
            this.tv_right = (TextView) rootView.findViewById(R.id.tv_right);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:

                break;
            case 102:

                break;
        }
    }
    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        wch(requestCode+":"+resultCode);
        if (resultCode == -1) {
            switch (requestCode) {
                case 1:
                    UploadPicUtiles.startZoomPic((Activity) ctx, data, 150, 150, 1, 1);
                    break;
                case 2:
                    UploadPicUtiles.startZoomPic((Activity) ctx, data, 150, 150, 1, 1);
                    break;
                case 5:
                    if (data == null) {
                        wch("空date");
                        return;
                    }
                    File filePath = UploadPicUtiles.getFilePath(data, ctx);
                    wch("上传图片："+filePath);
                    Glide.with(ctx).asFile().load(filePath).into(new SimpleTarget<File>() {
                        @Override
                        public void onResourceReady(File resource, Transition<? super File> transition) {
                            wch("上传图片："+resource);
                            Glide.with(ctx).load(resource).into(ivHead);
                            UpHead(resource);
                        }
                    });

                    break;
            }
         } else if (requestCode == 1001) {
            if (data != null) {
                String nickName = data.getStringExtra("nickName");
                typeid = nickName;
                tvNickname.setText(nickName);
                upRevampMess("1");
            }
        }
    }

    /**
     * 上传修改后的个人信息
     */
    private void upRevampMess(String type) {
        HttpType = 2;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("type", type);
        map.put("typeid", typeid);
        mPreenter.fetch(map, false, HtttpUrlUtils.UpRevampMess, "");
    }

    /**
     * 上传图片
     *
     * @param filePath
     */
    private void UpHead(File filePath) {

        loading.show();
        OkGo.<String>post(HtttpUrlUtils.UpPic)
                .params("file", filePath)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        loading.dismiss();
                        String body = response.body();
                        wch(body);
                        Gson gson = new Gson();
                        UpPicBean upPicBean = gson.fromJson(body, UpPicBean.class);
                        if (upPicBean.getCode() == 200) {
                            sp.edit().putString(SpUtils.header_pic, upPicBean.getBody().getFile()).commit();
                            typeid = upPicBean.getBody().getFileid() + "";
                        }
                       upRevampMess("2");
                    }
                    @Override
                    public void onError(Response<String> response) {
                        response.code();


                        loading.dismiss();
                        Toast.makeText(ctx, "网络连接失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
