package kiun.com.bvroutine.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.handlers.ActionBarHandler;
import kiun.com.bvroutine.interfaces.handler.FragmentNavigationHandler;
import kiun.com.bvroutine.utils.ViewBindingUtil;
import kiun.com.bvroutine.views.NavigatorBar;
import kiun.com.bvroutine.views.viewmodel.ActionBarItem;

public abstract class NavigationBaseFragment<T extends ViewDataBinding> extends RequestBVFragment<T> {

    public static final String ROOT_TAG = "ROOT_TAG";
    protected abstract String title();

    NavigatorBar barView;
    ActionBarItem actionBarItem;
    private ActionBarHandler actionBarHandler;
    boolean isRoot = false;
    protected FragmentNavigationHandler navigationHandler;

    public Class<? extends BVBaseFragment>[] getNextFragments(){
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null){
            isRoot = getArguments().getBoolean(ROOT_TAG, false);
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view;
        if (getActivity() instanceof BVBaseActivity){
            barView = ((BVBaseActivity) getActivity()).getBarView();
        }

        int layoutId = getViewId();
        if(isWithActionBar() && barView == null){
            view = inflater.inflate(R.layout.activiy_base, container, false);
            mViewBinding = ViewBindingUtil.inflate(inflater, layoutId, view.findViewById(R.id.view_content), true);
        }else{
            mViewBinding = ViewBindingUtil.inflate(inflater, layoutId, container, false);
            view = mViewBinding.getRoot();
        }

        if (barView == null){
            barView = view.findViewById(R.id.actionBarViewBinding);
        }
        actionBarItem = barView.getBarItem();
        actionBarItem.setTitle(title());
        actionBarItem.setBarNoBack(isRoot);
        setNavigationHandler(navigationHandler);
        return view;
    }

    public void setBarHandler(ActionBarHandler actionBarHandler) {
        actionBarItem.setHandler(actionBarHandler);
    }

    public void setNavigationHandler(FragmentNavigationHandler navigationHandler){
        if (getNavigationBR() > 0 && mViewBinding != null){
            mViewBinding.setVariable(getNavigationBR(), navigationHandler);
        }
        this.navigationHandler = navigationHandler;
    }

    public ActionBarItem getBarItem() {
        return actionBarItem;
    }
}
