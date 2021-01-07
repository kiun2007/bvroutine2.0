package com.szxgm.gusustreet.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.szxgm.gusustreet.ui.activity.im.IMVideoActivityHandler;

import lte.trunk.tapp.sdk.video.VideoConstants;

public class AppInComingCallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if ((VideoConstants.ACTION_VIDEO_CALL_INCOMING).equals(intent.getAction())) {

			Bundle bundle = intent.getExtras();
			int videoType = bundle.getInt(VideoConstants.VIDEO_TYPE);
			Intent callIntent = IMVideoActivityHandler.openActivityIntent(context, null, null, videoType, false);

			callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			callIntent.putExtras(bundle);

			context.startActivity(callIntent);
		}
	}
}
