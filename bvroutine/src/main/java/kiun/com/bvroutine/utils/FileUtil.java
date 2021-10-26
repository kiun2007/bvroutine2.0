package kiun.com.bvroutine.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.util.Date;

public class FileUtil {

    private static int tally = 0;

    @SuppressLint("DefaultLocale")
    public static File createFile(Context context, String type){

        File folder = new File(context.getExternalCacheDir(), type);
        if (!folder.exists()){
            folder.mkdir();
        }

        String time = MCString.formatDate("yyyyMMddHHmmssSSS", new Date());

        if (tally >= 10000){
            tally = 0;
        }
        return new File(folder, String.format("%s%04d.%s", time, tally++, type));
    }

    public static String getFileTail(String fileName){

        String str = "";
        if (TextUtils.isEmpty(fileName)) {
            return str;
        }
        int i = fileName.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }

        str = fileName.substring(i + 1);
        return str;
    }
}
