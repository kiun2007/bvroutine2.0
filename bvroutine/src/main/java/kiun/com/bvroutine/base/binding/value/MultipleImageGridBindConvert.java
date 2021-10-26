package kiun.com.bvroutine.base.binding.value;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.presenters.OnClickUploadPresenter;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.views.MultipleImageGrid;

public class MultipleImageGridBindConvert extends BindConvert<MultipleImageGrid, String, String> implements MultipleImageGrid.OnDataChangedLister {

    private String splitChar = ",";
    private String joinChar = ",";

    public MultipleImageGridBindConvert(MultipleImageGrid view) {
        super(view);
        view.setOnDataChangedLister(this);
        Object tag = view.getTag(R.id.tagExtra);

        if (tag instanceof String){
            joinChar = (String) tag;
            splitChar = (String) tag;
        }else if (tag instanceof List){
            List<String> tags = (List<String>) tag;
            if (tags.size() >= 2){
                joinChar = tags.get(0);
                splitChar = tags.get(1);
            }
        }
    }

    @Override
    public String getValue() {
        return ListUtil.join(view.getData(), joinChar);
    }

    @Override
    public void setValue(String value) {
        if (!TextUtils.isEmpty(value)){
            String[] list = value.split(splitChar);
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
