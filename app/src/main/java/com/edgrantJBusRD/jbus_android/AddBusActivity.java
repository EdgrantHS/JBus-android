package com.edgrantJBusRD.jbus_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;

import com.edgrantJBusRD.jbus_android.model.BaseResponse;
import com.edgrantJBusRD.jbus_android.model.Bus;
import com.edgrantJBusRD.jbus_android.model.BusType;
import com.edgrantJBusRD.jbus_android.model.Facility;
import com.edgrantJBusRD.jbus_android.model.Station;
import com.edgrantJBusRD.jbus_android.request.BaseApiService;
import com.edgrantJBusRD.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBusActivity extends AppCompatActivity {
    private final BusType[] busType = BusType.values();
    private BusType selectedBusType;
    private Spinner busTypeSpinner;
    private Spinner departureSpinner;
    private Spinner arivalSpinner;
    private List<Station> stationList = new ArrayList<>();

    List<String> stationNameList = new ArrayList<>();
    private int selectedDeptStationID;
    private int selectedArrStationID;
    private final Context mContext = this;
    private BaseApiService mApiService;

    //check box
    private CheckBox cAC, cLCDTV, cLargeBeverage;
    private CheckBox cWIFI, cCoolbox, cElectronicSocket;
    private CheckBox cToilet, cLunch;
    private List<Facility> selectedFacilities = new ArrayList<>();

    //text input
    TextView name = null;
    TextView capacity = null;
    TextView price = null;


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
            selectedDeptStationID = position;
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
            selectedArrStationID = position;
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
        Button addButton = findViewById(R.id.addButton);
        name = findViewById(R.id.BusName);
        price = findViewById(R.id.Price);
        capacity = findViewById(R.id.Capacity);

        ArrayAdapter adBus = new ArrayAdapter(this, android.R.layout.simple_list_item_1, busType);
        adBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        busTypeSpinner.setAdapter(adBus);
        // menambahkan OISL (OnItemSelectedListener) untuk spinner
        busTypeSpinner.setOnItemSelectedListener(busTypeOISL);

        handleStation();
        departureSpinner = this.findViewById(R.id.departure_type_dropdown);
        arivalSpinner = this.findViewById(R.id.arrival_type_dropdown);
        System.out.println(stationList);
//
//        ArrayAdapter deptBus = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stationNameList);
//        deptBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
//        departureSpinner.setAdapter(deptBus);
//        departureSpinner.setOnItemSelectedListener(deptOISL);
//
//
//        ArrayAdapter arvBus = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stationNameList);
//        arvBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
//        arivalSpinner.setAdapter(arvBus);
//        arivalSpinner.setOnItemSelectedListener(arvOISL);

        //check box
        cAC = findViewById(R.id.AC);
        cLCDTV = findViewById(R.id.LCDTV);
        cLargeBeverage = findViewById(R.id.LargeBeverage);
        cWIFI = findViewById(R.id.WIFI);
        cCoolbox = findViewById(R.id.Coolbox);
        cElectronicSocket = findViewById(R.id.ElectronicSocket);
        cToilet = findViewById(R.id.Toilet);
        cLunch = findViewById(R.id.Lunch);
        //add bus button
        addButton.setOnClickListener(view -> {
            selectedFacilities.clear(); // Clear the list before updating
            if (cAC.isChecked()) { selectedFacilities.add(Facility.AC);}
            if (cLCDTV.isChecked()) { selectedFacilities.add(Facility.LCD_TV);}
            if (cLargeBeverage.isChecked()) { selectedFacilities.add(Facility.LARGE_BAGGAGE);}
            if (cWIFI.isChecked()) { selectedFacilities.add(Facility.WIFI);}
            if (cCoolbox.isChecked()) { selectedFacilities.add(Facility.COOL_BOX);}
            if (cElectronicSocket.isChecked()) { selectedFacilities.add(Facility.ELECTRIC_SOCKET);}
            if (cToilet.isChecked()) { selectedFacilities.add(Facility.TOILET);}
            if (cLunch.isChecked()) { selectedFacilities.add(Facility.LUNCH);}

            handleCreateBus();
        });
    }


    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    protected void handleStation() {
        // pasrah dulu
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
//                List<Station> res = response.body();
//                finish();

//                if(res != null) {
                stationList = response.body(); //simpan response body ke stationList
                //mengambil nama station
                assert stationList != null;
                for (Station singleStation :
                        stationList) {
                    stationNameList.add(singleStation.stationName);
                }
                System.out.println(stationList);
                System.out.println("stationList");

                ArrayAdapter deptBus = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stationNameList);
                deptBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                departureSpinner.setAdapter(deptBus);
                departureSpinner.setOnItemSelectedListener(deptOISL);


                ArrayAdapter arvBus = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stationNameList);
                arvBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                arivalSpinner.setAdapter(arvBus);
                arivalSpinner.setOnItemSelectedListener(arvOISL);

                Toast.makeText(mContext, "berhasil dapat station", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(mContext, "station doesn't exist", Toast.LENGTH_SHORT).show();
//                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Station>> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
//        //cara curang
//        stationList.add(new Station(City.DEPOK, "Jl. Jatijajar 1 No.04, Jatijajar, Kec. Tapos, Jawa Barat", "Terminal Jatijajar"));
//        stationList.add(new Station(City.JAKARTA, "RT.2/RW.8, Pasar Manggis, Kota Jakarta Selatan", "Terminal Manggarai"));
//        stationList.add(new Station(City.SUKABUMI, "Jalan Lingkar Selatan Nomor 7, Kota Sukabumi", "Terminal Sukabumi"));
//        stationList.add(new Station(City.SURABAYA, "Kasian, Bungurasih, Kabupaten Sidoarjo, Jawa Timur", "Terminal Purabaya"));
//        stationList.add(new Station(City.SURABAYA, "Giwangan, Kec. Umbulharjo, Daerah Istimewa Yogyakarta", "Terminal Giwangan"));
    }

    protected void handleCreateBus() {
        // handling empty field
        int iId = LoginActivity.loggedAccount.id;
        String sName = name.getText().toString();
        String sCapacity = capacity.getText().toString();
        List<Facility>  lSelectedFacilities = selectedFacilities;
        BusType bBusType = selectedBusType;
        String sPrice = price.getText().toString();
        int iDepart = selectedDeptStationID;
        int iArive = selectedArrStationID;

        if (
            sName.isEmpty() ||
            sCapacity.isEmpty() ||
            sPrice.isEmpty()
        ) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        int iPrice = Integer.parseInt(sPrice);
        int iCapacity = Integer.parseInt(sCapacity);
        mApiService.createBus(
                iId, sName, iCapacity, lSelectedFacilities, bBusType, iPrice, iDepart, iArive
        ).enqueue(new Callback<BaseResponse<Bus>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<Bus>> call, @NonNull Response<BaseResponse<Bus>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " +
                            response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Bus> res = response.body();
                // if success finish this activity (back to login activity)
                assert res != null;
//                System.out.println("1" + res.message);
//                System.out.println(
//                        "iId" + iId + "\n" +
//                        "sName" + sName + "\n" +
//                        "iCapacity" + iCapacity + "\n" +
//                        "lSelectedFacilities" + lSelectedFacilities + "\n" +
//                        "bBusType" + bBusType + "\n" +
//                        "iPrice" + iPrice + "\n" +
//                        "iDepart" + iDepart + "\n" +
//                        "iArive" + iArive);
                if (res.success) finish();
                moveActivity(mContext, ManageBusActivity.class);
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(@NonNull Call<BaseResponse<Bus>> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}