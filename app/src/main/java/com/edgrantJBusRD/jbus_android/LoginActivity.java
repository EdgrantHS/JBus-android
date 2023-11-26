    package com.edgrantJBusRD.jbus_android;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Context;
    import android.content.Intent;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;

    import com.edgrantJBusRD.jbus_android.model.Account;
    import com.edgrantJBusRD.jbus_android.model.BaseResponse;
    import com.edgrantJBusRD.jbus_android.request.BaseApiService;
    import com.edgrantJBusRD.jbus_android.request.UtilsApi;

    import java.util.Objects;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;


    public class LoginActivity extends AppCompatActivity {
        private BaseApiService mApiService;
        private EditText emailEditText = null;
        private EditText passwordEditText = null;
        private final Context mContext = this;
        public static Account loggedAccount = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);


            //hide action bar dengan null error exception
            try {
                Objects.requireNonNull(getSupportActionBar()).hide();
            }
            catch (NullPointerException nullPointerException){
                System.out.println("nullPointerException");
            }


            emailEditText = (EditText) findViewById(R.id.loginEmail);
            Button loginButton = (Button) findViewById(R.id.registerButtonInActivity);
            Button registerButton = (Button) findViewById(R.id.registerButton);
            passwordEditText = (EditText) findViewById(R.id.loginPassword);
            mApiService = UtilsApi.getApiService();


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

                handleLogin();
            });
        }

        private void moveActivity(Context mContext, Class<?> cls){
            Intent intent = new Intent(mContext, cls);
            startActivity(intent);
        }
//        private void viewToast(Context mContext, String message){
//            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
//        }

        private boolean isValidEmail(String email) {
            return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

        protected void handleLogin() {
            // handling empty field
            String emailS = emailEditText.getText().toString();
            String passwordS = passwordEditText.getText().toString();
            if (emailS.isEmpty() || passwordS.isEmpty()) {
                Toast.makeText(mContext, "Field cannot be empty",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            mApiService.login(emailS, passwordS).enqueue(new Callback<BaseResponse<Account>>() {
                @Override
                public void onResponse(
                        @NonNull Call<BaseResponse<Account>> call,
                        @NonNull Response<BaseResponse<Account>> response) {
                    // handle the potential 4xx & 5xx error
                    if (!response.isSuccessful()) {
                        Toast.makeText(mContext, "Application error " +
                                response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    BaseResponse<Account> res = response.body();
                    assert res != null;
                    if (res.success) finish();
                    loggedAccount = res.payload;
                    if(res.success) {
                        moveActivity(mContext, MainActivity.class);
                    }
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(@NonNull Call<BaseResponse<Account>> call, @NonNull Throwable t) {
                    Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
