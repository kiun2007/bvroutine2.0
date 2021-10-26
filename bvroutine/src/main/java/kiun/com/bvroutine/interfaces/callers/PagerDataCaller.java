package kiun.com.bvroutine.interfaces.callers;

import java.util.List;

import kiun.com.bvroutine.data.PagerBean;

@FunctionalInterface
public interface PagerDataCaller {
    List<?> get(PagerBean pager) throws Exception;
}
