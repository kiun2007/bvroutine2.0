package com.szxgm.gusustreet.ui.activity.im;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.QueryType;
import com.szxgm.gusustreet.base.presenter.ECommVideo;
import com.szxgm.gusustreet.base.presenter.imp.ECommVideoPresenter;
import com.szxgm.gusustreet.base.VideoState;
import com.szxgm.gusustreet.databinding.ActivityRootImVideoBinding;
import com.szxgm.gusustreet.model.dto.IMList;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.net.services.GeneralListService;

import java.util.List;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine_apt.IntentValue;
import lte.trunk.tapp.sdk.media.SurfaceView;
import lte.trunk.tapp.sdk.video.VideoConstants;

/**
 * 文 件 名: IMVideo
 * 作 者: 刘春杰
 * 创建日期: 2020/6/19 19:41
 * 说明: 视频通话
 */
public class IMVideoActivity extends RequestBVActivity<ActivityRootImVideoBinding> {

    public static final Class clz = IMVideoActivity.class;

    ECommVideo eCommVideo;

    @IntentValue
    String name;

    @IntentValue
    String imUser;

    @IntentValue
    Integer type;

    @IntentValue
    boolean isCallTo = false;

    PowerManager.WakeLock lock;

    @Override
    public int getViewId() {
        return R.layout.activity_root_im_video;
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void initView() {

        binding.mineSurface.setZOrderOnTop(true);

        SurfaceView mine = binding.mineSurface;
        SurfaceView other = binding.otherSurface;
        binding.setCallType(type);

        if (type == VideoConstants.VIDEO_MONITOR && !isCallTo){
            mine = binding.otherSurface;
            other.setVisibility(View.GONE);
        }

        eCommVideo = new ECommVideoPresenter(other,  mine, this);
        eCommVideo.setCallBack(()->{
            binding.setState(VideoState.Ok);
        });
        binding.setState(isCallTo ? VideoState.ToWait : VideoState.FromWait);

        if (isCallTo){
            binding.setUserName(name);
            eCommVideo.call(imUser, type);
        }else{
            if (!eCommVideo.tryPcsIncomingCall()){
                super.finish();
                return;
            }

            GeneralListQuery query = new GeneralListQuery(IMList.class).field("userName").put("imUser", QueryType.Eq, imUser);
            rbp.addRequest(()->rbp.callServiceData(GeneralListService.class, s -> s.search(query)), this::setRemoteTitle);
        }

        PowerManager powerManager = (PowerManager)getSystemService(POWER_SERVICE);
        if (powerManager != null) {
            lock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "WakeLock");
        }
    }

    private void setRemoteTitle(List<IMList> imList){

        if (!ListUtil.isEmpty(imList)){
            IMList imUser = ListUtil.first(imList);
            binding.setUserName(imUser.getUserName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lock != null){
            lock.acquire(60*60*1000L );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (lock != null){
            lock.release();
        }
    }

    public void onSwitch(View view){
        eCommVideo.switchCamera();
    }

    public void onAccept(View view){
        eCommVideo.accept();
    }

    public void onRefused(View view){

        if (eCommVideo.isOnCall()){
            eCommVideo.terminate();
        }else{
            eCommVideo.refused();
        }
        super.finish();
    }

    public void onMute(View view){
        eCommVideo.mute();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void finish() {
        if(eCommVideo.isOnCall()){
            new AlertDialog.Builder(getContext()).setTitle("提示")
                    .setMessage("是否挂断当前通话?")
                    .setPositiveButton("挂断", ((dialog, which) -> {
                        onRefused(null);
                    })).setNegativeButton("取消", null).show();
            return;
        }
        super.finish();
    }
}