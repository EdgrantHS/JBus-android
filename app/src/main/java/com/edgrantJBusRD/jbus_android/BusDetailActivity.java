package com.edgrantJBusRD.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

public class BusDetailActivity extends AppCompatActivity {

    private TextView valueBusName;
    private TextView valueDStation;
    private TextView valueAStation;
    private TextView valuePrice;
    private TextView valueCapacity;
    private CheckBox cbAC;
    private CheckBox cbLCDTV;
    private CheckBox cbLargeBeverage;
    private CheckBox cbWIFI;
    private CheckBox cbCoolbox;
    private CheckBox cbElectronicSocket;
    private CheckBox cbToilet;
    private CheckBox cbLunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);

        // Initialize TextViews
        valueBusName = findViewById(R.id.valueBusName);
        valueDStation = findViewById(R.id.valueDStation);
        valueAStation = findViewById(R.id.valueAStation);
        valuePrice = findViewById(R.id.valuePrice);
        valueCapacity = findViewById(R.id.valueCapacity);

        // Initialize CheckBoxes
        cbAC = findViewById(R.id.cbAC);
        cbLCDTV = findViewById(R.id.cbLCDTV);
        cbLargeBeverage = findViewById(R.id.cbLargeBeverage);
        cbWIFI = findViewById(R.id.cbWIFI);
        cbCoolbox = findViewById(R.id.cbCoolbox);
        cbElectronicSocket = findViewById(R.id.cbElectronicSocket);
        cbToilet = findViewById(R.id.cbToilet);
        cbLunch = findViewById(R.id.cbLunch);

        // Set values based on variables (example values used here)
        String busName = "Express Bus";
        String departureStation = "Central Station";
        String arrivalStation = "Downtown";
        String price = "$20";
        String capacity = "40 seats";

        valueBusName.setText(busName);
        valueDStation.setText(departureStation);
        valueAStation.setText(arrivalStation);
        valuePrice.setText(price);
        valueCapacity.setText(capacity);

        // Set CheckBox states (example states used here)
        cbAC.setChecked(true);
        cbLCDTV.setChecked(false);
        cbLargeBeverage.setChecked(true);
        cbWIFI.setChecked(true);
        cbCoolbox.setChecked(false);
        cbElectronicSocket.setChecked(true);
        cbToilet.setChecked(true);
        cbLunch.setChecked(false);
    }
}
