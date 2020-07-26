package com.vaibhav.alakh;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideosList extends AppCompatActivity {

    List<GetDataAdapterVideos> GetDataAdapter1;

    RecyclerView recyclerView;

    ImageView noPostsIV;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    ShimmerFrameLayout videosSL;

    RecyclerView.Adapter recyclerViewadapter;
    String GET_JSON_DATA_HTTP_URL_VIDEOS = "https://seekho.xyz/pgp-android/api/get-videos.php";
    String JSON_URL = "url";
    String JSON_TITLE = "title";
    String JSON_DESCRIPTION = "description";
    String JSON_ISVIDEO = "isvideo";
    String JSON_CATEGORY = "category";
    String JSON_THUBMNAIL = "thumbnail";
    JsonArrayRequest jsonArrayRequest ;
    ProgressBar progressBar;

    RequestQueue requestQueue ;
    SwipeRefreshLayout talentSwipeRefreshLayout;

    String category_temp, category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Code for White Status Bar

        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

        videosSL = findViewById(R.id.videos_shimmer_layout);
        videosSL.startShimmerAnimation();

        GetDataAdapter1 = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_videos);

        // swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        JSON_DATA_WEB_CALL();

    }

    //The Main Function to Show all the Videos
    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL_VIDEOS,

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

            GetDataAdapterVideos GetDataAdapter2 = new GetDataAdapterVideos();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setTitle(json.getString(JSON_TITLE));
                GetDataAdapter2.setDescription(json.getString(JSON_DESCRIPTION));
                GetDataAdapter2.setUrl(json.getString(JSON_URL));
                GetDataAdapter2.setThumbnail(json.getString(JSON_THUBMNAIL));
               // GetDataAdapter2.setCategory(json.getString(JSON_CATEGORY));
                GetDataAdapter2.setIsvideo(json.getString(JSON_ISVIDEO));


            } catch (JSONException e) {

                e.printStackTrace();
            }

            GetDataAdapter1.add(GetDataAdapter2);

        }

        recyclerViewadapter = new RecyclerViewAdapterVideos(GetDataAdapter1, this);

        recyclerView.setAdapter(recyclerViewadapter);

        recyclerView.setVisibility(View.VISIBLE);
        videosSL.setVisibility(View.GONE);

    }


    // Back arrow click event to go to the parent Activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
