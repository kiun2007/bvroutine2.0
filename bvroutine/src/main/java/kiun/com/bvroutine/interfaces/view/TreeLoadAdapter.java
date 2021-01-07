package kiun.com.bvroutine.interfaces.view;

import kiun.com.bvroutine.data.viewmodel.TreeNode;

public interface TreeLoadAdapter<T> extends LoadAdapter<T> {

    void insert(TreeNode parent);
}
