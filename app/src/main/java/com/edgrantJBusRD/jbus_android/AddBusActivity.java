package com.edgrantJBusRD.jbus_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.edgrantJBusRD.jbus_android.model.BusType;
import com.edgrantJBusRD.jbus_android.model.Station;
import com.edgrantJBusRD.jbus_android.request.BaseApiService;
import com.edgrantJBusRD.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBusActivity extends AppCompatActivity {
    private BusType[] busType = BusType.values();
    private BusType selectedBusType;
    private Spinner busTypeSpinner;
    private Spinner departureSpinner;
    private Spinner arivalSpinner;
    private List<Station> stationList = new ArrayList<>();
    private int selectedDeptStationID;
    private int selectedArrStationID;
    private final Context mContext = this;
    private BaseApiService mApiService;


    AdapterView.OnItemSelectedListener busTypeOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            // mengisi field selectedBusType sesuai dengan item yang dipilih
            selectedBusType = busType[position];
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    AdapterView.OnItemSelectedListener deptOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            // mengisi field selectedBusType sesuai dengan item yang dipilih
            selectedDeptStationID = stationList.get(position).id;
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    AdapterView.OnItemSelectedListener arvOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            // mengisi field selectedBusType sesuai dengan item yang dipilih
            selectedArrStationID = stationList.get(position).id;
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);

        mApiService = UtilsApi.getApiService();
        busTypeSpinner = this.findViewById(R.id.bus_type_dropdown);
        ArrayAdapter adBus = new ArrayAdapter(this, android.R.layout.simple_list_item_1, busType);
        adBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        busTypeSpinner.setAdapter(adBus);
        // menambahkan OISL (OnItemSelectedListener) untuk spinner
        busTypeSpinner.setOnItemSelectedListener(busTypeOISL);

        handleStation();
        departureSpinner = this.findViewById(R.id.departure_type_dropdown);
        arivalSpinner = this.findViewById(R.id.arrival_type_dropdown);

        ArrayAdapter deptBus = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stationList);
        deptBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        departureSpinner.setAdapter(deptBus);
        departureSpinner.setOnItemSelectedListener(deptOISL);


        ArrayAdapter arvBus = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stationList);
        arvBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        arivalSpinner.setAdapter(arvBus);
        arivalSpinner.setOnItemSelectedListener(arvOISL);
    }


    protected void handleStation() {
        mApiService.getAllStation().enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<Station>> call,
                    @NonNull Response<List<Station>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " +
                            response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Station> res = response.body();
                finish();

                if(res != null) {
                    stationList.addAll(res); //simpan response body ke stationList
                    System.out.println(stationList);
                    Toast.makeText(mContext, "berhasil dapat station", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(mContext, "error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Station>> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}