package com.dlwx.onedrop.bean;

/**
 * Created by Administrator on 2017/11/20/020.
 */

public class PaySuccessBean {

    /**
     * code : 200
     * result : 获取成功
     * body : {"info":"只需298，即可拥有百万收入，模式简单，人人都能算，拒绝传销，资金盘等骗人模式。"}
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
         * info : 只需298，即可拥有百万收入，模式简单，人人都能算，拒绝传销，资金盘等骗人模式。
         */

        private String info;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
