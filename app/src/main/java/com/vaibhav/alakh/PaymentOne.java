package com.vaibhav.alakh;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class PaymentOne extends AppCompatActivity{

    Button paymentButton, paymentSkipButton;
    String mid,mobileno, shared_mid, shared_mobile;
    EditText midET, mobileET;
    ImageView imageView;
    int errorColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Code for White Status Bar
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_one);

        //Code for Error
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {

            errorColor = ContextCompat.getColor(getApplicationContext(), R.color.errorColor);
        } else {
            errorColor = getResources().getColor(R.color.errorColor);
        }

        midET = findViewById(R.id.payment_mid_et);
        mobileET = findViewById(R.id.payment_mobile_et);
        paymentButton = findViewById(R.id.buttonPayment);
        imageView = findViewById(R.id.payments_back_image);
        paymentSkipButton = findViewById(R.id.buttonPaymentSkip);

        //String str = membershipET.getText().toString();
        SharedPreferences sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        shared_mid = sharedPreferences.getString(Config.MID_SHARED_PREF,"");
        shared_mobile = sharedPreferences.getString(Config.PAYMENT_MOBILE,"");

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mid = midET.getText().toString();
                mobileno = mobileET.getText().toString();


                if (!validateMID()) {
                    return;
                }

                if (!validateMobile()) {
                    return;
                }

                Uri uri = Uri.parse("https://seekho.xyz/pgp-android/api/payments/web/index.php?membership_id="+mid+"&mobile="+mobileno); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        paymentSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity(0);
                //Starting login activity
                Intent intent = new Intent(PaymentOne.this,Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
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


    //Mobile Validation
    private boolean validateMID() {
        if (midET.getText().toString().trim().isEmpty() || midET.getText().toString().trim().length() < 6) {
            String errorString = "सही सदस्यता आईडी दर्ज करें।";  // your error message
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            midET.setError(spannableStringBuilder);
            return (false);

        }
        return (true);
    }


}
