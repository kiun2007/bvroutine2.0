package kiun.com.bvroutine.interfaces.view;

import kiun.com.bvroutine.data.viewmodel.TreeNode;
import kiun.com.bvroutine.data.viewmodel.TreeViewNode;
import kiun.com.bvroutine.presenters.StepTreePresenter;

public interface TreeStepView extends ListRequestView<TreeNode, TreeNode>{

    /**
     * 子节点是否还拥有扩展功能.
     * @param node 节点数据.
     * @return 是否有扩展.
     */
    TreeViewNode children(TreeNode node);

    /**
     * 选中状态发生变化.
     * @param p
     */
    void onCheckChanged(StepTreePresenter p);

    /**
     * 加载更多.
     * @param p
     */
    void onLoadMore(StepTreePresenter p);
}