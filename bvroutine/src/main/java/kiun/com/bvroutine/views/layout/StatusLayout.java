package kiun.com.bvroutine.views.layout;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import kiun.com.bvroutine.utils.ViewUtil;

/**
 * DeviceService
 * popchain
 * @author kiun_2007 on 2018/3/27.
 * Copyright (media_menu) 2017-2018 The Popchain Core Developers
 */

public class StatusLayout extends LinearLayout {
    public StatusLayout(Context context) {
        this(context, null);
    }

    public StatusLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        ViewGroup.LayoutParams lp = this.getLayoutParams();
        lp.width = -1;
        lp.height = ViewUtil.getStatusBarHeight(getContext());
        this.setLayoutParams(lp);
        super.onAttachedToWindow();
    }
}
