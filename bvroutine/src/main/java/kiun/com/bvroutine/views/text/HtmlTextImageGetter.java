package kiun.com.bvroutine.views.text;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;

public class HtmlTextImageGetter implements Html.ImageGetter{

    private Context context;
    float size;
    int offsetTop = 0;

    public HtmlTextImageGetter(Context context, float size, int lineHeight) {
        this.context = context;
        this.size = size;
        offsetTop = (int) ((size - lineHeight) / 2);
        offsetTop = 15;
    }

    public HtmlTextImageGetter setSize(float size){
        this.size = size;
        return this;
    }

    @Override
    public Drawable getDrawable(String source) {

        try {
            int id = Integer.parseInt(source);
            Drawable d = context.getResources().getDrawable(id);

            d.setBounds(0, offsetTop, (int) size, (int) size + offsetTop);
            return d;
        }catch (NumberFormatException ex){
        }
        return null;
    }
}
