package kiun.com.bvroutine.views.adapter;

public class Indexer {

    private int index;
    private int count;

    public Indexer(int index, int count) {
        this.index = index;
        this.count = count;
    }

    /**
     * 当前索引.
     * @return 索引值.
     */
    public int getIndex() {
        return index;
    }

    /**
     * 总数量.
     * @return 总个数.
     */
    public int getCount() {
        return count;
    }

    /**
     * 是否为第一个.
     * @return 第一个.
     */
    public boolean isFirst(){
        return index == 0;
    }

    /**
     * 是否为最后一个.
     * @return 最后一个.
     */
    public boolean isLast(){
        return index == (count - 1);
    }
}
