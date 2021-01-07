package kiun.com.bvroutine.utils.file;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import kiun.com.bvroutine.ActivityApplication;
import kiun.com.bvroutine.R;

public class MimeTypeUtil {

    public static String getType(Context context, String fileName){

        if (context == null){
            context = ActivityApplication.getApplication();
        }

        if (TextUtils.isEmpty(fileName)){
            return null;
        }

        fileName = fileName.toLowerCase();
        XmlResourceParser parser = context.getResources().getXml(R.xml.mime);
        try {
            parser.next();
            parser.next();

            parser.nextTag();
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){

                if(eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equals("type")){
                        if (fileName.endsWith(parser.getAttributeValue(0))){
                            return parser.getAttributeValue(1);
                        }
                    }
                }
                eventType = parser.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return "*/*";
    }
}
