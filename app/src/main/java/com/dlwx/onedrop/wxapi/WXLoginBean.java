package com.dlwx.onedrop.wxapi;

import java.util.List;

/**
 * @create at 2017/6/20 0012 下午 4:24
 * @name 微信登录
 */
public class WXLoginBean {

    /**
     * city : Shangqiu
     * country : CN
     * headimgurl : http://wx.qlogo.cn/mmopen/ajNVdqHZLLDYgE6JpbzOIkWkbLNgg7ia5MQlEpYFp8JLVT47aH48aicnpKgJaOdtaXS6jxl8KicLGAic1gbVcHbsJA/0
     * language : zh_CN
     * nickname :
     * openid : oGz8i1Ir0-Y_M2uY6oYbe-xcn9Rw
     * privilege : []
     * province : Henan
     * sex : 1
     * unionid : oj2ZT1c6aQ44yQ0O3H2m9-CL74lc
     */

    private String city;
    private String country;
    private String headimgurl;
    private String language;
    private String nickname;
    private String openid;
    private String province;
    private int sex;
    private String unionid;
    private List<?> privilege;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<?> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<?> privilege) {
        this.privilege = privilege;
    }
}
