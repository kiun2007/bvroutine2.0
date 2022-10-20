package kiun.com.bvroutine.views.delegate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ViewUtil;

/**
 * 视图代理基类
 * 主要用于视图的逻辑代理处理, 比如流式布局器, 容器视图使用该代理器之后，布局遵循流式布局规则.
 * @param <V> 代理类作用于的视图类型
 */
public abstract class ViewDelegate<V extends View> implements TypedView {

    protected V view;

    public ViewDelegate(@NonNull V view, AttributeSet attributeSet) {
        this.view = view;
        if (attributeSet != null){
            ViewUtil.initTyped(this, attributeSet);
        }
    }

    @Override
    public Context getContext() {
        return view.getContext();
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return view.findViewById(id);
    }
}
