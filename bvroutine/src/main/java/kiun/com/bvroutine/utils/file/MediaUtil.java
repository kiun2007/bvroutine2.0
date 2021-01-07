package kiun.com.bvroutine.utils.file;

import android.content.Context;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 媒体文件工具类.
 */
public class MediaUtil {

    public static MediaType mediaType(Context context, String fileName){
        String mimeType = MimeTypeUtil.getType(context, fileName);
        return MediaType.parse(mimeType);
    }

    public static MediaType mediaType(String fileName){
        return mediaType(null, fileName);
    }

    /**
     * 打包文件.
     * @param name 服务器接口中的参数名 @RequestParam
     * @param file 需要打包的本地文件.
     * @return 打包后的okhttp 参数.
     */
    public static MultipartBody.Part packageFile(String name, File file){

        RequestBody requestBody = RequestBody.create(mediaType(file.getName()), file);
        return MultipartBody.Part.createFormData(name, file.getName(), requestBody);
    }
}
