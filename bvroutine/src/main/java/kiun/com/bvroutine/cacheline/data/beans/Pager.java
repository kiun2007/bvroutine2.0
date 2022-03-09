package kiun.com.bvroutine.cacheline.data.beans;

/**
 * 分页信息.
 */
public class Pager{
    private int pageSize; //一页的数量.
    private int pageNum; //页码.
    private int pages;  //一共有多少页.
    private int total; //一共有多少条.
    private int size; //总共返回了多少条数据.

    public Pager(int pageSize, int pageNum){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int pageNum() {
        return pageNum;
    }

    public int pageSize(){
        return pageSize;
    }

    public void setRowSize(int rowSize) {
        total = rowSize;
        if (pageSize != 0){
            pages = (int) Math.ceil((float)rowSize / pageSize);
        }
        size = rowSize > pageSize ? pageSize : rowSize;
    }

    public int pages() {
        return pages;
    }

    public int size(){
        return size;
    }

    public int getTotal() {
        return total;
    }

    public String limit(){
        return String.format("%d,%d", (pageNum - 1) * pageSize, pageSize);
    }
}