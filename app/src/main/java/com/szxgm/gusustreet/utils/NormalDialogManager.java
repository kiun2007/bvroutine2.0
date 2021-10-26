package com.szxgm.gusustreet.utils;

import android.content.Context;
import android.view.Gravity;

import kiun.com.bvroutine.views.dialog.MCDialogManager;

public class NormalDialogManager {

    public static MCDialogManager createCenter(Context context, int layoutId, Object data){
        return MCDialogManager.create(context, layoutId, data).setGravity(Gravity.CENTER).setCancelable(true).transparent();
    }
}
