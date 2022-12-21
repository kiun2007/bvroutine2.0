package kiun.com.bvroutine.presenters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.databinding.InverseBindingListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.base.binding.DoublePartCaller;
import kiun.com.bvroutine.base.binding.PartCaller;
import kiun.com.bvroutine.handlers.GridHandler;
import kiun.com.bvroutine.interfaces.presenter.GridPresenter;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.presenter.UploadPresenter;
import kiun.com.bvroutine.media.Recorder;
import kiun.com.bvroutine.media.VideoRecorderActivityHandler;
import kiun.com.bvroutine.utils.AgileThread;
import kiun.com.bvroutine.utils.attri.ThemeUtil;
import kiun.com.bvroutine.utils.file.MediaPackage;
import kiun.com.bvroutine.views.dialog.MCDialogManager;
import kiun.com.bvroutine.views.popup.PopupManager;
import retrofit2.Call;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static kiun.com.bvroutine.media.av.BaseRecorder.getRecorder;

public class OnClickUploadPresenter<T> extends GridHandler<T> implements UploadPresenter<T>, View.OnClickListener {

    private RequestBVActivity activity;
    private PartCaller netCall;
    private DoublePartCaller thumbNetCall;
    private InverseBindingListener listener;
    private boolean isMultiple;
    private int limit;
    private Recorder audioRecorder;
    private String videoRecorderClass;
    private String filePartName = "file", thumbPartName = "thumb";
    private PopupManager popupManager;
    private Integer maxAudioDuration = -1, maxVideoDuration = -1;
    private Integer audioLayoutId;
    private Integer videoLayoutId;
    protected List<T> listArray = new ArrayList<>();

    private List<Uri> cacheUploadFileList = new LinkedList<>();
    private MediaPackage mediaPackage;

    private float compress = 1.0f;

    private String mediaType = "image/*";

    private Context context;

    private boolean uploadReviewIsSrc = false;

    //后上传模式.
    private boolean afterModel = false;

    public OnClickUploadPresenter(int limit, Context context){
        super(BR.handler, null);
        this.isMultiple = (limit > 1);
        this.limit = limit;
        this.context = context;

        TypedValue typedValue = ThemeUtil.getValue(context, R.attr.uploadReviewIsSrc);
        uploadReviewIsSrc = typedValue.data != 0;
    }

    public void setFilePartName(String filePartName) {
        this.filePartName = filePartName;
    }

    public void setThumbPartName(String thumbPartName) {
        this.thumbPartName = thumbPartName;
    }

    public void setCompress(float compress) {
        this.compress = compress;
    }

    public void addData(T data){

        int size = listArray.size();
        listArray.add((size > 0 ? size - 1 : 0), data);
        if (listArray.size() > limit){
            listArray.remove(listArray.size() - 1);
        }
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setRecorder(String clzName){
        audioRecorder = getRecorder(activity, clzName, maxAudioDuration);
    }

    public void setAfterModel(boolean afterModel) {
        this.afterModel = afterModel;
    }

    public void setAudioLayoutId(Integer audioLayoutId) {
        this.audioLayoutId = audioLayoutId;
    }

    public void setVideoLayoutId(Integer videoLayoutId) {
        this.videoLayoutId = videoLayoutId;
    }

    public void setVideoRecorderClass(String videoRecorderClass) {
        if (!"null".equals(videoRecorderClass)){
            this.videoRecorderClass = videoRecorderClass;
        }
    }

    public void setMaxAudioDuration(Integer maxAudioDuration) {
        this.maxAudioDuration = maxAudioDuration;
    }

    public void setMaxVideoDuration(Integer maxVideoDuration) {
        this.maxVideoDuration = maxVideoDuration;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public void setList(List<T> items, boolean isEdit){

        if (isMultiple){
            listArray = items;
            if(isEdit && items.size() < limit){
                listArray.add(null);
            }
            presenter.setList(listArray);
        }
    }

    public void setList(List<T> items){
        setList(items, false);
    }

    @Override
    public void removeAt(int index) {

        if (afterModel){
            cacheUploadFileList.remove(index);
        }else{
            super.removeAt(index);
            if (listArray.size() < limit){

                if (listArray.isEmpty() || listArray.get(listArray.size() - 1) != null){
                    presenter.add(null);
                }
            }

            if (listener != null){
                listener.onChange();
            }
        }
    }

    protected void replace(T old, T newData){

        int oldIndex = listArray.indexOf(old);
        listArray.remove(oldIndex);
        listArray.add(oldIndex, newData);
    }

    private void startAnim(View view){

        if (view instanceof ImageView){
            ImageView imageView = ((ImageView) view);
            imageView.setImageResource(R.drawable.bg_anim_upload);

            AnimationDrawable animation = (AnimationDrawable)((ImageView) view).getDrawable();
            animation.start();
            view.setEnabled(false);
        }
    }

    private void stopAnim(View view){

        if (view instanceof ImageView){
            AnimationDrawable animation = (AnimationDrawable)((ImageView) view).getDrawable();
            if (animation != null){
                animation.stop();
            }
            ((ImageView) view).setImageDrawable(null);
            view.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        onClick(v, null, 0);
    }

    @Override
    public void init(RequestBVActivity activity, InverseBindingListener listener) {
        this.activity = activity;
        this.listener = listener;
        this.mediaPackage = new MediaPackage(activity, filePartName, thumbPartName);
        mediaPackage.setCompress(compress);
    }

    @Override
    public void setNetCall(PartCaller caller) {
        netCall = caller;
    }

    @Override
    public void setNetCall(DoublePartCaller caller) {
        thumbNetCall = caller;

        assert mediaPackage != null;
        mediaPackage.setThumbEnable(true);
    }

    @Override
    public List<T> getData() {
        return listArray;
    }

    @Override
    public void onClick(Context context, int tag, T data) {
        if (tag == 1 && data instanceof String){
            MCDialogManager.create(context, R.layout.dialog_show_image, data, BR.url, BR.dialog).setCancelable(true).setGravity(Gravity.CENTER).show();
        }
    }

    private class ActivityCallBack{

        private View v;
        private T item;
        private int index;
        private Uri uri;

        public ActivityCallBack(View v, T item, int index) {
            this.v = v;
            this.item = item;
            this.index = index;
        }

        public ActivityCallBack setData(Uri uri){
            this.uri = uri;
            return this;
        }

        public void onResult(Intent intent){

            Uri uri = (intent == null || intent.getData() == null) ? this.uri : intent.getData();
            if (afterModel){
                cacheUploadFileList.add(uri);
            }else{
                uploadUri(uri, v, item, index);
            }
        }
    }

    @Override
    public void onClick(View v, T item, int index) {

        if (netCall == null && thumbNetCall == null){
            Toast.makeText(v.getContext(), "未设置上传服务接口", Toast.LENGTH_LONG).show();
            return;
        }

        if (!isEditMode()){
            MCDialogManager.create(v.getContext(), R.layout.dialog_show_image, item, BR.url, BR.dialog).setCancelable(true).setGravity(Gravity.CENTER).show();
            return;
        }

        ActivityCallBack activityCallBack = new ActivityCallBack(v, item, index);

        popupManager = new PopupManager(v, R.menu.media_menu);
        popupManager.addItem(R.id.camera, ()->{

            activity.startPermission(()->{
                Uri uri = mediaPackage.newUri("jpg");
                Intent data = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                data.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                activity.startForResult(data, activityCallBack.setData(uri)::onResult);

            }, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA);
        }).addItem(R.id.album, ()->{
            Intent data = new Intent(Intent.ACTION_PICK).setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mediaType);
            activity.startForResult(data, activityCallBack::onResult);
        });

        if (audioRecorder != null){

            popupManager.addItem(R.id.audio, ()->{

                MCDialogManager dialogManager = MCDialogManager.create(activity, audioLayoutId == null ? R.layout.dialog_record : audioLayoutId, audioRecorder, BR.recorder, BR.dialog);
                dialogManager.setGravity(Gravity.CENTER).transparent().show();

                audioRecorder.setCallBack(intent->{
                    uploadUri(intent.getData(), v, item, index);
                });
            });
        }

        if (videoRecorderClass != null){
            popupManager.addItem(R.id.video, ()->{
                Intent data = VideoRecorderActivityHandler.openActivityIntent(activity, videoRecorderClass, maxVideoDuration, videoLayoutId);
                activity.startForResult(data, activityCallBack.setData(data.getData())::onResult);
            });
        }

        popupManager.show();
    }

    /**
     * 上传文件.
     * @param uri
     * @param v
     * @param item
     */
    private void uploadUri(Uri uri, View v, T item, int index){

        Drawable drawable = v.getBackground();
        new AgileThread(activity, (thread)->{

            mediaPackage.packageFromUri(v, uri);
            Call networkCall = null;

            if (thumbNetCall != null){
                networkCall = thumbNetCall.get(mediaPackage.getFile(), mediaPackage.getThumb());
            }else if (netCall != null){
                networkCall = (Call) netCall.call(mediaPackage.getFile());
            }else{
                thread.into(()->stopAnim(v));
            }

            if (mediaPackage.getBitmap() != null){
                //显示图像.
                thread.into(()->{
                    if (uploadReviewIsSrc && v instanceof ImageView){
                        ImageView imageView = (ImageView) v;
                        imageView.setImageDrawable(new BitmapDrawable(v.getResources(), mediaPackage.getBitmap()));
                    }else{
                        v.setBackground(new BitmapDrawable(v.getResources(), mediaPackage.getBitmap()));
                    }
                });
            }

            if (networkCall != null){

                Call exeCall = networkCall;
                thread.into(()->{

                    startAnim(v);
                    RequestBindingPresenter p = activity.getRequestPresenter();
                    p.addRequest(()->p.execute(exeCall), (value)->{

                        stopAnim(v);
                        if (value != null && value.isSuccess()){

                            if (item != null){
                                replace(item, (T) value.getData());
                            }else{
                                addData((T) value.getData());
                            }

                            if (listener != null){
                                listener.onChange();
                            }
                        }else{
                            Toast.makeText(activity, value == null ? "网络错误,文件提交失败" : value.getMsg(), Toast.LENGTH_LONG).show();
                            v.setBackground(drawable);
                        }
                    }, Throwable::printStackTrace);
                });
            }
        }).start();
    }

    public boolean isThumb(){
        return thumbNetCall != null;
    }

    public String getItem(String item){
        return item == null ? null : (isThumb() ? String.format("%s.thumb.jpg", item) : item);
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public void setPresenter(GridPresenter presenter) {
        super.setPresenter(presenter);
        if (isMultiple){
            presenter.setList((List) getData());
        }
    }
}
