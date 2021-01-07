package kiun.com.bvroutine.base.binding;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

public class EditTextBinding {

    @BindingAdapter("android:onSearch")
    public static void setOnSearch(EditText editText, View.OnClickListener onClickListener){
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onClickListener.onClick(v);
                return true;
            }
            return false;
        });
    }
}
