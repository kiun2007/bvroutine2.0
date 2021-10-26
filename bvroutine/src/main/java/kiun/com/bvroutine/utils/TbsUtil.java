package kiun.com.bvroutine.utils;

import android.content.Context;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;

import kiun.com.bvroutine.base.BVBaseActivity;

import static android.Manifest.permission.READ_PHONE_STATE;

public class TbsUtil {

    /**
     * 下载X5内核.
     * @param context
     */
    public static void download(Context context){

        if (context instanceof BVBaseActivity){
            ((BVBaseActivity) context).startPermission(()->{
                QbSdk.reset(context);
                //手动开始下载，此时需要先判定网络是否符合要求
                TbsDownloader.startDownload(context);
            }, READ_PHONE_STATE);
        }
    }
}
