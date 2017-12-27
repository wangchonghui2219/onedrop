package com.dlwx.onedrop.bean;

/**
 * Created by Administrator on 2017/11/9/009.
 */

public class LoginBean {

    /**
     * code : 200
     * result : 登录成功
     * body : {"token":"05518fa8639753b62990481f1c6507c4","telephone":"18072723804","nickname":"180****3804","header_pic":"http://192.168.0.199/amoy//Uploads/598d54a420ebbx250.png","isqq":"3"}
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
         * token : 05518fa8639753b62990481f1c6507c4
         * telephone : 18072723804
         * nickname : 180****3804
         * header_pic : http://192.168.0.199/amoy//Uploads/598d54a420ebbx250.png
         * isqq : 3
         */

        private String token;
        private String telephone;
        private String nickname;
        private String header_pic;
        private String isqq;
        private String is_tx_passwrord;
        private String userid;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getIs_tx_passwrord() {
            return is_tx_passwrord;
        }

        public void setIs_tx_passwrord(String is_tx_passwrord) {
            this.is_tx_passwrord = is_tx_passwrord;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeader_pic() {
            return header_pic;
        }

        public void setHeader_pic(String header_pic) {
            this.header_pic = header_pic;
        }

        public String getIsqq() {
            return isqq;
        }

        public void setIsqq(String isqq) {
            this.isqq = isqq;
        }
    }
}
