package com.szxgm.gusustreet.model.dto.river;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class RiverPlain implements Parcelable {

    private String id;

    private String code;

    private String riveRnme;

    private String image;

    private Date lastPatrol;

    public RiverPlain(){
    }

    protected RiverPlain(Parcel in) {
        id = in.readString();
        code = in.readString();
        riveRnme = in.readString();
        image = in.readString();
    }

    public static final Creator<RiverPlain> CREATOR = new Creator<RiverPlain>() {
        @Override
        public RiverPlain createFromParcel(Parcel in) {
            return new RiverPlain(in);
        }

        @Override
        public RiverPlain[] newArray(int size) {
            return new RiverPlain[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getRiveRnme() {
        return riveRnme;
    }

    public void setRiveRnme(String riveRnme) {
        this.riveRnme = riveRnme == null ? null : riveRnme.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Date getLastPatrol() {
        return lastPatrol;
    }

    public void setLastPatrol(Date lastPatrol) {
        this.lastPatrol = lastPatrol;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(code);
        dest.writeString(riveRnme);
        dest.writeString(image);
    }
}
