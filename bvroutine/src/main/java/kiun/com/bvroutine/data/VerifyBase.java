package kiun.com.bvroutine.data;

import kiun.com.bvroutine.data.verify.Problem;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.utils.JexlUtil;

/**
 * 验证基础类.
 * @see T 指定验证类型, 对此类型进行验证.
 * @see E extra 经过 jexl 运算后得到的值类型.
 */
public abstract class VerifyBase<T extends Object, E> {

    protected Object object;

    protected Verify verify;

    protected String fieldName;

    protected Object extraRunValue;

    /**
     * 验证被检查的优先级，越高越优先检查.
     */
    protected int level;

    public VerifyBase(Object object, Verify verify, int defLevel) {
        this.object = object;
        this.verify = verify;
        level = verify.level() < 0 ? defLevel : verify.level();
    }

    protected abstract boolean verifyValue(T value);

    protected boolean descIsRuntime(){
        return false;
    }

    protected String getDesc(String field){
        return String.format("\"%s\"验证失败", field);
    }

    public E extra(E def){

        if (verify.extra().isEmpty()){
            return def;
        }
        return JexlUtil.run(verify.extra(), "that", object, "fieldName", fieldName);
    }

    protected boolean isPass(){

        String passValue = verify.pass();
        return !passValue.isEmpty() && (boolean)JexlUtil.run(passValue, "that", object, "fieldName", fieldName);
    }

    public Problem verify(T value, String fieldName){

        this.fieldName = fieldName;
        if (isPass()){
            return null;
        }

        if (verifyValue(value)){
            return null;
        }
        String desc = verify.desc().isEmpty() ? getDesc(fieldName) : verify.desc();
        if (descIsRuntime()){
            desc = JexlUtil.run(desc, "that", object, "fieldName", fieldName, "extraResult", extraRunValue);
        }
        return new Problem(desc, fieldName, verify.force(), level);
    }
}