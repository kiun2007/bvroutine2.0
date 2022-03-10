package kiun.com.bvroutine.service;

import android.app.AlertDialog;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import kiun.com.bvroutine.ActivityApplication;
import kiun.com.bvroutine.base.BaseService;
import kiun.com.bvroutine.test.Command;
import kiun.com.bvroutine.test.ConfigType;
import kiun.com.bvroutine.utils.SharedUtil;

/**
 * 调试服务
 */
public class DebugService extends BaseService implements Runnable {

    private boolean isRun = true;
    private static final int HeadByte = 0x2F;
    private static final int TailByte = 0xF2;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(this).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRun = false;
    }

    @Override
    public void run() {

        try {
            DatagramSocket datagramSocket = new DatagramSocket(20521);
            while (isRun){
                byte[] headBuff = new byte[60];
                DatagramPacket dataPacket = new DatagramPacket(headBuff, headBuff.length);

                try {
                    datagramSocket.receive(dataPacket);
                    byte[] bytes = dataPacket.getData();

                    Command command = Command.fromBytes(bytes);
                    if(command != null){
                        debugHandler.sendMessage(Message.obtain(debugHandler, 0, command));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private final Handler debugHandler = new Handler(ActivityApplication.getApplication().getMainLooper()){

        @Override
        public void handleMessage(@NonNull Message msg) {
            Command command = (Command) msg.obj;
            if (command != null){

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityApplication.getApplication().getTop());
                builder.setTitle("请求控制")
                        .setMessage(String.format("局域网请求执行<%s>，是否同意执行", command.getMessage()))
                        .setNegativeButton("不执行", null)
                        .setPositiveButton("执行", (v, n)->{
                            if(command.getType() == ConfigType.Shared){
                                String[] values = command.getData().split(":=");
                                if (values.length == 2){
                                    SharedUtil.saveValue(values[0], values[1]);
                                }
                            }
                        })
                        .create()
                        .show();
            }
        }
    };
}
