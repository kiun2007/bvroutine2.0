package kiun.com.bvroutine.base.binding;

import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.data.verify.Problem;
import kiun.com.bvroutine.data.verify.ProblemExport;
import kiun.com.bvroutine.presenters.TextViewLoadingPresenter;

public class VerifyFocus implements TextWatcher, View.OnFocusChangeListener, ProblemExport {

    private TextView textView;
    private int defHintColor;
    private TextViewLoadingPresenter presenter;
    private View.OnFocusChangeListener oldListener;
    private CharSequence oldHint;
    private View tagView;

    public VerifyFocus(@NonNull TextView textView, Problem problem,@NonNull View view){

        this.textView = textView;
        this.tagView = view;
        defHintColor = textView.getHintTextColors().getDefaultColor();
        presenter = new TextViewLoadingPresenter(textView);
        oldListener = textView.getOnFocusChangeListener();
        int textColor = problem.isForce() ? Color.RED : 0xFFFF9933;
        int iconId = problem.isForce() ? R.drawable.ic_error_outline_red_16dp
                                        : R.drawable.ic_outline_notification_important_16;

        textView.setEnabled(false);
        if (textView.getText().length() == 0){
            textView.setHintTextColor(textColor);
            oldHint = textView.getHint();
            textView.setHint(presenter.drawSpanned(iconId, problem.getDesc(), false));
        }else {

            oldHint = textView.getText();
            SpannableStringBuilder builder = new SpannableStringBuilder();
            CharSequence charSequence = presenter.drawSpanned(iconId, problem.getDesc(), true);

            builder.append(oldHint);
            builder.append(' ');
            builder.append(charSequence);

            builder.setSpan(new ForegroundColorSpan(textColor), oldHint.length() + 2, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new RelativeSizeSpan(0.9f), oldHint.length() + 2, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.setText(builder);
        }
        textView.setEnabled(true);
    }

    public VerifyFocus(TextView textView, Problem problem){
        this(textView, problem, textView);
    }

    @Override
    public void onProblem(Problem problem) {

    }

    public void clear(){

        textView.setHintTextColor(defHintColor);
        presenter.clear();
        this.textView.removeTextChangedListener(this);
        this.textView.setOnFocusChangeListener(oldListener);
        if (oldHint != null){
            if (textView.getText().length() == 0){
                textView.setHint(oldHint);
            }else{
                textView.setText(oldHint);
            }
        }

        if (tagView != null){
            tagView.setTag(R.id.tagHasProblem, null);
        }
        oldListener = null;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (oldListener != null){
            oldListener.onFocusChange(v, hasFocus);
        }

        if (hasFocus){
            clear();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        clear();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}