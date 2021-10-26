package kiun.com.bvroutine.base.binding;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.data.verify.Problem;
import kiun.com.bvroutine.data.verify.ProblemExport;
import kiun.com.bvroutine.utils.ViewUtil;

public class VerifyBinder {

    private View rootView;

    public VerifyBinder(View rootView) {
        this.rootView = rootView;
        rootView.setFocusable(true);
        rootView.setFocusableInTouchMode(true);
    }

    public void sendProblem(List<Problem> problems, EventBean eventBean){

        for(Problem problem : problems){

            View problemView = ViewUtil.findChildViewTag(rootView, R.id.tagVerifyField, problem.getField());

            if (problemView == null){
                continue;
            }

            Boolean hasProblem = (Boolean) problemView.getTag(R.id.tagHasProblem);
            if (hasProblem != null && hasProblem){
                continue;
            }
            problemView.setTag(R.id.tagHasProblem, true);

            if (problemView instanceof TextView){

                TextView textView = (TextView) problemView;
                textView.clearFocus();

                VerifyFocus verifyFocus = new VerifyFocus(textView, problem);

                textView.addTextChangedListener(verifyFocus);
                textView.setOnFocusChangeListener(verifyFocus);

                if (!(problemView instanceof EditText)){
                    verifyFocus.onProblem(problem);
                    eventBean.addExport(problem.getField(), verifyFocus);
                }
            }else if (problemView instanceof ProblemExport){
                ProblemExport export = (ProblemExport) problemView;
                export.onProblem(problem);
                eventBean.addExport(problem.getField(), export);
            }
        }

    }
}
