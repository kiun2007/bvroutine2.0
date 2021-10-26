package com.szxgm.gusustreet;

import android.content.Context;
import android.location.Location;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.amap.api.maps2d.CoordinateConverter;
import com.amap.api.maps2d.model.LatLng;
import com.szxgm.gusustreet.utils.LatLngUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.utils.LocationUtils;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    public Location addLocation(double lng, double lat){

        Location location = new Location("");
        location.setLatitude(lat);
        location.setLongitude(lng);
        return location;
    }


    @Test
    public void useAppContext() {

        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        double[] latLng2 = LatLngUtil.gcj02ToWGS84(120, 31);

        LatLng latLng1 = LatLngUtil.convert(addLocation(latLng2[0],latLng2[1]), CoordinateConverter.CoordType.GPS);

        List<Location> locations = new LinkedList<>();

        locations.add(addLocation(120.60049821350859,31.27821344546207));
        locations.add(addLocation(120.60032655213163,31.27832806373594));
        locations.add(addLocation(120.60029973004148,31.278410588806963));
        locations.add(addLocation(120.6002675435333,31.27852979155986));
        locations.add(addLocation(120.6002685435333,31.278745273076815));
        locations.add(addLocation(120.60027844168089,31.278745273076815));
        locations.add(addLocation(120.60028844168089,31.278745273076815));
        locations.add(addLocation(120.60030844168089,31.278745273076815));
        locations.add(addLocation(120.60109244168089,31.278785273076815));

        String compress = LocationUtils.compressLocation(locations);

        System.out.println("========" + compress.length());
        List<Location> newLocations = LocationUtils.uncompress(compress);

        System.out.print("\n");
        for (int i = 0; i < locations.size(); i++) {
            System.out.print(i + "->");
            System.out.print(newLocations.get(i).distanceTo(locations.get(i)) + "\n");
        }

        assertEquals("com.szxgm.gusustreet", appContext.getPackageName());
    }
}
