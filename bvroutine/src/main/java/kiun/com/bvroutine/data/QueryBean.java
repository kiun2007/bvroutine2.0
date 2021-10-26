package kiun.com.bvroutine.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kiun.com.bvroutine.base.EventBean;

public class QueryBean<T> extends EventBean implements Map<String, Object> {

    @JSONField(serialize = false)
    private T extra;

    @JSONField(serialize = false)
    private Map<String, Object> values;

    public QueryBean(){
    }

    protected void initValues(){
        JavaBeanSerializer javaBeanSerializer = new JavaBeanSerializer(this.getClass());
        try {
            if (values == null){
                values = new LinkedHashMap<>();
            }

            values.putAll(javaBeanSerializer.getFieldValuesMap(this));
            List<String> emptyKeys = new LinkedList<>();

            for (String key : values.keySet()){
                if(values.get(key) == null){
                    emptyKeys.add(key);
                }
            }

            for (String key : emptyKeys){
                values.remove(key);
            }
        } catch (Exception e) {
            throw new JSONException("toJSON error", e);
        }
    }

    public QueryBean(T extra){
        this();
        setExtra(extra);
    }
    
    public T getExtra() {
        return extra;
    }

    public void setExtra(T extra) {
        this.extra = extra;
        initValues();

        if (extra != null){
            if (extra instanceof Map){
                values.putAll((Map<? extends String, ? extends Object>) extra);
            }else{
                JSONObject extraObject = (JSONObject) JSONObject.toJSON(extra);
                values.putAll(extraObject);
            }
        }
    }

    @Override
    public int size() {
        initValues();
        return values.size();
    }

    @Override
    @JSONField(serialize = false)
    public boolean isEmpty() {
        if (values != null){
            return values.isEmpty();
        }
        return true;
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        initValues();
        return values.containsKey(key);
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        initValues();
        return values.containsValue(value);
    }

    @Nullable
    @Override
    public Object get(@Nullable Object key) {
        initValues();
        return values.get(key);
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
        initValues();
        return values.keySet();
    }

    @NonNull
    @Override
    public Collection<Object> values() {
        initValues();
        return values.values();
    }

    @NonNull
    @Override
    public Set<Entry<String, Object>> entrySet() {
        initValues();
        return values.entrySet();
    }

    public static QueryBean create(){
        return new QueryBean();
    }
}
