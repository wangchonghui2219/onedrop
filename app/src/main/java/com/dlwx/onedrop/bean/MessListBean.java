package com.dlwx.onedrop.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/17/017.
 */

public class MessListBean {


    /**
     * code : 200
     * result : 获取成功
     * body : [{"id":"1","title":"系统消息","info":"系统消息内容","order_num":"","createdtime":"2017-11-17 14:41:03"},{"id":"2","title":"系统消息","info":"系统消息内容","order_num":"","createdtime":"2017-11-17 14:41:06"},{"id":"3","title":"系统消息","info":"系统消息内容","order_num":"","createdtime":"2017-11-17 14:41:08"},{"id":"11","title":"系统消息","info":"<p>系统消息&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<\/p>","order_num":"","createdtime":"2017-11-17 15:00:34"},{"id":"13","title":"消息推送测试","info":"<p>消息推送测试1 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<\/p>","order_num":"","createdtime":"2017-11-17 15:41:07"}]
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
         * id : 1
         * title : 系统消息
         * info : 系统消息内容
         * order_num :
         * createdtime : 2017-11-17 14:41:03
         */

        private String id;
        private String title;
        private String info;
        private String order_num;
        private String createdtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getCreatedtime() {
            return createdtime;
        }

        public void setCreatedtime(String createdtime) {
            this.createdtime = createdtime;
        }
    }
}
