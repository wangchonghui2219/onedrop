package com.dlwx.onedrop.bean;

/**
 * Created by Administrator on 2017/11/9/009.
 */

public class ImageAuthBean {

    /**
     * code : 200
     * result : 获取成功
     * body : {"imgname":"151019778281.png","imgurl":"http://192.168.0.199/tea/smsimgcode/151019778281.png"}
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
         * imgname : 151019778281.png
         * imgurl : http://192.168.0.199/tea/smsimgcode/151019778281.png
         */

        private String imgname;
        private String imgurl;

        public String getImgname() {
            return imgname;
        }

        public void setImgname(String imgname) {
            this.imgname = imgname;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }
}
