package kiun.com.bvroutine.data;

import java.util.List;

import kiun.com.bvroutine.interfaces.callers.GetCaller;

public class SpinnerValue<ITEM> {

    private int position;
    private GetCaller<ITEM,String> getCaller;
    private List<ITEM> itemList;

    public SpinnerValue(int position, List<ITEM> itemList, GetCaller<ITEM, String> getCaller) {
        this.position = position;
        this.getCaller = getCaller;
        this.itemList = itemList;
    }

    public SpinnerValue(int position, List<ITEM> itemList){
        this(position, itemList,null);
    }

    public int getPosition() {
        return position;
    }

    public List<ITEM> getItems() {
        return itemList;
    }

    public GetCaller<ITEM, String> getCaller() {
        return getCaller;
    }
}
