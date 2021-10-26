package kiun.com.bvroutine.base.binding.value;

import android.view.View;

public abstract class BindConvertBuilder<V extends View> {

    public abstract BindConvert<V,?,?> build(V view);
}
