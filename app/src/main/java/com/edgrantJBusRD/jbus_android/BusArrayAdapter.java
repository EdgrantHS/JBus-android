package com.edgrantJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.edgrantJBusRD.jbus_android.model.Bus;

import java.util.ArrayList;

public class BusArrayAdapter extends ArrayAdapter<BusView> {

    public static Bus selectedBus; // Static variable to hold the selected bus object

    public BusArrayAdapter(@NonNull Context context, ArrayList<BusView> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view, parent, false);
        }

        BusView currentBus = getItem(position);

        TextView busNameTextView = currentItemView.findViewById(R.id.busName);
        TextView departureTextView = currentItemView.findViewById(R.id.valDTxt);
        TextView arrivalTextView = currentItemView.findViewById(R.id.valATxt);
        Button detailButton = currentItemView.findViewById(R.id.detailBtn);

        if (currentBus != null) {
            busNameTextView.setText(currentBus.getBusName());
            departureTextView.setText(currentBus.getDeptStation());
            arrivalTextView.setText(currentBus.getArrStation());

            detailButton.setOnClickListener(view -> {
                selectedBus = MainActivity.listBus.get(position);
                // move to bus detail
                Intent intent = new Intent(getContext(), BusDetailActivity.class);
                getContext().startActivity(intent);
            });
        }

        return currentItemView;
    }
}
