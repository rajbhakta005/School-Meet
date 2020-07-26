package com.vaibhav.alakh;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Posts extends AppCompatActivity {

    List<GetDataAdapter> GetDataAdapter1;

    RecyclerView recyclerView;

    ImageView noPostsIV;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    ShimmerFrameLayout postSL;

    RecyclerView.Adapter recyclerViewadapter;
    String GET_JSON_DATA_HTTP_URL_RJ = "https://seekho.xyz/pgp-android/api/posts.php";
    String JSON_IMAGE = "thumbnail";
    String JSON_TITLE = "title";
    String JSON_DESCRIPTION = "description";
    String JSON_POSTID = "id";
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
        setContentView(R.layout.activity_posts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

        GetDataAdapter1 = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        postSL = findViewById(R.id.post_shimmer_layout);

        postSL.startShimmerAnimation();

        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        switch (category){

            case "leap" : getSupportActionBar().setTitle("लीप");
            break;

            case "peoplesgreen" : getSupportActionBar().setTitle("पीपल्स ग्रीन");
            break;
        }
        //getSupportActionBar().setTitle(category);

        //Get All the Posts
        JSON_DATA_WEB_CALL();

    }


    //The Main Function to Show all the Videos
    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL_RJ+"?type="+category,

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

            GetDataAdapter GetDataAdapter2 = new GetDataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setTitle(json.getString(JSON_TITLE));
                GetDataAdapter2.setImage(json.getString(JSON_IMAGE));
                GetDataAdapter2.setVideoUrl(json.getString(JSON_IMAGE));
                GetDataAdapter2.setDescription(json.getString(JSON_DESCRIPTION));
                GetDataAdapter2.setPostID(json.getString(JSON_POSTID));


            } catch (JSONException e) {

                e.printStackTrace();
            }

                GetDataAdapter1.add(GetDataAdapter2);

        }

        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, this);

        recyclerView.setAdapter(recyclerViewadapter);

        recyclerView.setVisibility(View.VISIBLE);
        postSL.setVisibility(View.GONE);



    }
    // Back arrow click event to go to the parent Activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
