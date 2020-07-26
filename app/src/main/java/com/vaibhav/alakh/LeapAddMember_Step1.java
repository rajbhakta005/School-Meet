package com.vaibhav.alakh;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.CountDownTimer;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LeapAddMember_Step1 extends AppCompatActivity {


    ImageButton mobileNextBT, otpNextBT;
    EditText mobileET, otpET;
    TextView resendotpTV,countdownTV,pleasewaitTV;
    RelativeLayout mobileRL, otpRL;
    int errorColor;

    private static final String SEND_OTP_URL = "https://seekho.xyz/pgp-android/leap/api/send-otp.php";
    private static final String VERIFY_OTP_URL = "https://seekho.xyz/pgp-android/leap/api/verify-otp.php";
    private static final String RESEND_OTP_URL = "https://seekho.xyz/pgp-android/leap/api/resend-otp.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Code for White Status Bar

        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leap_add_member__step1);
        Toolbar toolbar = findViewById(R.id.toolbar);
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

            errorColor = ContextCompat.getColor(getApplicationContext(), R.color.errorColor);
        } else {
            errorColor = getResources().getColor(R.color.errorColor);
        }

        mobileET = findViewById(R.id.mobileET);
        otpET = findViewById(R.id.otpET);

        resendotpTV = findViewById(R.id.resend_otpTV);
        countdownTV = findViewById(R.id.countdownTV);
        pleasewaitTV = findViewById(R.id.please_waitTV);

        mobileNextBT = findViewById(R.id.mobile_next_BT);
        otpNextBT = findViewById(R.id.otp_next_BT);

        mobileRL = findViewById(R.id.mobileRL);
        otpRL = findViewById(R.id.otpRL);


        //Verify Mobile Button Click Listener
        mobileNextBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateMobile()) {
                    return;
                }

                loginUser();
            }
        });

        //Verify OTP Button Click Listener
        otpNextBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateOTP()) {
                    return;
                }

                verifyOTP();
            }
        });

    }

    //Main Code for Mobile Number Verification
    private void loginUser() {

        String mobile = mobileET.getText().toString().trim();

        login(mobile);
    }

    private void login(final String mobile) {

        String urlSuffix = "?mobile=" + mobile;
        class RegisterUser extends AsyncTask<String, Void, String> {

            private ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = new ProgressDialog(LeapAddMember_Step1.this,R.style.MyAlertDialogStyle);
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

                    URL url = new URL(SEND_OTP_URL + s);
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

    //Positive Alert for Mobile
    public void showAlert(){
        LeapAddMember_Step1.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LeapAddMember_Step1.this);
                builder.setMessage("आपके मोबाइल नंबर पर एक OTP भेजा गया है। कृपया OTP दर्ज करें।")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        mobileRL.setVisibility(View.GONE);
                                        otpRL.setVisibility(View.VISIBLE);

                                        //Countdoun Timer for Resend OTP Button to Activate
                                        new CountDownTimer(60000, 1000) {

                                            public void onTick(long millisUntilFinished) {

                                                Long minutes = ((millisUntilFinished / 1000) / 60) % 60;
                                                Long sec = (millisUntilFinished / 1000) % 60;

                                                countdownTV.setText(minutes + ":" + sec);
                                            }

                                            @Override
                                            public void onFinish() {
                                                countdownTV.setVisibility(View.GONE);
                                                pleasewaitTV.setVisibility(View.GONE);
                                                resendotpTV.setVisibility(View.VISIBLE);

                                            }
                                        }.start();

                                        //Resend OTP Click Listener
                                        resendotpTV.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                resendOTP();
                                            }
                                        });



//                                        Intent intent = new Intent(Step1.this, MainActivity.class);
//                                        startActivity(intent);
//                                        finish();

                                    }
                                });
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    //Negetive Alert for Mobile
    public void showNegetiveAlert(){
        LeapAddMember_Step1.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LeapAddMember_Step1.this);
                builder.setMessage("आपका नंबर हमारे पास पहले से पंजीकृत है। कृपया अन्य मोबाइल नंबर के साथ प्रयास करें।")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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


    //Main Code for Mobile Number Verification
    private void verifyOTP() {

        String mobile = mobileET.getText().toString().trim();
        String otp = otpET.getText().toString().trim();

        verifyotp(mobile,otp);
    }


    private void verifyotp(final String mobile,final String otp) {

        String urlSuffix = "?mobile=" + mobile + "&otp=" + otp;
        class RegisterUser extends AsyncTask<String, Void, String> {

            private ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = new ProgressDialog(LeapAddMember_Step1.this,R.style.MyAlertDialogStyle);
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

                    showAlertOTP();

                } else {

                    showNegativeAlertOTP();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;

                try {

                    URL url = new URL(VERIFY_OTP_URL + s);
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


    //Function to Resend the OTP to the User in Case OTP is not Received
    private void resendOTP() {

        String mobile = mobileET.getText().toString().trim();

        resendotp(mobile);
    }



    private void resendotp(String mobile)
    {

        String urlSuffix = "?mobile="+mobile;
        class ValidateUser extends AsyncTask<String,Void,String>
        {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(LeapAddMember_Step1.this, "","ओटीपी का अनुरोध कर रहे हैं",true,false);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                String b = s.toString().trim();
                String a = "success";


                if(a.equals(b)){

                    Toast.makeText(LeapAddMember_Step1.this, "OTP को सफलतापूर्वक भेजा गया है", Toast.LENGTH_LONG).show();
                    countdownTV.setVisibility(View.VISIBLE);
                    pleasewaitTV.setVisibility(View.VISIBLE);
                    resendotpTV.setVisibility(View.GONE);
                    new CountDownTimer(60000, 1000) {

                        public void onTick(long millisUntilFinished) {

                            Long minutes = ((millisUntilFinished / 1000) / 60) % 60;
                            Long sec = (millisUntilFinished / 1000) % 60;

                            countdownTV.setText(minutes + ":" + sec);
                        }

                        @Override
                        public void onFinish() {
                            countdownTV.setVisibility(View.GONE);
                            pleasewaitTV.setVisibility(View.GONE);
                            resendotpTV.setVisibility(View.VISIBLE);

                        }
                    }.start();

                    //Resend OTP Click Listener
                    resendotpTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            resendOTP();

                        }
                    });


                }

                else{

                    Toast.makeText(LeapAddMember_Step1.this, "कृपया पुन: प्रयास करें", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(RESEND_OTP_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                }catch(Exception e){
                    return null;
                }
            }
        }
        ValidateUser ru = new ValidateUser();
        ru.execute(urlSuffix);
    }

    //Positive Alert for OTP
    public void showAlertOTP(){
        LeapAddMember_Step1.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LeapAddMember_Step1.this);
                builder.setMessage("आपका मोबाइल नंबर सफलतापूर्वक सत्यापित हो गया है।")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        //Creating a shared preference
                                        SharedPreferences sharedPreferences = LeapAddMember_Step1.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                        //Creating editor to store values to shared preferences
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        String mobile = mobileET.getText().toString().trim();
                                        //Adding values to editor
                                        editor.putString(Config.MOBILE_REG_SP, mobile);
                                        editor.commit();

                                        //Toast.makeText(Step1.this, mobile, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LeapAddMember_Step1.this, LeapSelectPicture.class);
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

    //Negative Alert for OTP
    public void showNegativeAlertOTP(){
        LeapAddMember_Step1.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LeapAddMember_Step1.this);
                builder.setMessage("कृपया सही ओटीपी दर्ज करें और पुनः प्रयास करेंं।")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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


    //Mobile Validation
    private boolean validateMobile() {
        if (mobileET.getText().toString().trim().isEmpty() || mobileET.getText().toString().trim().length() < 10) {
            String errorString = "सही मोबाइल नंबर दर्ज करें।";  // your error message
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            mobileET.setError(spannableStringBuilder);
            return (false);

        }
        return (true);
    }



    //OTP Validation
    private boolean validateOTP() {
        if (otpET.getText().toString().trim().isEmpty() || otpET.getText().toString().trim().length() < 6) {
            String errorString = "सही OTP दर्ज करें।";  // your error message
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            otpET.setError(spannableStringBuilder);
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
