package kiun.com.bvroutine.presenters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.binding.ActionAnim;
import kiun.com.bvroutine.utils.ViewUtil;
import kiun.com.bvroutine.views.loadingdrawable.render.LoadingDrawable;
import kiun.com.bvroutine.views.loadingdrawable.render.circle.rotate.LevelLoadingRenderer;
import kiun.com.bvroutine.views.text.CenterImageSpan;

public class TextViewLoadingPresenter implements ActionAnim {

    private Context context;
    private TextView textView;
    private Drawable drawable;
    private Animatable animatable;
    private String sourceText;
    private int size;
    private int gravity = Gravity.RIGHT;
    private Drawable[] lastDrawables;

    public TextViewLoadingPresenter(@NonNull TextView view){
        this(view, (Integer) view.getTag(R.id.tagIconGravity));
    }

    public TextViewLoadingPresenter(@NonNull TextView view, Integer gravity){

        textView = view;
        if (gravity != null){
            this.gravity = gravity;
        }

        context = view.getContext();
        sourceText = view.getText().toString();
        size = ViewUtil.dp2px(context, 18);

        if (textView.getTag(R.id.tagLastDrawables) instanceof Drawable[]){
            lastDrawables = (Drawable[]) textView.getTag(R.id.tagLastDrawables);
        } else {
            lastDrawables = textView.getCompoundDrawables();
            textView.setTag(R.id.tagLastDrawables, lastDrawables);
        }
    }

    public void begin(){

        LevelLoadingRenderer.Builder builder = new LevelLoadingRenderer.Builder(textView.getContext());

        int defColor = textView.getCurrentTextColor();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (textView.getCompoundDrawableTintList() != null){
                defColor = textView.getCompoundDrawableTintList().getDefaultColor();
            }
        }

        builder.setWidth(size).setHeight(size).setLevelColor(defColor);

        LoadingDrawable loadingDrawable = new LoadingDrawable(builder.build());
        drawable = loadingDrawable;
        animatable = loadingDrawable;

        animatable.start();
        setDrawable(drawable);
    }

    @SuppressLint("DefaultLocale")
    public Spanned drawSpanned(int resId, String text, boolean isBegin){

        SpannableStringBuilder builder = new SpannableStringBuilder();

        if (isBegin){
            builder.append(" ");
            builder.append(text);
            builder.setSpan(new CenterImageSpan(context, resId), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }else{
            builder.append(text);
            builder.append(" ");
            builder.setSpan(new CenterImageSpan(context, resId), builder.length() - 1, builder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return builder;
    }

    private void setDrawable(Drawable drawable){

        if (drawable != null){
            if ((textView.getGravity() & Gravity.LEFT) == Gravity.LEFT){
                drawable.setBounds(0,0, size, size);
            }else if ((textView.getGravity() & Gravity.CENTER) == Gravity.CENTER){
                float textSize = textView.getPaint().measureText(sourceText);
                int offset = (int) (textSize - textView.getWidth() - textView.getPaddingLeft() - textView.getPaddingRight()) / 2;
                offset += size/2;
                drawable.setBounds(offset,0,size + offset, size);
            }else if ((textView.getGravity() & Gravity.RIGHT) == Gravity.RIGHT){
                drawable.setBounds(0,0, size, size);
            }
        }

        if (gravity == Gravity.LEFT){
            textView.setCompoundDrawables(drawable, lastDrawables[1], lastDrawables[2], lastDrawables[3]);
        }else if (gravity == Gravity.RIGHT){
            textView.setCompoundDrawables(lastDrawables[0],lastDrawables[1], drawable, lastDrawables[3]);
        }else if (gravity == Gravity.TOP){
            textView.setCompoundDrawables(lastDrawables[0], drawable, lastDrawables[2], lastDrawables[3]);
        }else if (gravity == Gravity.BOTTOM){
            textView.setCompoundDrawables(lastDrawables[0],lastDrawables[1], lastDrawables[2], drawable);
        }
    }

    private void setDrawable(int resId){
        setDrawable(context.getDrawable(resId));
    }

    private void reset(){
        textView.setCompoundDrawables(lastDrawables[0],lastDrawables[1], lastDrawables[2], lastDrawables[3]);
    }

    private void stopAnim(){

        if (animatable != null){
            animatable.stop();
            animatable = null;
        }
        drawable = null;
        textView.setEnabled(true);
    }

    /**
     * 清除图标.
     */
    public void clear(){
        stopAnim();
        setDrawable(null);
    }

    /**
     * 完成.
     */
    public void complete(){
        stopAnim();
        setDrawable(R.drawable.ic_cloud_done_black_18dp);
        textView.postDelayed(this::reset, 2000);
    }

    /**
     * 网络反馈结果错误.
     */
    public void error(){
        stopAnim();
        setDrawable(R.drawable.ic_report_problem_red_18dp);
        textView.postDelayed(this::reset, 2000);
    }

    /**
     * 本地验证错误.
     */
    public void failed(){
        stopAnim();
        setDrawable(R.drawable.ic_error_outline_yellow_24dp);
    }
}
