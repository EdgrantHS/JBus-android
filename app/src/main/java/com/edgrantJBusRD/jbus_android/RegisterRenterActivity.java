package com.edgrantJBusRD.jbus_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import com.edgrantJBusRD.jbus_android.model.BaseResponse;
import com.edgrantJBusRD.jbus_android.model.Renter;
import com.edgrantJBusRD.jbus_android.request.BaseApiService;
import com.edgrantJBusRD.jbus_android.request.UtilsApi;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterRenterActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private EditText CompanyName, Address, PhoneNumber;
    private boolean registerSuccess = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //hide action bar dengan null error exception
        try {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }
        catch (NullPointerException nullPointerException){
            System.out.println("nullPointerException");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_renter);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        CompanyName = findViewById(R.id.CompanyName);
        Address = findViewById(R.id.Address);
        PhoneNumber = findViewById(R.id.PhoneNumber);
        Button registerButton = findViewById(R.id.registerButtonInActivity);

        registerButton.setOnClickListener(v -> {
            handleRegister();
            if (registerSuccess){
                moveActivity(mContext, AboutMeActivity.class);
            }
        });
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    protected void handleRegister() {
        // handling empty field
        String companyNameS = CompanyName.getText().toString();
        String addressS = Address.getText().toString();
        String phoneNumberS = PhoneNumber.getText().toString();
        if (companyNameS.isEmpty() || addressS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (phoneNumberS.isEmpty() || !phoneNumberS.matches("\\d+(\\.\\d+)?")) {
            Toast.makeText(mContext, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.registerRenter(LoginActivity.loggedAccount.id, companyNameS, addressS, phoneNumberS).enqueue(new Callback<BaseResponse<Renter>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<Renter>> call, @NonNull Response<BaseResponse<Renter>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " +
                            response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Renter> res = response.body();
                // if success finish this activity (back to login activity)
                assert res != null;
                if (res.success) finish();
                registerSuccess = res.success;
                LoginActivity.loggedAccount.company = new Renter();
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(@NonNull Call<BaseResponse<Renter>> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}