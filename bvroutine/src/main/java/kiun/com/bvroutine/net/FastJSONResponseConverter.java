package kiun.com.bvroutine.net;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import kiun.com.bvroutine.interfaces.wrap.DepthWrap;
import kiun.com.bvroutine.interfaces.wrap.ListWrap;
import kiun.com.bvroutine.utils.ObjectUtil;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class FastJSONResponseConverter implements Converter<ResponseBody, Object> {

    private Type type;
    public FastJSONResponseConverter(Type type){
        this.type = type;
    }

    @Override
    public Object convert(ResponseBody value) throws IOException {

        String bodyString = value.string().replace("{}", "null");
        Log.i("Response", bodyString);

        Object ret;
        if (type instanceof Class && Number.class.isAssignableFrom((Class<?>) type)){
            ret = ObjectUtil.newObject((Class<?>)type, bodyString);
        }else{
            if(type.equals(String.class)){
                return bodyString;
            }
            ret = JSONObject.parseObject(bodyString, type);
        }

        DepthWrap depthWarp = ret.getClass().getAnnotation(DepthWrap.class);
        if (depthWarp != null){

            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] types = parameterizedType.getActualTypeArguments();

            if (types.length > 0 && !types[0].equals(Object.class)){

                Type itemType = types[0];
                ListWrap listWrap = (ListWrap) ret;

                if (listWrap.wrapList() != null && !listWrap.wrapList().isEmpty()){

                    for (int i = 0; i < listWrap.wrapList().size(); i++){

                        JSONObject source = listWrap.wrapList().get(i) instanceof JSONObject ? (JSONObject) listWrap.wrapList().get(i) : null;
                        if (source != null){
                            listWrap.wrapList().set(i, JSONObject.parseObject(source.toJSONString(), itemType));
                        }
                    }
                }
            }
        }
        return ret;
    }
}