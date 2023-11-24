package com.edgrantJBusRD.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import com.edgrantJBusRD.jbus_android.model.Account;
import com.edgrantJBusRD.jbus_android.model.BaseResponse;
import com.edgrantJBusRD.jbus_android.request.BaseApiService;
import com.edgrantJBusRD.jbus_android.request.UtilsApi;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private EditText name, email, password;
    private Button registerButton = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //hide action bar dengan null error exeption
        try {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }
        catch (NullPointerException nullPointerException){
            System.out.println("nullPointerExeption");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        name = findViewById(R.id.loginUsername);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        registerButton = findViewById(R.id.registerButtonInActivity);

        registerButton.setOnClickListener(v -> {
            handleRegister();
            moveActivity(this, LoginActivity.class);
        });
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    protected void handleRegister() {
    // handling empty field
        String nameS = name.getText().toString();
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();
        if (nameS.isEmpty() || emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.register(nameS, emailS, passwordS).enqueue(new Callback<BaseResponse<Account>>() {
           @Override
           public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
            // handle the potential 4xx & 5xx error
               if (!response.isSuccessful()) {
                   Toast.makeText(mContext, "Application error " +
                           response.code(), Toast.LENGTH_SHORT).show();
                   return;
               }
               BaseResponse<Account> res = response.body();
            // if success finish this activity (back to login activity)
               if (res.success) finish();
               Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
           }
           @Override
           public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
               Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
           }
       });
    }
}