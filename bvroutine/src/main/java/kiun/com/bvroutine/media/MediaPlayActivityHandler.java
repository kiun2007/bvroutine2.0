package kiun.com.bvroutine.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import kiun.com.bvroutine.ActivityCallback;

public final class MediaPlayActivityHandler extends ActivityCallback {
  public static Intent openActivityIntent(Context context, String url) {
    String guid = getGuid();
    Intent intent = new Intent(context,MediaPlayActivity.class);
    intent.putExtra(ACTIVITY_GUID,guid);
    intent.putExtra("url",url);
    addCallBack(guid,MediaPlayActivityHandler::openActivityNormal_create);
    return intent;
  }

  public static void openActivityNormal(Context context, String url) {
    Intent intent = openActivityIntent(context,url);
    context.startActivity(intent);
  }

  private static void openActivityNormal_create(Activity activity) {
    if(activity instanceof MediaPlayActivity){
      MediaPlayActivity sActivity = (MediaPlayActivity)activity;
      Intent intent = sActivity.getIntent();
      sActivity.url = intent.getStringExtra("url");
      }
  }
}
