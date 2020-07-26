package com.vaibhav.alakh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

public class DonateFunds extends AppCompatActivity {

    CardView one, two, three, four, five, six;
    String shared_mid, shared_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Code for White Status Bar
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_funds);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

        SharedPreferences sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        shared_mid = sharedPreferences.getString(Config.MID_SHARED_PREF,"");
        shared_mobile = sharedPreferences.getString(Config.PAYMENT_MOBILE,"");

        one = findViewById(R.id.donate_1_CV);
        two = findViewById(R.id.donate_2_CV);
        three = findViewById(R.id.donate_3_CV);
        four = findViewById(R.id.donate_4_CV);
        five = findViewById(R.id.donate_5_CV);
        six = findViewById(R.id.donate_6_CV);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://seekho.xyz/pgp-android/api/payments/web/donate.php?membership_id="+shared_mid+"&mobile="+shared_mobile+"&amt=5000"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://seekho.xyz/pgp-android/api/payments/web/donate.php?membership_id="+shared_mid+"&mobile="+shared_mobile+"&amt=2500"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://seekho.xyz/pgp-android/api/payments/web/donate.php?membership_id="+shared_mid+"&mobile="+shared_mobile+"&amt=1500"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://seekho.xyz/pgp-android/api/payments/web/donate.php?membership_id="+shared_mid+"&mobile="+shared_mobile+"&amt=1000"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://seekho.xyz/pgp-android/api/payments/web/donate.php?membership_id="+shared_mid+"&mobile="+shared_mobile+"&amt=500"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://seekho.xyz/pgp-android/api/payments/web/donate.php?membership_id="+shared_mid+"&mobile="+shared_mobile+"&amt=100"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

    }

    // Back arrow click event to go to the parent Activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
