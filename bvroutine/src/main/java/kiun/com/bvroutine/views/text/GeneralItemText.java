package kiun.com.bvroutine.views.text;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingListener;
import java.util.Map;
import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.base.binding.ValListener;
import kiun.com.bvroutine.interfaces.GeneralItemTextListener;
import kiun.com.bvroutine.interfaces.callers.GetCaller;
import kiun.com.bvroutine.interfaces.view.ItemValue;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ViewUtil;
import kiun.com.bvroutine.views.dialog.MCDialogManager;

/**
 * 文 件 名: GeneralItemText
 * 作 者: 刘春杰
 * 创建日期: 2020/5/25 18:23
 * 说明: 通用选项文本
 */
@SuppressLint("AppCompatCustomView")
public class GeneralItemText extends TextView implements TypedView, View.OnClickListener, ValListener {

    @FunctionalInterface
    public interface GetParamCaller extends GetCaller<GeneralItemText, Object>{}

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String EXTRA = "extra";

    public static final String PARAM = "param";

    public static final String RETURN = "return";

    @AttrBind
    private String tableName;

    @AttrBind
    private String fieldName;

    @AttrBind(def = 0)
    private int dialogLayoutId;

    @AttrBind
    private String[] titleArray;

    @AttrBind
    private BVBaseActivity activity;

    private InverseBindingListener listener;

    private GeneralItemTextListener textListener;

    private String id;

    private Object extra;

    Class<? extends Activity> clz;

    Object params;

    GetParamCaller paramGetter;

    public GeneralItemText(Context context) {
        super(context);
    }

    public GeneralItemText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public GeneralItemText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    public void setListener(InverseBindingListener listener) {
        this.listener = listener;
    }

    public void setVal(Object val) {
        if (val instanceof String){
            setText((String) val);
        }else if (val instanceof Parcelable){
            params = val;
        }
    }

    public Object getVal() {
        if (extra != null){
            return extra;
        }
        return id;
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.GeneralItemText;
    }

    @Override
    public void initView() {
        setOnClickListener(this);
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return getRootView().findViewById(id);
    }

    @Override
    public void onClick(View v) {

        if (paramGetter != null){
            params = paramGetter.call(this);
        }

        if (dialogLayoutId != 0){

            MCDialogManager<Object,?> mcDialogManager = MCDialogManager.create(getContext(), dialogLayoutId, params, BR.data, BR.dialog);
            mcDialogManager.setCancelable(true).setGravity(Gravity.BOTTOM);
            mcDialogManager.setCaller(item->{

                if (item instanceof ItemValue){
                    id = ((ItemValue) item).itemId();
                    setText(((ItemValue) item).itemTitle());
                }else if (item instanceof String){
                    id = (String) item;
                    setText(id);
                }

                if (textListener != null){
                    textListener.onChanged(this, id, ((ItemValue) item).itemTitle(), extra);
                }
                if (listener != null){
                    listener.onChange();
                }
            });
            mcDialogManager.show();
        }else if (clz != null){

            Intent intent = new Intent(getContext(), clz);
            if (params instanceof Map){
                Map<String, String> maps = (Map<String, String>) params;
                for (String key : maps.keySet()){
                    intent.putExtra(key, maps.get(key));
                }
            }else if (params instanceof Parcelable){
                intent.putExtra(PARAM, (Parcelable) params);
            }
            intent.putExtra(RETURN, true);

            activity.startForResult(intent, result->{
                extra = result.getParcelableExtra(EXTRA);
                if (extra == null){
                    extra = result.getSerializableExtra(EXTRA);
                }
                id = result.getStringExtra(ID);
                String title = result.getStringExtra(TITLE);
                if (!TextUtils.isEmpty(title)){
                    setText(result.getStringExtra(TITLE));
                }

                if (textListener != null){
                    textListener.onChanged(this, id, title, extra);
                }
                if (listener != null){
                    listener.onChange();
                }
            });
        }
    }

    @BindingAdapter("listener")
    public static void setListener(GeneralItemText itemText, GeneralItemTextListener listener){
        itemText.textListener = listener;
    }

    @BindingAdapter("param")
    public static void setParam(GeneralItemText itemText, Object keyValues){
        itemText.params = keyValues;
        itemText.setText(null);
    }

    @BindingAdapter("paramGetter")
    public static void setParamGetter(GeneralItemText itemText, GetParamCaller caller){
        itemText.paramGetter = caller;
    }

    @BindingAdapter("activityClz")
    public static void setActivityClz(GeneralItemText itemText, Class<? extends Activity> clz){
        itemText.clz = clz;
    }
}