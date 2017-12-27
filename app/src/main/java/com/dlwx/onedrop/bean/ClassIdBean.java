package com.dlwx.onedrop.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/9/009.
 */

public class ClassIdBean {

    /**
     * code : 200
     * result : 获取成功
     * body : {"banner":[{"banner_img":"http://192.168.0.199/tea//Uploads/5a012f229e4f5.jpg"},{"banner_img":"http://192.168.0.199/tea//Uploads/5a012f22b47b9.jpg"},{"banner_img":"http://192.168.0.199/tea//Uploads/5a012f22e5d5e.jpg"}],"list":[{"productid":"2","title_pic":"http://192.168.0.199/tea//Uploads/59e060c26516ex250.png"},{"productid":"1","title_pic":"http://192.168.0.199/tea//Uploads/59e060c26516ex250.png"}]}
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
        private List<BannerBean> banner;
        private List<ListBean> list;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class BannerBean {
            /**
             * banner_img : http://192.168.0.199/tea//Uploads/5a012f229e4f5.jpg
             */

            private String banner_img;

            public String getBanner_img() {
                return banner_img;
            }

            public void setBanner_img(String banner_img) {
                this.banner_img = banner_img;
            }
        }

        public static class ListBean {
            /**
             * productid : 2
             * title_pic : http://192.168.0.199/tea//Uploads/59e060c26516ex250.png
             */

            private String productid;
            private String title_pic;
            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getProductid() {
                return productid;
            }

            public void setProductid(String productid) {
                this.productid = productid;
            }

            public String getTitle_pic() {
                return title_pic;
            }

            public void setTitle_pic(String title_pic) {
                this.title_pic = title_pic;
            }
        }
    }
}
