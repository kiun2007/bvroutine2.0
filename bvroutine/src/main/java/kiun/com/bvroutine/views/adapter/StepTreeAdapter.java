package kiun.com.bvroutine.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.base.BaseRecyclerAdapter;
import kiun.com.bvroutine.base.BindingHolder;
import kiun.com.bvroutine.data.viewmodel.TreeNode;
import kiun.com.bvroutine.data.viewmodel.TreeViewNode;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.view.IndentTreeStepView;
import kiun.com.bvroutine.interfaces.view.LoadAdapter;
import kiun.com.bvroutine.interfaces.view.TreeSmartView;
import kiun.com.bvroutine.interfaces.view.TreeStepView;
import kiun.com.bvroutine.presenters.StepTreePresenter;
import kiun.com.bvroutine.utils.ViewBindingUtil;

public class StepTreeAdapter extends BaseRecyclerAdapter<TreeNode, StepTreePresenter> implements LoadAdapter<TreeNode> {

    TreeStepView mTreeStepView;
    int expHandlerBr;
    List<TreeHandler> showTreeNodes = new LinkedList<>();

    public StepTreeAdapter(StepTreePresenter presenter, int expHandlerBr, int errLayout, int dataBr, ListHandler handler, TreeStepView treeStepView) {

        super(presenter, 0, errLayout, dataBr, handler);
        this.expHandlerBr = expHandlerBr;
        mTreeStepView = treeStepView;
    }

    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType < 0){
            return super.onCreateViewHolder(parent, viewType);
        }else{
            ViewDataBinding binding = ViewBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
            if(mHandler != null && mHandler.getBR() > 0){
                binding.setVariable(mHandler.getBR(), mHandler);
            }

            BindingHolder holder = new BindingHolder(binding.getRoot());
            holder.setBinding(binding);
            return holder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int superType;

        if ((superType = super.getItemViewType(position)) < 0){
            return superType;
        }

        TreeNode treeNode = showTreeNodes.get(position).getTreeNode();
        if (mTreeStepView instanceof TreeSmartView){
            ((TreeSmartView) mTreeStepView).rootLayout(treeNode);
        }
        return treeNode.getLayout();
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {

        if (holder != null){
            int viewType = getItemViewType(position);
            if (viewType < 0){
                super.onBindViewHolder(holder, position);
            }else{
                TreeHandler handler = showTreeNodes.get(position);
                TreeNode treeNode = handler.getTreeNode();

                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
                holder.itemView.setVisibility(treeNode.isParentExpansion() ? View.VISIBLE : View.GONE);

                if (mTreeStepView instanceof IndentTreeStepView){
                    //缩进
                    layoutParams.leftMargin = treeNode.parentLevel() * ((IndentTreeStepView) mTreeStepView).indent();
                }
                holder.itemView.setLayoutParams(layoutParams);

                holder.getBinding().setVariable(mDataBr, treeNode.getExtra());
                holder.getBinding().setVariable(expHandlerBr, handler);
                holder.getBinding().executePendingBindings();
            }
        }
    }

    @Override
    public List showList() {
        return showTreeNodes;
    }

    @Override
    public void add(List<TreeNode> list) {
        super.add(list);
    }

    @Override
    public void notifySet() {
        showTreeNodes.clear();
        for (int i = 0; i < listData.size(); i ++){
            showTreeNodes.add(new TreeHandler(listData.get(i), showTreeNodes.size()));
            if (!listData.get(i).isExpansion() && listData.get(i).childCount() > 0){
                i += listData.get(i).childCount();
            }
        }
        super.notifySet();
    }

    public int indexOfTreeHandler(TreeNode treeNode){
        int count = showTreeNodes.size();

        for (int i = 0; i < count; i++) {
            if (showTreeNodes.get(i).getTreeNode().equals(treeNode))
                return i;
        }
        return -1;
    }

    private TreeHandler errorHandler(){
        return new TreeHandler(null, ERROR_VIEW);
    }

    private TreeHandler emptyHandler(){
        return new TreeHandler(null, ERROR_VIEW);
    }

    public class TreeHandler extends BaseHandler{

        //展开.
        public final static int EVENT_EXP = 0;
        //选中.
        public final static int EVENT_CHECK = 1;
        //加载更多.
        public final static int EVENT_MORE = 2;
        //重新加载.
        public final static int EVENT_RELOAD = 3;

        private TreeNode treeNode;
        TreeViewNode treeViewNode;
        private int position;
        private boolean isLoading = false;

        public TreeHandler(TreeNode treeNode, int position) {
            this.treeNode = treeNode;
            this.position = position;
        }

        @Override
        public void onClick(Context context, int tag, Object data) {

            if (EVENT_EXP == tag){
                if (listViewPresenter.isLoading()) return;

                if(!treeNode.isExpansion()){
                    if (!treeNode.withPager()){

                        treeViewNode = mTreeStepView.children(treeNode);
                        listViewPresenter.loadTree(treeNode, treeViewNode);
                    }
                }
                treeNode.expansion(!treeNode.isExpansion());
                notifySet();
            }else if (EVENT_CHECK == tag){
                treeNode.setCheck();

                for (int i = position; i <= position + (treeNode.childCount() < 0 ? 0 : treeNode.childCount()); i++) {
                    notifyItemChanged(i);
                }

                for (TreeNode parentTree = treeNode; parentTree != null; parentTree = parentTree.getParent()){
                    notifyItemChanged(indexOfTreeHandler(parentTree));
                }
                mTreeStepView.onCheckChanged(listViewPresenter);
            }else if (EVENT_MORE == tag){
                if (listViewPresenter.isLoading()) return;

                TreeNode parent = treeNode.getParent();
                if (parent != null){
                    isLoading = true;
                    notifyItemChanged(position);
                    listViewPresenter.loadMore(parent, new TreeViewNode(treeNode.getLayout(), treeNode.childCount() != -1));
                }
            }else if (EVENT_RELOAD == tag){

            }
        }

        public boolean isErrorView(){
            return position == ERROR_VIEW;
        }

        public boolean isEmptyView(){
            return position == EMPTY_VIEW;
        }

        public TreeNode getTreeNode() {
            return treeNode;
        }

        public boolean isShowMore(){
            TreeNode parent = treeNode.getParent();
            if (parent != null && parent.withPager()){
                if (treeNode.indexOfParent() == parent.childCount() - 1){
                    if (!parent.getPager().isPageOver()){
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean isLoading() {
            return isLoading;
        }
    }
}
