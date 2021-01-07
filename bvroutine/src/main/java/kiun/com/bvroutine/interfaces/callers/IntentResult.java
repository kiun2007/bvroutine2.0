package kiun.com.bvroutine.interfaces.callers;

import android.content.Intent;

@FunctionalInterface
public interface IntentResult {

    void callBack(Intent intent);
}
