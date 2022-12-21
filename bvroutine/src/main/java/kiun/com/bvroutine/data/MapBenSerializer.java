package kiun.com.bvroutine.data;

import com.alibaba.fastjson.serializer.FieldSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;

class MapBenSerializer extends JavaBeanSerializer {

    public MapBenSerializer(Class<?> beanType) {
        super(beanType);
    }
}
