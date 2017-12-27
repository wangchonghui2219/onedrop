package com.dlwx.onedrop.bean;

/**
 * Created by Administrator on 2017/11/14/014.
 */

public class KeFuMessBean {

    /**
     * code : 200
     * result : 获取成功
     * body : {"tel":"13525873279","qq":"295075064"}
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
         * tel : 13525873279
         * qq : 295075064
         */

        private String tel;
        private String qq;

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }
    }
}
