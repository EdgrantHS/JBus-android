package com.edgrantJBusRD.jbus_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edgrantJBusRD.jbus_android.request.BaseApiService;
import com.edgrantJBusRD.jbus_android.request.UtilsApi;

public class IpAddActivity extends AppCompatActivity {
    private final Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_add);

        EditText inputIP3 = findViewById(R.id.inputIP3);
        EditText inputIP2 = findViewById(R.id.inputIP2);
        EditText inputIP1 = findViewById(R.id.inputIP1);
        EditText inputIP0 = findViewById(R.id.inputIP0);
        Button buttonSumbitIP = findViewById(R.id.buttonSumbitIP);

        buttonSumbitIP.setOnClickListener(v -> {
            if (validateInput(inputIP3, 0, 255) ||
                    validateInput(inputIP2,0, 255) ||
                    validateInput(inputIP1, 0, 255) ||
                    validateInput(inputIP0, 0, 255)) {
                Toast.makeText(IpAddActivity.this, "Invalid Input Detected", Toast.LENGTH_SHORT).show();
            } else {
                setApiBaseUrl(inputIP3, inputIP2, inputIP1, inputIP0);
            }
        });
    }

    private void setApiBaseUrl(EditText ip3, EditText ip2, EditText ip1, EditText ip0) {
        String ipString = "http://"
                + ip3.getText().toString()
                + "." + ip2.getText().toString()
                + "." + ip1.getText().toString()
                + "." + ip0.getText().toString()
                + ":5000/";
        UtilsApi.BASE_URL_API = ipString;
        moveActivity(mContext, LoginActivity.class);
        Toast.makeText(IpAddActivity.this, "IP Changed", Toast.LENGTH_SHORT).show();
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
}
