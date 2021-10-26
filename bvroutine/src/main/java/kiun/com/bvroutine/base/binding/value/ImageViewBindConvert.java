package kiun.com.bvroutine.base.binding.value;

import android.widget.ImageView;

import kiun.com.bvroutine.utils.GlideUtil;

public class ImageViewBindConvert extends BindConvert<ImageView, String, String> {

    public ImageViewBindConvert(ImageView view) {
        super(view);
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public void setValue(String value) {
        GlideUtil.into(view, value, false, true);
    }
}
