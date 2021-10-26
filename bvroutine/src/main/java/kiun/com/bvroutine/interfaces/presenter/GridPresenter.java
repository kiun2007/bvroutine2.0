package kiun.com.bvroutine.interfaces.presenter;

import java.util.List;

import kiun.com.bvroutine.interfaces.callers.CallBack;

public interface GridPresenter<T>{

    void setList(List<T> list);

    void add(T item);

    void remove(T item);

    void removeAt(int index);

    void clear();

    void notifySet();

    void addList(List<T> items);

    List<T> allList();

    void setOnChanged(CallBack callBack);
}
