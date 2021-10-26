package kiun.com.bvroutine.data;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import kiun.com.bvroutine.base.EventBean;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BodyBean extends EventBean {

    @JSONField(serialize = false)
    public RequestBody toBody(){
        String jsonString = JSON.toJSONString(this);
        return RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), jsonString);
    }
}
