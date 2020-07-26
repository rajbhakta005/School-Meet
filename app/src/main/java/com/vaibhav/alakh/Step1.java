package com.vaibhav.alakh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Step1 extends AppCompatActivity {

    Button next_mobile,next_otp;
    CardView mobileCV,otpCV;
    EditText mobileET,otpET;
    ImageView backButtonImage;
    private static final String SEND_OTP_URL = "https://seekho.xyz/pgp-android/api/send-otp.php";
    private static final String VERIFY_OTP_URL = "https://seekho.xyz/pgp-android/api/verify-otp.php";
    int errorColor;
    TextView pleaseWaitTV,countdownTV,resend_otp;
    private static final String RESEND_OTP_URL = "https://seekho.xyz/pgp-android/api/resend-otp.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step1);

        next_mobile = findViewById(R.id.step1_send_otp_button);
        next_otp = findViewById(R.id.step1_verify_otp_button);
        mobileET = findViewById(R.id.mobile_number_step1_et);
        otpET = findViewById(R.id.otp_step1_et);
        mobileCV = findViewById(R.id.step1_mobile_card_view);
        otpCV = findViewById(R.id.step1_otp_card_view);
        backButtonImage = findViewById(R.id.step1_back_button_image);

        //Code for Error
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {

            errorColor = ContextCompat.getColor(getApplicationContext(), R.color.errorColor);
        } else {
            errorColor = getResources().getColor(R.color.errorColor);
        }

        pleaseWaitTV = findViewById(R.id.step1_please_wait_text);
        countdownTV = findViewById(R.id.step1_countdown);
        resend_otp = findViewById(R.id.step1_resend_otp);



        //Verify Mobile Button Click Listener
        next_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateMobile()) {
                    return;
                }

                loginUser();
            }
        });

        //Verify OTP Button Click Listener
        next_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateOTP()) {
                    return;
                }

               verifyOTP();
            }
        });

        backButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
                loading = new ProgressDialog(Step1.this,R.style.MyAlertDialogStyle);
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
        Step1.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Step1.this);
                builder.setMessage("आपके मोबाइल नंबर पर एक OTP भेजा गया है। कृपया OTP दर्ज करें।")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        mobileCV.setVisibility(View.GONE);
                                        otpCV.setVisibility(View.VISIBLE);
                                        next_mobile.setVisibility(View.GONE);
                                        next_otp.setVisibility(View.VISIBLE);
                                        countdownTV.setVisibility(View.VISIBLE);
                                        pleaseWaitTV.setVisibility(View.VISIBLE);

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
                                                pleaseWaitTV.setVisibility(View.GONE);
                                                resend_otp.setVisibility(View.VISIBLE);

                                            }
                                        }.start();

                                        //Resend OTP Click Listener
                                        resend_otp.setOnClickListener(new View.OnClickListener() {
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
        Step1.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Step1.this);
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
                loading = new ProgressDialog(Step1.this,R.style.MyAlertDialogStyle);
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

                loading = ProgressDialog.show(Step1.this, "","ओटीपी का अनुरोध कर रहे हैं",true,false);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                String b = s.toString().trim();
                String a = "success";


                if(a.equals(b)){

                    Toast.makeText(Step1.this, "OTP को सफलतापूर्वक भेजा गया है", Toast.LENGTH_LONG).show();
                    countdownTV.setVisibility(View.VISIBLE);
                    pleaseWaitTV.setVisibility(View.VISIBLE);
                    resend_otp.setVisibility(View.GONE);
                    new CountDownTimer(60000, 1000) {

                        public void onTick(long millisUntilFinished) {

                            Long minutes = ((millisUntilFinished / 1000) / 60) % 60;
                            Long sec = (millisUntilFinished / 1000) % 60;

                            countdownTV.setText(minutes + ":" + sec);
                        }

                        @Override
                        public void onFinish() {
                            countdownTV.setVisibility(View.GONE);
                            pleaseWaitTV.setVisibility(View.GONE);
                            resend_otp.setVisibility(View.VISIBLE);

                        }
                    }.start();

                    //Resend OTP Click Listener
                    resend_otp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            resendOTP();

                        }
                    });


                }

                else{

                    Toast.makeText(Step1.this, "कृपया पुन: प्रयास करें", Toast.LENGTH_LONG).show();
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
        Step1.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Step1.this);
                builder.setMessage("आपका मोबाइल नंबर सफलतापूर्वक सत्यापित हो गया है।")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        //Creating a shared preference
                                        SharedPreferences sharedPreferences = Step1.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                        //Creating editor to store values to shared preferences
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        String mobile = mobileET.getText().toString().trim();
                                        //Adding values to editor
                                        editor.putString(Config.MOBILE_REG_SP, mobile);
                                        editor.commit();

                                        //Toast.makeText(Step1.this, mobile, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Step1.this, SelectPicture.class);
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
        Step1.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Step1.this);
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
}
