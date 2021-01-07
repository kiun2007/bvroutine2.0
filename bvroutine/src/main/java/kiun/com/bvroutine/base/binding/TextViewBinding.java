package kiun.com.bvroutine.base.binding;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.BindingAdapter;

import kiun.com.bvroutine.base.DrawableIndex;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.interfaces.binding.NetTextBean;
import kiun.com.bvroutine.interfaces.callers.GetTNoParamCall;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.presenters.TextViewLoadingPresenter;
import kiun.com.bvroutine.utils.image.DrawableUtil;
import retrofit2.Call;

public class TextViewBinding {

    @BindingAdapter("android:tint")
    public static void setTint(TextView textView, int color){
        setDrawableTintColor(textView, color);
        textView.setTextColor(color);
    }

    @BindingAdapter("android:drawableTintColor")
    public static void setDrawableTintColor(TextView textView, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setCompoundDrawableTintList(ColorStateList.valueOf(color));
        }
    }

    @BindingAdapter("android:drawableLeft")
    public static void setDrawableLeft(TextView textView, String drawableName){
        if (drawableName == null){
            return;
        }

        String[] typeArray = drawableName.split("/");
        if (typeArray.length != 3){
            return;
        }

        String name = typeArray[2].split("\\.")[0];
        int resId = textView.getResources().getIdentifier(name, typeArray[1], textView.getContext().getPackageName());
        if (resId == 0){
            return;
        }
        DrawableUtil.setLeftDrawable(textView, resId);
    }

    @BindingAdapter("android:selected")
    public static void setSelected(TextView textView, boolean isSelected){
        textView.setSelected(isSelected);
    }

    @BindingAdapter("android:drawableClosely")
    public static void setDrawableClosely(TextView textView, boolean isClosely){

    }

    @BindingAdapter("android:netText")
    public static void setNetText(TextView textView, GetTNoParamCall<Call> call) {

        if (call != null && textView.getContext() instanceof RequestBVActivity){

            RequestBVActivity activity = (RequestBVActivity) textView.getContext();
            RequestBindingPresenter p = activity.getRequestPresenter();
            TextViewLoadingPresenter presenter = new TextViewLoadingPresenter(textView);

            presenter.begin();
            textView.setText("加载中");
            p.addRequest(()->p.execute(call.call()), v -> {

                if (v != null && v.isSuccess()){

                    if (v.getData() instanceof String){
                        textView.setText((String) v.getData());
                    }else if (v.getData() instanceof NetTextBean){
                        textView.setText(((NetTextBean) v.getData()).getNetText());
                    }
                    presenter.clear();
                    return;
                }

                textView.setTextColor(Color.RED);
                textView.setText(v.getMsg());
                presenter.error();
            }, ex->{
                Toast.makeText(activity, "网络异常", Toast.LENGTH_LONG).show();
                textView.setText("加载失败");
                textView.setTextColor(Color.RED);
                presenter.failed();
            });
        }
    }
}