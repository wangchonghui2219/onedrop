package com.dlwx.onedrop.bean;

/**
 * Created by Administrator on 2017/11/15/015.
 */

public class TiXianBean {

    /**
     * body : {"bank":"中国银行 8602","time":"2017-11-20 11:11:37","tx_price":100}
     * code : 200
     * result : 提现成功
     */

    private BodyBean body;
    private int code;
    private String result;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

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

    public static class BodyBean {
        /**
         * bank : 中国银行 8602
         * time : 2017-11-20 11:11:37
         * tx_price : 100
         */

        private String bank;
        private String time;
        private int tx_price;

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getTx_price() {
            return tx_price;
        }

        public void setTx_price(int tx_price) {
            this.tx_price = tx_price;
        }
    }
}
