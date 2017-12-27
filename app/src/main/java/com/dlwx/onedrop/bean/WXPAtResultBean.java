package com.dlwx.onedrop.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/12/15/015.
 */

public class WXPAtResultBean {

    /**
     * code : 200
     * result : 操作成功
     * body : {"appid":"wxd9d9719bda9d3dc0","partnerid":"1494474992","prepayid":"wx20171215094738ca80236a8d0859727446","package":"Sign=WXPay","noncestr":"yVWENOzhRlTUZVwaC5Avg1hkzIY8zNYA","timestamp":1513302458,"sign":"76AF8B42CA6C00C201CAD58574585B77"}
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
         * appid : wxd9d9719bda9d3dc0
         * partnerid : 1494474992
         * prepayid : wx20171215094738ca80236a8d0859727446
         * package : Sign=WXPay
         * noncestr : yVWENOzhRlTUZVwaC5Avg1hkzIY8zNYA
         * timestamp : 1513302458
         * sign : 76AF8B42CA6C00C201CAD58574585B77
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
