package kiun.com.bvroutine.presenters;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import kiun.com.bvroutine.data.error.ServiceException;
import kiun.com.bvroutine.interfaces.callers.BatchVoidCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.presenter.ExceptionCatcher;

public class ErrorMessageCatcher implements ExceptionCatcher {
    private SetCaller<String> messageCaller;
    private BatchVoidCaller<ErrorMessageCatcher> completeCaller;

    public ErrorMessageCatcher(SetCaller<String> messageCaller, BatchVoidCaller<ErrorMessageCatcher> completeCaller){
        this.messageCaller = messageCaller;
        this.completeCaller = completeCaller;
    }

    public ErrorMessageCatcher(SetCaller<String> messageCaller){
        this(messageCaller, null);
    }

    @Override
    public void onCatch(Exception ex) {
        if (messageCaller == null) return;
        if (ex instanceof SocketTimeoutException){
            messageCaller.call("连接超时");
        }else if (ex instanceof TimeoutException){
            messageCaller.call("超时");
        }else if (ex instanceof SocketException){
            messageCaller.call("连接错误");
        }else if (ex instanceof IOException){
            messageCaller.call("文件读写错误");
        }else if (ex instanceof ServiceException){
            messageCaller.call("服务器错误");
        }else if (ex instanceof NullPointerException){
            messageCaller.call("空指针");
        }else{
            messageCaller.call(ex.getMessage());
        }

        if (completeCaller != null){
            completeCaller.call(this);
        }
    }
}
