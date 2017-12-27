package com.dlwx.onedrop.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/14/014.
 */

public class TeamCenBean {

    /**
     * body : {"person_info":[{"exten_code":"wvn7ni","header_pic":"http://192.168.0.196/tea/Uploads/59d8922618938x250.jpg","nickname":"人从众","telephone":"18072723807","use_price":"79.99"},{"exten_code":"67glmP","header_pic":"","nickname":"132****6257","telephone":"13298356257","use_price":"222.00"},{"exten_code":"dVN2Dh","header_pic":"http://192.168.0.196/tea/Uploads/20171110/5a050565e42aa.png","nickname":"王","telephone":"18637051978","use_price":"0.00"},{"exten_code":"lH6Dkc","header_pic":"5","nickname":"132****3520","telephone":"13298653520","use_price":"1.00"},{"exten_code":"KU8u4E","header_pic":"51","nickname":"啊哈哈","telephone":"13298356250","use_price":"88556.00"}],"total_num":5,"total_price":88858.99}
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
         * person_info : [{"exten_code":"wvn7ni","header_pic":"http://192.168.0.196/tea/Uploads/59d8922618938x250.jpg","nickname":"人从众","telephone":"18072723807","use_price":"79.99"},{"exten_code":"67glmP","header_pic":"","nickname":"132****6257","telephone":"13298356257","use_price":"222.00"},{"exten_code":"dVN2Dh","header_pic":"http://192.168.0.196/tea/Uploads/20171110/5a050565e42aa.png","nickname":"王","telephone":"18637051978","use_price":"0.00"},{"exten_code":"lH6Dkc","header_pic":"5","nickname":"132****3520","telephone":"13298653520","use_price":"1.00"},{"exten_code":"KU8u4E","header_pic":"51","nickname":"啊哈哈","telephone":"13298356250","use_price":"88556.00"}]
         * total_num : 5
         * total_price : 88858.99
         */

        private int total_num;
        private double total_price;
        private List<PersonInfoBean> person_info;

        public int getTotal_num() {
            return total_num;
        }

        public void setTotal_num(int total_num) {
            this.total_num = total_num;
        }

        public double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(double total_price) {
            this.total_price = total_price;
        }

        public List<PersonInfoBean> getPerson_info() {
            return person_info;
        }

        public void setPerson_info(List<PersonInfoBean> person_info) {
            this.person_info = person_info;
        }

        public static class PersonInfoBean {
            /**
             * exten_code : wvn7ni
             * header_pic : http://192.168.0.196/tea/Uploads/59d8922618938x250.jpg
             * nickname : 人从众
             * telephone : 18072723807
             * use_price : 79.99
             */

            private String exten_code;
            private String header_pic;
            private String nickname;
            private String telephone;
            private String use_price;

            public String getExten_code() {
                return exten_code;
            }

            public void setExten_code(String exten_code) {
                this.exten_code = exten_code;
            }

            public String getHeader_pic() {
                return header_pic;
            }

            public void setHeader_pic(String header_pic) {
                this.header_pic = header_pic;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getUse_price() {
                return use_price;
            }

            public void setUse_price(String use_price) {
                this.use_price = use_price;
            }
        }
    }
}
