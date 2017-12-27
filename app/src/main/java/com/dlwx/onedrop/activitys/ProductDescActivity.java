package com.dlwx.onedrop.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.SetBanner;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.adapter.EvaluateAdapter;
import com.dlwx.onedrop.base.MyApplication;
import com.dlwx.onedrop.bean.KeFuMessBean;
import com.dlwx.onedrop.bean.ProductDescBean;
import com.dlwx.onedrop.bean.ResuleBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.dlwx.onedrop.utiles.SpUtils;
import com.dlwx.onedrop.views.MyNiceVideoPlayer;
import com.dlwx.onedrop.views.MyTxVideoPlayerController;
import com.dlwx.onedrop.views.ProductDescToCat;
import com.dlwx.onedrop.wxapi.QQUtiles;
import com.example.xlhratingbar_lib.XLHRatingBar;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.header.FalsifyHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.youth.banner.Banner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 商品详情
 */
public class ProductDescActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rb_video)
    RadioButton rbVideo;
    @BindView(R.id.rb_pic)
    RadioButton rbPic;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.ev_ratingBar)
    XLHRatingBar evRatingBar;
    @BindView(R.id.tv_totalNum)
    TextView tvTotalNum;
    @BindView(R.id.lv_list)
    MyListView lvList;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_kefu)
    RelativeLayout rlKefu;
    @BindView(R.id.rl_shopcat)
    RelativeLayout rlShopcat;
    @BindView(R.id.rl_buy)
    RelativeLayout rlBuy;
    @BindView(R.id.rl_adcat)
    RelativeLayout rlAdcat;
    @BindView(R.id.iv_play)
    ImageView iv_play;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.ll_allevaluate)
    LinearLayout ll_allevaluate;
    private ViewHolder vh;
    private String productid;
    private ProductDescBean.BodyBean.InfoBean.AllfileBean allfile;
    private ProductDescBean.BodyBean.InfoBean info;
    private AlertDialog kefushow;
    private PopupWindow productpopu;
    private List<String> videoid;
    private List<String> imgids;
    private ViewHolderVideo viewHolderVideo;
    private PopupWindow popuVideo;
    private Bitmap frameAtTime;

    @Override
    protected void initView() {
        productid = getIntent().getStringExtra("productid");
        setContentView(R.layout.activity_product_desc);
        ButterKnife.bind(this);
        refreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadmore(true);//是否启用上拉加载功能
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshLayout.setEnableAutoLoadmore(true);//是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setRefreshHeader(new FalsifyHeader(ctx));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new FalsifyFooter(ctx));
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        getSupportActionBar().setHomeAsUpIndicator(com.dlwx.baselib.R.mipmap.icon_jiantoubaise);
        rbVideo.setChecked(true);
        getData();
    }


    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(200);

            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();

            }
        });
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    if (allfile.getVideoid() != null & allfile.getVideoid().size() != 0) {
                        iv_play.setVisibility(View.VISIBLE);
                    } else {
                        iv_play.setVisibility(View.GONE);
                    }
                } else {
                    iv_play.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.rl_kefu, R.id.rl_shopcat, R.id.rl_buy, R.id.rl_adcat, R.id.ll_allevaluate, R.id.iv_play})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.rl_kefu:
                if (TextUtils.isEmpty(sp.getString(SpUtils.TOKEN,""))) {
                    startActivity(new Intent(ctx,LoginActivity.class));
                    return;
                }
                isFirst = 1;
                getKeFu();
                break;
            case R.id.rl_shopcat:
                if (TextUtils.isEmpty(sp.getString(SpUtils.TOKEN,""))) {
                    startActivity(new Intent(ctx,LoginActivity.class));
                    return;
                }
                finish();
                ProductDescToCat.descToCatInterface.start();
                break;
            case R.id.rl_buy:
                if (TextUtils.isEmpty(sp.getString(SpUtils.TOKEN,""))) {
                    startActivity(new Intent(ctx,LoginActivity.class));
                    return;
                }
                isShopCat = false;
                showPopu();
                break;
            case R.id.rl_adcat:
                if (TextUtils.isEmpty(sp.getString(SpUtils.TOKEN,""))) {
                    startActivity(new Intent(ctx,LoginActivity.class));
                    return;
                }
                isShopCat = true;
                showPopu();
                break;
            case R.id.ll_allevaluate:
                startActivity(new Intent(ctx, AllEvaluateActivity.class).putExtra("productid", productid));
                break;
            case R.id.iv_play:
                showVideoPopu();


                break;

        }
    }


    private boolean isShopCat;

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_video:
                break;
            case R.id.rb_pic:
                break;

        }
    }

    private int num = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_aff:


                if (isShopCat) {
                    addCat();
                } else {
                    submitOrder();

                }
                break;
            case R.id.iv_addNum:
                num++;
                vh.tv_num.setText(num + "");
                break;
            case R.id.iv_minus:
                if (num <= 1) {
                    return;
                }
                num--;
                vh.tv_num.setText(num + "");
                break;
            case R.id.tv_left:
                kefushow.dismiss();
                if (isFirst == 1) {
                    isFirst = 2;
                    isPhone = 1;
                    showDialogWindow(MyApplication.ServPhone, "取消", "呼叫");
                }
                break;
            case R.id.tv_right:
                kefushow.dismiss();
                if (isFirst == 1) {
                    isFirst = 2;
                    isPhone = 2;
                    if (QQUtiles.isQQClientAvailable(ctx)) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + MyApplication.ServQQ + "&version=1")));
                    }
//                    showDialogWindow(MyApplication.ServQQ, "取消", "呼叫");
                } else if (isPhone == 1) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + MyApplication.ServPhone));
                    startActivity(intent);
                }

                break;
        }
    }

    /**
     * 提交訂單
     */
    private void submitOrder() {
        Intent intent = new Intent(ctx, SubmitOrderActivity.class);
        intent.putExtra("num", num);
        intent.putExtra("ordertype", "NoCat");
        intent.putExtra("productid", productid);
        startActivity(intent);
        productpopu.dismiss();
    }

    /**
     * 加入购物车
     */
    private void addCat() {
        HttpType = 3;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("num", num + "");
        map.put("productid", productid);
        mPreenter.fetch(map, true, HtttpUrlUtils.AddCat, "");
    }

    private int isFirst = 0;
    private int isPhone = 0;

    /**
     * 获取客服信息
     */
    private void getKeFu() {
        HttpType = 2;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        mPreenter.fetch(map, true, HtttpUrlUtils.KeFuMess, HtttpUrlUtils.KeFuMess);
    }

    /**
     * 获取详情数据
     */
    private void getData() {
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("productid", productid);
        mPreenter.fetch(map, true, HtttpUrlUtils.ProductDesc, productid);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {

            productData(s, gson);
        } else if (HttpType == 2) {

            KeFuMessBean keFuMessBean = gson.fromJson(s, KeFuMessBean.class);
            if (keFuMessBean.getCode() == 200) {
                KeFuMessBean.BodyBean body = keFuMessBean.getBody();
                MyApplication.ServQQ = body.getQq();
                MyApplication.ServPhone = body.getTel();
                showDialogWindow("联系客服", "电话", "QQ");
            } else {
                Toast.makeText(ctx, keFuMessBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        } else if (HttpType == 3) {

            ResuleBean resuleBean = gson.fromJson(s, ResuleBean.class);
            if (resuleBean.getCode() == 200) {
                productpopu.dismiss();
            }
            Toast.makeText(ctx, resuleBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    private void productData(String s, Gson gson) {
        ProductDescBean productDescBean = gson.fromJson(s, ProductDescBean.class);
        if (productDescBean.getCode() == 200) {
            ProductDescBean.BodyBean body = productDescBean.getBody();
            info = body.getInfo();
            allfile = info.getAllfile();
            imgids = allfile.getImgids();
            List<String> video_imgid = allfile.getVideo_imgid();
            for (int i = 0; i < imgids.size(); i++) {
                video_imgid.add(imgids.get(i));
            }
            videoid = allfile.getVideoid();
            SetBanner.startBanner(ctx, banner, video_imgid);
            if (allfile.getVideoid() != null & allfile.getVideoid().size() != 0) {
                iv_play.setVisibility(View.VISIBLE);
                String path = allfile.getVideo_imgid().get(0);
                Glide.with(ctx).asBitmap().load(path).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        frameAtTime = resource;
                    }
                });
            } else {
                iv_play.setVisibility(View.GONE);
            }
            tvDesc.setText(info.getIntro());
            tvName.setText(info.getTitle());
            tvTitle.setText(info.getTitle());
            tv_content.setText(info.getShort_content());
            tvPrice.setText("￥" + info.getPrice());
            String content = info.getContent();
            initWebView(content);
            List<ProductDescBean.BodyBean.PingjiaBean> pingjia = body.getPingjia();
            wch(pingjia.size());
            lvList.setAdapter(new EvaluateAdapter(ctx, pingjia));
            tvTotalNum.setText(info.getTotal_pingjia() + "条评价");
        } else {
            Toast.makeText(ctx, productDescBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_desc, null);
        vh = new ViewHolder(view);
        productpopu = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        productpopu.setFocusable(true);
        productpopu.setOutsideTouchable(true);
        backgroundAlpha(0.7f);
        productpopu.setBackgroundDrawable(new BitmapDrawable());
        productpopu.showAtLocation(banner, Gravity.BOTTOM, 0, 0);
        productpopu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        vh.btn_aff.setOnClickListener(this);
        vh.iv_addNum.setOnClickListener(this);
        vh.iv_minus.setOnClickListener(this);
        vh.tv_price.setText("￥" + info.getPrice());
        vh.tv_name.setText(info.getTitle());
        String path = info.getAllfile().getImgids().get(0);
        Glide.with(ctx).load(path).into(vh.iv_pic);
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
        kefushow = builder.show();
        diavh.tv_right.setOnClickListener(this);
        diavh.tv_left.setOnClickListener(this);
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


    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_price;
        public TextView tv_name;
        public ImageView iv_minus;
        public TextView tv_num;
        public ImageView iv_addNum;
        public Button btn_aff;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_price = (TextView) rootView.findViewById(R.id.tv_price);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.iv_minus = (ImageView) rootView.findViewById(R.id.iv_minus);
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
            this.iv_addNum = (ImageView) rootView.findViewById(R.id.iv_addNum);
            this.btn_aff = (Button) rootView.findViewById(R.id.btn_aff);
        }

    }

    /**
     * 设置WebView自适应屏幕
     *
     * @param data 目标字符串
     */
    public void initWebView(String data) {
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);//调整到适合webview的大小，不过尽量不要用，有些手机有问题
        settings.setLoadWithOverviewMode(true);//设置WebView是否使用预览模式加载界面。
        webView.setVerticalScrollBarEnabled(false);//不能垂直滑动
        webView.setHorizontalScrollBarEnabled(false);//不能水平滑动
        settings.setTextSize(WebSettings.TextSize.LARGEST);//通过设置WebSettings，改变HTML中文字的大小
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        //设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);//设置js可用
        webView.setWebViewClient(new WebViewClient());
//        webView.addJavascriptInterface(new AndroidJavaScript(getApplication()), "android");//设置js接口
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局


        webView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
    }

    private void showVideoPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_video, null);
        popuVideo = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        popuVideo.setFocusable(true);

        viewHolderVideo = new ViewHolderVideo(view);
        viewHolderVideo.videoplay.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
        viewHolderVideo.videoplay.setUp(videoid.get(0), null);

        MyTxVideoPlayerController controller = new MyTxVideoPlayerController(this);
        controller.setClose(closeInterface);
        controller.setTitle("");
        controller.setImage(frameAtTime);
        viewHolderVideo.videoplay.setController(controller);
        if (viewHolderVideo.videoplay.isIdle()) {
            viewHolderVideo.videoplay.start();
        }
        popuVideo.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
            }
        });
        popuVideo.showAtLocation(iv_play,Gravity.CENTER,0,0);
    }
    private MyTxVideoPlayerController.CloseInterface closeInterface = new MyTxVideoPlayerController.CloseInterface() {
        @Override
        public void close() {
            popuVideo.dismiss();
        }
    };
    @Override
    protected void onStop() {
        super.onStop();
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

   private class ViewHolderVideo {
        public View rootView;
        public MyNiceVideoPlayer videoplay;

        public ViewHolderVideo(View rootView) {
            this.rootView = rootView;
            this.videoplay = (MyNiceVideoPlayer) rootView.findViewById(R.id.videoplay);
        }

    }
}
