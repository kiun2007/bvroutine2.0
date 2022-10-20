package kiun.com.bvroutine.cacheline;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.ActivityApplication;
import kiun.com.bvroutine.cacheline.data.beans.UploadObject;
import kiun.com.bvroutine.utils.SharedUtil;
import retrofit2.Invocation;

public class CacheSettings {

    private static CacheKernel cacheKernel;
    private static Context mContext;
    private static boolean isOffline;

    /**
     * 使用缓存内核完成缓存逻辑.
     * @param invocation 请求的实际地址.
     * @param inBytes 如果是在线缓存将在线数据进行存储.
     * @return 返回到UI端需要展示的数据.
     * @throws Exception 如果没有初始化就直接使用会导致内核无法使用.
     */
    public static byte[] cacheIn(Invocation invocation,Map<String, String> header, byte[] inBytes) throws Exception {
        if(cacheKernel == null){
            cacheKernel = new CacheKernel(ActivityApplication.getApplication());
        }
        return cacheKernel.cacheIn(invocation, header, inBytes);
    }

    public static boolean isOnline(){

        if(isOffline()){
            return false;
        }

        ConnectivityManager connManger = (ConnectivityManager) ActivityApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo active_info = connManger.getActiveNetworkInfo();
        if (active_info == null || !active_info.isConnected()){
            return false;
        }
        return true;
    }

    public static void setOffline(boolean offline){
        isOffline = offline;
        SharedUtil.saveValue("isCache", isOffline);
    }

    public static boolean isOffline(){
        return SharedUtil.getValue("isCache", isOffline);
    }

    public static List<UploadObject> readUpload(boolean isUpload) throws Exception {
        if(cacheKernel == null){
            cacheKernel = new CacheKernel(ActivityApplication.getApplication());
        }
        return cacheKernel.relationManager.readUpload(isUpload);
    }

    public static boolean upload(UploadObject uploadObject) throws Exception{
        return cacheKernel.relationManager.upload(uploadObject);
    }
}
