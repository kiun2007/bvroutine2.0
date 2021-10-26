package kiun.com.bvroutine.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class ParcelableUtil {

    public static void writeDate(Parcel parcel, Date date){
        parcel.writeLong(date == null ? 0 : date.getTime());
    }

    public static Date readDate(Parcel in){
        long time = in.readLong();
        if (time == 0){
            return null;
        }
        return new Date(time);
    }

    public static void writeList(Parcel parcel, List<? extends Parcelable> list){
        parcel.writeList(list);
    }
}
