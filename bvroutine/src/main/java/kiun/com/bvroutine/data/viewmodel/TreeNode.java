package kiun.com.bvroutine.data.viewmodel;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.callers.BatchVoidCaller;

public class TreeNode {

    //不选.
    public static final int STATUS_OFF = 0;

    //混合.
    public static final int STATUS_BLEND = 1;

    //全选.
    public static final int STATUS_ON = 2;

    //父节点.
    private TreeNode parent = null;

    private List<TreeNode> treeNodes;

    //包含多少个子节点,如果为负值是不可能拥有子节点的含义.
    private int childCount = 0;

    //子节点选中多少个.
    private int checkedCount = 0;

    //使用视图Layout.
    private int viewLayoutId;

    //展开或者收缩.
    private boolean expansion = false;

    //正在加载子节点.
    private boolean loading = false;

    //附带数据.
    private Object extra;

    /**
     * 在父节点的索引.
     */
    private int indexOfParent = 0;

    //分页信息.
    private PagerBean pager;

    public int treeIndex(){
        if (treeNodes == null){
            return -1;
        }
        return treeNodes.indexOf(this);
    }

    public int indexOfParent() {
        return indexOfParent;
    }

    /**
     * 初始化一个节点,包含节点的视图和数据.
     * @param treeNodes 节点总数组.
     * @param viewLayoutId 视图Layout.
     * @param extra 附带数据.
     */
    public TreeNode(List<TreeNode> treeNodes, int viewLayoutId, Object extra){
        this.treeNodes = treeNodes;
        this.viewLayoutId = viewLayoutId;
        this.extra = extra;
    }

    /**
     * 初始化一个节点被包含在某个父节点,包含节点的视图和数据.
     * @param treeNodes 节点总数组.
     * @param parent 父节点对象.
     * @param viewLayoutId 视图Layout.
     * @param extra 附带数据.
     */
    public TreeNode(List<TreeNode> treeNodes, TreeNode parent, int viewLayoutId, Object extra){
        this(treeNodes, viewLayoutId, extra);
        this.parent = parent;
    }

    /**
     * 展开数据.
     * @param expansion true展开, false 收起.
     */
    public void expansion(boolean expansion) {
        this.expansion = expansion;
    }

    /**
     * 是否已经展开.
     * @return
     */
    public boolean isExpansion() {
        return expansion;
    }

    public TreeNode getParent() {
        return parent;
    }

    /**
     * 向上选多少级.
     * @param upLevel
     * @return
     */
    public TreeNode getParent(int upLevel){

        int level = 1;
        for (TreeNode parentTree = parent; parentTree != null; parentTree = parentTree.getParent()){
            if (level == upLevel){
                return parentTree;
            }
            level++;
        }
        return null;
    }

    public boolean isParentExpansion(){

        for (TreeNode parentTree = parent; parentTree != null; parentTree = parentTree.getParent()){
            if (!parentTree.isExpansion()) return false;
        }
        return true;
    }

    public int parentLevel(){
        final int[] level = {0};
        forParent(item -> {
            level[0]++;
        });
        return level[0];
    }

    public void setViewLayoutId(int viewLayoutId) {
        this.viewLayoutId = viewLayoutId;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public int status(){
        if (childCount <= 0){
            return checkedCount == 0 ? STATUS_OFF : STATUS_ON;
        }
        return checkedCount == 0 ? STATUS_OFF : (checkedCount == (childCount + 1) ? STATUS_ON : STATUS_BLEND);
    }

    public int childCount() {
        return childCount;
    }

    public int checkedCount(){
        return checkedCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public boolean withChildren(){
        return childCount >= 0;
    }

    public int getLayout() {
        return viewLayoutId;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public PagerBean getPager() {
        if (pager == null){
            pager = new PagerBean(extra);
        }
        return pager;
    }

    public void setPager(PagerBean pager) {
        this.pager = pager;
    }

    public boolean withPager(){
        return pager != null;
    }

    public List<TreeNode> findChildren(){
        if (childCount <= 0){
            return null;
        }
        int index = treeNodes.indexOf(this);
        return treeNodes.subList(index + 1, index + childCount + 1);
    }

    public void setCheck(){
        setCheck(checkedCount == 0, true);
    }

    public void setCheck(boolean isChecked, boolean isTarget){

        int inc = isChecked ? (childCount <= 0 ? 1 : childCount + 1) : checkedCount * -1;
        checkedCount = isChecked ? inc : 0;

        if (parent != null){
            if (isChecked && parent.checkedCount == 0){
                inc ++;
            }
            if (!isChecked && parent.checkedCount==2){
                inc --;
            }
        }

        //影响上级.
        for (TreeNode item = parent; item != null; item = item.getParent()){
            if (isChecked){
                if (item != parent && item.checkedCount == 0){
                    inc ++;
                }
                item.checkedCount += inc;
            }else{
                item.checkedCount += inc;
                if (item.checkedCount == 1){ //只有自身的节点去除.
                    item.checkedCount = 0;
                    inc --;
                }
            }
        }

        if(isTarget){
            List<TreeNode> children = findChildren();
            if (children == null) return;

            for (TreeNode child : children){
                child.checkedCount = isChecked ? (child.childCount <= 0 ? 1 : child.childCount + 1) : 0;
            }
        }
    }

    public void forParent(BatchVoidCaller<TreeNode> caller){
        for (TreeNode parentTree = parent; parentTree != null; parentTree = parentTree.getParent()){
            caller.call(parentTree);
        }
    }

    /**
     * 根据数据向节点增加子节点.
     * @param datas 子节点的数据.
     * @param viewLayoutId 子节点使用什么视图.
     * @return 返回的子节点对象.
     */
    public List<TreeNode> addNode(List<TreeNode> root, List datas, int viewLayoutId, boolean children){

        List<TreeNode> list = new LinkedList<>();
        for (Object item : datas){
            TreeNode treeNode = new TreeNode(root,this, viewLayoutId, item);
            treeNode.setChildCount(children ? 0 : -1);
            treeNode.indexOfParent = childCount;
            childCount ++;
            list.add(treeNode);

            //父节点也要增加个数.
            forParent(parentTree -> parentTree.childCount++);
        }
        return list;
    }

    public static List<TreeNode> addToRoot(List<TreeNode> root,int viewLayoutId, List datas){
        return insertTo(root, null, viewLayoutId, datas, true);
    }

    public static List<TreeNode> insertTo(List<TreeNode> root, TreeNode parent, int viewLayoutId, List datas, boolean children){

        if (parent != null){
            int insertIndex = root.indexOf(parent);
            insertIndex =  parent.childCount() + insertIndex + 1;
            List<TreeNode> nodes = parent.addNode(root, datas, viewLayoutId, children);
            root.addAll(insertIndex, nodes);
            return nodes;
        }else{
            List<TreeNode> rootNodes = new LinkedList<>();
            for (Object item:datas) {
                TreeNode itemNode = new TreeNode(root, viewLayoutId, item);
                itemNode.indexOfParent = root.size();
                rootNodes.add(itemNode);
            }
            return rootNodes;
        }
    }
}