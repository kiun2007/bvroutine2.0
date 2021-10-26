package kiun.com.bvroutine.views.word;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tencent.smtt.sdk.TbsReaderView;
import java.io.File;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.databinding.LayoutLoadingBaseBinding;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.FileUtil;
import kiun.com.bvroutine.utils.ViewUtil;

public class TbsView extends FrameLayout implements TypedView, TbsReaderView.ReaderCallback{

    private String tbsReaderTemp = Environment.getExternalStorageDirectory() + "/TbsReaderTemp";
    private TbsReaderView mTbsReaderView;
    private LayoutLoadingBaseBinding binding;

    public TbsView(@NonNull Context context) {
        super(context);
    }

    public TbsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public TbsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.TbsView;
    }

    @Override
    public void initView() {

        tbsReaderTemp = getContext().getFilesDir().getAbsolutePath()+ "/TbsReaderTemp";

        File bsReaderTempFile = new File(tbsReaderTemp);
        if (!bsReaderTempFile.exists() && !bsReaderTempFile.mkdir()) {
            Log.e("print","创建/TbsReaderTemp失败！！！！！");
        }
        mTbsReaderView = new TbsReaderView(getContext(), this);
        addView(mTbsReaderView, new ViewGroup.LayoutParams(-1,-1));
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_loading_base, this, true);
        binding.webView.loadUrl("https://www.baidu.com");
    }

    public void displayFile(File file){

        showMessage("正在打开文件", false);
        String type = FileUtil.getFileTail(file.getName());

        boolean result = mTbsReaderView.preOpen(type, false);
        if (result) {
            Bundle bundle = new Bundle();
            bundle.putString("filePath", file.getAbsolutePath());
            bundle.putString("tempPath", tbsReaderTemp);
            mTbsReaderView.openFile(bundle);
            binding.getRoot().setVisibility(GONE);
        }else{
            showMessage("文件打开失败,不支持该格式!", true);
        }
    }

    public void showMessage(String msg, boolean isError){
        if (binding != null){
            binding.setError(isError);
            binding.setMessage(msg);
        }
    }

    public void destroy(){
        if (mTbsReaderView != null){
            mTbsReaderView.onStop();
        }
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
    }
}
