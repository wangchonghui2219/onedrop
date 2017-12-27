package com.dlwx.onedrop.bean;

/**
 * Created by Administrator on 2017/11/18/018.
 */

public class MessNumBean {


    /**
     * code : 200
     * result : 获取成功
     * body : {"sys_msgnum":"0","order_msgnum":"3"}
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
         * sys_msgnum : 0
         * order_msgnum : 3
         */

        private int sys_msgnum;
        private int order_msgnum;

        public int getSys_msgnum() {
            return sys_msgnum;
        }

        public void setSys_msgnum(int sys_msgnum) {
            this.sys_msgnum = sys_msgnum;
        }

        public int getOrder_msgnum() {
            return order_msgnum;
        }

        public void setOrder_msgnum(int order_msgnum) {
            this.order_msgnum = order_msgnum;
        }
    }
}
