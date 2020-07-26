package com.vaibhav.alakh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PayMembershipFee extends AppCompatActivity {

    TextView midTV, mobileTV;
    ProgressDialog loading;
    Button paybutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Code for White Status Bar
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_membership_fee);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);


        paybutton = findViewById(R.id.pay_fee_button);
        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mi, mo;
                mi = midTV.getText().toString();
                mo = mobileTV.getText().toString();
                Uri uri = Uri.parse("https://seekho.xyz/pgp-android/api/payments/web/index.php?membership_id="+mi+"&mobile="+mo); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        //Load DigitalID
        loading = new ProgressDialog(PayMembershipFee.this,R.style.MyAlertDialogStyle);
        loading.setMessage("कृपया प्रतीक्षा करें...");
        loading.setCancelable(false);
        loading.show();

        searchMembershipID();

    }

    private void searchMembershipID() {

        //String str = membershipET.getText().toString();
        SharedPreferences sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(Config.MID_SHARED_PREF,"");

        String url = Config.SEARCH_PAYMENT_DETAILS+str;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loading.dismiss();
                showJSON(response);

            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PayMembershipFee.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void showJSON(String response) {

        String mid = "";
        String mobile = "";



        midTV = findViewById(R.id.pay_midTV);
        mobileTV = findViewById(R.id.pay_mobileTV);




        try {

            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject userdata = result.getJSONObject(0);

            mid = userdata.getString(Config.PAYMENT_PAY_MID);
            mobile = userdata.getString(Config.PAYMENT_PAY_MOBILE);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Setting all the Values in their Respective TextViews
        midTV.setText(mid);
        mobileTV.setText(mobile);


    }

    // Back arrow click event to go to the parent Activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
