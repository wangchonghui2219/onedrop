package com.dlwx.onedrop.bean;

/**
 * Created by Administrator on 2017/11/17/017.
 */

public class AliPayBean {

    /**
     * code : 200
     * result : 操作成功
     * body : alipay_sdk=alipay-sdk-php-20161101&app_id=2017111609968070&biz_content=%7B%22body%22%3A%22%E8%B4%AD%E4%B9%B0%E5%95%86%E5%93%81%E8%AE%A2%E5%8D%95%22%2C%22subject%22%3A%22%E4%B8%80%E6%BB%B4%E6%B0%B4%E5%95%86%E5%9F%8E%E8%AE%A2%E5%8D%95%22%2C%22out_trade_no%22%3A%22%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%22200%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2F39.106.68.124%2Findex.php%2FMobile%2FAlipay%2Freturn_url&sign_type=RSA×tamp=2017-11-16+17%3A51%3A41&version=1.0&sign=M7de%2BrgyI6dNpIrdltUU6WrX37h6htH9zwjZQxcF0GTJ3tNj9KbSgijyhZiHw0ZJ4tqf9vZi73bodAkCVj%2Fm1DAVtWpg88y61rhn9gcvwAROaMPTOvA4SgQYpFaq5kAZuejL3g%2BYF4CByJMZ41jfzh%2F1SM4%2F%2BezuN0NSoqBvBCo%3D
     */

    private int code;
    private String result;
    private String body;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
