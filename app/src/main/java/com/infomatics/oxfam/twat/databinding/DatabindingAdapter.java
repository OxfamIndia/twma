package com.infomatics.oxfam.twat.databinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.util.ApplicationUtils;


public class DatabindingAdapter {
    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource){
        if(resource != -1) {
            imageView.setImageResource(resource);
        }
    }


    @BindingAdapter({"android:text"})
    public static void setText(TextView textView, String text){

        if(ApplicationUtils.isNull(text)){
            textView.setText("-");
        }else if(text.equalsIgnoreCase("null")){
            textView.setText("-");
        }else {
            if(text.toLowerCase().contains("null")){
                text.replaceAll("null", "0");
            }
            textView.setText(text);
        }
    }

}
