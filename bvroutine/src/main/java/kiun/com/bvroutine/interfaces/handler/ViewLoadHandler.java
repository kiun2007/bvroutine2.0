package kiun.com.bvroutine.interfaces.handler;

import android.content.Context;

public interface ViewLoadHandler {

    void setContext(Context context);

    boolean isInitStart();

    void start();
}
