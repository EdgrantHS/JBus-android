package com.edgrantJBusRD.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

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

        Button registerButton = (Button) findViewById(R.id.registerButtonInActivity);
        registerButton.setOnClickListener(v -> {moveActivity(this, LoginActivity.class);});
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
}