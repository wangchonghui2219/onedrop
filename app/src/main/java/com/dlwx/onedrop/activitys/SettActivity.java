package com.dlwx.onedrop.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.UpVersionUtiles;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.adapter.MyListAdapter;
import com.dlwx.onedrop.base.MyApplication;
import com.dlwx.onedrop.bean.KeFuMessBean;
import com.dlwx.onedrop.bean.VersionBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.dlwx.onedrop.utiles.SpUtils;
import com.dlwx.onedrop.wxapi.QQUtiles;
import com.google.gson.Gson;
import com.lzy.okgo.db.CacheManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 设置
 */
public class SettActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    private String[] listName;
    private int[] pics = {R.mipmap.icon_wdjibie,R.mipmap.icon_wdyaoqing,R.mipmap.icon_wdqianbao,
            R.mipmap.icon_wdtuandui,R.mipmap.icon_wdtuandui, R.mipmap.icon_wdkefu, R.mipmap.icon_wdbangzhu,
            R.mipmap.icon_wdqingchu, R.mipmap.icon_wdxitong,};
    private AlertDialog show;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_sett);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.Sett);
        listName = getResources().getStringArray(R.array.sett_name);
        String is_tx_passwrord = sp.getString(SpUtils.is_tx_passwrord, "");
        if (is_tx_passwrord.equals("0")) {
            listName[4] = "设置提现密码";
        }
        lvList.setAdapter(new MyListAdapter(ctx, listName,pics));
    }

    @Override
    protected void initListener() {
        lvList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0://个人资料
                startActivity(new Intent(ctx, PersionCenActivity.class));
                break;
            case 1://更改手机号
                startActivity(new Intent(ctx, RevampPhoneActivity.class));
                break;
            case 2://收货地址
                startActivity(new Intent(ctx,ShippingAddressActivity.class));
                break;
            case 3://更改登录密码
                startActivity(new Intent(ctx, ForgitPwdActivity.class).putExtra("title", "更改登录密码"));
                break;
            case 4://更改提现密码
                startActivity(new Intent(ctx, ForgitPwdActivity.class).putExtra("title", listName[4]));
                break;
            case 5://客服中心
                isFirst = 1;
                getKeFu();

                break;
            case 6://帮助中心
                startActivity(new Intent(ctx, HelpCeneterActivity.class));
                break;
            case 7://清理缓存
                isFirst = 3;
                showDialogWindow("删除所有缓存的图片和视频\n删除后不可恢复，请在交易完成后操作", "取消", "确认");
                break;
            case 8:  //版本更新
                Version();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
         switch (v.getId()){
             case R.id.tv_left:
                 show.dismiss();
                 if (isFirst == 1) {
                     isFirst = 2;
                     isPhone = 1;
                     showDialogWindow(MyApplication.ServPhone, "取消", "呼叫");
                 }
                 break;
             case R.id.tv_right:
                 show.dismiss();
                 if (isFirst == 1) {
                     isFirst = 2;
                     isPhone = 2;
                     if (QQUtiles.isQQClientAvailable(ctx)) {
                         startActivity(new Intent(Intent.ACTION_VIEW,
                                 Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + MyApplication.ServQQ + "&version=1")));
                     }
//                    showDialogWindow(MyApplication.ServQQ, "取消", "呼叫");
                 } else if (isPhone == 1) {
                     intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + MyApplication.ServPhone));
                     startActivity(intent);
                 }
                 if (isFirst == 3) {//清理缓存
                     boolean clear = CacheManager.getInstance().clear();
                     if (clear) {
                         Toast.makeText(ctx, "清除缓存成功", Toast.LENGTH_SHORT).show();
                     } else {
                         Toast.makeText(ctx, "当前没有缓存信息", Toast.LENGTH_SHORT).show();
                     }
                 }
                 break;
                }
    }

    private int isFirst = 0;
    private int isPhone = 0;
    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void showData(String s) {
        disLoading();
        Gson gson = new Gson();
        if (HttpType == 1) {

            KeFuMessBean keFuMessBean = gson.fromJson(s, KeFuMessBean.class);
            if (keFuMessBean.getCode() == 200) {
                KeFuMessBean.BodyBean body = keFuMessBean.getBody();
                MyApplication.ServQQ = body.getQq();
                MyApplication.ServPhone = body.getTel();
                showDialogWindow("联系客服", "电话", "QQ");
            } else {
                Toast.makeText(ctx, keFuMessBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }else{
            versionUp(s, gson);
        }
    }

    private void versionUp(String s, Gson gson) {
        VersionBean version = gson.fromJson(s, VersionBean.class);
        if (version.getCode() == 200) {
            VersionBean.BodyBean body = version.getBody();
            int new_num = body.getNew_num();

            if (new_num > versionCode) {
                UpVersionUtiles versionUtiles = new UpVersionUtiles(ctx);
                versionUtiles.showVersionDia(body.getDownload_url());
            } else {
                Toast.makeText(ctx, version.getResult(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ctx, version.getResult(), Toast.LENGTH_SHORT).show();
        }
    }
    private void showDialogWindow(String titname, String left, String right) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_persion, null);
       ViewHolderDia diavh = new ViewHolderDia(view);
        diavh.tv_titlename.setText(titname);
        diavh.tv_left.setText(left);
        diavh.tv_right.setText(right);
        builder.setView(view);
        builder.setCancelable(true);
        show = builder.show();
        diavh.tv_right.setOnClickListener(this);
        diavh.tv_left.setOnClickListener(this);
    }
    /**
     * 获取客服信息
     */
    private void getKeFu() {
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        mPreenter.fetch(map, true, HtttpUrlUtils.KeFuMess, HtttpUrlUtils.KeFuMess);
    }
    private int versionCode;
    private void Version() {
        HttpType = 2;
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(ctx.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
            HttpType = 2;
            Map<String, String> map = new HashMap<>();
            map.put("version", versionCode + "");

            mPreenter.fetch(map, true, HtttpUrlUtils.upVersion, "upVersion");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    private class ViewHolderDia {
        public View rootView;
        public TextView tv_titlename;
        public TextView tv_left;
        public TextView tv_right;

        public ViewHolderDia(View rootView) {
            this.rootView = rootView;
            this.tv_titlename = (TextView) rootView.findViewById(R.id.tv_titlename);
            this.tv_left = (TextView) rootView.findViewById(R.id.tv_left);
            this.tv_right = (TextView) rootView.findViewById(R.id.tv_right);
        }
    }

}
