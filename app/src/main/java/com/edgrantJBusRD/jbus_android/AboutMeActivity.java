package com.edgrantJBusRD.jbus_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edgrantJBusRD.jbus_android.model.BaseResponse;
import com.edgrantJBusRD.jbus_android.request.BaseApiService;
import com.edgrantJBusRD.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private final Context mContext = this;
    private EditText balanceEditText = null;
    private boolean accountIsRenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        //temporary text value
        String nameText = LoginActivity.loggedAccount.name;
        String emailText = LoginActivity.loggedAccount.email;
        String balanceText = String.valueOf(LoginActivity.loggedAccount.balance);

        // find object
        TextView username = findViewById(R.id.valueUsername);
        TextView email = findViewById(R.id.valueEmail);
        TextView balance = findViewById(R.id.valueBalance);

        //interactive object
        balanceEditText = findViewById(R.id.topUpInput);
        Button topUpButton = findViewById(R.id.topUpButton);

        //check if account is renter
        accountIsRenter = LoginActivity.loggedAccount.company != null;

        //renter objects
        TextView renterText = findViewById(R.id.renterStatus);
        Button renterButton = findViewById(R.id.registerRenterButton);

        // set value
        username.setText(nameText);
        email.setText(emailText);
        balance.setText(balanceText);
        mApiService = UtilsApi.getApiService();

        // membuat initial
        TextView accountInitial = findViewById(R.id.accountInitial);
        String initialText = username.getText().toString().substring(0, 1);
        accountInitial.setText(initialText);

        topUpButton.setOnClickListener(e -> {
            // calling API
            handleTopUp();
        });

        //different text based on if account is renter or not
        if (accountIsRenter){ //kalau renter
            renterText.setText("You're already registered as a renter");
        }
        else { //kalau bukan renter
            renterText.setText("You're not registered as a renter");
        }

        //melakukan hal yg beda berdasarkan renter or not
        renterButton.setOnClickListener(v -> {
            if (accountIsRenter)//kalau renter
                moveActivity(mContext, ManageBusActivity.class);
            else //kalau bukan renter
                moveActivity(mContext, RegisterRenterActivity.class);
        });
    }

    private void moveActivity(Context mContext, Class<?> cls){
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }
    protected void handleTopUp() {
        // handling empty field
        String balanceText = balanceEditText.getText().toString();
        if (balanceText.isEmpty() || !balanceText.matches("\\d+(\\.\\d+)?")) {
            Toast.makeText(mContext, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }
        double balanceD = Double.parseDouble(balanceText);
        mApiService.topUp(LoginActivity.loggedAccount.id , balanceD).enqueue(new Callback<BaseResponse<Double>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<Double>> call, @NonNull Response<BaseResponse<Double>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " +
                            response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Double> res = response.body();
                // if success finish this activity (back to login activity)
                assert res != null;
                if (res.success) finish();
                LoginActivity.loggedAccount.balance += balanceD;
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(@NonNull Call<BaseResponse<Double>> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}