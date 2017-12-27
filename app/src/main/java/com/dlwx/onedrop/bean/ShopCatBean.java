package com.dlwx.onedrop.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/16/016.
 */

public class ShopCatBean {

    /**
     * code : 200
     * result : 获取成功
     * body : {"list":[{"carid":"1","num":"1","title":"茶叶","title_pic":"http://192.168.0.199/tea//Uploads/59e060c26516ex250.png","price":"100.00","classname":"茶叶"},{"carid":"4","num":"3","title":"茶叶","title_pic":"http://192.168.0.199/tea//Uploads/59e060c26516ex250.png","price":"100.00","classname":"茶叶"},{"carid":"2","num":"1","title":"茶具","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg","price":"1.00","classname":"茶具"},{"carid":"3","num":"1","title":"茶具","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg","price":"1.00","classname":"茶具"}],"total_price":402}
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
         * list : [{"carid":"1","num":"1","title":"茶叶","title_pic":"http://192.168.0.199/tea//Uploads/59e060c26516ex250.png","price":"100.00","classname":"茶叶"},{"carid":"4","num":"3","title":"茶叶","title_pic":"http://192.168.0.199/tea//Uploads/59e060c26516ex250.png","price":"100.00","classname":"茶叶"},{"carid":"2","num":"1","title":"茶具","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg","price":"1.00","classname":"茶具"},{"carid":"3","num":"1","title":"茶具","title_pic":"http://192.168.0.199/tea//Uploads/5a016c831969ax250.jpg","price":"1.00","classname":"茶具"}]
         * total_price : 402
         */

        private double total_price;
        private List<ListBean> list;

        public double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(double total_price) {
            this.total_price = total_price;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * carid : 1
             * num : 1
             * title : 茶叶
             * title_pic : http://192.168.0.199/tea//Uploads/59e060c26516ex250.png
             * price : 100.00
             * classname : 茶叶
             */

            private String carid;
            private int num;
            private String title;
            private String title_pic;
            private double price;
            private String classname;
            private boolean isCheck;

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public String getCarid() {
                return carid;
            }

            public void setCarid(String carid) {
                this.carid = carid;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
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

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getClassname() {
                return classname;
            }

            public void setClassname(String classname) {
                this.classname = classname;
            }
        }
    }
}
