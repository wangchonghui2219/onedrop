package com.dlwx.onedrop.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13/013.
 */

public class ClassListBean {

    /**
     * body : {"banner":[],"list":[{"productid":"14","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg"},{"productid":"13","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg"},{"productid":"12","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg"},{"productid":"11","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg"},{"productid":"10","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg"},{"productid":"9","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg"},{"productid":"8","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg"},{"productid":"7","title_pic":""}]}
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
        private List<?> banner;
        private List<ListBean> list;

        public List<?> getBanner() {
            return banner;
        }

        public void setBanner(List<?> banner) {
            this.banner = banner;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * productid : 14
             * title_pic : http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg
             */

            private String productid;
            private String title_pic;

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
