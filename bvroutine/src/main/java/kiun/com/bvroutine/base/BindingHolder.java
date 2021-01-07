package kiun.com.bvroutine.base;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

public class BindingHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;
    private Object item;
    public BindingHolder(View itemView) {
        super(itemView);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }

    public void bind(int br, Object data){

        if (data instanceof EventBean){
            if (item instanceof EventBean){
                //清空上一个的绑定.
                ((EventBean) item).bind(-1, null);
            }
            EventBean eventBean = (EventBean) data;
            binding.setVariable(br, eventBean.bind(br, binding));
        }else{
            binding.setVariable(br, data);
        }
        item = data;
    }
}