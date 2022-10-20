package kiun.com.bvroutine.utils.file;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import kiun.com.bvroutine.ActivityApplication;
import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.ContextHandlerVariableSet;
import kiun.com.bvroutine.interfaces.callers.GetCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.SharedUtil;

@AutoImport(ContextHandlerVariableSet.class)
public class CacheManage extends EventBean {

    private Context context;
    private File file;

    public CacheManage(Context context){
        this.context = context;
        file = context.getCacheDir();
    }

    /**
     * 获取缓存大小
     * @return
     */
    public String getTotalSize(){

        if (file == null) return "0";

        long cacheSize = getFolderSize(file);
        return MCString.byteFormat(cacheSize);
    }

    public CacheManage files(File file){
        this.file = file;
        return this;
    }

    public void clear() {
        clear(false, true);
    }

    public void clear(boolean restart)
    {
        clear(restart, false);
    }

    /***
     * 清理所有缓存
     */
    public void clear(boolean restart, boolean isShareClear) {

        deleteDir(file);
        if (isShareClear){
            SharedUtil.clear();
        }
        onChanged(false);

        if (restart){
            ActivityApplication.getApplication().restart();
        }
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
