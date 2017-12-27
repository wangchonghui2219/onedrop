package com.dlwx.onedrop.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15/015.
 */

public class MyCommissBean {

    /**
     * code : 200
     * result : 获取成功
     * body : [{"change_price":"39.90","handle_name":"推荐佣金","createdtime":"2017年11月10日 00:02","isincome":"1"},{"change_price":"29.90","handle_name":"推荐佣金","createdtime":"2017年11月10日 00:00","isincome":"1"},{"change_price":"49.90","handle_name":"推荐佣金","createdtime":"2017年11月10日 00:00","isincome":"1"}]
     */

    private int code;
    private String result;
    private List<BodyBean> body;

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

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * change_price : 39.90
         * handle_name : 推荐佣金
         * createdtime : 2017年11月10日 00:02
         * isincome : 1
         */

        private String change_price;
        private String handle_name;
        private String createdtime;
        private String isincome;

        public String getChange_price() {
            return change_price;
        }

        public void setChange_price(String change_price) {
            this.change_price = change_price;
        }

        public String getHandle_name() {
            return handle_name;
        }

        public void setHandle_name(String handle_name) {
            this.handle_name = handle_name;
        }

        public String getCreatedtime() {
            return createdtime;
        }

        public void setCreatedtime(String createdtime) {
            this.createdtime = createdtime;
        }

        public String getIsincome() {
            return isincome;
        }

        public void setIsincome(String isincome) {
            this.isincome = isincome;
        }
    }
}
