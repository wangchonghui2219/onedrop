package com.dlwx.onedrop.bean;

/**
 * Created by Administrator on 2017/11/14/014.
 */

public class HtmlBean {

    /**
     * body : {"info":"1.根据运营商网络情况不同授权服务需要1~3分钟时间才能完成，请耐心等待。如果您发现等待时间过长，请选择非网络高峰期的时间重新授权。 2.根据运营商网络情况不同授权服务需要1~3分钟时间才能完成，请耐心等待。如果您发现等待时间过长，请选择非网络高峰期的时间重新授权。 3.根据运营商网络情况不同授权服务需要1~3分钟时间才能完成，请耐心等待。如果您发现等待时间过长，请选择非网络高峰期的时间重新授权。"}
     * code : 200
     * result : 获取成功
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
         * info : 1.根据运营商网络情况不同授权服务需要1~3分钟时间才能完成，请耐心等待。如果您发现等待时间过长，请选择非网络高峰期的时间重新授权。 2.根据运营商网络情况不同授权服务需要1~3分钟时间才能完成，请耐心等待。如果您发现等待时间过长，请选择非网络高峰期的时间重新授权。 3.根据运营商网络情况不同授权服务需要1~3分钟时间才能完成，请耐心等待。如果您发现等待时间过长，请选择非网络高峰期的时间重新授权。
         */

        private String info;
        private String title;
        private String title_pic;
        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getTitle_pic() {
            return title_pic;
        }

        public void setTitle_pic(String title_pic) {
            this.title_pic = title_pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
