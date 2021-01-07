package kiun.com.bvroutine.data;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

public class PagerBean<T,P extends PagerBean> extends QueryBean<T>{

    private int pageNum = 1;

    private int limit = -1;

    private int pageSize = 10;

    @JSONField(serialize = false)
    private int pages = -1;

    @JSONField(serialize = false)
    private int total = -1;

    @JSONField(serialize = false)
    private int realPageNum = 1;

    @JSONField(serialize = false)
    private boolean empty = false;

    public PagerBean(){
        super();
    }

    public void initPage(){
        empty = false;
        pageNum = 1;
    }

    public P inherit(PagerBean pagerBean){
        setPageNum(pagerBean.pageNum);
        setPageSize(pagerBean.pageSize);
        return (P) this;
    }

    public PagerBean(T extra){
        super(extra);
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int realPageNum() {
        return realPageNum;
    }

    public P setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return (P) this;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void addPage(){
        pageNum ++;
    }

    public void rollbackPage(){
        pageNum --;
    }

    public void real(){
        realPageNum = pageNum;
    }

    /**
     * 界面全部加载完毕.
     * @return 是否完毕.
     */
    @JSONField(serialize = false)
    public boolean isPageOver(){
        return realPageNum >= pages;
    }

    public boolean isEmpty(){
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        pages = (int)Math.ceil((float)total / pageSize);
    }

    public int getLimit() {
        return limit;
    }

    public P setLimit(int limit) {
        this.limit = limit;
        setPageSize(limit);
        return (P) this;
    }

    public PagerBean extra(Map<String, Object> map){
        setExtra((T) map);
        return this;
    }
}
