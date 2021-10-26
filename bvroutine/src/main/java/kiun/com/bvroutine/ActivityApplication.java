
package kiun.com.bvroutine;

import android.app.Activity;
import android.app.Application;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.base.binding.BindConvertBridge;
import kiun.com.bvroutine.base.binding.BindConvertImport;
import kiun.com.bvroutine.base.jexl.MCStringHelper;
import kiun.com.bvroutine.base.jexl.RuntimeContext;
import kiun.com.bvroutine.net.BuilderSet;
import kiun.com.bvroutine.net.BuilderSets;
import kiun.com.bvroutine.net.HttpInterceptor;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.utils.type.ClassUtil;
import kiun.com.bvroutine.utils.ObjectUtil;
import kiun.com.bvroutine.utils.SharedUtil;
import okhttp3.Interceptor;

public abstract class ActivityApplication extends Application {

    private static ActivityApplication application;

    private ActivityCallback.Callback callback = null;

    private static String appPackageName;

    public static<T extends ActivityApplication> T getApplication(){
        return (T) application;
    }

    public static void registerCallbacks(ActivityLifecycleCallbacks callbacks){
        if (application != null){
            application.registerActivityLifecycleCallbacks(callbacks);
        }
    }

    public static String getAppPackageName() {
        return appPackageName;
    }

    private void loadAnnotations(){

        String packageName = ActivityApplication.class.getPackage().getName();
        appPackageName = this.getPackageName();
        SearchPackage searchPackage = getClass().getAnnotation(SearchPackage.class);

        String[] packageNames = new String[]{packageName, appPackageName};
        if (searchPackage != null){
            String[] values = searchPackage.value();

            packageNames = new String[values.length+2];
            if (!searchPackage.codePackage().isEmpty()){
                appPackageName = searchPackage.codePackage();
            }

            packageNames[0] = packageName;
            packageNames[1] = appPackageName;

            for (int i = 2; i < packageNames.length; i++) {
                packageNames[i] = values[i-2];
            }
        }

        ClassUtil.initAllClass(packageNames);

        BindConvertImport bindConvertImport = this.getClass().getAnnotation(BindConvertImport.class);
        if (bindConvertImport != null && bindConvertImport.value().length > 0){
            BindConvertBridge.loadPackage(bindConvertImport.value());
        }

        BuilderSet builderSet = this.getClass().getAnnotation(BuilderSet.class);
        BuilderSets builderSets = this.getClass().getAnnotation(BuilderSets.class);

        List<BuilderSet> builderSetList = new LinkedList<>();
        if (builderSets != null){
            builderSetList.addAll(Arrays.asList(builderSets.value()));
        }

        if (builderSet != null){
            builderSetList.add(builderSet);
        }

        for (BuilderSet item : builderSetList){

            Interceptor interceptor = null;
            if (!item.interceptorClz().equals(HttpInterceptor.class)){
                interceptor = ObjectUtil.newObject(item.interceptorClz());
            }
            ServiceGenerator.putBuild(interceptor, item.prefix(), item.key(), item.loginClass());
        }

        RuntimeContext.runTime().set("MCString", new MCStringHelper());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        registerCallbacks(callback = new ActivityCallback.Callback());
        SharedUtil.initContext(this);
        loadAnnotations();
    }

    public final void finish(Class notClz){
        callback.allFinish(notClz);
    }

    public final Activity getTop(){
        return callback.getTop();
    }
}
