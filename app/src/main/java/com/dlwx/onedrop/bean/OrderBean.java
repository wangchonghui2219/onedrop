package com.dlwx.onedrop.bean;

/**
 * Created by Administrator on 2017/11/16/016.
 */

public class OrderBean {

    /**
     * code : 200
     * result : 生成订单成功
     * body : {"orderid":"S20171108112843F64QR"}
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
         * orderid : S20171108112843F64QR
         */

        private String orderid;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }
    }
}
