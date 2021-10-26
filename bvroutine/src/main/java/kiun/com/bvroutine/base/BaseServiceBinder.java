package kiun.com.bvroutine.base;

import android.app.Service;
import android.os.Binder;

public class BaseServiceBinder extends Binder implements ServiceBinder{

    private BaseService service;

    public BaseServiceBinder(BaseService service){
        this.service = service;
    }

    @Override
    public Service getService() {
        return service;
    }
}
