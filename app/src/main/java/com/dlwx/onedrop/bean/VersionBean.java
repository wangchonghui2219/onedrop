package com.dlwx.onedrop.bean;

/**
 * Created by Administrator on 2017/9/6/006.
 */

public class VersionBean {


    /**
     * code : 200
     * result : 需要升级最新版本
     * body : {"new_num":"1","download_url":"http://39.106.68.124/Uploads/apk/tea.apk"}
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
         * new_num : 1
         * download_url : http://39.106.68.124/Uploads/apk/tea.apk
         */

        private int new_num;
        private String download_url;

        public int getNew_num() {
            return new_num;
        }

        public void setNew_num(int new_num) {
            this.new_num = new_num;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }
    }
}
