package com.edgrantJBusRD.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import com.edgrantJBusRD.jbus_android.model.Facility;

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

        String busName = BusArrayAdapter.selectedBus.name;
        String departureStation = BusArrayAdapter.selectedBus.departure.stationName;
        String arrivalStation = BusArrayAdapter.selectedBus.arrival.stationName;
        String price = String.valueOf(BusArrayAdapter.selectedBus.price.price);
        String capacity = String.valueOf(BusArrayAdapter.selectedBus.capacity);

        valueBusName.setText(busName);
        valueDStation.setText(departureStation);
        valueAStation.setText(arrivalStation);
        valuePrice.setText(price);
        valueCapacity.setText(capacity);

        // Set CheckBox states (example states used here)
        cbAC.setChecked(BusArrayAdapter.selectedBus.facilities.contains(Facility.AC));
        cbLCDTV.setChecked(BusArrayAdapter.selectedBus.facilities.contains(Facility.LCD_TV));
        cbLargeBeverage.setChecked(BusArrayAdapter.selectedBus.facilities.contains(Facility.LARGE_BAGGAGE));
        cbWIFI.setChecked(BusArrayAdapter.selectedBus.facilities.contains(Facility.WIFI));
        cbCoolbox.setChecked(BusArrayAdapter.selectedBus.facilities.contains(Facility.COOL_BOX));
        cbElectronicSocket.setChecked(BusArrayAdapter.selectedBus.facilities.contains(Facility.ELECTRIC_SOCKET));
        cbToilet.setChecked(BusArrayAdapter.selectedBus.facilities.contains(Facility.TOILET));
        cbLunch.setChecked(BusArrayAdapter.selectedBus.facilities.contains(Facility.LUNCH));
    }
}
