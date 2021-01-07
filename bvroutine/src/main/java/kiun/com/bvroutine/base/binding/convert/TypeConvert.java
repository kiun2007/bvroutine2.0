package kiun.com.bvroutine.base.binding.convert;

/**
 * 类型转换器.
 */
public abstract class TypeConvert<F,T> {

    protected abstract T convert(F value);

    protected abstract F convertFrom(T value);

    public T to(F value){
        try {
            if (value == null){
                return null;
            }
            return convert(value);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public F from(T value){
        try {
            if (value == null){
                return null;
            }
            return convertFrom(value);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
