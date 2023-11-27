package com.edgrantJBusRD.jbus_android;

public class CalenderBusView {
    private String busName;
    private int busId;

    public CalenderBusView(String busName, int busId) {
        this.busName = busName;
        this.busId = busId;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusName() {
        return this.busName;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getBusId() {
        return busId;
    }
}