package com.dlwx.onedrop.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/14/014.
 */

public class BankListBean {

    /**
     * body : [{"bank_name":"中国银行","id":"1"},{"bank_name":"中国工商银行","id":"2"},{"bank_name":"中国建设银行","id":"3"},{"bank_name":"中国农业银行","id":"4"},{"bank_name":"中国光大银行","id":"5"},{"bank_name":"中国民生银行","id":"6"},{"bank_name":"中信银行","id":"7"},{"bank_name":"交通银行","id":"8"},{"bank_name":"华夏银行","id":"9"},{"bank_name":"招商银行","id":"10"},{"bank_name":"兴业银行","id":"11"},{"bank_name":"广发银行","id":"12"},{"bank_name":"平安银行","id":"13"},{"bank_name":"上海浦东发展银行","id":"14"},{"bank_name":"恒丰银行","id":"15"},{"bank_name":"浙商银行","id":"16"},{"bank_name":"渤海银行","id":"17"},{"bank_name":"中国邮政储蓄银行","id":"18"}]
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

    public static class BodyBean {
        /**
         * bank_name : 中国银行
         * id : 1
         */

        private String bank_name;
        private String id;

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
