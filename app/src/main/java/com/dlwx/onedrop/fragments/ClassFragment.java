package com.dlwx.onedrop.fragments;


import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.activitys.ProductDescActivity;
import com.dlwx.onedrop.adapter.ClassAdapter;
import com.dlwx.onedrop.adapter.ClassRightAdapter;
import com.dlwx.onedrop.base.MyApplication;
import com.dlwx.onedrop.bean.ClassIdBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.dlwx.onedrop.base.MyApplication.classTitleAdapter;

/**
 * 分类
 */
public class ClassFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.gv_list)
    GridView gvList;
    Unbinder unbinder;
    private String[] classtitlename;
    private List<ClassIdBean.BodyBean.ListBean> list;


    @Override
    public int getLayoutID() {
        return R.layout.fragment_class;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        Resources res =getResources();
        classtitlename = res.getStringArray(R.array.class_titlename);
    }

    @Override
    protected void initDate() {
        classTitleAdapter = new ClassAdapter.ClassTitleAdapter(ctx,classtitlename);
        lvList.setAdapter(classTitleAdapter);
        getIndex(MyApplication.classpos+1+"");

    }

    /**
     * 获取分类数据
     */
    private void getIndex(String index) {
        Map<String,String> map = new HashMap<>();
        map.put("classid",index);
        mPreenter.fetch(map,true, HtttpUrlUtils.GetIndex,HtttpUrlUtils.GetIndex+index);
    }

    @Override
    protected void initListener() {
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                classTitleAdapter.setSelete(i);
                getIndex(i+1+"");
            }
        });
        gvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClassIdBean.BodyBean.ListBean listBean = list.get(i);
                String productid = listBean.getProductid();
                Intent intent = new Intent(ctx, ProductDescActivity.class);
                intent.putExtra("productid", productid);
                startActivity(intent);
            }
        });

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(ctx, ProductDescActivity.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        ClassIdBean classIdBean = gson.fromJson(s, ClassIdBean.class);
        if (classIdBean.getCode() == 200) {
            list = classIdBean.getBody().getList();
            gvList.setAdapter(new ClassRightAdapter(ctx, list));
        }else{
            Toast.makeText(ctx, classIdBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }
}
