package com.edgrantJBusRD.jbus_android.model;

public class Station extends Serializable {
    public String stationName;
    public City city;
    public String address;

    public Station(City city, String address, String stationName){
        this.stationName = stationName;
        this.city = city;
        this.address = address;
    }
}