package jzt.erp.store.model;

/**
 * @author panjj
 * @create 2021/2/15 - 21:48
 */
public class PageParams {
    private  int start;
    private  int limit;


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
