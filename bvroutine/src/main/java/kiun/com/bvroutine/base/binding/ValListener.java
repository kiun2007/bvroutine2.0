package kiun.com.bvroutine.base.binding;


import androidx.databinding.InverseBindingListener;

public interface ValListener<VType> {

    VType getVal();

    void setVal(VType val);

    void setListener(InverseBindingListener listener);
}