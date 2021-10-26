package kiun.com.bvroutine.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.net.ServiceGenerator;

public class GlideUtil {

    private static String emptyPrefix = "/profile/avatar/";

    public static void setEmptyPrefix(String prefix){
        emptyPrefix = prefix;
    }

    public static void into(View view, String src, boolean isBackground){
        into(view, src, isBackground, false);
    }

    public static void into(View view, String src, boolean isBackground, boolean isNoHead){
        into(view, src, null, isBackground, isNoHead);
    }

    public static void into(View view, String src, String thumb, boolean isBackground, boolean isNoHead){
        into(view, src, thumb, isBackground, isNoHead, false);
    }

    public static void into(View view, String src, String thumb, boolean isBackground, boolean isNoHead, boolean noCache){

        if (!src.contains("http://") && !src.contains("https://")){
            src = ServiceGenerator.getBasePrefix() + (src.startsWith("/") ? "" : emptyPrefix) + src;
        }


        RequestBuilder<Drawable> builder;
        if (!isNoHead){
            GlideUrl cookie = new GlideUrl(src, () -> {
                Map<String, String> header = new HashMap<>();

                if (ServiceGenerator.getLogin() != null){
                    return ServiceGenerator.getLogin().getHeader();
                }
                return header;
            });
            builder = Glide.with(view.getContext()).load(cookie);
        }else{
            builder = Glide.with(view.getContext()).load(src);
        }

        if (thumb != null){
            if (thumb.startsWith(".")){
                thumb = String.format("%s%s", src, thumb);
            }else if (!thumb.contains("http://") && !thumb.contains("https://")){
                thumb = ServiceGenerator.getBasePrefix() + thumb;
            }
            RequestBuilder<Drawable> thumbnailBuilder = Glide.with(view.getContext()).load(thumb);
            builder.thumbnail(thumbnailBuilder);
        }

        if (noCache){
            builder.apply(RequestOptions.skipMemoryCacheOf(true).diskCacheStrategy(DiskCacheStrategy.NONE));
        }

        if (isBackground){
            builder.into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    view.setBackground(resource);
                }
            });
        }else{
            if (view instanceof ImageView){
                builder.into((ImageView) view);
            }
        }
    }
}
