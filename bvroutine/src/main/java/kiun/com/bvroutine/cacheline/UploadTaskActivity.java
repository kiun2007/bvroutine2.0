package kiun.com.bvroutine.cacheline;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import kiun.com.bvroutine.ActivityApplication;
import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.cacheline.data.beans.SettingUnit;
import kiun.com.bvroutine.cacheline.data.beans.UploadObject;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.databinding.ActivityUploadObjectListBinding;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.presenters.list.NetListProvider;
import kiun.com.bvroutine.utils.AlertUtil;
import kiun.com.bvroutine.utils.ListUtil;

public class UploadTaskActivity extends RequestBVActivity<ActivityUploadObjectListBinding> {

    ListHandler<UploadObject> listHandler = new ListHandler<UploadObject>(BR.handler, R.layout.list_error_normal_base, "获取失败", "数据已全部同步"){

        @Override
        public void onClick(Context context, int tag, UploadObject data) {

            if (CacheSettings.isOffline()){
                Toast.makeText(context, "请在网络通畅的环境下使用，并且关闭强制离线", Toast.LENGTH_LONG).show();
                return;
            }

            AlertUtil.build(context, "是否开始同步数据到服务器?")
                    .setPositiveButton("是", (v, d)->{
                        if (tag == 1 && !data.isUploading()){
                            Toast.makeText(ActivityApplication.getApplication().getTop(), "开始同步请留意结果", Toast.LENGTH_LONG).show();
                            data.startUpload(getRequestPresenter());
                        }else if (tag == 0){
                            try {
                                Toast.makeText(ActivityApplication.getApplication().getTop(), "开始同步请留意结果", Toast.LENGTH_LONG).show();
                                ListUtil.map((List<UploadObject>) binding.getProvider().getPresenter().list(), item-> item.startUpload(getRequestPresenter()));
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton("否", null)
                    .create()
                    .show();
        }
    };

    @Override
    public int getViewId() {
        return R.layout.activity_upload_object_list;
    }

    @Override
    public void initView() {
        binding.setProvider(NetListProvider
                .create(this, listHandler, R.layout.item_uplaod_object)
                .setCaller(this::getUploadObject)
                .complete(this::onComplete)
        );
        binding.setHandler(listHandler);
    }

    private void onComplete(ListViewPresenter listViewPresenter) {
        getBarItem().setRightTitle(listViewPresenter.count() <= 0 ? "" : "提交全部");
    }

    private List<UploadObject> getUploadObject(PagerBean pagerBean) throws Exception{
        List<UploadObject> countList = CacheSettings.readUpload(false);
        pagerBean.setTotal(countList.size());
        return ListUtil.page(countList, pagerBean);
    }
}
