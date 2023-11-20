package com.edgrantJBusRD.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText emailEditText = null;
    Button loginButton = null;
    Button registerButton = null;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //hide action bar dengan null error exeption
        try {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }
        catch (NullPointerException nullPointerException){
            System.out.println("nullPointerExeption");
        }


        emailEditText = (EditText) findViewById(R.id.loginEmail);
        loginButton = (Button) findViewById(R.id.registerButtonInActivity);
        registerButton = (Button) findViewById(R.id.registerButton);


//        String text = emailEditText.getText().toString();
        registerButton.setOnClickListener(v -> {
            moveActivity(this, RegisterActivity.class);
        });
        loginButton.setOnClickListener(view -> {
                String email = emailEditText.getText().toString();

                if (!isValidEmail(email)) {
                    Toast.makeText(LoginActivity.this, "Invalid email format. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

            moveActivity(ctx, MainActivity.class);
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
        });
    }

    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
