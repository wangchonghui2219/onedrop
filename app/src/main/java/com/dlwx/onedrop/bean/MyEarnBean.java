package com.dlwx.onedrop.bean;

/**
 * Created by Administrator on 2017/12/6/006.
 */

public class MyEarnBean {

    /**
     * code : 200
     * result : 获取成功
     * body : {"use_price":"87656.00","totalprice":"100506.00","day_shouyi":0,"month_shouyi":0}
     */

    private int code;
    private String result;
    private BodyBean body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * use_price : 87656.00
         * totalprice : 100506.00
         * day_shouyi : 0
         * month_shouyi : 0
         */

        private String use_price;
        private String totalprice;
        private String day_shouyi;
        private String month_shouyi;

        public String getUse_price() {
            return use_price;
        }

        public void setUse_price(String use_price) {
            this.use_price = use_price;
        }

        public String getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(String totalprice) {
            this.totalprice = totalprice;
        }

        public String getDay_shouyi() {
            return day_shouyi;
        }

        public void setDay_shouyi(String day_shouyi) {
            this.day_shouyi = day_shouyi;
        }

        public String getMonth_shouyi() {
            return month_shouyi;
        }

        public void setMonth_shouyi(String month_shouyi) {
            this.month_shouyi = month_shouyi;
        }
    }
}
