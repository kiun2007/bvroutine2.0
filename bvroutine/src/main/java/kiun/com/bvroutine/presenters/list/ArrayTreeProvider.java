package kiun.com.bvroutine.presenters.list;

import android.content.Context;

import java.util.List;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.data.FieldEvent;
import kiun.com.bvroutine.data.viewmodel.TreeNode;
import kiun.com.bvroutine.data.viewmodel.TreeViewNode;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.TreeItem;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.presenters.StepTreePresenter;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.ViewUtil;

public class ArrayTreeProvider extends TreeProvider{

    private List<? extends TreeItem> treeItems;

    private NetTree netTree;

    private int rootItemLayout = 0;

    private int indent = 0;

    private FieldEvent fieldEvent;

    public ArrayTreeProvider(RequestBVActivity context, ListHandler handler, List<? extends TreeItem> treeItems, int rootItemLayout) {
        super(context, handler);
        this.treeItems = treeItems;
        this.rootItemLayout = rootItemLayout;
    }

    public ArrayTreeProvider(RequestBVActivity context, ListHandler handler, NetTree netTree, int rootItemLayout) {
        super(context, handler);
        this.netTree = netTree;
        this.rootItemLayout = rootItemLayout;
    }

    @Override
    public int indent() {
        return ViewUtil.dp2px(context, indent);
    }

    @Override
    public TreeViewNode children(TreeNode treeNode) {

        if (treeNode.getExtra() instanceof TreeItem){
            TreeItem item = (TreeItem) treeNode.getExtra();
            return new TreeViewNode(item.itemLayoutId(), item.withChild(treeNode.parentLevel()));
        }
        return null;
    }

    public ArrayTreeProvider indent(int indent) {
        this.indent = indent;
        return this;
    }

    public ArrayTreeProvider listener(FieldEvent fieldEvent){
        this.fieldEvent = fieldEvent;
        return this;
    }

    @Override
    public int getDataBind() {
        return BR.item;
    }

    @Override
    public int getItemLayoutId() {
        return rootItemLayout;
    }

    @Override
    public void onCheckChanged(StepTreePresenter p) {

        if (fieldEvent != null){
            List<TreeNode> selectTreeNode = p.filter(item -> !item.withChildren() && item.checkedCount() > 0);
            List values = ListUtil.toList(selectTreeNode, item->item.getExtra());
            fieldEvent.setValue(values);
        }
    }

    @Override
    public List requestPager(RequestBindingPresenter p, TreeNode bean) throws Exception {

        List list = null;
        if (bean == null){
            if (treeItems != null){
                list = treeItems;
                treeItems = null;
            }

            if (netTree != null){
                list = netTree.itemList();
            }
        }else if (bean.getExtra() instanceof TreeItem){
            TreeItem treeItem = (TreeItem) bean.getExtra();
            list = treeItem.itemList();
        }
        return list;
    }

    public static ArrayTreeProvider create(Context context, ListHandler<?> listHandler, int rootItemLayout, List treeItems){

        if (context instanceof RequestBVActivity){
            return new ArrayTreeProvider((RequestBVActivity) context, listHandler, treeItems, rootItemLayout);
        }
        return null;
    }

    public static ArrayTreeProvider create(Context context, ListHandler<?> listHandler, int rootItemLayout, NetTree netTree){
        if (context instanceof RequestBVActivity){
            return new ArrayTreeProvider((RequestBVActivity) context, listHandler, netTree, rootItemLayout);
        }
        return null;
    }
}
