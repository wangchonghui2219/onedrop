package com.dlwx.onedrop;

/**
 * Created by Administrator on 2017/11/8/008.
 */

public class ClassSelete {
        public interface ClassSeleteListener {
            void seletepos(int i);
        }
        public static ClassSeleteListener seleteListener;

        public static void setSeleteListener(ClassSeleteListener classSeleteListener){
            seleteListener = classSeleteListener;
        }
}
