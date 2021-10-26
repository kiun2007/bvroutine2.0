// Generated code from BV Routine. Do not modify!
package kiun.com.bvroutine.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import kiun.com.bvroutine.ActivityCallback;

public final class MediaBrowsingActivityHandler extends ActivityCallback {
  public static Intent openActivityIntent(Context context, String url, boolean isThumb) {
    String guid = getGuid();
    Intent intent = new Intent(context,MediaBrowsingActivity.class);
    intent.putExtra(ACTIVITY_GUID,guid);
    intent.putExtra("url",url);
    intent.putExtra("isThumb", isThumb);
    addCallBack(guid,MediaBrowsingActivityHandler::openActivityNormal_create);
    return intent;
  }

  public static void openActivityNormal(Context context, String url, boolean isThumb) {
    Intent intent = openActivityIntent(context, url, isThumb);
    context.startActivity(intent);
  }

  public static void openActivityNormal(Context context, String url) {
    openActivityNormal(context, url, true);
  }

  private static void openActivityNormal_create(Activity activity) {
    if(activity instanceof MediaBrowsingActivity){
      MediaBrowsingActivity sActivity = (MediaBrowsingActivity)activity;
      Intent intent = sActivity.getIntent();
      sActivity.url = intent.getStringExtra("url");
      sActivity.isThumb = intent.getBooleanExtra("isThumb", false);
      }
  }
}
