package kiun.com.bvroutine.utils;


import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kiun.com.bvroutine.data.ExtraValue;
import kiun.com.bvroutine.interfaces.callers.CallBack;

public class PermissionUtil {

    private final Activity activity;
    Map<Integer, ExtraValue<String[], CallBack, CallBack>> permissionCallBacks = new HashMap<>();

    public PermissionUtil(Activity activity) {
        this.activity = activity;
    }

    public void startPermission(String[] permission, CallBack okCallBack, CallBack refuseCall){

        boolean isPass = true;
        List<String> newPermission = new ArrayList<>();

        for (int i = 0; i < permission.length; i++) {
            if (ContextCompat.checkSelfPermission(activity, permission[i]) != PackageManager.PERMISSION_GRANTED){
                isPass = false;
                newPermission.add(permission[i]);
            }
        }

        if (isPass){
            okCallBack.call();
            return;
        }

        permission = new String[newPermission.size()];
        //还未通过的权限.
        permission = newPermission.toArray(permission);

        ExtraValue<String[], CallBack, CallBack> keyValue = new ExtraValue<>(permission, okCallBack, refuseCall);
        int hashCode = keyValue.hashCode() & 0xFFFF;
        permissionCallBacks.put(hashCode, keyValue);
        ActivityCompat.requestPermissions(activity, permission, hashCode);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        ExtraValue<String[], CallBack, CallBack> callbackKeyValue = permissionCallBacks.get(requestCode);

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(activity, String.format("%s权限被拒绝, 功能受到限制", permissions[i]), Toast.LENGTH_LONG).show();
                if (callbackKeyValue.extra() != null){
                    callbackKeyValue.extra().call();
                }
                return;
            }
        }

        if (callbackKeyValue.value() != null){
            callbackKeyValue.value().call();
        }
    }
}
