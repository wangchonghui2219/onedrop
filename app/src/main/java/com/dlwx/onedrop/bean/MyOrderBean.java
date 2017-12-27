package com.dlwx.onedrop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/16/016.
 */

public class MyOrderBean implements Serializable{

    /**
     * code : 200
     * result : 获取成功
     * body : [{"orderid":"11","productid":"0","carids":"1,2","product_orderid":"S20171108113909H3NXo","status":"0","createdtime":"2017-11-08 11:39","product":{"list":[{"title":"茶叶","title_pic":"http://192.168.0.199/tea//Uploads/59e060c26516ex250.png"},{"title":"茶具","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg"}]}},{"orderid":"10","productid":"0","carids":"1,2","product_orderid":"S20171108113903L3RGp","status":"0","createdtime":"2017-11-08 11:39","product":{"list":[{"title":"茶叶","title_pic":"http://192.168.0.199/tea//Uploads/59e060c26516ex250.png"},{"title":"茶具","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg"}]}},{"orderid":"9","productid":"1","carids":"","product_orderid":"S20171108112843F64QR","status":"0","createdtime":"2017-11-08 11:28","product":{"list":[{"title":"茶叶","title_pic":"http://192.168.0.199/tea//Uploads/59e060c26516ex250.png"}]}}]
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

    public static class BodyBean implements Serializable{
        /**
         * orderid : 11
         * productid : 0
         * carids : 1,2
         * product_orderid : S20171108113909H3NXo
         * status : 0
         * createdtime : 2017-11-08 11:39
         * product : {"list":[{"title":"茶叶","title_pic":"http://192.168.0.199/tea//Uploads/59e060c26516ex250.png"},{"title":"茶具","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg"}]}
         */

        private String orderid;
        private String productid;
        private String carids;
        private String product_orderid;
        private int status;
        private String createdtime;
        private ProductBean product;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getCarids() {
            return carids;
        }

        public void setCarids(String carids) {
            this.carids = carids;
        }

        public String getProduct_orderid() {
            return product_orderid;
        }

        public void setProduct_orderid(String product_orderid) {
            this.product_orderid = product_orderid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreatedtime() {
            return createdtime;
        }

        public void setCreatedtime(String createdtime) {
            this.createdtime = createdtime;
        }

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public static class ProductBean {
            private List<ListBean> list;

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * title : 茶叶
                 * title_pic : http://192.168.0.199/tea//Uploads/59e060c26516ex250.png
                 */

                private String title;
                private String title_pic;

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
            }
        }
    }
}
