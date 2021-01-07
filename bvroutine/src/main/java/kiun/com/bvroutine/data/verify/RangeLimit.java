package kiun.com.bvroutine.data.verify;

import kiun.com.bvroutine.data.VerifyBase;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.utils.MCString;

/**
 * 数值范围限制.
 * 请在 验证附加数据{@link Verify#extra} 添加jexl表达式,返回值为数组值.
 * 0元素(必选):最小值限制值.
 * 1元素(可选):最大限制值.
 * 2元素(可选):比较模式,0全部包含临界值,1仅小于包含临界值,2仅大于包含临界值,3全部不包含临界值.
 */
public class RangeLimit extends VerifyBase<Comparable<Number>, int[]> {

    public RangeLimit(Object object, Verify verify) {
        super(object, verify, 1000);
    }

    @Override
    protected boolean verifyValue(Comparable<Number> value) {
        int[] limits = extra(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE});
        int compareType = 0;
        if (limits.length == 3){
            //获取比较模式.
            compareType = limits[2];
        }

        if (value != null){
            Integer limitMin = limits[0];
            Integer limitMax = limits.length != 2 ? null : limits[1];

            int minCompare = value.compareTo(limitMin);
            //当前值小于最小值或者当前值等于最小值但比较模式不能包含临界值,则验证不通过.
            if (minCompare < 0 || (minCompare == 0 && !MCString.isWith(compareType, 0, 1))){
                return false;
            }

            if (limitMax != null){
                int maxCompare = value.compareTo(limitMax);
                //当前值大于最大值或者当前值等于最大值但比较模式不能包含临界值,则验证不通过.
                return maxCompare <= 0 && (maxCompare != 0 || MCString.isWith(compareType, 0, 2));
            }
            return true;
        }
        return false;
    }
}
