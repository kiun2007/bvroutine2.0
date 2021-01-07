package kiun.com.bvroutine;

import android.content.Context;
import android.location.Location;

import androidx.test.InstrumentationRegistry;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import kiun.com.bvroutine.test.TestActivity;
import kiun.com.bvroutine.utils.HTTPUtil;
import kiun.com.bvroutine.utils.JexlUtil;
import kiun.com.bvroutine.utils.file.MimeTypeUtil;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {


    @Rule
    public ActivityTestRule<TestActivity> mActivityRule = new ActivityTestRule<>(TestActivity.class);

    @Test
    public void testShortListView(){
    }

    public Location addLocation(double lng, double lat){

        Location location = new Location("");
        location.setLatitude(lat);
        location.setLongitude(lng);
        return location;
    }

    private static JSONObject postURL(String url, String data){
        String content = HTTPUtil.postURL(url, data);
        if (content.indexOf("{") != 0){
            return null;
        }
        return JSONObject.parseObject(content);
    }

    private static JSONObject postURLObject(String url, String data, String objectName){
        JSONObject object = postURL(url, data);
        return object.getJSONObject("data").getJSONObject(objectName);
    }

    private static JSONArray postURLList(String url, String data, String listName){
        JSONObject object = postURL(url, data);
        return object.getJSONObject("data").getJSONArray(listName);
    }

    @Test
    public void useJexl(){

        int[] values = (int[]) JexlUtil.run("[0,1]");

        int astr = 0;
        int astr1 = 0;
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();


//        JSONArray areaList = postURLList("http://36.153.23.211:8001/smart_city_hzz/mobile/v1/areaList.action", "{}", "areaList");
//        areaList.remove(0);
//
//        List<JSONObject> allRiverList = new LinkedList<>();
//
//        for (int i = 0; i < areaList.size(); i++) {
//            JSONObject item = areaList.getJSONObject(i);
//            String id = item.getString("id");
//            item.put("areaId", id);
//            item.put("rivername", "");
//            item.remove("id");
//            item.remove("name");
//
//            JSONArray riverList = postURLList("http://36.153.23.211:8001/smart_city_hzz/mobile/v1/riverList.action", item.toJSONString(), "riverList");
//
//            for (int j = 0; j < riverList.size(); j++) {
//                JSONObject riverInfo = riverList.getJSONObject(j);
//
//                JSONObject river = postURLObject("http://36.153.23.211:8001/smart_city_hzz/mobile/v1/riverDetail.action",
//                        "{\"id\":\""+riverInfo.getString("id")+"\"}","river");
//                String locationURL = getLatLng(river);
//
//                river.put("image", "http://36.153.23.211:8001" + riverInfo.getString("image"));
//                river.put("lnglat", locationURL);
//                river.put("area", id);
//                allRiverList.add(river);
//                postURL("http://192.168.1.107:8019/app/river/record", river.toJSONString());
//            }
//        }
//
//        assertEquals("kiun.com.bvroutine.test", appContext.getPackageName());
    }

    private String getLatLng(JSONObject river) {

        if (river.getString("id").equals("36ef11a5e3ea49f991daa78a3c1dd8fe")){
            river.put("lnglat", "[[120.63839,31.2924],[120.63515,31.292],[120.62777,31.29139],[120.61302,31.28962],[120.61163,31.28988],[120.61082,31.28983],[120.61058,31.29013],[120.61184,31.29045],[120.61322,31.29031],[120.61493,31.29035],[120.61893,31.29088],[120.62021,31.29116],[120.62435,31.29159],[120.62835,31.29226],[120.63173,31.2924],[120.63549,31.29283],[120.63796,31.29292],[120.63982,31.29326],[120.64068,31.29364],[120.64096,31.29302],[120.64034,31.29278],[120.61174,31.28919],[120.61127,31.28912],[120.61027,31.28984],[120.60738,31.30688],[120.60806,31.30675],[120.61069,31.29],[120.61082,31.28983],[120.61249,31.28923],[120.61174,31.28919]]");
        }

        if (river.getString("id").equals("7696b4a3ee5541c9ac9abb942a903d09")){
            river.put("lnglat","[[120.58842,31.29668],[120.58842,31.29667],[120.58839,31.29668],[120.58842,31.29668],[120.58842,31.29667],[120.58839,31.29668],[120.58842,31.29668],[120.58842,31.29667],[120.58839,31.29668],[120.58842,31.29668]]");
        }

        if (river.getString("id").equals("fed055163efc4c8db7fcf83ff138fb9c")){
            river.put("lnglat","[[120.58842,31.29668],[120.58842,31.29667],[120.58839,31.29668],[120.58595,31.31507],[120.58837,31.30047],[120.58808,31.30045],[120.58588,31.31359],[120.58571,31.31506],[120.58595,31.31507]]");
        }

        String lnglat = river.getString("lnglat");
        try{
            JSONArray array = JSONObject.parseArray(lnglat);
//            List<Location> locations = new LinkedList<>();
//            for (int k = 0; k < array.size(); k++) {
//                JSONArray latLngs = array.getJSONArray(k);
//
//                locations.add(addLocation(latLngs.getBigDecimal(0).doubleValue(),latLngs.getBigDecimal(1).doubleValue()));
//            }
//
//            return LocationUtils.compressLocation(locations);
            return array.toJSONString();
        }catch (JSONException ex){
            System.out.println(lnglat);
        }

        return null;
    }

    @Test
    public void testMineType() throws IOException, XmlPullParserException {

        Context appContext = InstrumentationRegistry.getTargetContext();
        String type = MimeTypeUtil.getType(appContext, "qpp.aac");
        int a = 0;
    }
}
