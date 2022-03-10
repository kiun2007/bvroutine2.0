package kiun.com.bvroutine.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ViewDataBinding;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.base.ViewBind;
import kiun.com.bvroutine.interfaces.callers.GetBooleanCaller;
import kiun.com.bvroutine.interfaces.view.TypedBindView;
import kiun.com.bvroutine.interfaces.view.TypedView;

public class ViewUtil {

    private static List<ViewNotice> viewNotices = new LinkedList<>();

    public static class ViewNotice implements View.OnAttachStateChangeListener {

        private View view;

        private GetBooleanCaller caller;

        ViewNotice(GetBooleanCaller caller) {
            this.caller = caller;
            viewNotices.add(this);
        }

        @Override
        public void onViewAttachedToWindow(View v) {
            view = v;
        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            viewNotices.remove(this);
        }

        /**
         * 通知变化.
         */
        void onChanged(){
            if (view != null && caller != null){
                Boolean isShow = caller.get(view);
                view.setVisibility(isShow != null && isShow ? View.VISIBLE : View.GONE);
            }
        }
    }

    private static Map<Integer, ViewDataBinding> viewDataBindingCache = new HashMap<>();

    public static void noticeAll(){
        for (ViewNotice notice : viewNotices){
            notice.onChanged();
        }
    }

    public static ViewNotice createNotice(GetBooleanCaller caller){
        return new ViewNotice(caller);
    }

    public static BVBaseActivity getActivity(Context context){
        if (context instanceof BVBaseActivity){
            return (BVBaseActivity) context;
        }

        return null;
    }

    public static void inverse(InverseBindingListener listener){
        if (listener != null){
            listener.onChange();
        }
    }

    public static String initTyped(TypedView typedView, AttributeSet attrs){

        StringBuilder logError = null;
        if(attrs == null){
            return null;
        }

        if (typedView.getContext() instanceof Activity && typedView instanceof View){

            boolean isEditModal = ((Activity) typedView.getContext()).getIntent().getBooleanExtra("isEditModal", true);
            if (!isEditModal){
                ((View) typedView).setEnabled(false);
            }
        }

        boolean isEditMode = false;

        if (typedView instanceof View){
            isEditMode = ((View) typedView).isInEditMode();
        }

        if (isEditMode){
            logError = new StringBuilder();

        }

        Context context = typedView.getContext();
        TypedArray array = context.obtainStyledAttributes(attrs, typedView.getStyleableId());
        Class typeClz = typedView.getClass();

        AttriUtil.readAllAttri(typedView, logError, isEditMode, context, array, typeClz);
        bindViews(typedView, context, typeClz);

        typedView.initView();
        return logError == null ? "" : logError.toString();
    }

    private static void bindViews(TypedView typedView, Context context, Class typeClz) {
        if (typedView instanceof TypedBindView && typedView instanceof ViewGroup){
            TypedBindView typedBindView = (TypedBindView) typedView;
            View root = LayoutInflater.from(context).inflate(typedBindView.layoutId(), (ViewGroup) typedView, true);

            for (Field field : typeClz.getDeclaredFields()){
                ViewBind viewBind = field.getAnnotation(ViewBind.class);

                if (viewBind != null && View.class.isAssignableFrom(field.getType())){

                    int id = viewBind.value();
                    //默认使用字段名查找资源
                    if (id == -1){
                        id = context.getResources().getIdentifier(field.getName(), "id", context.getPackageName());
                    }

                    //使用ID查找对应的控件
                    View view = root.findViewById(id);

                    if (view != null){
                        try {
                            field.setAccessible(true);
                            field.set(typedView, view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale);
    }

    public static<T> T findRootViewTag(View view, Class<T> clz){

        ViewParent parent = view.getParent();
        View rootView = null;

        while (parent.getParent() != null && parent.getParent() instanceof View) {
            parent = parent.getParent();
            View parentView = (View) parent;
            if (parentView.getTag() != null){
                if (clz.isAssignableFrom(parentView.getTag().getClass())){
                    return (T) parentView.getTag();
                }
            }
        }
        return null;
    }

    /**
     * 查找指定Tag的View.
     * @param rootView
     * @param tag
     * @param value
     * @param <T>
     * @return
     */
    public static<T extends View> T findChildViewTag(View rootView, @IdRes int tag, @NonNull Object value){

        if (value.equals(rootView.getTag(tag))){
            return (T) rootView;
        }

        if (rootView instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) rootView;

            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = findChildViewTag(viewGroup.getChildAt(i), tag, value);
                if (child != null){
                    return (T) child;
                }
            }
        }
        return null;
    }

    public static View makeViewOfBindLayout(Context context, int layoutId, Object data){

        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, null,false);

//        if (viewDataBinding == null){
//            viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, null,false);
//            viewDataBindingCache.put(layoutId, viewDataBinding);
//        }

        viewDataBinding.setVariable(BR.data, data);
        viewDataBinding.executePendingBindings();
        return viewDataBinding.getRoot();
    }
}
