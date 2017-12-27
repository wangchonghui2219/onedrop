package com.dlwx.onedrop.bean;

/**
 * Created by Administrator on 2017/11/17/017.
 */

public class SysMessDescBean {


    /**
     * code : 200
     * result : 获取成功
     * body : {"title":"系统消息","info":"系统消息内容"}
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
         * title : 系统消息
         * info : 系统消息内容
         */

        private String title;
        private String info;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
