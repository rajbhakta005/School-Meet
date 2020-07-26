package com.vaibhav.alakh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class MainActivity extends AppCompatActivity {

    EditText membership_id_et;
    ImageButton loginButton;
    TextView forgotID_TV, registerTV;
    int errorColor;
    private boolean loggedIn = false;
    public static final int MY_REQUEST_CODE = 1;
    private static final String LOGIN_URL = "https://seekho.xyz/pgp-android/api/login-user.php";

//    AppUpdateManager appUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        membership_id_et = (findViewById(R.id.login_edit_text));
        loginButton = (findViewById(R.id.login_button));
        forgotID_TV = findViewById(R.id.forgot_membership_id_text_view);
        registerTV = findViewById(R.id.registerHomeTV);

        //Code for Error
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {

            errorColor = ContextCompat.getColor(getApplicationContext(), R.color.errorColor);
        } else {
            errorColor = getResources().getColor(R.color.errorColor);
        }

        forgotID_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ForgotID.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateMembershipID()) {
                    return;
                }
                loginUser();
            }
        });

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Step1.class);
                startActivity(intent);
            }
        });




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }


    // When User Resumes the App
    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
        }


    }




    private void loginUser() {

        String membership_id = membership_id_et.getText().toString().trim();

        login(membership_id);
    }

    private void login(final String membership_id) {

        String urlSuffix = "?membership_id=" + membership_id;
        class RegisterUser extends AsyncTask<String, Void, String> {

            private ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = new ProgressDialog(MainActivity.this,R.style.MyAlertDialogStyle);
                loading.setMessage("आपको लॉगिन कर रहे हैं।");
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
                    //Creating a shared preference
                    SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Adding values to editor
                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                    editor.putString(Config.MID_SHARED_PREF, membership_id);

                    //Saving values to editor
                    editor.commit();
                    Bundle b7 = new Bundle();
                    b7.putString("mid", membership_id);
                    Intent i = new Intent(MainActivity.this, Home.class);
                    i.putExtras(b7);
                    startActivity(i);
                } else {
                    showAlert();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;

                try {

                    URL url = new URL(LOGIN_URL + s);
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
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("अमान्य सदस्यता आईडी। कृपया सही सदस्यता आईडी दर्ज करें।")
                        .setCancelable(false)
                        .setPositiveButton("ठीक।", new DialogInterface.OnClickListener() {
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
    private boolean validateMembershipID() {
        if (membership_id_et.getText().toString().trim().isEmpty() || membership_id_et.getText().toString().trim().length() < 8) {
            String errorString = "वैध सदस्यता आईडी दर्ज करें।";  // your error message
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            membership_id_et.setError(spannableStringBuilder);
            return (false);

        }
        return (true);
    }





}
