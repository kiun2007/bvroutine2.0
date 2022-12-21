package kiun.com.bvroutine.data.json;

import com.google.gson.Gson;

/**
 * Gson 单例
 */
public class GsonInstance {

    private static Gson gson;

    public static Gson getInstance() {
        if(gson == null){
            gson = new Gson();
        }
        return gson;
    }
}
