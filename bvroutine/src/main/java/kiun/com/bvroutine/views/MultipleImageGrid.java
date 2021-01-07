package kiun.com.bvroutine.views;

import android.content.Context;
import androidx.databinding.BindingAdapter;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import java.util.List;
import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.base.binding.DoublePartCaller;
import kiun.com.bvroutine.base.binding.PartCaller;
import kiun.com.bvroutine.interfaces.presenter.GridPresenter;
import kiun.com.bvroutine.interfaces.view.BaseView;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.interfaces.view.UploadView;
import kiun.com.bvroutine.media.av.MediaAACRecorder;
import kiun.com.bvroutine.media.av.MediaMp4Recorder;
import kiun.com.bvroutine.presenters.GridViewPresenter;
import kiun.com.bvroutine.presenters.OnClickUploadPresenter;
import kiun.com.bvroutine.utils.ViewUtil;

/**
 * 文 件 名: MultipleImageGrid
 * 作 者: 刘春杰
 * 创建日期: 2020/7/7 23:20
 * 说明: 多个图片上传控件
 */
public class MultipleImageGrid extends GridView implements TypedView, UploadView, BaseView{

    public interface OnDataChangedLister{
        void onChanged();
    }

    @AttrBind
    RequestBVActivity activity;

    @AttrBind(def = 9)
    private int imageLimit;

    @AttrBind
    private float itemScaleHeight = 1.0f;

    @AttrBind(boolDef = true)
    private boolean commitMode = true;

    @AttrBind
    private String audioRecorder = MediaAACRecorder.class.getName();

    @AttrBind
    private String videoRecorder = MediaMp4Recorder.class.getName();

    @AttrBind
    private String filePartName = "file";

    @AttrBind
    private String thumbPartName = "thumb";

    @AttrBind(def = -1)
    private int maxVideoDuration;

    @AttrBind(def = -1)
    private int maxAudioDuration;

    @AttrBind(def = 0)
    private float videoQuality;

    @AttrBind(def = -1)
    private int videoLayoutId = -1;

    @AttrBind(def = -1)
    private int audioLayoutId = -1;

    @AttrBind
    private boolean afterMode = false;

    @AttrBind
    private int gridItemLayout = R.layout.item_uplaod_view;

    GridPresenter<String> gridPresenter;

    private OnDataChangedLister onDataChangedLister;

    private OnClickUploadPresenter<List<?>> uploadPresenter = new OnClickUploadPresenter<>(9);

    public MultipleImageGrid(Context context) {
        super(context);
    }

    public MultipleImageGrid(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public MultipleImageGrid(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.MultipleImageGrid;
    }

    @Override
    public void initView() {

        uploadPresenter.setFilePartName(filePartName);
        uploadPresenter.setThumbPartName(thumbPartName);
        uploadPresenter.setAfterModel(afterMode);
        uploadPresenter.init(activity, ()->{
            gridPresenter.notifySet();
            layoutViews();
            if (onDataChangedLister != null){
                onDataChangedLister.onChanged();
            }
        });

        if (!isInEditMode()){
            if (videoLayoutId != -1){
                uploadPresenter.setVideoLayoutId(videoLayoutId);
            }

            if (audioLayoutId != -1){
                uploadPresenter.setAudioLayoutId(audioLayoutId);
            }

            uploadPresenter.setMaxVideoDuration(maxVideoDuration);
            uploadPresenter.setMaxAudioDuration(maxAudioDuration);
            uploadPresenter.setRecorder(audioRecorder);
            uploadPresenter.setVideoRecorderClass(videoRecorder);

            uploadPresenter.setScaleHeight(itemScaleHeight);
            uploadPresenter.setLimit(imageLimit);
            uploadPresenter.setEditMode(commitMode);
        }

        gridPresenter = new GridViewPresenter(this, this, gridItemLayout, BR.item, uploadPresenter);
        gridPresenter.setOnChanged(this::layoutViews);
        if (isInEditMode()){
        }else{
            gridPresenter.add(null);
        }
    }

    private void layoutViews(){

        int height = (int) (getColumnWidth() * itemScaleHeight);
        int columns = getNumColumns();
        int rows = (int) Math.ceil((float)gridPresenter.allList().size() / columns);

        ViewGroup.LayoutParams parentLayout = getLayoutParams();
        if (parentLayout != null){
            parentLayout.height = getPaddingTop() + getPaddingBottom() + (rows * height) + (rows - 1) * getVerticalSpacing();
            setLayoutParams(parentLayout);
        }
    }

    @Override
    public void onViewAdded(View child) {

        int height = (int) (getColumnWidth() * itemScaleHeight);
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        layoutParams.height = height;
        child.setLayoutParams(layoutParams);
        super.onViewAdded(child);
        layoutViews();
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return getRootView().findViewById(id);
    }

    @Override
    public void setService(PartCaller call) {
        uploadPresenter.setNetCall(call);
    }

    @Override
    public void setThumbService(DoublePartCaller caller) {
        uploadPresenter.setNetCall(caller);
    }

    @BindingAdapter("commitMode")
    public static void setCommitMode(MultipleImageGrid grid, boolean commitMode){
        grid.commitMode = commitMode;
        grid.uploadPresenter.setEditMode(commitMode);
    }

    public List<?> getData(){
        return uploadPresenter.getData();
    }

    public OnClickUploadPresenter<?> getUploadPresenter() {
        return uploadPresenter;
    }

    public boolean isCommitMode() {
        return commitMode;
    }

    public void setOnDataChangedLister(OnDataChangedLister onDataChangedLister) {
        this.onDataChangedLister = onDataChangedLister;
    }
}