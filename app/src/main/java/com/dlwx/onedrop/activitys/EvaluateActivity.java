package com.dlwx.onedrop.activitys;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.UploadPicUtiles;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.adapter.EvaluaUpPicAdapter;
import com.dlwx.onedrop.bean.ResuleBean;
import com.dlwx.onedrop.bean.UpPicBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.example.xlhratingbar_lib.XLHRatingBar;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.baselib.utiles.UploadPicUtiles.openAlbum;
import static com.dlwx.baselib.utiles.UploadPicUtiles.opencamera;
import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 发表评价
 */
public class EvaluateActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.gv_list)
    GridView gvList;
    @BindView(R.id.ev_ratingBar)
    XLHRatingBar evRatingBar;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.et_content)
    EditText et_content;
    private int rating;
    private List<File> pics = new ArrayList<>();
    private EvaluaUpPicAdapter evaluaUpPicAdapter;
    private AlertDialog show;
    private File filePath;
    private String typeid = "";
    private String productid;

    @Override
    protected void initView() {
        productid = getIntent().getStringExtra("productid");
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.evaluate);
        evaluaUpPicAdapter = new EvaluaUpPicAdapter(ctx, pics);
        gvList.setAdapter(evaluaUpPicAdapter);
    }

    @Override
    protected void initListener() {
        evRatingBar.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(int countSelected) {

                rating = countSelected;


                Log.d("xx", String.valueOf(rating));
            }
        });
        gvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        wch(i);
        if (i == pics.size()) {
            if (pics.size() >= 3) {
                return;
            }
            //调用相机/相册选取图片
            showDialogWindow("修改头像", "相册", "拍照");
        } else {
            //删除

            deleImage(typeids.get(i));
            pics.remove(i);
            typeids.remove(i);
            evaluaUpPicAdapter.notifyDataSetChanged();
        }
    }


    private void showDialogWindow(String titname, String left, String right) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_persion, null);
        ViewHolder vh = new ViewHolder(view);
        vh.tv_titlename.setText(titname);
        vh.tv_left.setText(left);
        vh.tv_right.setText(right);
        builder.setView(view);
        builder.setCancelable(true);
        show = builder.show();
        vh.tv_right.setOnClickListener(this);
        vh.tv_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 102);
                } else {
                    show.dismiss();
                    UploadPicUtiles.isCamera = false;
                    openAlbum((Activity) ctx, "com.dlwx.onedrop");
                }
                break;
            case R.id.tv_right:
                if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.CAMERA}, 101);
                } else {
                    UploadPicUtiles.isCamera = true;
                    show.dismiss();
                    opencamera((Activity) ctx, "com.dlwx.onedrop");
                }

                break;

        }
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        String cobtent = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(cobtent)) {
            Toast.makeText(ctx, "请输入评价内容", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpType = 2;
        int countNum = evRatingBar.getCountNum();
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("productrid", productid);
        map.put("pingjia", cobtent);
        map.put("pingjia_img", typeid);
        map.put("rate_level", countNum + "");
        mPreenter.fetch(map, false, HtttpUrlUtils.UpEvaluate, "");
    }

    /**
     * 删除图片
     *
     * @param s
     */
    private void deleImage(String s) {
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("imgid", s);
        mPreenter.fetch(map, true, HtttpUrlUtils.DeleteImg, "");
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();

            ResuleBean resuleBean = gson.fromJson(s, ResuleBean.class);
            if (resuleBean.getCode() == 200) {
                if (HttpType == 2) {
                    finish();
                }
            }
            Toast.makeText(ctx, resuleBean.getResult(), Toast.LENGTH_SHORT).show();


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        wch(requestCode + ":" + resultCode);
        if (resultCode == -1) {
            switch (requestCode) {
                case 1:
                    String paths = UploadPicUtiles.getPicCamera(data, (Activity) ctx);
                    filePath = new File(paths);
                    wch(filePath);
                    pics.add(filePath);
                    evaluaUpPicAdapter.notifyDataSetChanged();
//                    UploadPicUtiles.startZoomPic((Activity) ctx, data, 150, 150, 1, 1);
                    break;
                case 2:
                    String path = UploadPicUtiles.getPicAlbum(data, ctx);
                    filePath = new File(path);
                    wch(filePath);

                    upPic(filePath);
                    break;
            }
        }
    }

    private List<String> typeids = new ArrayList<>();

    /**
     * 上传图片
     */
    private void upPic(File file) {
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
                            pics.add(filePath);
                            evaluaUpPicAdapter.notifyDataSetChanged();

                            typeids.add(upPicBean.getBody().getFileid() + "");

                            if (TextUtils.isEmpty(typeid)) {

                                typeid = upPicBean.getBody().getFileid() + "";
                            } else {
                                typeid = typeid + "," + upPicBean.getBody().getFileid();
                            }

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        loading.dismiss();
                        Toast.makeText(ctx, "网络连接失败", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
