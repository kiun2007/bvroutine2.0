package kiun.com.bvroutine.test;

public class Command {

    private ConfigType type;

    private String data;

    public ConfigType getType() {
        return type;
    }

    public void setType(ConfigType type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage(){
        if(type == ConfigType.Shared){
            return String.format("%s,[%s]", type.getMessage(), data);
        }
        return type.getMessage();
    }

    public static byte countVerity(byte[] bytes, int offset, int count){

        int sum = 0;
        for (int i = offset; i < offset + count; i++) {
            sum += bytes[i];
        }

        return (byte) (sum & 0xFF);
    }

    public static Command fromBytes(byte[] bytes){

        Command command = new Command();
        //头校验
        if(bytes[0] != 0x2F){
            return null;
        }

        //数据长度
        int length = bytes[1];
        if(countVerity(bytes, 1, length + 1) != bytes[length + 3]){
            //和校验失败
            return null;
        }

        command.type = ConfigType.findType(bytes[2]);
        if (length > 0){
            command.data = new String(bytes, 3, length);
        }
        return command;
    }
}
