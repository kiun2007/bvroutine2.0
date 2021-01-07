package kiun.com.bvroutine.interfaces.view;

import kiun.com.bvroutine.base.binding.DoublePartCaller;
import kiun.com.bvroutine.base.binding.PartCaller;
import kiun.com.bvroutine.base.binding.RequestBodyCaller;

public interface UploadView {

    void setService(PartCaller call);

    void setThumbService(DoublePartCaller caller);
}
