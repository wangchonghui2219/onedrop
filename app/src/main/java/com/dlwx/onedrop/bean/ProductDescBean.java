package com.dlwx.onedrop.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15/015.
 */

public class ProductDescBean {

    /**
     * code : 200
     * result : 获取成功
     * body : {"info":{"productid":"1","scid":"1","title":"茶叶","title_pic":"http://192.168.0.199/tea//Uploads/59e060c26516ex250.png","intro":"沉香+茶叶+香烟","short_content":"豪华大礼包，物美价廉","content":"产品图文介绍","price":"100.00","imgids":"1,2,3","videoid":"1","video_imgid":"1","createdtime":"2017-11-07","isonline":"1","total_pingjia":"100","allfile":{"imgids":["http://192.168.0.199/tea//Uploads/59a60d0438e10x250.jpg","http://192.168.0.199/tea//Uploads/59d88e3deb939x250.jpg","http://192.168.0.199/tea//Uploads/59d88e4799157x250.png"],"videoid":["http://192.168.0.199/tea//Uploads/59a60d0438e10x250.jpg"],"video_imgid":["http://192.168.0.199/tea//Uploads/59a60d0438e10x250.jpg"]}},"pingjia":[{"userid":"1","pingjia":"这是我的评价","create_time":"2017-03-15","pingjia_img":[],"rate_level":"1","user_nickname":"璐璐2","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"阿斯顿发送到发送到发送的阿斯顿发送到发送到发送的饭","create_time":"2017-09-06","pingjia_img":[],"rate_level":"1","user_nickname":"璐璐2","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"123请问阿萨德自行车道 v水电费水电费","create_time":"2017-09-06","pingjia_img":[],"rate_level":"2","user_nickname":"璐璐2","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"企鹅污染水淀粉阿斯顿发送到发送的饭阿斯顿发阿斯顿发","create_time":"2017-09-06","pingjia_img":[],"rate_level":"3","user_nickname":"璐璐2","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"123123123123123123123123","create_time":"2017-09-06","pingjia_img":[],"rate_level":"1","user_nickname":"璐璐2","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"真好真好真好真好真好真好真好真好真好真好","create_time":"2017-09-11","pingjia_img":[],"rate_level":"2","user_nickname":"璐璐2","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"}]}
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
         * info : {"productid":"1","scid":"1","title":"茶叶","title_pic":"http://192.168.0.199/tea//Uploads/59e060c26516ex250.png","intro":"沉香+茶叶+香烟","short_content":"豪华大礼包，物美价廉","content":"产品图文介绍","price":"100.00","imgids":"1,2,3","videoid":"1","video_imgid":"1","createdtime":"2017-11-07","isonline":"1","total_pingjia":"100","allfile":{"imgids":["http://192.168.0.199/tea//Uploads/59a60d0438e10x250.jpg","http://192.168.0.199/tea//Uploads/59d88e3deb939x250.jpg","http://192.168.0.199/tea//Uploads/59d88e4799157x250.png"],"videoid":["http://192.168.0.199/tea//Uploads/59a60d0438e10x250.jpg"],"video_imgid":["http://192.168.0.199/tea//Uploads/59a60d0438e10x250.jpg"]}}
         * pingjia : [{"userid":"1","pingjia":"这是我的评价","create_time":"2017-03-15","pingjia_img":[],"rate_level":"1","user_nickname":"璐璐2","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"阿斯顿发送到发送到发送的阿斯顿发送到发送到发送的饭","create_time":"2017-09-06","pingjia_img":[],"rate_level":"1","user_nickname":"璐璐2","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"123请问阿萨德自行车道 v水电费水电费","create_time":"2017-09-06","pingjia_img":[],"rate_level":"2","user_nickname":"璐璐2","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"企鹅污染水淀粉阿斯顿发送到发送的饭阿斯顿发阿斯顿发","create_time":"2017-09-06","pingjia_img":[],"rate_level":"3","user_nickname":"璐璐2","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"123123123123123123123123","create_time":"2017-09-06","pingjia_img":[],"rate_level":"1","user_nickname":"璐璐2","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"},{"userid":"1","pingjia":"真好真好真好真好真好真好真好真好真好真好","create_time":"2017-09-11","pingjia_img":[],"rate_level":"2","user_nickname":"璐璐2","user_headerpic":"http://192.168.0.199/tea//Uploads/59d8922618938x250.jpg"}]
         */

        private InfoBean info;
        private List<PingjiaBean> pingjia;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public List<PingjiaBean> getPingjia() {
            return pingjia;
        }

        public void setPingjia(List<PingjiaBean> pingjia) {
            this.pingjia = pingjia;
        }

        public static class InfoBean {
            /**
             * productid : 1
             * scid : 1
             * title : 茶叶
             * title_pic : http://192.168.0.199/tea//Uploads/59e060c26516ex250.png
             * intro : 沉香+茶叶+香烟
             * short_content : 豪华大礼包，物美价廉
             * content : 产品图文介绍
             * price : 100.00
             * imgids : 1,2,3
             * videoid : 1
             * video_imgid : 1
             * createdtime : 2017-11-07
             * isonline : 1
             * total_pingjia : 100
             * allfile : {"imgids":["http://192.168.0.199/tea//Uploads/59a60d0438e10x250.jpg","http://192.168.0.199/tea//Uploads/59d88e3deb939x250.jpg","http://192.168.0.199/tea//Uploads/59d88e4799157x250.png"],"videoid":["http://192.168.0.199/tea//Uploads/59a60d0438e10x250.jpg"],"video_imgid":["http://192.168.0.199/tea//Uploads/59a60d0438e10x250.jpg"]}
             */

            private String productid;
            private String scid;
            private String title;
            private String title_pic;
            private String intro;
            private String short_content;
            private String content;
            private String price;
            private String imgids;
            private String videoid;
            private String video_imgid;
            private String createdtime;
            private String isonline;
            private String total_pingjia;
            private AllfileBean allfile;

            public String getProductid() {
                return productid;
            }

            public void setProductid(String productid) {
                this.productid = productid;
            }

            public String getScid() {
                return scid;
            }

            public void setScid(String scid) {
                this.scid = scid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitle_pic() {
                return title_pic;
            }

            public void setTitle_pic(String title_pic) {
                this.title_pic = title_pic;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getShort_content() {
                return short_content;
            }

            public void setShort_content(String short_content) {
                this.short_content = short_content;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getImgids() {
                return imgids;
            }

            public void setImgids(String imgids) {
                this.imgids = imgids;
            }

            public String getVideoid() {
                return videoid;
            }

            public void setVideoid(String videoid) {
                this.videoid = videoid;
            }

            public String getVideo_imgid() {
                return video_imgid;
            }

            public void setVideo_imgid(String video_imgid) {
                this.video_imgid = video_imgid;
            }

            public String getCreatedtime() {
                return createdtime;
            }

            public void setCreatedtime(String createdtime) {
                this.createdtime = createdtime;
            }

            public String getIsonline() {
                return isonline;
            }

            public void setIsonline(String isonline) {
                this.isonline = isonline;
            }

            public String getTotal_pingjia() {
                return total_pingjia;
            }

            public void setTotal_pingjia(String total_pingjia) {
                this.total_pingjia = total_pingjia;
            }

            public AllfileBean getAllfile() {
                return allfile;
            }

            public void setAllfile(AllfileBean allfile) {
                this.allfile = allfile;
            }

            public static class AllfileBean {
                private List<String> imgids;
                private List<String> videoid;
                private List<String> video_imgid;

                public List<String> getImgids() {
                    return imgids;
                }

                public void setImgids(List<String> imgids) {
                    this.imgids = imgids;
                }

                public List<String> getVideoid() {
                    return videoid;
                }

                public void setVideoid(List<String> videoid) {
                    this.videoid = videoid;
                }

                public List<String> getVideo_imgid() {
                    return video_imgid;
                }

                public void setVideo_imgid(List<String> video_imgid) {
                    this.video_imgid = video_imgid;
                }
            }
        }

        public static class PingjiaBean {
            /**
             * userid : 1
             * pingjia : 这是我的评价
             * create_time : 2017-03-15
             * pingjia_img : []
             * rate_level : 1
             * user_nickname : 璐璐2
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
