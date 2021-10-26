package kiun.com.bvroutine.base;

public class DrawableIndex {

    private int arrayResId;

    private int index;

    private DrawableIndex(int arrayResId, int index) {
        this.arrayResId = arrayResId;
        this.index = index;
    }

    public int getArrayResId() {
        return arrayResId;
    }

    public void setArrayResId(int arrayResId) {
        this.arrayResId = arrayResId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static DrawableIndex create(int arrayResId, int index){
        return new DrawableIndex(arrayResId, index);
    }
}
