package com.edgrantJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.edgrantJBusRD.jbus_android.model.BaseResponse;
import com.edgrantJBusRD.jbus_android.model.Bus;
import com.edgrantJBusRD.jbus_android.request.BaseApiService;
import com.edgrantJBusRD.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddScheduleActivity extends AppCompatActivity {
    private final Context mContext = this;
    private BaseApiService mApiService;
    private String time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule); // Replace with your layout file name
        mApiService = UtilsApi.getApiService();

        EditText yearInput = findViewById(R.id.inputYear);
        EditText monthInput = findViewById(R.id.inputMonth);
        EditText dayInput = findViewById(R.id.inputDay);
        EditText hourInput = findViewById(R.id.inputHour);
        EditText minuteInput = findViewById(R.id.inputMinutes);
        Button submitButton = findViewById(R.id.scheduleSubmit);

        submitButton.setOnClickListener(v -> {
            if (validateInput(yearInput, 1900, 2100) ||
                    validateInput(monthInput, 1, 12) ||
                    validateInput(dayInput, 1, 31) ||
                    validateInput(hourInput, 0, 23) ||
                    validateInput(minuteInput, 0, 59)) {
                Toast.makeText(AddScheduleActivity.this, "Invalid Input Detected", Toast.LENGTH_SHORT).show();
            } else {
                time = mergeString(yearInput, monthInput, dayInput, hourInput, minuteInput);
                handleAddSchedule();
            }
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

    private String mergeString(
            EditText yearInput,
            EditText monthInput,
            EditText dayInput,
            EditText hourInput,
            EditText minuteInput) {

        String year = yearInput.getText().toString();
        String month = monthInput.getText().toString();
        String day = dayInput.getText().toString();
        String hour = hourInput.getText().toString();
        String minute = minuteInput.getText().toString();

        // Ensure that the values have at least two digits
        if (year.length() < 2) {
            year = "0" + year;
        }
        if (month.length() < 2) {
            month = "0" + month;
        }
        if (day.length() < 2) {
            day = "0" + day;
        }
        if (hour.length() < 2) {
            hour = "0" + hour;
        }
        if (minute.length() < 2) {
            minute = "0" + minute;
        }

        // Build the formatted time string
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
    }


    private boolean validateInput(@NonNull EditText editText, int min, int max) {
        try {
            int value = Integer.parseInt(editText.getText().toString());
            return value < min || value > max;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    protected void handleAddSchedule() {
        // handling empty field
        int iId = CalendarBusArrayAdapter.busId;
        String sTime = time;

        if (sTime.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mApiService.addSchedule(iId, sTime).enqueue(new Callback<BaseResponse<Bus>>() {
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
                if (res.success) {
                    finish();
                    moveActivity(mContext, ManageBusActivity.class);
                }
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(@NonNull Call<BaseResponse<Bus>> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
