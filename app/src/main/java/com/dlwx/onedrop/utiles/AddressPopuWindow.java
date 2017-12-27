package com.dlwx.onedrop.utiles;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dlwx.onedrop.R;
import com.dlwx.onedrop.views.SeleteAddressInter;

import java.util.List;

/**
 * 显示地址选择的窗体
 */
public  class AddressPopuWindow {
    /**
     *
     */
    private static List<CityJson.SanJiLianDBean.CityList> cityLists;
    private static ViewHolder vh;
    private static String[] pro;
    private static String[] city;
    private static List<CityJson.SanJiLianDBean.CityList.CBean> citys;
    private static String[] country;
    private static List<CityJson.SanJiLianDBean.CityList.CBean.aBean> countrys;
    private static TextView tv_cancel,tv_achieve;
    private static PopupWindow popu;
    private static SeleteAddressInter addressInterface;
    public static void showWindow(final Context ctx, View parent) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_city, null, false);
        popu = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        vh = new ViewHolder(view);
        popu.setFocusable(true);
        ColorDrawable drawable = new ColorDrawable(0x77000000);
        popu.setBackgroundDrawable(drawable);
        popu.setAnimationStyle(R.style.ButtomPopu);
        backgroundAlpha(0.5f, ctx);
        popu.showAtLocation(parent, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        popu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f, ctx);
            }
        });
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_achieve = (TextView) view.findViewById(R.id.tv_achieve);
            tv_achieve.setOnClickListener(listener);
        tv_cancel.setOnClickListener(listener);
        cityLists = CityJsonUtile.initJsonData(ctx);


        setProData();
        setCityData(0);
        setCountryData(0);

    }
    private static View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.tv_cancel) {
                popu.dismiss();

            } else if (i == R.id.tv_achieve) {
                String p = cityLists.get(vh.province.getValue()).getP();//省
                String c = citys.get(vh.city.getValue()).getN(); //市
                String s = "";
                if (citys.get(vh.city.getValue()).getA() != null) {

                    s = countrys.get(vh.county.getValue()).getS();//县


                }else{
                    s = c;
                    c = "";
                }
                popu.dismiss();
                addressInterface.setAddressSeleteData(p,c,s);

                getData(p + c + s);

            }
        }
    };

    /**
     * 设置数据暴露的接口
     * @param address
     */
    public static void setData(SeleteAddressInter address){
        addressInterface = address;
    }

    private static NumberPicker.OnValueChangeListener valueChangeListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            int i = picker.getId();
            if (i == R.id.province) {
                String p = cityLists.get(newVal).getP();
                Log.i("wch", newVal + "");
                setCityData(newVal);


            } else if (i == R.id.city) {
                setCountryData(newVal);

            } else if (i == R.id.county) {
            }
        }
    };


    /**
     * 设置省显示的数据
     */
    private static void setProData() {
        //省
        vh.province.setOnValueChangedListener(valueChangeListener);

        vh.province.setValue(0);
        vh.province.setDisplayedValues(provinceData(cityLists));
        vh.province.setMinValue(0);
        vh.province.setMaxValue(pro.length-1);
    }

    /**
     * 设置市显示的数据
     * @param position
     */
    private static void setCityData(int position){
        vh.city.setOnValueChangedListener(valueChangeListener);

        String[] strings = cityData(position);
        Log.i("wch",strings.length+"wwwwww");
        vh.city.setValue(0);
        vh.city.setMinValue(0);
        //市
            if (strings.length-1 > vh.city.getMaxValue()) {
                vh.city.setDisplayedValues(strings);

                vh.city.setMaxValue(strings.length-1);
            }else{
                vh.city.setMaxValue(strings.length-1);
                vh.city.setDisplayedValues(strings);

            }
            setCountryData(0);
    }
    /**
     * 设置县显示的数据
     * @param position
     */
    private static void setCountryData(int position){
        String[] countrys = countryData(position);
        if (countrys != null) {
        //省
            vh.county.setOnValueChangedListener(valueChangeListener);
        vh.county.setValue(0);
            vh.county.setMinValue(0);

            if (countrys.length-1 >vh.county.getMaxValue()) {

                vh.county.setDisplayedValues(countrys);
                vh.county.setMaxValue(countrys.length-1);
            }else{
                vh.county.setMaxValue(countrys.length-1);
                vh.county.setDisplayedValues(countrys);
            }
        }else{

            String[] strings = {"空"};
            vh.county.setMaxValue(strings.length-1);
            vh.county.setDisplayedValues(strings);
        }
    }
    /**
     * 省
     * @param list
     * @return
     */
    private static String[] provinceData(List<CityJson.SanJiLianDBean.CityList> list){
        pro = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            pro[i] = list.get(i).getP();
        }
        return pro;
    }

    /**
     * 市
     * @param position
     * @return
     */
    private  static String[] cityData(int position){
        city = null;
        citys = cityLists.get(position).getC();

        city = new String[citys.size()];
        for (int i = 0; i < citys.size(); i++) {
            city[i] = citys.get(i).getN();
        }
        return city;
    }

    /**
     * 县
     * @param position
     * @return
     */
    private  static String[] countryData(int position){
        country = null;
        countrys = citys.get(position).getA();
        if (countrys != null) {
            country = new String[countrys.size()];
            for (int i = 0; i < countrys.size(); i++) {
                country[i] = countrys.get(i).getS();
            }
        }
        return country;

    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    private static void backgroundAlpha(float bgAlpha, Context ctx) {
        WindowManager.LayoutParams lp = ((Activity) ctx).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) ctx).getWindow().setAttributes(lp);
    }

    /**
     * 选择地区后返回的数据
     * @param data
     * @return
     */
    public static String getData(String data) {

        return data;
    }

    private static class ViewHolder {
        public View rootView;
        public NumberPicker province;
        public NumberPicker city;
        public NumberPicker county;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.province = (NumberPicker) rootView.findViewById(R.id.province);
            this.city = (NumberPicker) rootView.findViewById(R.id.city);
            this.county = (NumberPicker) rootView.findViewById(R.id.county);
        }

    }
}
