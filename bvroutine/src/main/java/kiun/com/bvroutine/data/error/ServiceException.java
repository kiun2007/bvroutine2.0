package kiun.com.bvroutine.data.error;

/**
 * 服务器异常
 */
public class ServiceException extends Exception{

    int errorCode;

    public ServiceException(int errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public int getCode() {
        return errorCode;
    }
}
