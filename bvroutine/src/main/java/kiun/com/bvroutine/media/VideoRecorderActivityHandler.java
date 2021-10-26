// Generated code from BV Routine. Do not modify!
package kiun.com.bvroutine.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import kiun.com.bvroutine.ActivityCallback;

public final class VideoRecorderActivityHandler extends ActivityCallback {
  public static Intent openActivityIntent(Context context, String className, Integer maxDuration,
      Integer layoutId) {
    String guid = getGuid();
    Intent intent = new Intent(context,VideoRecorderActivity.class);
    intent.putExtra(ACTIVITY_GUID,guid);
    intent.putExtra("className",className);
    intent.putExtra("maxDuration",maxDuration);
    intent.putExtra("layoutId",layoutId);
    addCallBack(guid,VideoRecorderActivityHandler::openActivityNormal_create);
    return intent;
  }

  public static void openActivityNormal(Context context, String className, Integer maxDuration,
      Integer layoutId) {
    Intent intent = openActivityIntent(context,className,maxDuration,layoutId);
    context.startActivity(intent);
  }

  private static void openActivityNormal_create(Activity activity) {
    if(activity instanceof VideoRecorderActivity){
      VideoRecorderActivity sActivity = (VideoRecorderActivity)activity;
      Intent intent = sActivity.getIntent();
      sActivity.className = intent.getStringExtra("className");
      sActivity.maxDuration = intent.getIntExtra("maxDuration", 0);
      sActivity.layoutId = intent.getIntExtra("layoutId", 0);
      }
  }
}
