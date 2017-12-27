package com.dlwx.onedrop.bean;

/**
 * Created by Administrator on 2017/11/29/029.
 */

public class InviteCodeBean {

    /**
     * code : 200
     * result : 二维码获取成功
     * body : {"qr_codepic":"http://192.168.0.196/tea/qrcode/exten/150889298228.png"}
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
         * qr_codepic : http://192.168.0.196/tea/qrcode/exten/150889298228.png
         */

        private String qr_codepic;
        private String qr_url;
        private int  is_share;

        public int getIs_share() {
            return is_share;
        }

        public void setIs_share(int is_share) {
            this.is_share = is_share;
        }

        public String getQr_url() {
            return qr_url;
        }

        public void setQr_url(String qr_url) {
            this.qr_url = qr_url;
        }

        public String getQr_codepic() {
            return qr_codepic;
        }

        public void setQr_codepic(String qr_codepic) {
            this.qr_codepic = qr_codepic;
        }
    }
}
