package kiun.com.bvroutine.data.viewmodel;

public class TreeViewNode {

    /**
     * 节点子节点视图.
     */
    private int layoutId;

    /**
     * 子节点是否拥有扩展功能.
     */
    private boolean children;

    /**
     * 初始化节点视图参数对象.
     * @param layoutId 子节点使用的视图.
     * @param children 子节点的扩展能力.
     */
    public TreeViewNode(int layoutId, boolean children) {
        this.layoutId = layoutId;
        this.children = children;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public boolean isChildren() {
        return children;
    }
}
