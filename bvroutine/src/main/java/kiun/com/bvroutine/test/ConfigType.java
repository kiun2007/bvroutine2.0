package kiun.com.bvroutine.test;

public enum ConfigType {

    /**
     * 重启命令
     */
    Restart(0, "重启应用"),

    /**
     * 开启日志
     */
    OpenLog(1, "开启日志"),

    /**
     * 关闭日志
     */
    CloseLog(2, "关闭日志"),

    /**
     * 打开日志页面
     */
    OpenLogActivity(3,"打开日志页面"),

    /**
     * 本地Shared数据库
     */
    Shared(4,"修改Shared数据");

    private int type;

    private String message;

    ConfigType(int type, String message){
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public static ConfigType findType(int type){
        for (ConfigType codeType : ConfigType.values()){
            if(codeType.getType() == type){
                return codeType;
            }
        }
        return null;
    }

    public String getMessage() {
        return message;
    }
}
