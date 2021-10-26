package kiun.com.bvroutine.utils.file;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.ContextHandlerVariableSet;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.SharedUtil;

@AutoImport(ContextHandlerVariableSet.class)
public class CacheManage extends EventBean {

    private Context context;

    public CacheManage(Context context){
        this.context = context;
    }

    /**
     * 获取缓存大小
     * @return
     */
    public String getTotalSize(){

        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return MCString.byteFormat(cacheSize);
    }

    /***
     * 清理所有缓存
     */
    public void clear() {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
        SharedUtil.clear();
        onChanged(false);
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static long getFolderSize(File file){

        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}
