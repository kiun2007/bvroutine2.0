package kiun.com.bvroutine.interfaces.callers;

import kiun.com.bvroutine.data.PagerBean;
import retrofit2.Call;

@FunctionalInterface
public interface PagerCaller {
    Call get(PagerBean pager);
}
