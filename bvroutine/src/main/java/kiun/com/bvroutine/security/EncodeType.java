package kiun.com.bvroutine.security;

import android.text.TextUtils;

public enum EncodeType {

    /**
     * 参数加密.
     */
    P,

    /**
     * BODY 加密.
     */
    B,

    /**
     * 未加密.
     */
    N;

    public static EncodeType getType(String type){

        if (!TextUtils.isEmpty(type)){
            switch (type){
                case "P":
                    return P;
                case"B":
                    return B;
            }
        }
        return N;
    }
}