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
        String nameText = "Satrio temporary";
        String emailText = "email@email.com";
        String balanceText = "10";

        // find object
        TextView username = findViewById(R.id.valueUsername);
        TextView email = findViewById(R.id.valueEmail);
        TextView balance = findViewById(R.id.valueBalance);

        // set value
        username.setText(nameText);
        email.setText(emailText);
        balance.setText(balanceText);

        // membuat initial
        TextView accountInitial = findViewById(R.id.accountInitial);
        String initialText = username.getText().toString().substring(0, 1);
        accountInitial.setText(initialText);
    }
}