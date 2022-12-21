package kiun.com.bvroutine.net;

import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import kiun.com.bvroutine.data.json.GsonInstance;
import kiun.com.bvroutine.interfaces.wrap.DepthWrap;
import kiun.com.bvroutine.interfaces.wrap.ListWrap;
import kiun.com.bvroutine.utils.ObjectUtil;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class GsonResponseConverter implements Converter<ResponseBody, Object> {

    private Type type;
    public GsonResponseConverter(Type type) {
        this.type = type;
    }

    @Nullable
    @Override
    public Object convert(ResponseBody value) throws IOException {

        String bodyString = value.string().replace("{}", "null");
        Log.i("Gson Response", bodyString);

        Object ret;
        if (type instanceof Class && Number.class.isAssignableFrom((Class<?>) type)){
            ret = ObjectUtil.newObject((Class<?>)type, bodyString);
        }else{
            if(type.equals(String.class)){
                return bodyString;
            }
            ret = GsonInstance.getInstance().fromJson(bodyString, type);
        }

        //深度类型
        DepthWrap depthWarp = ret.getClass().getAnnotation(DepthWrap.class);
        if (depthWarp != null){

            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] types = parameterizedType.getActualTypeArguments();

            if (types.length > 0 && !types[0].equals(Object.class)){

                Type itemType = types[0];
                ListWrap listWrap = (ListWrap) ret;

                if (listWrap.wrapList() != null && !listWrap.wrapList().isEmpty()){

                    for (int i = 0; i < listWrap.wrapList().size(); i++){

                        Map<String, Object> source = listWrap.wrapList().get(i) instanceof Map ? (Map) listWrap.wrapList().get(i) : null;
                        if (source != null){
                            String jsonStr = GsonInstance.getInstance().toJson(source);
                            listWrap.wrapList().set(i, GsonInstance.getInstance().fromJson(jsonStr, itemType));
                        }
                    }
                }
            }
        }
        return ret;
    }
}
