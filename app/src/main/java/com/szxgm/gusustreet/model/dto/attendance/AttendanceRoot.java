package com.szxgm.gusustreet.model.dto.attendance;

public class AttendanceRoot {

    public AttendanceRoot(String title, String value, int color, int id, boolean visible) {
        this.title = title;
        this.value = value;
        this.color = color;
        this.id = id;
        this.visible = visible;
    }

    private boolean visible;

    private int id;

    private String title;

    private String value;

    private int color;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
