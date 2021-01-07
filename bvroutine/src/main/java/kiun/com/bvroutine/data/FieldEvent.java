package kiun.com.bvroutine.data;


import java.util.List;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.ObjectVariableSet;
import kiun.com.bvroutine.utils.ListUtil;

@AutoImport(ObjectVariableSet.class)
public class FieldEvent<T> extends EventBean {

    private T v;

    public FieldEvent(){}

    public FieldEvent(T v) {
        this.v = v;
    }

    public void setValue(T v){
        this.v = v;
        onChanged(false);
    }

    public T getValue() {
        return v;
    }

    public boolean isEmpty(){
       if ( v == null){
           return true;
       }

       if (v instanceof List){
           return ((List) v).isEmpty();
       }

       if (v instanceof String){
           return ((String) v).isEmpty();
       }
       return false;
    }

    @Override
    public String toString() {

        if (v instanceof List){
            return ListUtil.join((List) v, ",");
        }
        return super.toString();
    }
}
