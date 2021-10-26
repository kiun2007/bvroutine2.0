package kiun.com.bvroutine.views.text;

import android.content.Context;
import android.graphics.drawable.Drawable;

import kiun.com.bvroutine.text.Html;

public class HtmlTextImageGetter implements Html.ImageGetter {

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
            int id = -1;

            if (source.startsWith("@")){
                String[] names = source.split("/");
                if (names.length == 2){
                    id = context.getResources().getIdentifier(names[1], names[0].substring(1), context.getPackageName());
                }
            }else{
                id = Integer.parseInt(source);
            }

            if (id == -1) return null;

            Drawable d = context.getResources().getDrawable(id);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            return d;
        }catch (NumberFormatException ex){
        }
        return null;
    }
}
