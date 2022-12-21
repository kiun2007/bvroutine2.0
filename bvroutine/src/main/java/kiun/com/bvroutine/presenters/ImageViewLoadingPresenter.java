package kiun.com.bvroutine.presenters;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.binding.ActionAnim;
import kiun.com.bvroutine.utils.ViewUtil;
import kiun.com.bvroutine.views.loadingdrawable.render.LoadingDrawable;
import kiun.com.bvroutine.views.loadingdrawable.render.circle.rotate.LevelLoadingRenderer;

/**
 * 界面反馈表达
 */
public class ImageViewLoadingPresenter implements ActionAnim {

    private Context context;
    private ImageView imageView;
    private Drawable drawable;
    private Animatable animatable;
    private Drawable lastDrawable;
    private int size;

    public ImageViewLoadingPresenter(ImageView imageView) {
        this.imageView = imageView;
        context = imageView.getContext();
        size = ViewUtil.dp2px(context, 18);
        lastDrawable = imageView.getDrawable();
    }

    @Override
    public void begin() {

        LevelLoadingRenderer.Builder builder = new LevelLoadingRenderer.Builder(context);

        int defColor = imageView.getImageTintList() == null ? 0xFF333333 : imageView.getImageTintList().getDefaultColor();
        builder.setWidth(size).setHeight(size).setLevelColor(defColor);

        LoadingDrawable loadingDrawable = new LoadingDrawable(builder.build());
        drawable = loadingDrawable;
        animatable = loadingDrawable;

        animatable.start();
        imageView.setImageDrawable(drawable);
    }

    private void reset(){
        imageView.setImageDrawable(lastDrawable);
    }

    private void stopAnim(){

        if (animatable != null){
            animatable.stop();
            animatable = null;
        }
        drawable = null;
        imageView.setEnabled(true);
    }

    public void clear(){
        stopAnim();
        imageView.setImageDrawable(null);
    }

    /**
     * 完成.
     */
    public void complete(){
        stopAnim();
        imageView.setImageDrawable(context.getDrawable(R.drawable.ic_cloud_done_black_18dp));
        imageView.postDelayed(this::reset, 2000);
    }

    /**
     * 网络反馈结果错误.
     */
    public void error(){
        stopAnim();
        imageView.setImageDrawable(context.getDrawable(R.drawable.ic_report_problem_red_18dp));
        imageView.postDelayed(this::reset, 2000);
    }

    /**
     * 本地验证错误.
     */
    public void failed(){
        stopAnim();
        imageView.setImageDrawable(context.getDrawable(R.drawable.ic_error_outline_yellow_24dp));
    }
}
