package com.vaibhav.alakh;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ForgotID extends AppCompatActivity {

    Button submitID;
    EditText forgot_ID_ET;
    private static final String FORGOT_URL = "https://seekho.xyz/pgp-android/api/forgot-id.php";
    int errorColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Code for White Status Bar
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

        //Code for Error
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {

            errorColor = ContextCompat.getColor(getApplicationContext(), R.color.errorColorWhite);
        } else {
            errorColor = getResources().getColor(R.color.errorColorWhite);
        }


        forgot_ID_ET = findViewById(R.id.forgot_id_edit_text);
        submitID = findViewById(R.id.recover_id_button);

        submitID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateMobile()) {
                    return;
                }
                loginUser();
            }
        });

    }



    private void loginUser() {

        String mobile = forgot_ID_ET.getText().toString().trim();

        login(mobile);
    }



    private void login(final String mobile) {

        String urlSuffix = "?mobile=" + mobile;
        class RegisterUser extends AsyncTask<String, Void, String> {

            private ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = new ProgressDialog(ForgotID.this,R.style.MyAlertDialogStyle);
                loading.setMessage("कृपया प्रतीक्षा करें।");
                loading.setCancelable(false);
                loading.show();
            }

            @Override
            protected void onPostExecute(final String result) {

                super.onPostExecute(result);
                loading.dismiss();
                //  Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                String b = result.toString().trim();
                String a = "success";
                if (a.equals(b)) {

                    showAlert();

                } else {

                    showNegetiveAlert();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;

                try {

                    URL url = new URL(FORGOT_URL + s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                } catch (Exception e) {
                    return null;
                }
            }


        }
        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }

    public void showAlert(){
        ForgotID.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotID.this);
                builder.setMessage("हमने आपकी सदस्यता आईडी आपके मोबाइल नंबर पर भेजी है।")
                        .setCancelable(false)
                        .setPositiveButton("ठीक", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        Intent intent = new Intent(ForgotID.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                });
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    public void showNegetiveAlert(){
        ForgotID.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotID.this);
                builder.setMessage("हम आपकी सदस्यता आईडी को पुनः प्राप्त करने में असमर्थ हैं। कृपया पुन: प्रयास करें।")
                        .setCancelable(false)
                        .setPositiveButton("ठीक", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                runOnUiThread(new Runnable() {
                                    public void run() {


                                    }
                                });
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    //Password Validation
    private boolean validateMobile() {
        if (forgot_ID_ET.getText().toString().trim().isEmpty() || forgot_ID_ET.getText().toString().trim().length() < 10) {
            String errorString = "सही मोबाइल नंबर दर्ज करें।";  // your error message
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            forgot_ID_ET.setError(spannableStringBuilder);
            return (false);

        }
        return (true);
    }

    // Back arrow click event to go to the parent Activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
