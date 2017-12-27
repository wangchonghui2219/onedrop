package com.dlwx.onedrop.bean;

/**
 * Created by Administrator on 2017/11/15/015.
 */

public class MyYueBean {

    /**
     * code : 200
     * result : 获取成功
     * body : {"use_price":"3402.00"}
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
         * use_price : 3402.00
         */

        private String use_price;

        public String getUse_price() {
            return use_price;
        }

        public void setUse_price(String use_price) {
            this.use_price = use_price;
        }
    }
}