package kiun.com.bvroutine.views.text;

import android.content.Context;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.BindingAdapter;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.text.Html;
import kiun.com.bvroutine.utils.ViewUtil;

import static kiun.com.bvroutine.text.Html.*;

public class HtmlTextView extends AppCompatTextView implements TypedView {

    @AttrBind
    private int html = -1;

    private String htmlText;

    @AttrBind
    private String nullText;

    @AttrBind
    private String[] titleArray;

    @AttrBind
    private boolean initShow = true;

    private Object[] args = null;

    private HtmlTextImageGetter htmlTextImageGetter;

    public HtmlTextView(Context context) {
        super(context);
        htmlTextImageGetter = new HtmlTextImageGetter(context, 16, 20);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        htmlTextImageGetter = new HtmlTextImageGetter(context, 16, 20);
        ViewUtil.initTyped(this, attrs);
    }

    public HtmlTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        htmlTextImageGetter = new HtmlTextImageGetter(context, 16, 20);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.HtmlTextView;
    }

    @Override
    public void initView() {
        if (initShow){
            show();
        }
    }

    public void setHtmlText(String text){
        Spanned spanned = Html.fromHtml(text, null, new HtmlTag());
        setText(spanned);
    }

    private void show(){

        if (html != -1){
            String value = null;
            if (args != null){
                value = getResources().getString(html, args);
            }else{
                value = getResources().getString(html);
                if (value.matches("%")){
                    value = null;
                }
            }

            if (value == null){
                return;
            }

            Spanned spanned = Html.fromHtml(value, TO_HTML_PARAGRAPH_LINES_CONSECUTIVE, htmlTextImageGetter, new HtmlTag());
            setText(spanned);
        }
    }

    public void setArgs(Object[] args) {
        this.args = args;
        if (args != null){
            for (int i = 0; i < args.length; i++) {

                if (this.args[i] instanceof ArrayIndex && titleArray != null){
                    ArrayIndex arrayIndex = (ArrayIndex) this.args[i];
                    this.args[i] = arrayIndex.getValue(titleArray);
                }

                if (this.args[i] == null && nullText != null){
                    this.args[i] = nullText;
                }
            }
            show();
        }
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }

    @BindingAdapter("html")
    public static void setHtml(HtmlTextView textView, String htmlText){
        textView.setHtmlText(htmlText);
    }
}
