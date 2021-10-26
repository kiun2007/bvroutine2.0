package kiun.com.bvroutine.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.callers.CallBack;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ViewUtil;

/**
 * 按钮
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraButton extends View implements TypedView {

    @AttrBind(color = true)
    private int outCircleColor = 0xFF888888;

    @AttrBind(color = true)
    private int innerCircleColor = 0xFF00DBDB;

    @AttrBind(color = true)
    private int progressColor = 0xffff4060;

    @AttrBind
    private int duration;

    @AttrBind
    private int progressWidth = 10;

    //点击控件的时间
    long downMillus = 0;

    Handler handler = new Handler();

    private int viewWidth, viewHeight;
    private int centerX, centerY;

    private RectF rectF;

    CountDownTimer countDownTimer;

    private int outscale = 50;

    private int inscale = 30;

    private int margin = 0;

    //外环和内环的大小
    float out, in, origin;

    float progressAngle = 0;

    /**
     * 0 拍照
     * 1 录影
     * -1 初始化
     */
    private int state = 0;

    private CallBack startCall;

    private CallBack endCall;

    private CallBack takeCall;

    public CameraButton(Context context) {
        super(context);
    }

    public CameraButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public CameraButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);

        centerX = (int) (viewWidth * 0.5f);
        centerY = (int) (viewHeight * 0.5f);

        origin = (viewWidth - margin * 2) / 3;
        out = origin;
        in = (origin - inscale);

        outscale = viewWidth / 6;
        inscale = outscale / 2;

        float left = (viewWidth / 2 - out - (outscale) + progressWidth / 2);
        float top = (viewHeight / 2 - out - (outscale) + progressWidth / 2);
        float right = (viewWidth / 2 + out + (outscale) - progressWidth / 2);
        float bottom = (viewHeight / 2 + out + (outscale) - progressWidth / 2);
        rectF = new RectF(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();

        paint.setColor(outCircleColor);
        paint.setAntiAlias(true);
        canvas.drawCircle(centerX, centerY, out, paint);
        paint.reset();
        paint.setColor(innerCircleColor);
        paint.setAntiAlias(true);
        canvas.drawCircle(centerX, centerY, in, paint);

        if (state == 1) {
            paint.reset();
            paint.setColor(progressColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(progressWidth);
            paint.setAntiAlias(true);
            canvas.drawArc(rectF, -90, progressAngle, false, paint);
        }
    }

    private void startRecordAnim() {
        ValueAnimator outside_anim = ValueAnimator.ofFloat(out, out + outscale);
        ValueAnimator inside_anim = ValueAnimator.ofFloat(in, in - inscale);

        outside_anim.addUpdateListener(animation -> {
            out = (float) animation.getAnimatedValue();
            invalidate();
        });

        inside_anim.addUpdateListener(animation -> {
            in = (float) animation.getAnimatedValue();
            invalidate();
        });


        AnimatorSet set = new AnimatorSet();
        //当动画结束后启动录像Runnable并且回调录像开始接口

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                countDownTimer = new CountDownTimer(duration * 1000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        //这边+1 因为millisUntilFinished总是比整秒数少几毫秒，类似1998，2999，这样导致进度圆画不全
                        int left = (int) ((duration * 1000) - millisUntilFinished);
                        progressAngle = ((360 * 1.00f / (duration * 1000)) * (left));
                        invalidate();
                    }

                    @Override
                    public void onFinish() {
                        progressAngle = 360 * 1.0f;
                        invalidate();
                        MotionEvent upEvent = MotionEvent.obtain(downMillus, System.currentTimeMillis(), MotionEvent.ACTION_UP, centerX, centerY, 0);
                        dispatchTouchEvent(upEvent);
                    }
                };

                countDownTimer.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        set.playTogether(outside_anim, inside_anim);
        set.setDuration(200);
        set.start();
    }


    /**
     * 按钮复位
     */
    private void resetAnim() {
        Log.e(getClass().getSimpleName(), "resetAnim");
        state = -1;
        ValueAnimator outside_anim = ValueAnimator.ofFloat(out, origin);
        ValueAnimator inside_anim = ValueAnimator.ofFloat(in, (origin - inscale));
        outside_anim.addUpdateListener(animation -> {
            out = (float) animation.getAnimatedValue();
            invalidate();
        });
        inside_anim.addUpdateListener(animation -> {
            in = (float) animation.getAnimatedValue();
            invalidate();
        });
        AnimatorSet set = new AnimatorSet();
        //当动画结束后启动录像Runnable并且回调录像开始接口

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                progressAngle = 0;
                countDownTimer.cancel();
                endRecord();
                postInvalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        set.playTogether(outside_anim, inside_anim);
        set.setDuration(200);
        set.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downMillus = System.currentTimeMillis();
                handler.postDelayed(() -> {
                    state = 1;
                    startRecordAnim();
                    startRecord();
                }, 500);
                break;

            case MotionEvent.ACTION_UP:
                long downTimes = System.currentTimeMillis() - downMillus;
                if (downTimes <= 500) {
                    //短按
                    handler.removeCallbacksAndMessages(null);
                    takePic();
                } else {
                    resetAnim();
                }
                break;
        }
        return true;
    }

    private void startRecord() {
        if (startCall != null){
            startCall.call();
        }
    }

    private void endRecord() {
        if (endCall != null){
            endCall.call();
        }
    }

    private void takePic() {
        if (takeCall != null){
            takeCall.call();
        }
    }

    public void setCallBack(CallBack startCall, CallBack endCall){
        this.startCall = startCall;
        this.endCall = endCall;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setCallBack(CallBack takeCall){
        this.takeCall = takeCall;
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.CameraButton;
    }

    @Override
    public void initView() {
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }
}
