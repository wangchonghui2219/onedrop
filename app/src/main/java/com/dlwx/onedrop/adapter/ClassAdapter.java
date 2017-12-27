package com.dlwx.onedrop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.base.MyApplication;

/**
 * Created by Administrator on 2017/11/8/008.
 */

public class ClassAdapter {

    public static class ClassTitleAdapter extends BaseFastAdapter {
        private String[] classtitname;
        private int i =  MyApplication.classpos;
        public ClassTitleAdapter(Context ctx, String[] classtitname) {
            super(ctx);
            this.classtitname = classtitname;
        }

        @Override
        public int getCount() {
            return classtitname.length;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolderTitle vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(ctx).inflate(R.layout.item_class_title, null);
                vh = new ViewHolderTitle(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolderTitle) convertView.getTag();
            }
            String s = classtitname[position];
            vh.tv_name.setText(s);
            if (position == i) {

                vh.tv_name.setTextColor(ContextCompat.getColor(ctx,R.color.orange));
                vh.view.setBackgroundColor(ContextCompat.getColor(ctx,R.color.orange));
            }else{
                vh.tv_name.setTextColor(ContextCompat.getColor(ctx,R.color.black));
                vh.view.setBackgroundColor(ContextCompat.getColor(ctx,R.color.div_color));
            }
            return convertView;
        }

        public void setSelete(int i) {
            this.i = i;
            notifyDataSetChanged();
        }

        private class ViewHolderTitle {
            public View rootView;
            public TextView tv_name;
            public View view;
            public ViewHolderTitle(View rootView) {
                this.rootView = rootView;
                this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
                this.view = rootView.findViewById(R.id.view);
            }

        }
    }
}
