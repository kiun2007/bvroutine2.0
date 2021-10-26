package kiun.com.bvroutine.base.binding;

import android.annotation.SuppressLint;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.databinding.BindingAdapter;

import kiun.com.bvroutine.utils.ObjectUtil;

public class WebViewBinding {

    @SuppressLint("SetJavaScriptEnabled")
    @BindingAdapter("android:url")
    public static void setUrl(WebView webView, String url){

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024*1024*24);
        String appCachePath = webView.getContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);

        webView.loadUrl(url);
    }

    @BindingAdapter("android:clientClass")
    public static void setWebViewClient(WebView webView, Class<? extends WebViewClient> webViewClientClz){

        if (webViewClientClz != null){
            WebViewClient webViewClient = ObjectUtil.newObject(webViewClientClz, webView);
            webView.setWebViewClient(webViewClient);
        }
    }
}
