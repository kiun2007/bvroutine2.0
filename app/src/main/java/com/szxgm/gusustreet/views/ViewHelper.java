package com.szxgm.gusustreet.views;

import androidx.databinding.BindingAdapter;

public class ViewHelper {

    @BindingAdapter("imgUrl")
    public static void setImageUrl(RadiusImageView imageView, String src){
        imageView.setSrc(src);
    }
}