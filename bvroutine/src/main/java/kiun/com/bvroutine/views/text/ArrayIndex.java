package kiun.com.bvroutine.views.text;

import kiun.com.bvroutine.utils.MCString;

public class ArrayIndex {

    private Object index;

    private int offset;

    public ArrayIndex(Object index, int offset) {
        this.index = index;
        this.offset = offset;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getValue(String[] values){
        return MCString.item(index, offset, values);
    }

    public static ArrayIndex create(Object index, int offset){
        return new ArrayIndex(index, offset);
    }
}
