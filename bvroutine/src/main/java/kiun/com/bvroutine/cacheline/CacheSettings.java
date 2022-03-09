package kiun.com.bvroutine.cacheline;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.cacheline.data.beans.UploadObject;
import kiun.com.bvroutine.utils.SharedUtil;

public class CacheSettings {

    private static CacheKernel cacheKernel;
    private static Context mContext;
    private static boolean isOffline;

    /**
     * 初始化缓存配置.
     * @param context APP上下文.
     * @param settingInterfaces 配置接口集合.
     */
    public static void initSettings(Context context, String userField, CacheSettingInterface... settingInterfaces){

        SharedUtil.initContext(context);

        if (cacheKernel != null){
            cacheKernel.release();
        }

        mContext = context;
        try {
            cacheKernel = new CacheKernel(context, userField, settingInterfaces);
            Log.d("CacheSettings", "初始化旧版本离线");
            SharedPreferences preferences = context.getSharedPreferences("CacheSettings", Context.MODE_PRIVATE);
            isOffline = preferences.getBoolean("isOffline", false);
        } catch (Exception e) {
            e.printStackTrace();
            isOffline = false;
        }
    }

    /**
     * 使用缓存内核完成缓存逻辑.
     * @param url 请求的实际地址.
     * @param params 请求的实际参数.
     * @param inBytes 如果是在线缓存将在线数据进行存储.
     * @return 返回到UI端需要展示的数据.
     * @throws Exception 如果没有初始化就直接使用会导致内核无法使用.
     */
    public static byte[] cacheIn(String url, Object params, Map<String, String> header, byte[] inBytes) throws Exception {
        if(cacheKernel == null){
            throw new Exception("Not Init Cache Setting!");
        }
        return cacheKernel.cacheIn(url, params, header, inBytes);
    }

    public static boolean isOnline(){

        //内核对象未初始化.
        if(cacheKernel == null){
            return true;
        }
        if(isOffline){
            return false;
        }

        ConnectivityManager connManger = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo active_info = connManger.getActiveNetworkInfo();
        if (active_info == null || !active_info.isConnected()){
            return false;
        }
        return true;
    }

    public static void setOffline(boolean offline){
        isOffline = offline;
        SharedPreferences preferences = mContext.getSharedPreferences("CacheSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isOffline", isOffline);
        editor.commit();
    }

    public static boolean isOffline(){
        return isOffline;
    }

    public static List<UploadObject> readAllUpload(){
        if(cacheKernel == null){
            return null;
        }
        return cacheKernel.readAllUpload();
    }

    public static void startUpload(List<UploadObject> uploadList, CacheUploadEventer eventer){
        if(cacheKernel != null){
            cacheKernel.startUpload(eventer, uploadList);
        }
    }

    public static void stopUpload(){
        if(cacheKernel != null){
            cacheKernel.stopUpload();
        }
    }
}
