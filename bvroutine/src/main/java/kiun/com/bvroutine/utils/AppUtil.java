package kiun.com.bvroutine.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class AppUtil {

    public static PackageInfo getPackageInfo(String packageName, Context context) throws PackageManager.NameNotFoundException {

        PackageManager packageManager = context.getPackageManager();
        return packageManager.getPackageInfo(packageName, 0);
    }

    public static PackageInfo getPackageInfo(Context context){
        try {
            return getPackageInfo(context.getPackageName(), context);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
