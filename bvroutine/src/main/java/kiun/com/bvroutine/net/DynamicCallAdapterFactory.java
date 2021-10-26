package kiun.com.bvroutine.net;

import com.alibaba.fastjson.util.ParameterizedTypeImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

class DynamicCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {

        if (getRawType(returnType) == Call.class){
            if (!(returnType instanceof ParameterizedType)) {
                throw new IllegalStateException("返回值必须包含类型参数 (e.g., Call<ResponseBody>)");
            }

            Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
            Executor callbackExecutor = retrofit.callbackExecutor();
            return new DynamicCallAdapter(responseType, callbackExecutor);
        }
        return null;
    }

    private class DynamicCallAdapter<T> implements CallAdapter<T, Call<T>> {

        private final Type responseType;
        private final Executor callbackExecutor;

        private DynamicCallAdapter(Type responseType, Executor callbackExecutor) {
            this.responseType = responseType;
            this.callbackExecutor = callbackExecutor;
        }

        @Override
        public Type responseType() {

            return responseType;
        }

        @Override
        public Call<T> adapt(Call<T> call) {
            return call;
        }
    }
}
