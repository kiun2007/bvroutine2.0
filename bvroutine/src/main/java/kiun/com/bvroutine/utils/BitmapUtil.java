package kiun.com.bvroutine.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.util.Size;
import android.view.View;

import java.io.File;
import java.io.IOException;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.media.BitmapDraw;
import kiun.com.bvroutine.media.MediaDuration;
import okhttp3.MediaType;

public class BitmapUtil {

    public static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap drawableToBitmap(Drawable drawable, Size size){

        Matrix matrix = new Matrix();
        float scale = ((float)drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight());
        float scaleWidth = ((float) size.getWidth() / drawable.getIntrinsicWidth());


        matrix.postScale(scaleWidth, scaleWidth);
        Bitmap oldBmp = drawableToBitmap(drawable);

        return Bitmap.createBitmap(oldBmp, 0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), matrix, true);
    }

    /**
     * Bitmap放大的方法
     * @param bitmap
     * @return
     */
    public static Bitmap big(Bitmap bitmap) {

        Matrix matrix = new Matrix();
        matrix.postScale(1.5f,1.5f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }

    /**
     * Bitmap缩小的方法
     * @param bitmap
     * @return
     */
    public static Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.8f,0.8f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }

    public static Bitmap postScale(Bitmap source, int width){

        Matrix matrix = new Matrix();
        float scale = (float) width / source.getWidth();
        matrix.postScale(scale,scale);
        Bitmap resizeBmp = Bitmap.createBitmap(source, 0, 0, source.getWidth(),source.getHeight(), matrix,true);
        return resizeBmp;
    }
}
