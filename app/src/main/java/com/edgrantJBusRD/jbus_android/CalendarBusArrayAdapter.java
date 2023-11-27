package com.edgrantJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CalendarBusArrayAdapter extends ArrayAdapter<CalenderBusView> {

    public CalendarBusArrayAdapter(@NonNull Context context, ArrayList<CalenderBusView> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view_calendar, parent, false);
        }

        // Get the data item for this position
        CalenderBusView busView = getItem(position);

        // Lookup view for data population
        TextView tvBusName = convertView.findViewById(R.id.busName);
        ImageView imgCalendar = convertView.findViewById(R.id.imgCalendar); // Make sure you have imgCalendar ID in your layout

        // Populate the data into the template view using the data object
        if (busView != null) {
            tvBusName.setText(busView.getBusName());
        }

        // Set click listener for calendar icon
        imgCalendar.setOnClickListener(v -> {
            // Redirect to AddSchedule Activity
            Intent intent = new Intent(getContext(), AddScheduleActivity.class);
            // Add any extra data if needed
            getContext().startActivity(intent);
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
