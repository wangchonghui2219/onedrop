package com.dlwx.onedrop.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/16/016.
 */

public class MyOrderDescBean {


    /**
     * body : {"addr_addr":"吉林长春南关区考虑考虑","addr_name":"王崇辉","addr_tel":"18637051978","addrid":"81","carids":"","createdtime":"2017-12-07 09:03","orderid":"164","product":{"list":[{"num":1,"price":"0.01","productid":"32","title":"杜仲养生茶","title_pic":"http://39.106.68.124//Uploads/5a24f7475ff7c.jpg"}],"totalprice":0.01},"product_orderid":"S20171207090334sPzAY","productid":"32","status":"1","totalprice":"0.01","type":"1"}
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
         * addr_addr : 吉林长春南关区考虑考虑
         * addr_name : 王崇辉
         * addr_tel : 18637051978
         * addrid : 81
         * carids :
         * createdtime : 2017-12-07 09:03
         * orderid : 164
         * product : {"list":[{"num":1,"price":"0.01","productid":"32","title":"杜仲养生茶","title_pic":"http://39.106.68.124//Uploads/5a24f7475ff7c.jpg"}],"totalprice":0.01}
         * product_orderid : S20171207090334sPzAY
         * productid : 32
         * status : 1
         * totalprice : 0.01
         * type : 1
         */

        private String addr_addr;
        private String addr_name;
        private String addr_tel;
        private String addrid;
        private String carids;
        private String createdtime;
        private String orderid;
        private ProductBean product;
        private String product_orderid;
        private String productid;
        private int status;
        private String totalprice;
        private String type;

        public String getAddr_addr() {
            return addr_addr;
        }

        public void setAddr_addr(String addr_addr) {
            this.addr_addr = addr_addr;
        }

        public String getAddr_name() {
            return addr_name;
        }

        public void setAddr_name(String addr_name) {
            this.addr_name = addr_name;
        }

        public String getAddr_tel() {
            return addr_tel;
        }

        public void setAddr_tel(String addr_tel) {
            this.addr_tel = addr_tel;
        }

        public String getAddrid() {
            return addrid;
        }

        public void setAddrid(String addrid) {
            this.addrid = addrid;
        }

        public String getCarids() {
            return carids;
        }

        public void setCarids(String carids) {
            this.carids = carids;
        }

        public String getCreatedtime() {
            return createdtime;
        }

        public void setCreatedtime(String createdtime) {
            this.createdtime = createdtime;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public String getProduct_orderid() {
            return product_orderid;
        }

        public void setProduct_orderid(String product_orderid) {
            this.product_orderid = product_orderid;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(String totalprice) {
            this.totalprice = totalprice;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public static class ProductBean {
            /**
             * list : [{"num":1,"price":"0.01","productid":"32","title":"杜仲养生茶","title_pic":"http://39.106.68.124//Uploads/5a24f7475ff7c.jpg"}]
             * totalprice : 0.01
             */

            private double totalprice;
            private List<ListBean> list;

            public double getTotalprice() {
                return totalprice;
            }

            public void setTotalprice(double totalprice) {
                this.totalprice = totalprice;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * num : 1
                 * price : 0.01
                 * productid : 32
                 * title : 杜仲养生茶
                 * title_pic : http://39.106.68.124//Uploads/5a24f7475ff7c.jpg
                 */

                private int num;
                private double price;
                private String productid;
                private String title;
                private String title_pic;

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public String getProductid() {
                    return productid;
                }

                public void setProductid(String productid) {
                    this.productid = productid;
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
            }
        }
    }
}
