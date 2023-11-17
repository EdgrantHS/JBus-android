package com.edgrantJBusRD.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
    }
}