package kiun.com.bvroutine.utils.attri;

import android.content.Context;
import android.content.res.TypedArray;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;

public abstract class AttributeReaderBase {

    private static List<AttributeReaderBase> allAttriReaders = new LinkedList<>();
    static {
        allAttriReaders.add(new IntAttributeReader());
        allAttriReaders.add(new BooleanAttributeReader());
        allAttriReaders.add(new DrawableAttributeReader());
        allAttriReaders.add(new FloatAttributeReader());
        allAttriReaders.add(new IntArrayAttributeReader());
        allAttriReaders.add(new StringArrayAttributeReader());
        allAttriReaders.add(new StringAttributeReader());
        allAttriReaders.add(new ViewAttributeReader());
        allAttriReaders.add(new ClassAttributeReader());
    }
    
    /**
     * 支持的类型.
     */
    protected Type[] supportTypes;

    /**
     * 检测类型.
     */
    protected Type type;

    public AttributeReaderBase(Type... types){
        supportTypes = types;
    }

    public boolean isWithType(Type type){

        this.type = type;
        for (Type item : supportTypes){
            if (item.equals(type)){
                return true;
            }
        }
        return false;
    }

    /**
     * 读取属性相关值.
     * @param array
     * @param attrId
     * @param attrBind
     * @return
     */
    public abstract Object read(Context context, TypedView typedView, TypedArray array, int attrId, AttrBind attrBind);

    /**
     * 查找属性读取器.
     * @param type
     * @return
     */
    public static AttributeReaderBase find(Type type){
        for (AttributeReaderBase attributeReaderBase : allAttriReaders){
            if(attributeReaderBase.isWithType(type)){
                return attributeReaderBase;
            }
        }
        return null;
    }
}
