package kiun.com.bvroutine.utils.file;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.provider.MediaStore;
import android.util.Size;
import android.view.View;

import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.data.KeyValue;
import kiun.com.bvroutine.media.BitmapDraw;
import kiun.com.bvroutine.media.MediaDuration;
import kiun.com.bvroutine.utils.ViewUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MediaPackage {

    private Context context;

    private String thumbPartName, filePartName;

    private MultipartBody.Part file, thumb;

    private Bitmap thumbBitmap;

    private float compress = 1.0f;

    private boolean isThumbEnable = false; //启用缩略图模式.

    public MediaPackage(Context context, String filePartName, String thumbPartName){
        this.context = context;
        this.thumbPartName = thumbPartName;
        this.filePartName = filePartName;
    }

    public void setThumbEnable(boolean thumbEnable) {
        isThumbEnable = thumbEnable;
    }

    public Uri newUri(String type){

        File path = new File(context.getExternalCacheDir(), type);
        if (!path.exists()){
            path.mkdir();
        }
        File file = new File(path, String.format("%d.%s", System.currentTimeMillis(), type));
        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
    }

    public void setCompress(float compress) {
        this.compress = compress;
    }

    /**
     * 打包主文件.
     * @param uri
     * @return
     */
    private MultipartBody.Part packageUri(Uri uri){
        KeyValue<String, RequestBody> requestBody = packageUriBody(uri);
        if (requestBody != null){
            return MultipartBody.Part.createFormData(filePartName, requestBody.key(), requestBody.value());
        }
        return null;
    }

    /**
     * 压缩主图.
     * @return
     */
    private byte[] compress(byte[] buffer){

        if (compress < 1.0f){
            Bitmap bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, (int) (compress * 100), outputStream);
            return outputStream.toByteArray();
        }
        return buffer;
    }

    private KeyValue<String, RequestBody> packageUriBody(Uri uri){

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);

            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor metaCursor = context.getContentResolver().query(uri, projection, null,null, null);

            String fileName = uri.getPath();
            if (metaCursor != null && metaCursor.moveToFirst()){
                if (metaCursor.getColumnCount() > 0 && metaCursor.getString(0) != null){
                    fileName = metaCursor.getString(0);
                }
                metaCursor.close();
            }
            fileName = new File(fileName).getName();

            assert inputStream != null;
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();

            buffer = compress(buffer);
            RequestBody requestFile = RequestBody.create(MediaUtil.mediaType(context, fileName), buffer);
            return new KeyValue<>(fileName, requestFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new KeyValue<>(null,null);
    }

    /**
     * 打包缩略图
     * @param bitmap
     * @return
     */
    private MultipartBody.Part packageThumb(Bitmap bitmap){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), outputStream.toByteArray());
        return MultipartBody.Part.createFormData(thumbPartName, "thumb.jpg", requestFile);
    }

    private Bitmap getThumbBitmap(View v, Uri uri, Bitmap source, String fileName){

        Bitmap thumbBitmap = null;
        Context context = v.getContext();

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                thumbBitmap = context.getContentResolver().loadThumbnail(uri, new Size(v.getWidth(), v.getHeight()), new CancellationSignal());
            } else {

                MediaType mediaType = MediaUtil.mediaType(context, fileName);

                if ("image".equals(mediaType.type())) {
                    thumbBitmap = ThumbnailUtils.extractThumbnail(source, v.getWidth(), v.getHeight());
                } else if ("audio".equals(mediaType.type())) {

                    MediaDuration duration = new MediaDuration(context, uri);
                    BitmapDraw draw = new BitmapDraw(v.getWidth(), v.getHeight(), 0xFFE0E0E0).setTextSize(ViewUtil.dp2px(context, 12));
                    draw.drawIcon(context.getDrawable(R.drawable.ic_baseline_audiotrack_40));
                    draw.drawText(duration.getFormat(), 0xFF628CFF);
                    thumbBitmap = draw.getBitmap();
                } else if ("video".equals(mediaType.type())) {

                    MediaMetadataRetriever media = new MediaMetadataRetriever();
                    String videoPath = uri.getPath();
                    media.setDataSource(videoPath);
                    thumbBitmap = media.getFrameAtTime();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return thumbBitmap;
    }

    public void packageFromUri(View view, Uri uri){

        try {
            Bitmap source = thumbBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            KeyValue<String, RequestBody> nameAndBody = packageUriBody(uri);

            //设置主文件Part.
            file = MultipartBody.Part.createFormData(filePartName, nameAndBody.key(), nameAndBody.value());

            if (isThumbEnable){
                thumbBitmap = getThumbBitmap(view, uri, source, nameAndBody.key());
                thumb = packageThumb(thumbBitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap() {
        return thumbBitmap;
    }

    public MultipartBody.Part getFile() {
        return file;
    }

    public MultipartBody.Part getThumb() {
        return thumb;
    }
}
