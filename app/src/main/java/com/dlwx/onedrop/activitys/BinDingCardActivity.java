package com.dlwx.onedrop.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.adapter.BankListAdapter;
import com.dlwx.onedrop.bean.BankCardListBean;
import com.dlwx.onedrop.bean.ResuleBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 绑定银行卡
 */
public class BinDingCardActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rl_entry)
    RelativeLayout rlEntry;
    @BindView(R.id.rev_view)
    SwipeMenuRecyclerView recyclerView;
    @BindView(R.id.btn_addBink)
    Button btn_addBink;
    private ViewHolderFood foodvh;
    private View footView;
    private List<BankCardListBean.BodyBean> body;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_bin_ding_card);
        ButterKnife.bind(this);
        footView = LayoutInflater.from(ctx).inflate(R.layout.foot_view, null);

    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.bankcarbinding);

    }

    @Override
    protected void onResume() {
        super.onResume();

        getCardList();
    }

    @Override
    protected void initListener() {

        LinearLayoutManager manager = new LinearLayoutManager(ctx, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addFooterView(footView);
        foodvh = new ViewHolderFood(footView);
        foodvh.btn_add.setOnClickListener(this);
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }



    @OnClick({R.id.rl_entry,R.id.btn_addBink})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_entry:
                startActivity(new Intent(ctx,AddBankActivity.class));
                break;
            case R.id.btn_addBink:
                startActivity(new Intent(ctx,AddBankActivity.class));
                break;

        }
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
                    case R.id.btn_add:
                        startActivity(new Intent(ctx,AddBankActivity.class));
                        break;
                }
    }

    private void getCardList() {
        HttpType = 1;

        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        mPreenter.fetch(map, true, HtttpUrlUtils.BankCardList, Token + HtttpUrlUtils.BankCardList);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch("银行卡列表：" + s);
        Gson gson = new Gson();
        if (HttpType == 1) {

            getBankList(s, gson);
        }else{
            ResuleBean resuleBean = gson.fromJson(s, ResuleBean.class);
            if (resuleBean.getCode() == 200) {
                getCardList();

            }
            Toast.makeText(ctx, resuleBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getBankList(String s, Gson gson) {
        BankCardListBean bankCardListBean = gson.fromJson(s, BankCardListBean.class);
        if (bankCardListBean.getCode() == 200) {
            body = bankCardListBean.getBody();
            if (body == null && body.size() == 0) {
                rlEntry.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                btn_addBink.setVisibility(View.VISIBLE);

            } else {
                rlEntry.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                btn_addBink.setVisibility(View.GONE);
                foodvh.btn_add.setVisibility(View.GONE);
                recyclerView.setAdapter(new BankListAdapter(ctx, body));
            }
        }else if (bankCardListBean.getCode() == 204) {
            rlEntry.setVisibility(View.VISIBLE);
            btn_addBink.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            Toast.makeText(ctx, bankCardListBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.delete);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = DensityUtil.dip2px(ctx,130);


            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(ctx)
                        .setBackground(R.color.red)
                        .setImage(R.drawable.ic_delete_black_24dp)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

//                SwipeMenuItem addItem = new SwipeMenuItem(ctx)
//                        .setBackground(R.color.red)
//                        .setText("添加")
//                        .setTextColor(Color.WHITE)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            }
        }
    };


    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            // 菜单在RecyclerView的Item中的Position。
            int menuPosition = menuBridge.getPosition();

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                HttpType = 2;
                Map<String, String> map = new HashMap<>();
                wch("Token:"+Token+"BankID:"+body.get(adapterPosition).getBank_id());
                map.put("token", Token);
                map.put("bank_id", body.get(adapterPosition).getBank_id());
                mPreenter.fetch(map, true, HtttpUrlUtils.delete_bank, "");
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(ctx, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private class ViewHolderFood {
        public View rootView;
        public Button btn_add;

        public ViewHolderFood(View rootView) {
            this.rootView = rootView;
            this.btn_add = (Button) rootView.findViewById(R.id.btn_add);

        }

    }

    public static class DensityUtil {

        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        public static int dip2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        public static int px2dip(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }
    }

}
