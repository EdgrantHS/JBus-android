package com.edgrantJBusRD.jbus_android;

public class BusView {

    private String busName;
    private String deptStation;
    private String arrStation;

    public BusView(String busName, String deptStation, String arrStation) {
        this.busName = busName;
        this.deptStation = deptStation;
        this.arrStation = arrStation;
    }

    public String getBusName() {
        return this.busName;
    }

    public String getDeptStation() {
        return this.deptStation;
    }

    public String getArrStation() {
        return this.arrStation;
    }
}
