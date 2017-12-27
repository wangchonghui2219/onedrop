package com.dlwx.onedrop.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.adapter.MyAdapter;
import com.dlwx.onedrop.base.MyApplication;
import com.dlwx.onedrop.initialsort.CharacterParser;
import com.dlwx.onedrop.initialsort.PinyinComparator;
import com.dlwx.onedrop.initialsort.SideBar;
import com.dlwx.onedrop.initialsort.SortAdapter;
import com.dlwx.onedrop.initialsort.SortModel;
import com.dlwx.onedrop.utiles.CityJson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.onedrop.utiles.AmapUtils.city;

public class CityListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.dialog)
    TextView dialog;
    @BindView(R.id.sidrbar)
    SideBar sidrbar;
    private SortAdapter adapter;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private List<SortModel> mSortList;
    private List<String> privates = new ArrayList<>();
    private CityJson.SanJiLianDBean sanJiLianDBean;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_city_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.location);
        sanJiLianDBean = MyApplication.sanJiLianDBean;
        final List<CityJson.SanJiLianDBean.CityList> citylist = sanJiLianDBean.getCitylist();
        privates.add("全国");
        for (int i = 0; i < citylist.size(); i++) {
            privates.add(citylist.get(i).getP());
        }
        sidrbar.setTextView(dialog);
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();
        tvCity.setText(city);
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_pro_head, null);
        lvList.addHeaderView(view);
        GridView gv_list = (GridView) view.findViewById(R.id.gv_list);
        final MyAdapter hotadapter = new MyAdapter(ctx, privates);
        gv_list.setAdapter(hotadapter);
        SourceDateList = filledData(citylist);
//        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList);
        lvList.setAdapter(adapter);
        gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hotadapter.setCheckpos(position);
                if (position == 0) {
                    SourceDateList = filledData(citylist);
                }else{
                    List<CityJson.SanJiLianDBean.CityList.CBean> c = citylist.get(position-1).getC();
                    SourceDateList =   filledProData(c);
                }



//        // 根据a-z进行排序源数据
                Collections.sort(SourceDateList, pinyinComparator);
                adapter = new SortAdapter(CityListActivity.this, SourceDateList);
                lvList.setAdapter(adapter);
            }
        });
        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               successBack(city);
            }
        });
    }

    private void successBack(String hotCity1) {
        String hotCity = hotCity1;
        Intent intent = new Intent();
        intent.putExtra("city",hotCity);
        setResult(1,intent);
        finish();
    }

    @Override
    protected void initListener() {
        //设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    lvList.setSelection(position);
                }

            }
        });
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SortModel sortModel = mSortList.get(position-1);

                successBack(sortModel.getName());
            }
        });
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    /**
     * 为ListView填充数据(全国)
     *
     * @param
     * @return
     */
    private List<SortModel> filledData(List<CityJson.SanJiLianDBean.CityList> citylist) {
        mSortList = new ArrayList<SortModel>();
        for (int i = 0; i < citylist.size(); i++) {
            List<CityJson.SanJiLianDBean.CityList.CBean> c = citylist.get(i).getC();
            for (int j = 0; j < c.size(); j++) {
                SortModel sortModel = new SortModel();
                sortModel.setName(c.get(j).getN());
                sortModel.setPro(citylist.get(i).getP());
                //汉字转换成拼音
                String pinyin = characterParser.getSelling(c.get(j).getN());
                String sortString = pinyin.substring(0, 1).toUpperCase();

                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    sortModel.setSortLetters(sortString.toUpperCase());
                } else {
                    sortModel.setSortLetters("#");
                }

                mSortList.add(sortModel);

            }
        }

        return mSortList;

    }
    /**
     * 为ListView填充数据(全国)
     *
     * @param
     * @return
     */
    private List<SortModel> filledProData(List<CityJson.SanJiLianDBean.CityList.CBean> c) {
        mSortList = new ArrayList<SortModel>();

            for (int j = 0; j < c.size(); j++) {
                SortModel sortModel = new SortModel();
                sortModel.setName(c.get(j).getN());
                //汉字转换成拼音
                String pinyin = characterParser.getSelling(c.get(j).getN());
                String sortString = pinyin.substring(0, 1).toUpperCase();

                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    sortModel.setSortLetters(sortString.toUpperCase());
                } else {
                    sortModel.setSortLetters("#");
                }

                mSortList.add(sortModel);

            }

        return mSortList;

    }

}
