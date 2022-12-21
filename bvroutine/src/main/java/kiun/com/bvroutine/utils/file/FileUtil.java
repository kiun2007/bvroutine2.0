package kiun.com.bvroutine.utils.file;

import android.content.Context;

import java.io.File;

public class FileUtil {

    public static File getFiles(Context context, String path){
        return new File(context.getFilesDir(), path);
    }
}
