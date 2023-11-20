package com.edgrantJBusRD.jbus_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.edgrantJBusRD.jbus_android.model.Bus;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Bus> busList = (ArrayList<Bus>) Bus.sampleBusList(10);
        ArrayList<BusView> busViewList = new ArrayList<>();

        for (Bus singleBus :
                busList) {
            busViewList.add(new BusView(singleBus.name));
        }

//         debug
//        for (BusView singleView : busViewList){
//            System.out.println(singleView.getBusName());
//        }

        BusArrayAdapter busArrayAdapter = new BusArrayAdapter(this, busViewList);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(busArrayAdapter);

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
        return super.onOptionsItemSelected(item);
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }


}