package com.dlwx.onedrop.views;

/**
 * Created by Administrator on 2017/11/16/016.
 */

public class ProductDescToCat {
    public static ProductDescToCatInterface descToCatInterface;
        public interface ProductDescToCatInterface{
            void start();

        }
        public static void setDescToCatInterface(ProductDescToCatInterface productDescToCatInterface){
            descToCatInterface = productDescToCatInterface;

        }
}
