package kiun.com.bvroutine.views.dialog;

import android.app.Dialog;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

public class DialogLifecycleOwner implements LifecycleOwner {

    private final LifecycleRegistry lifecycleRegistry;

    public DialogLifecycleOwner(Dialog dialog) {
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);

        dialog.setOnCancelListener(dialog1 -> {
            onDestroy();
        });

        dialog.setOnDismissListener(dialog12 -> {
            onDestroy();
        });
    }

    public void onDestroy() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
