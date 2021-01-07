package kiun.com.bvroutine.base.binding.value;

import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.interfaces.callers.GetCaller;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.utils.ByteUtil;
import kiun.com.bvroutine.utils.FileUtil;
import kiun.com.bvroutine.utils.MD5Util;
import kiun.com.bvroutine.views.word.TbsView;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class TbsViewBindConvert extends BindConvert<TbsView, String, String> {

    private GetCaller<String, Call<ResponseBody>> netCall;
    File docFile;

    public TbsViewBindConvert(TbsView view, GetCaller<String, Call<ResponseBody>> caller){
        super(view);
        netCall = caller;
        QbSdk.initX5Environment(view.getContext(),null);
    }

    public TbsViewBindConvert(TbsView view){
        super(view);
    }

    @Override
    public String getValue() {
        return null;
    }

    private void downloadComplete(boolean v){
        if (v){
            view.displayFile(docFile);
        }
    }

    private void downloadError(Exception ex){
        view.showMessage(ex.getMessage(), true);
    }

    @Override
    public void setValue(String value) {

        if (view.getContext() instanceof RequestBVActivity && netCall != null) {

            File dir = new File(view.getContext().getCacheDir(), "word");
            if (!dir.exists()){
                dir.mkdir();
            }

            String tail = FileUtil.getFileTail(value);
            docFile = new File(dir, ByteUtil.bytesToHexString(MD5Util.MD5(value)) + "." + tail);

            if (docFile.exists()){
                view.displayFile(docFile);
                return;
            }

            RequestBindingPresenter presenter = ((RequestBVActivity) view.getContext()).getRequestPresenter();
            view.showMessage("正在下载文件", false);
            presenter.addRequest(()->writeResponseBodyToDisk(netCall.call(value).execute().body()), this::downloadComplete, this::downloadError);
        }
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) throws IOException {

        byte[] fileReader = new byte[4096];
        long fileSize = body.contentLength();
        long fileSizeDownloaded = 0;

        InputStream inputStream = body.byteStream();
        OutputStream outputStream = new FileOutputStream(docFile);

        while (true) {
            int read = inputStream.read(fileReader);
            if (read == -1)  break;
            outputStream.write(fileReader, 0, read);
            fileSizeDownloaded += read;
        }
        outputStream.flush();
        return true;
    }

    public static class Builder extends BindConvertBuilder<TbsView> {

        private GetCaller<String, Call<ResponseBody>> netCall;

        @Override
        public BindConvert<TbsView, ?, ?> build(TbsView view) {
            return new TbsViewBindConvert(view, netCall);
        }

        public static Builder create(GetCaller<String, Call<ResponseBody>> netCall){
            Builder builder = new Builder();
            builder.netCall = netCall;
            return builder;
        }
    }
}
