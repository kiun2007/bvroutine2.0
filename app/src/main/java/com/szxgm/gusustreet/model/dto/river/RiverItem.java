package com.szxgm.gusustreet.model.dto.river;

import android.os.Parcel;
import android.os.Parcelable;

public class RiverItem implements Parcelable {

    private String id;

    private String riverId;

    private String rivername;

    private String image;

    private String riverlevel;

    private String rivermaste;

    private String area;

    private String areaName;

    public RiverItem(){
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRivername() {
        return rivername;
    }

    public void setRivername(String rivername) {
        this.rivername = rivername == null ? null : rivername.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getRiverlevel() {
        return riverlevel;
    }

    public void setRiverlevel(String riverlevel) {
        this.riverlevel = riverlevel == null ? null : riverlevel.trim();
    }

    public String getRivermaste() {
        return rivermaste;
    }

    public void setRivermaste(String rivermaste) {
        this.rivermaste = rivermaste == null ? null : rivermaste.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRiverId() {
        return riverId;
    }

    public void setRiverId(String riverId) {
        this.riverId = riverId;
    }


    protected RiverItem(Parcel in) {
        id = in.readString();
        riverId = in.readString();
        rivername = in.readString();
        image = in.readString();
        riverlevel = in.readString();
        rivermaste = in.readString();
        area = in.readString();
        areaName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(riverId);
        dest.writeString(rivername);
        dest.writeString(image);
        dest.writeString(riverlevel);
        dest.writeString(rivermaste);
        dest.writeString(area);
        dest.writeString(areaName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RiverItem> CREATOR = new Creator<RiverItem>() {
        @Override
        public RiverItem createFromParcel(Parcel in) {
            return new RiverItem(in);
        }

        @Override
        public RiverItem[] newArray(int size) {
            return new RiverItem[size];
        }
    };

}
