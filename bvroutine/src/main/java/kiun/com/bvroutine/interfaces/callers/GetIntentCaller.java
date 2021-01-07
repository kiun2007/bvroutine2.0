package kiun.com.bvroutine.interfaces.callers;

import android.content.Intent;
import android.view.View;

@FunctionalInterface
public interface GetIntentCaller {

    Intent get(View view);
}
