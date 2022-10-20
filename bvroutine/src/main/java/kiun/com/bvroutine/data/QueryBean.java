package kiun.com.bvroutine.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.utils.ObjectUtil;
import kiun.com.bvroutine.utils.type.ClassUtil;

@JSONType(ignores = {"entries", "values", "keys", "size"})
public class QueryBean<T> extends EventBean implements Map<String, Object> {

    @JSONField(serialize = false)
    private T extra;

    @JSONField(serialize = false)
    private Map<String, Object> __values;

    public QueryBean() {
    }

    private LinkedHashMap<String, Object> changeValue(){

        LinkedHashMap<String, Object> values = new LinkedHashMap<>();
        try {

            for (Field field : ClassUtil.getRangeField(this.getClass(), QueryBean.class)){

                JSONField jsonField = field.getAnnotation(JSONField.class);
                Expose expose = field.getAnnotation(Expose.class);

                if ((jsonField != null && !jsonField.serialize()) || (expose != null && !expose.deserialize())){
                    continue;
                }

                field.setAccessible(true);
                Object value = field.get(this);
                if (value != null){
                    values.put(field.getName(), value);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!Objects.equals(__values, values)){
            __values = values;
        }
        return values;
    }

    public QueryBean(T extra){
        setExtra(extra);
    }
    
    public T getExtra() {
        return extra;
    }

    public void setExtra(T extra) {
        this.extra = extra;
        changeValue();

        if (extra != null){
            if (extra instanceof Map){
                __values.putAll((Map<? extends String, ? extends Object>) extra);
            }else{
                JSONObject extraObject = (JSONObject) JSONObject.toJSON(extra);
                __values.putAll(extraObject);
            }
        }
    }

    @Override
    public int size() {
        changeValue();
        return __values.size();
    }

    @Override
    @JSONField(serialize = false)
    public boolean isEmpty() {
        if (__values != null){
            return __values.isEmpty();
        }
        return true;
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        changeValue();
        return __values.containsKey(key);
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        changeValue();
        return __values.containsValue(value);
    }

    @Nullable
    @Override
    public Object get(@Nullable Object key) {
        changeValue();
        return __values.get(key);
    }

    @Nullable
    @Override
    public Object put(String key, Object value) {
        try {
            if (value == null){
                return null;
            }

            Field field = this.getClass().getDeclaredField(key);
            field.setAccessible(true);

            if (field.getType().equals(Double.class)){
                field.set(this, Double.parseDouble(value.toString()));
            }else if (field.getType().equals(Float.class)){
                field.set(this, Float.parseFloat(value.toString()));
            }else if (field.getType().equals(Integer.class)){
                field.set(this, Integer.parseInt(value.toString()));
            }else if (field.getType().equals(Long.class)){
                field.set(this, Long.parseLong(value.toString()));
            }else{
                field.set(this, value);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
        return value;
    }

    @Nullable
    @Override
    public Object remove(@Nullable Object key) {
        return null;
    }

    @Override
    public void putAll(@NonNull Map<? extends String, ?> m) {
    }

    @Override
    public void clear() {
    }

    @NonNull
    @Override
    public Set<String> keySet() {
        changeValue();
        return __values.keySet();
    }

    @NonNull
    @Override
    public Collection<Object> values() {
        changeValue();
        return __values.values();
    }

    @NonNull
    @Override
    public Set<Entry<String, Object>> entrySet() {
        changeValue();
        return __values.entrySet();
    }

    public static QueryBean create(){
        return new QueryBean();
    }
}
