package com.dlwx.onedrop.bean;

/**
 * Created by Administrator on 2017/11/17/017.
 */

public class MyLeceBean {

    /**
     * code : 200
     * result : 获取成功
     * body : {"nickname":"1","header_pic":"http://192.168.0.199/tea//Uploads/20171114/5a0a64fd95ba1.png","vip":"3","vip_name":"销售主管","new_vip":"恭喜您直推50人,培养两个主管,团队三级人数达到1001人,即可升级销售经理"}
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
         * nickname : 1
         * header_pic : http://192.168.0.199/tea//Uploads/20171114/5a0a64fd95ba1.png
         * vip : 3
         * vip_name : 销售主管
         * new_vip : 恭喜您直推50人,培养两个主管,团队三级人数达到1001人,即可升级销售经理
         */

        private String nickname;
        private String header_pic;
        private String vip;
        private String vip_name;
        private String new_vip;
        private String vip_yw_count;
        private String vip_jl_count;

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

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getVip_name() {
            return vip_name;
        }

        public void setVip_name(String vip_name) {
            this.vip_name = vip_name;
        }

        public String getNew_vip() {
            return new_vip;
        }

        public void setNew_vip(String new_vip) {
            this.new_vip = new_vip;
        }

        public String getVip_yw_count() {
            return vip_yw_count;
        }

        public void setVip_yw_count(String vip_yw_count) {
            this.vip_yw_count = vip_yw_count;
        }

        public String getVip_jl_count() {
            return vip_jl_count;
        }

        public void setVip_jl_count(String vip_jl_count) {
            this.vip_jl_count = vip_jl_count;
        }
    }
}
