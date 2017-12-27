package com.dlwx.onedrop.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/16/016.
 */

public class SubOrderBean {


    /**
     * code : 200
     * result : 获取成功
     * body : {"addr":{"uaid":"4","userid":"1","name":"张三","tel":"13525873279","province":"河南省","city":"郑州市","area":"金水区","addr":"花园路安华大厦","isdefaut":"2"},"info":{"totalprice":301,"list":[{"title":"茶叶","title_pic":"http://192.168.0.199/tea//Uploads/59e060c26516ex250.png","price":"100.00","num":"3"},{"title":"茶具","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg","price":"1.00","num":"1"}]}}
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
         * addr : {"uaid":"4","userid":"1","name":"张三","tel":"13525873279","province":"河南省","city":"郑州市","area":"金水区","addr":"花园路安华大厦","isdefaut":"2"}
         * info : {"totalprice":301,"list":[{"title":"茶叶","title_pic":"http://192.168.0.199/tea//Uploads/59e060c26516ex250.png","price":"100.00","num":"3"},{"title":"茶具","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg","price":"1.00","num":"1"}]}
         */

        private AddrBean addr;
        private InfoBean info;

        public AddrBean getAddr() {
            return addr;
        }

        public void setAddr(AddrBean addr) {
            this.addr = addr;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class AddrBean {
            /**
             * uaid : 4
             * userid : 1
             * name : 张三
             * tel : 13525873279
             * province : 河南省
             * city : 郑州市
             * area : 金水区
             * addr : 花园路安华大厦
             * isdefaut : 2
             */

            private String uaid;
            private String userid;
            private String name;
            private String tel;
            private String province;
            private String city;
            private String area;
            private String addr;
            private String isdefaut;

            public String getUaid() {
                return uaid;
            }

            public void setUaid(String uaid) {
                this.uaid = uaid;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public String getIsdefaut() {
                return isdefaut;
            }

            public void setIsdefaut(String isdefaut) {
                this.isdefaut = isdefaut;
            }
        }

        public static class InfoBean {
            /**
             * totalprice : 301
             * list : [{"title":"茶叶","title_pic":"http://192.168.0.199/tea//Uploads/59e060c26516ex250.png","price":"100.00","num":"3"},{"title":"茶具","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg","price":"1.00","num":"1"}]
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
                 * title : 茶叶
                 * title_pic : http://192.168.0.199/tea//Uploads/59e060c26516ex250.png
                 * price : 100.00
                 * num : 3
                 */

                private String title;
                private String title_pic;
                private String price;
                private String num;

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

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getNum() {
                    return num;
                }

                public void setNum(String num) {
                    this.num = num;
                }
            }
        }
    }
}
