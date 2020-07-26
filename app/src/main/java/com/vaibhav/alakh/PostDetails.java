package com.vaibhav.alakh;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PostDetails extends AppCompatActivity {

    TextView titleTV, descriptionTV;
    ImageView imageIV;
    FloatingActionButton shareFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Code for White Status Bar
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);


        titleTV = findViewById(R.id.postdetails_title);
        descriptionTV = findViewById(R.id.postdetails_description);
        imageIV = findViewById(R.id.postdetails_image);
        shareFAB = findViewById(R.id.shareFAB);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("title");
        String description = bundle.getString("description");
        String image_url = bundle.getString("imageurl");
        final String id = bundle.getString("id");

        getSupportActionBar().setTitle(title);

        Glide.with(this)
                .load(image_url)
                .asBitmap()
                .into(imageIV);

        titleTV.setText(title);
        descriptionTV.setText(description);

        shareFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = id;
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"https://seekho.xyz/alakh-posts/view.php?id="+a);
                intent.setType("text/plain");
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
