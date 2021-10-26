package kiun.com.bvroutine.interfaces.callers;

import java.util.List;

import kiun.com.bvroutine.data.PagerBean;

@FunctionalInterface
public interface ListPagerCaller<T> {
    List<T> listCall(PagerBean pagerBean);
}