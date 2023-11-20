package com.edgrantJBusRD.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        //temporary text value
        String nameText = "Nama temporary";
        String emailText = "email@email.com";
        int balanceText = 10;

        // find object
        TextView username = (TextView) findViewById(R.id.valueUsername);
        TextView email = (TextView) findViewById(R.id.valueEmail);
        TextView balance = (TextView) findViewById(R.id.valueBalance);

        //set value
        username.setText(nameText);
        email.setText(emailText);
        balance.setText(balanceText);
    }
}