package com.dlwx.onedrop.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/10/010.
 */

public class BankCardListBean {

    /**
     * code : 200
     * result : 获取成功
     * body : [{"bank_id":"53","bank_number":"************7303","bank_name":"光大银行","bank_type":"储蓄卡"},{"bank_id":"55","bank_number":"***************0510","bank_name":"中国农业银行","bank_type":"储蓄卡"},{"bank_id":"56","bank_number":"***************0510","bank_name":"中国农业银行","bank_type":"储蓄卡"}]
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
         * bank_id : 53
         * bank_number : ************7303
         * bank_name : 光大银行
         * bank_type : 储蓄卡
         */

        private String bank_id;
        private String bank_number;
        private String bank_name;
        private String bank_type;
        private String name_num;

        public String getBank_id() {
            return bank_id;
        }

        public void setBank_id(String bank_id) {
            this.bank_id = bank_id;
        }

        public String getBank_number() {
            return bank_number;
        }

        public void setBank_number(String bank_number) {
            this.bank_number = bank_number;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBank_type() {
            return bank_type;
        }

        public void setBank_type(String bank_type) {
            this.bank_type = bank_type;
        }

        public String getName_num() {
            return name_num;
        }

        public void setName_num(String name_num) {
            this.name_num = name_num;
        }
    }
}
