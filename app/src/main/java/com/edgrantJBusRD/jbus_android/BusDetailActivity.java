package com.edgrantJBusRD.jbus_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.edgrantJBusRD.jbus_android.model.BaseResponse;
import com.edgrantJBusRD.jbus_android.model.Bus;
import com.edgrantJBusRD.jbus_android.model.BusType;
import com.edgrantJBusRD.jbus_android.model.Facility;
import com.edgrantJBusRD.jbus_android.model.Payment;
import com.edgrantJBusRD.jbus_android.model.Schedule;
import com.edgrantJBusRD.jbus_android.request.BaseApiService;
import com.edgrantJBusRD.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusDetailActivity extends AppCompatActivity {
    private final Context mContext = this;
    private BaseApiService mApiService;
    private TextView seatNumTxt = null;
    private String bookSchedule = null;
    private List<String> scheduleList = new ArrayList<>();

    AdapterView.OnItemSelectedListener scheduleOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            bookSchedule = scheduleList.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);

        mApiService = UtilsApi.getApiService();

        // Initialize TextViews
        TextView valueBusName = findViewById(R.id.valueBusName);
        TextView valueDStation = findViewById(R.id.valueDStation);
        TextView valueAStation = findViewById(R.id.valueAStation);
        TextView valuePrice = findViewById(R.id.valuePrice);
        TextView valueCapacity = findViewById(R.id.valueCapacity);
        Button bookSeatButton = findViewById(R.id.buyTicketBtn);
        seatNumTxt = findViewById(R.id.seatNumTxt);

        // Initialize CheckBoxes
        CheckBox cbAC = findViewById(R.id.cbAC);
        CheckBox cbLCDTV = findViewById(R.id.cbLCDTV);
        CheckBox cbLargeBeverage = findViewById(R.id.cbLargeBeverage);
        CheckBox cbWIFI = findViewById(R.id.cbWIFI);
        CheckBox cbCoolbox = findViewById(R.id.cbCoolbox);
        CheckBox cbElectronicSocket = findViewById(R.id.cbElectronicSocket);
        CheckBox cbToilet = findViewById(R.id.cbToilet);
        CheckBox cbLunch = findViewById(R.id.cbLunch);
        Spinner schedSpinner = findViewById(R.id.schedule_dropdown);

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



        //spinner
        List<Schedule> scheduleListTmp = BusArrayAdapter.selectedBus.schedules;
        for (Schedule single :
                scheduleListTmp) {
            scheduleList.add(single.departureSchedule.toString());
        }
        ArrayAdapter schedAdpt = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, scheduleList);
        schedAdpt.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        schedSpinner.setAdapter(schedAdpt);
        schedSpinner.setOnItemSelectedListener(scheduleOISL);

        bookSeatButton.setOnClickListener(view -> {
            handleBookSeat();
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.account_button) {
            moveActivity(this, AboutMeActivity.class);
            return true;
        }

        if (item.getItemId() == R.id.payment_button) {
            moveActivity(this, MainActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }


    protected void handleBookSeat() {
        int buyerId = LoginActivity.loggedAccount.id;
        int renterId = BusArrayAdapter.selectedBus.accountId;
        int busId = BusArrayAdapter.selectedBus.id;
        List<String> busSeats = new ArrayList<>();
        busSeats.add(seatNumTxt.getText().toString());
        BusType busType = BusArrayAdapter.selectedBus.busType;
        String departureDate = bookSchedule;

        if (departureDate.isEmpty() || busSeats.get(0).isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.makeBooking(
                buyerId, renterId, busId, busSeats, busType, departureDate
        ).enqueue(new Callback<BaseResponse<Payment>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<Payment>> call, @NonNull Response<BaseResponse<Payment>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " +
                            response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Payment> res = response.body();
                // if success finish this activity (back to login activity)
                assert res != null;
                if (res.success) finish();
                moveActivity(mContext, MainActivity.class);
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(@NonNull Call<BaseResponse<Payment>> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
