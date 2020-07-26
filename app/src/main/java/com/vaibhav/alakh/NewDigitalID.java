package com.vaibhav.alakh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewDigitalID extends AppCompatActivity {
    ImageView backButton;
    TextView nameTV,midTV,mobileTV,wardTV,assemblyTV,occupationTV,cityTV,addressTV, pgpTV, asangathitTV, entrepreneurshipTV,digitalPaymentRemaining;
    ProgressDialog loading;
    CircularImageView memberImageView;
    LinearLayout payment_status_linear_layout, asangathit_linear_layout, entrepreneurship_linear_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Code for White Status Bar

        getWindow().setStatusBarColor(Color.LTGRAY);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        //Transparent Status Bar
//        Window w = getWindow(); // in Activity's onCreate() for instance
//        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_digital_id);

        SharedPreferences sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String mob = sharedPreferences.getString(Config.KEY_MOBILE,"");

//        digitalPaymentRemaining = findViewById(R.id.digital_payment_remaining);



        backButton = findViewById(R.id.back_button_digital_id);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Load DigitalID
        loading = new ProgressDialog(NewDigitalID.this,R.style.MyAlertDialogStyle);
        loading.setMessage("लोडिंग डिजिटल आईडी...");
        loading.setCancelable(false);
        loading.show();
        searchMembershipID();
    }


    private void searchMembershipID() {

        //String str = membershipET.getText().toString();
        SharedPreferences sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(Config.MID_SHARED_PREF,"");

        String url = Config.SEARCH_DIGITAL_ID_URL+str;

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
                        Toast.makeText(NewDigitalID.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void showJSON(String response) {
        String name = "";
        String mid = "";
        String mobile = "";
        String profile_image = "";
        String ward = "";
        String assembly = "";
        String occupation = "";
        String city = "";
        String address = "";
        String pgp = "";
        String asangathit = "";
        String entrepreneurship = "";
        String payment_status = "";

        nameTV = findViewById(R.id.digital_name_text_new);
        midTV = findViewById(R.id.digital_membership_text_new);
        mobileTV = findViewById(R.id.digital_mobile_text_new);
//        wardTV = findViewById(R.id.digital_ward_text_new);
//        assemblyTV = findViewById(R.id.digital_assembly_text_new);
//        occupationTV = findViewById(R.id.digital_occupation_text_new);
//        cityTV = findViewById(R.id.digital_city_text_new);
//        addressTV = findViewById(R.id.digital_address_text_new);
//        pgpTV = findViewById(R.id.digital_pgp_text_new);
//        asangathitTV = findViewById(R.id.digital_asangathit_text_new);
//        entrepreneurshipTV = findViewById(R.id.digital_entrepreneur_text_new);
        memberImageView = findViewById(R.id.digital_member_image_new);
//        asangathit_linear_layout = findViewById(R.id.digital_asangathit_layout);
//        entrepreneurship_linear_layout = findViewById(R.id.digital_entrepreneurship_layout);



        try {

            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject userdata = result.getJSONObject(0);

            name = userdata.getString(Config.TEXT_NAME);
            mid = userdata.getString(Config.TEXT_MID);
            mobile = userdata.getString(Config.TEXT_MOBILE);
            profile_image = userdata.getString(Config.TEXT_PROFILE_IMAGE_URL);
//            ward = userdata.getString(Config.TEXT_WARD);
//            assembly = userdata.getString(Config.TEXT_ASSEMBLY);
//            occupation = userdata.getString(Config.TEXT_OCCUPATION);
//            city = userdata.getString(Config.TEXT_CITY);
//            address = userdata.getString(Config.TEXT_ADDRESS);
//            pgp = userdata.getString(Config.TEXT_PGP);
//            asangathit = userdata.getString(Config.TEXT_ASANGATHIT);
//            entrepreneurship = userdata.getString(Config.TEXT_ENTREPRENEURSHIP);
            payment_status = userdata.getString(Config.TEXT_PAYMENT_STATUS);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Setting all the Values in their Respective TextViews
        nameTV.setText(name);
        midTV.setText(mid);
        mobileTV.setText(mobile);
//        wardTV.setText(ward);
//        assemblyTV.setText(assembly);
//        occupationTV.setText(occupation);
//        cityTV.setText(city);
//        addressTV.setText(address);
//        pgpTV.setText(pgp);
//        asangathitTV.setText(asangathit);
//        entrepreneurshipTV.setText(entrepreneurship);

        //For Setting the Profile Image
        Glide.with(this)
                .load(profile_image)
                .asBitmap()
                .into(memberImageView);

        //Visibility Condition for Payment Status
//        if(payment_status.equals("1")){
//
//        }
//        else {
//
//            nameTV.setTextColor(Color.parseColor("#DC143C"));
//            midTV.setTextColor(Color.parseColor("#DC143C"));
//
//        }

//        //Set the Visibility of Asangathit Union Member Layout
//        if(asangathit.equals("No") || asangathit.equals(""))
//        {
//            asangathit_linear_layout.setVisibility(View.GONE);
//
//        }
//        else {
//
//            asangathit_linear_layout.setVisibility(View.VISIBLE);
//        }
//
//
//        //Set the Visibility of Entrepreneurship Kraanti Member Layout
//        if(entrepreneurship.equals("No") || entrepreneurship.equals(""))
//        {
//            entrepreneurship_linear_layout.setVisibility(View.GONE);
//        }
//        else {
//            entrepreneurship_linear_layout.setVisibility(View.VISIBLE);
//        }



    }
}
