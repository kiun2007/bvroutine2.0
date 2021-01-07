package kiun.com.bvroutine.base.binding.value;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kiun.com.bvroutine.presenters.OnClickUploadPresenter;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.views.MultipleImageGrid;

public class MultipleImageGridBindConvert extends BindConvert<MultipleImageGrid, String, String> implements MultipleImageGrid.OnDataChangedLister {

    public MultipleImageGridBindConvert(MultipleImageGrid view) {
        super(view);
        view.setOnDataChangedLister(this);
    }

    @Override
    public String getValue() {
        return ListUtil.join(view.getData(), ",");
    }

    @Override
    public void setValue(String value) {
        if (!TextUtils.isEmpty(value)){
            String[] list = value.split(",");
            OnClickUploadPresenter<String> presenter = (OnClickUploadPresenter<String>) view.getUploadPresenter();
            List<String> arrayList = new ArrayList<>();
            arrayList.addAll(Arrays.asList(list));
            presenter.setList(arrayList, view.isCommitMode());
        }else{
            if (!view.isCommitMode()){
                view.getUploadPresenter().setList(new ArrayList<>(), false);
            }
        }
    }
}
