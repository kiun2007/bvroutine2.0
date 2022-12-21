package kiun.com.bvroutine.utils;

import android.util.Base64;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class RsaEncryptUtil {

    public static List<String> createKey() throws NoSuchAlgorithmException{
        return createKey(2048);
    }

    /**
     * 创建秘钥对
     * @param keySize 秘钥大小
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static List<String> createKey(int keySize) throws NoSuchAlgorithmException {

        // 获取RSA算法实例
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(keySize);

        KeyPair keyPair = keyPairGen.generateKeyPair();
        return Arrays.asList(Base64.encodeToString(keyPair.getPrivate().getEncoded(), Base64.NO_WRAP),
                Base64.encodeToString(keyPair.getPublic().getEncoded(), Base64.NO_WRAP));
    }
}
