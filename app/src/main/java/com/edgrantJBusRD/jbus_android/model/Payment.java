package com.edgrantJBusRD.jbus_android.model;

import java.sql.Timestamp;
import java.util.List;

public class Payment extends Serializable {
    public List<String> busSeats;
    public Timestamp departureDate;
}