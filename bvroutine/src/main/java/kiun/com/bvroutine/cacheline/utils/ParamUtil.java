package kiun.com.bvroutine.cacheline.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.utils.ObjectUtil;
import retrofit2.Invocation;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class ParamUtil {

    private static boolean isWithAnnotation(Annotation[] annotations, Class<? extends Annotation> annotationClz){
        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i].annotationType().equals(annotationClz)){
                return true;
            }
        }
        return false;
    }

    private static boolean isWithParamMap(Class<? extends Annotation> type){
        return type.equals(FieldMap.class)
                ||type.equals(QueryMap.class)
                ||type.equals(HeaderMap.class)
                ||type.equals(PartMap.class);
    }

    private static boolean isWithParamMap(Annotation[] annotations){
        for(Annotation annotation : annotations){
            if (isWithParamMap(annotation.annotationType())){
                return true;
            }
        }
        return false;
    }

    private static boolean isParamSingle(Class<? extends Annotation> type){
        return type.equals(Field.class)
                ||type.equals(Header.class)
                ||type.equals(Query.class)
                ||type.equals(Path.class)
                ||type.equals(Part.class);
    }

    private static boolean isParamSingle(Annotation[] annotations){
        for(Annotation annotation : annotations){
            if (isParamSingle(annotation.annotationType())){
                return true;
            }
        }
        return false;
    }

    private static String valueOfAnnotation(Annotation[] annotations){

        for (Annotation annotation : annotations){
            if (isParamSingle(annotation.annotationType())){
                return ObjectUtil.get(annotation, "value", String.class);
            }
        }
        return null;
    }

    public static Map<String, Object> paramFormInvocation(Invocation invocation){

        Map<String, Object> params = new HashMap<>();
        Method method = invocation.method();
        List<?> arguments = invocation.arguments();

        for (int i = 0; i < method.getParameterAnnotations().length; i++) {

            Annotation[] annotations = method.getParameterAnnotations()[i];
            Class<?> type = method.getParameterTypes()[i];
            Object value = arguments.get(i);

            if (isWithAnnotation(annotations, Body.class)){
                Object json = JSON.toJSON(value);
                if (json instanceof JSONObject){
                    params.putAll((Map<? extends String, ?>) json);
                }else if (json instanceof JSONArray){
                    params.put("body", json);
                }
            } else {
                if (isWithParamMap(annotations) && value instanceof Map){
                    params.putAll((Map<? extends String, ?>) value);
                }else if (isParamSingle(annotations)){
                    String keyName = valueOfAnnotation(annotations);
                    params.put(keyName, value);
                }
            }
        }

        return params;
    }
}
