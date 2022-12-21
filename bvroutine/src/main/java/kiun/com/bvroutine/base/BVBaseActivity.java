package kiun.com.bvroutine.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.binding.variable.VariableBinding;
import kiun.com.bvroutine.base.jexl.ProxyEnable;
import kiun.com.bvroutine.base.jexl.ProxyHandler;
import kiun.com.bvroutine.data.BaseBean;
import kiun.com.bvroutine.data.TypeSetter;
import kiun.com.bvroutine.interfaces.StateTransfer;
import kiun.com.bvroutine.interfaces.callers.CallBack;
import kiun.com.bvroutine.interfaces.callers.IntentSetCaller;
import kiun.com.bvroutine.interfaces.callers.PutVoidCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.view.BindingView;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.ObjectUtil;
import kiun.com.bvroutine.utils.PermissionUtil;
import kiun.com.bvroutine.utils.StatusBarUtils;
import kiun.com.bvroutine.utils.ViewBindingUtil;
import kiun.com.bvroutine.views.NavigatorBar;
import kiun.com.bvroutine.views.viewmodel.ActionBarItem;

public abstract class BVBaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements BindingView, LifecycleOwner {

    /**
     * 事件响应接口
     */
    interface EventPutCall extends PutVoidCaller<Integer, Object>{}

    /**
     * 导航条对象
     */
    private NavigatorBar barView;
    private int requestIdBase = 50;
    private PermissionUtil permissionUtil;
    private List<ScheduledMethod> methodList;
    protected T binding = null;
    private ActionBarItem barItem = null;
    private Map<Integer, SetCaller<Intent>> resultCallers = new HashMap<>();

    //碎片对象监听.
    private Map<Fragment, EventPutCall> fragmentSetCallerMap = new HashMap<>();

    //原返回值
    protected Intent resultIntent = null;

    //原返回Code
    private int resultCode = Activity.RESULT_CANCELED;

    private StateTransfer transfer;

    public boolean isWithActionBar(){
        return false;
    }

    public void onCreateBefore(){
        setRequestedOrientation(getOrientation());
    }

    protected int getOrientation(){
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    private void create(){

        permissionUtil = new PermissionUtil(this);
        if(isWithActionBar()){
            setContentView(R.layout.activiy_base);
            binding = ViewBindingUtil.inflate(LayoutInflater.from(this), getViewId(), findViewById(R.id.view_content), true);
            barView = findViewById(R.id.actionBarViewBinding);
        }else{
            binding = ViewBindingUtil.setContentView(this, getViewId());
            barView = binding.getRoot().findViewById(R.id.actionBarViewBinding);
        }

        if(barView != null){
            barItem = barView.getBarItem();
        }

        StatusBarUtils.with(this).setColor(-1).init();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|
                                     WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        methodList = ScheduledMethod.getScheduled(this);
        initView();
    }

    /**
     * 传递参数给上一个页面,为了安全起见请使用此方法替代 {@link Activity#setResult(int, Intent)}
     * @param code 动作值
     * @param intent 参数Intent
     */
    public final void setResultAction(int code, Intent intent){
        resultCode = code;
        resultIntent = intent;

        if (transfer == null){
            setResult(code, intent);
        }
    }

    /**
     * 传递参数给上一个页面,为了安全起见请使用此方法替代 {@link Activity#setResult(int)}
     * @param code 动作值
     */
    public final void setResultAction(int code){
        setResultAction(code, null);
    }

    public View getRootView(){
        return binding.getRoot();
    }

    public NavigatorBar getBarView() {
        return barView;
    }

    public void start(){
        ListUtil.map(methodList, ScheduledMethod::start);
    }

    public void stop(){
        ListUtil.map(methodList, ScheduledMethod::stop);
    }

    protected void setVariable(int variableId, @Nullable Object value){

        if (value == null){
            return;
        }

        if (value.getClass().isAnnotationPresent(ProxyEnable.class)){
            value = ProxyHandler.createBy(this, value.getClass(), value);
        }

        if (value instanceof EventBean){
            EventBean eventBean = (EventBean) value;
            eventBean.error(this::onError);
            eventBean.bind(variableId, binding);
        }
        binding.setVariable(variableId, value);
    }

    protected void onError(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.transfer = ObjectUtil.getFirstAnnotation(getClass(), StateTransfer.class);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        onCreateBefore();
        super.onCreate(savedInstanceState);
        create();
    }

    protected <B extends BaseBean> B getExtra(){
        return getIntent().getParcelableExtra("EXTRA");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        onCreateBefore();
        super.onCreate(savedInstanceState, persistentState);
        create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (transfer != null){
            transferToIntent(getIntent(), data);
        }
        SetCaller caller = resultCallers.remove(requestCode);
        if (caller != null && resultCode == Activity.RESULT_OK){
            caller.call(data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionUtil != null){
            permissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void startPermission(CallBack callBack, CallBack refuseCall, String... permission){
        startPermission(permission, callBack, refuseCall);
    }

    public void startPermission(CallBack callBack, String... permission){
        startPermission(callBack, null, permission);
    }

    public void startPermission(String[] permission, CallBack callBack){
        startPermission(permission, callBack, null);
    }

    public void startPermission(String[] permission, CallBack okCall, CallBack refuseCall){
        if (permissionUtil != null){
            permissionUtil.startPermission(permission, okCall, refuseCall);
        }
    }

    public ActionBarItem getBarItem() {
        return barItem;
    }

    public void setBarItem(ActionBarItem barItem){
        if (barView != null){
            barView.setBarItem(barItem);
        }
    }

    public void startForResult(Class clz, String key, Parcelable value, IntentSetCaller caller){
        Intent intent = new Intent(this, clz);
        if (value != null){
            intent.putExtra(key, value);
        }
        startForResult(intent, caller);
    }

    public void startForResult(Intent intent, IntentSetCaller caller){
        if (requestIdBase > 5000){
            requestIdBase = 50;
        }
        int requestId = requestIdBase++;

        resultCallers.put(requestId, caller);
        startActivityForResult(intent, requestId);
    }

    public void startForResult(Class clz, IntentSetCaller caller){
        startForResult(clz, null,null, caller);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        putIntent(intent);
        super.startActivity(intent, options);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        fragmentSetCallerMap.clear();
        fragmentSetCallerMap = null;
    }

    void registerListener(Fragment fragment, EventPutCall caller){
        fragmentSetCallerMap.put(fragment, caller);
    }

    void unregisterListener(Fragment fragment){
        fragmentSetCallerMap.remove(fragment);
    }

    protected void putEvent(Integer tag, Object value, Class<? extends Fragment> filterClz){

        for (Fragment fragment : fragmentSetCallerMap.keySet()){
            if (filterClz == null || filterClz.isAssignableFrom(fragment.getClass())){
                try {
                    fragmentSetCallerMap.get(fragment).call(tag, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void startActivity(Intent intent) {
        putIntent(intent);
        super.startActivity(intent);
    }

    private void transferToIntent(Intent intent, Intent inIntent){
        transferToIntent(intent, inIntent, false);
    }

    //透传参数
    private void transferToIntent(Intent intent, Intent inIntent, boolean isOver){

        if (inIntent == null || inIntent.getExtras() == null || intent == null){
            return;
        }

        Bundle bundle = new Bundle();
        for (String key : transfer.value()){
            Object value = inIntent.getExtras().get(key);

            if (intent.getExtras() != null && intent.getExtras().get(key) != null && !isOver){
                continue;
            }

            if (value instanceof Serializable){
                intent.putExtra(key, (Serializable)value);
            }else if (value instanceof Parcelable){
                intent.putExtra(key, (Parcelable)value);
            }else if (value != null){
                new TypeSetter().addPutter(String.class, bundle::putString)
                        .addPutter(Integer.class, bundle::putInt)
                        .addPutter(Long.class, bundle::putLong)
                        .addPutter(Float.class, bundle::putFloat)
                        .addPutter(Double.class, bundle::putDouble)
                        .execute(key, value);
            }
        }

        intent.putExtras(bundle);
    }

    private void putIntent(Intent intent){
        if(transfer != null){
            boolean isBreak = !ListUtil.filter(Arrays.asList(transfer.breakAction()),
                    item-> item.equals(intent.getAction())).isEmpty();

            if (!isBreak){
                transferToIntent(intent, getIntent());
            }
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        putIntent(intent);
        super.startActivityForResult(intent, requestCode, options);
    }

    protected void restart(){
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void finish() {
        if (transfer != null){
            if (resultIntent == null){
                resultIntent = new Intent();
            }
            transferToIntent(resultIntent, getIntent());
            setResult(resultCode, resultIntent);
        }
        super.finish();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }
}
