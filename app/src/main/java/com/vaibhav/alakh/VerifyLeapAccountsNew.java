package com.vaibhav.alakh;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VerifyLeapAccountsNew extends AppCompatActivity {

    List<GetDataAdapterLeap> GetDataAdapter1;

    RecyclerView recyclerView;

    ImageView noPostsIV;
    ShimmerFrameLayout shimmerFrameLayout;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;
    String GET_JSON_DATA_HTTP_URL_V = "https://seekho.xyz/pgp-android/api/verify-leap-members.php";

    String JSON_LEAP_IMAGE = "member_photo";
    String JSON_LEAP_NAME = "name";
    String JSON_LEAP_MOBILE = "mobile";
    String JSON_LEAP_MID = "membership_id";
    JsonArrayRequest jsonArrayRequest ;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    String mid;

    RequestQueue requestQueue ;
    SwipeRefreshLayout talentSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Status Bar Color
        getWindow().setStatusBarColor(Color.WHITE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_leap_accounts_new);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);


        shimmerFrameLayout = findViewById(R.id.verify_shimmer_layout);
        linearLayout = findViewById(R.id.leap_verifyLL);

        shimmerFrameLayout.startShimmerAnimation();

        GetDataAdapter1 = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.leap_recyclerView);

        // swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        //String str = membershipET.getText().toString();
        SharedPreferences sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mid = sharedPreferences.getString(Config.MID_SHARED_PREF,"");

        JSON_DATA_WEB_CALL();

    }

    //The Main Function to Show all the Videos
    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL_V+"?owner="+mid,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }


    //After Web Call function
    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            GetDataAdapterLeap GetDataAdapter2 = new GetDataAdapterLeap();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setName(json.getString(JSON_LEAP_NAME));
                GetDataAdapter2.setImage(json.getString(JSON_LEAP_IMAGE));
                GetDataAdapter2.setMobile(json.getString(JSON_LEAP_MOBILE));
                GetDataAdapter2.setMid(json.getString(JSON_LEAP_MID));


            } catch (JSONException e) {

                e.printStackTrace();
            }

            GetDataAdapter1.add(GetDataAdapter2);

        }

        recyclerViewadapter = new RecyclerViewAdapterLeap(GetDataAdapter1, this);

        recyclerView.setAdapter(recyclerViewadapter);

        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmerAnimation();
        linearLayout.setVisibility(View.VISIBLE);

    }

    // Back arrow click event to go to the parent Activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
