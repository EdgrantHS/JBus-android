package com.edgrantJBusRD.jbus_android;


public class BusView {

    private String busName;

    public BusView(String busName) {
        this.busName = busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusName() {
        return this.busName;
    }
}
