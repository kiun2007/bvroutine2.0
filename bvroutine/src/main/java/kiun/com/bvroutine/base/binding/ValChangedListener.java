package kiun.com.bvroutine.base.binding;

import kiun.com.bvroutine.interfaces.callers.ObjectSetCaller;

public interface ValChangedListener extends ValListener{

    void setListener(ObjectSetCaller caller);
}
