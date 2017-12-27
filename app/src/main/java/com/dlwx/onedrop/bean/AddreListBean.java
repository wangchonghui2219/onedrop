package com.dlwx.onedrop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/14/014.
 */

public class AddreListBean {

    /**
     * body : [{"addr":"安华大厦","area":"金水区","city":"郑州市","isdefault":"1","name":"张三","province":"河南省","tel":"15617806545","uaid":"1"},{"addr":"安华大厦","area":"金水区","city":"郑州市","isdefault":"2","name":"张三","province":"河南省","tel":"15617806545","uaid":"2"}]
     * code : 200
     * result : 获取成功
     */

    private int code;
    private String result;
    private List<BodyBean> body;

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

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean implements Serializable{
        /**
         * addr : 安华大厦
         * area : 金水区
         * city : 郑州市
         * isdefault : 1
         * name : 张三
         * province : 河南省
         * tel : 15617806545
         * uaid : 1
         */

        private String addr;
        private String area;
        private String city;
        private String isdefault;
        private String name;
        private String province;
        private String tel;
        private String uaid;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getIsdefault() {
            return isdefault;
        }

        public void setIsdefault(String isdefault) {
            this.isdefault = isdefault;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getUaid() {
            return uaid;
        }

        public void setUaid(String uaid) {
            this.uaid = uaid;
        }
    }
}
