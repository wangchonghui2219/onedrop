package com.dlwx.onedrop.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/16/016.
 */

public class AllEvaluteBean {


    /**
     * code : 200
     * result : 获取成功
     * body : {"list":[{"userid":"1","pingjia":"我的评价","create_time":"2017-11-09","pingjia_img":["http://192.168.0.199/tea//Uploads/59d8a0720921ex250.jpg","http://192.168.0.199/tea//Uploads/59d8a076e254ex250.jpg"],"rate_level":"4","user_nickname":"璐璐4","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"真好真好真好真好真好真好真好真好真好真好","create_time":"2017-09-11","pingjia_img":[],"rate_level":"2","user_nickname":"璐璐4","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"123123123123123123123123","create_time":"2017-09-06","pingjia_img":[],"rate_level":"1","user_nickname":"璐璐4","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"企鹅污染水淀粉阿斯顿发送到发送的饭阿斯顿发阿斯顿发","create_time":"2017-09-06","pingjia_img":[],"rate_level":"3","user_nickname":"璐璐4","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"123请问阿萨德自行车道 v水电费水电费","create_time":"2017-09-06","pingjia_img":[],"rate_level":"2","user_nickname":"璐璐4","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"阿斯顿发送到发送到发送的阿斯顿发送到发送到发送的饭","create_time":"2017-09-06","pingjia_img":[],"rate_level":"1","user_nickname":"璐璐4","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"这是我的评价","create_time":"2017-03-15","pingjia_img":[],"rate_level":"1","user_nickname":"璐璐4","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * userid : 1
             * pingjia : 我的评价
             * create_time : 2017-11-09
             * pingjia_img : ["http://192.168.0.199/tea//Uploads/59d8a0720921ex250.jpg","http://192.168.0.199/tea//Uploads/59d8a076e254ex250.jpg"]
             * rate_level : 4
             * user_nickname : 璐璐4
             * user_headerpic : http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg
             */

            private String userid;
            private String pingjia;
            private String create_time;
            private int rate_level;
            private String user_nickname;
            private String user_headerpic;
            private List<String> pingjia_img;

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getPingjia() {
                return pingjia;
            }

            public void setPingjia(String pingjia) {
                this.pingjia = pingjia;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getRate_level() {
                return rate_level;
            }

            public void setRate_level(int rate_level) {
                this.rate_level = rate_level;
            }

            public String getUser_nickname() {
                return user_nickname;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getUser_headerpic() {
                return user_headerpic;
            }

            public void setUser_headerpic(String user_headerpic) {
                this.user_headerpic = user_headerpic;
            }

            public List<String> getPingjia_img() {
                return pingjia_img;
            }

            public void setPingjia_img(List<String> pingjia_img) {
                this.pingjia_img = pingjia_img;
            }
        }
    }
}
