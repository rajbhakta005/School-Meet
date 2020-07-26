package com.vaibhav.alakh;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String emid;
    CardView add_member_card, pgp_card, second_card, third_card, pgp2CV,pgp3CV,pgp4CV,pgp5CV,pgp6CV,pgp7CV;
    CircularImageView circularImageView;
    String versionName;
    TextView nameTV,PGPTV, SecondTV, PGPTV2, PGPTV3, PGPTV4, PGPTV5, PGPTV6, PGPTV7;
    CoordinatorLayout homeCoordinatorLayout;


    ShimmerFrameLayout shimmerFrameLayout, paymnetAlertShimmer;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Status Bar Color
        getWindow().setStatusBarColor(Color.WHITE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        //Menu Icon
        toolbar.setNavigationIcon(R.drawable.menunew);

        //Get the App Version Programmatically
        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String packageName = info.packageName;
            int versionCode = info.versionCode;
            versionName = info.versionName;

            View headerView = navigationView.getHeaderView(0);
            TextView navversion = headerView.findViewById(R.id.nav_version);
            navversion.setText("एप्लिकेशन संस्करण v."+versionName);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
        }

        shimmerFrameLayout = findViewById(R.id.homeSL);
        linearLayout = findViewById(R.id.homeLL);
        paymnetAlertShimmer = findViewById(R.id.home_payment_alert_shimmer);
        shimmerFrameLayout.startShimmerAnimation();

        // No. 1 Card (Add Memeber)
        add_member_card = (findViewById(R.id.add_member_card));

        //No. 2 Card (Video)
        pgp_card = (findViewById(R.id.home_pgp_card));

        //No. 3 Card (Peoples Green)
        pgp2CV = findViewById(R.id.home_pgp_card2);

        //No.4 Card (Sampark)
        pgp3CV = findViewById(R.id.home_pgp_card3);


        // No. 6 Card (Leap)
        third_card = (findViewById(R.id.home_third_card));

        //        pgp4CV = findViewById(R.id.home_pgp_card4);
//        pgp5CV = findViewById(R.id.home_pgp_card5);
//        pgp6CV = findViewById(R.id.home_pgp_card6);
//        pgp7CV = findViewById(R.id.home_pgp_card7);

        circularImageView = findViewById(R.id.home_profile_image_view);
        nameTV = findViewById(R.id.name_tv_home);
        homeCoordinatorLayout = findViewById(R.id.home_coordinator_layout);


        // Add Member Card
        add_member_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Step1.class);
                startActivity(intent);
            }
        });

        // Videos Card
        pgp_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, VideosList.class);
                startActivity(intent);
            }
        });

        // Peoples Green Card
        pgp2CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Posts.class);
                String category = "peoplesgreen";
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        // Leap Card
        pgp3CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Posts.class);
                String category = "leap";
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        // Sampark Card
        third_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, LeapMemberList.class);

                startActivity(intent);
            }
        });

        nameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, NewDigitalID.class);
                startActivity(intent);
            }
        });

        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, NewDigitalID.class);
                startActivity(intent);
            }
        });

//        group_1_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(Home.this, A.class);
//                startActivity(intent);
//            }
//        });


        //Getting Data from Server
        getData();
    }


    //onBackPresses Exit
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (exit){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            //***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);
        }

        else {

            Snackbar.make(homeCoordinatorLayout, "बाहर निकलने के लिए फिर से दबाएं", Snackbar.LENGTH_LONG).show();
           // Toast.makeText(this, "बाहर निकलने के लिए फिर से दबाएं",Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2 * 1000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent in = new Intent(Home.this, Settings.class);
            startActivity(in);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_leap_member) {
            // Handle the Add Member action

            Intent intent = new Intent(Home.this, LeapAddMember_Step1.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_digital_id) {

            Intent intent = new Intent(Home.this, NewDigitalID.class);
            startActivity(intent);

        } else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(Home.this, AboutUs.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_referral) {

            Intent intent = new Intent(Home.this, ReferralHome.class);
            startActivity(intent);

        }


        else if (id == R.id.nav_leap_verify) {

            Intent intent = new Intent(Home.this, VerifyLeapAccountsNew.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_donate) {

            Intent intent = new Intent(Home.this, DonateFunds.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_fee_payment) {
            Intent intent = new Intent(Home.this, PayMembershipFee.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_settings) {

            Intent intent = new Intent(Home.this, Settings.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_logout) {

            logout();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getData() {
        //Fetching MID from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(Config.MID_SHARED_PREF,"");
        if (str.equals("")) {
            Toast.makeText(this, "जारी रखने के लिए कृपया लॉग इन करें", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Home.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
            startActivity(intent);
            return;
        }

        String url = Config.DATA_URL+str;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Home.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
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
        String payment_status_home="";
        String app_version = "";
        String pgp_option1 = "";
        String pgp_option2 = "";
        String pgp_option3 = "";
        String pgp_option4 = "";
        String pgp_option5 = "";
        String pgp_option6 = "";
        String pgp_option7 = "";
        String asangathit_option = "";
        String entrepreneurship_option = "";

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navname = headerView.findViewById(R.id.nav_name);
        TextView navmid = headerView.findViewById(R.id.nav_mid);
        TextView nametv_home = findViewById(R.id.name_tv_home);
        CircularImageView imageView = findViewById(R.id.imageView);
        CircularImageView homeImageView = findViewById(R.id.home_profile_image_view);
        PGPTV = findViewById(R.id.home_pgp_card_TV);
        PGPTV2 = findViewById(R.id.home_pgp_card_TV2);
        PGPTV3 = findViewById(R.id.home_pgp_card_TV3);
//        PGPTV4 = findViewById(R.id.home_pgp_card_TV4);
//        PGPTV5 = findViewById(R.id.home_pgp_card_TV5);
//        PGPTV6 = findViewById(R.id.home_pgp_card_TV6);
//        PGPTV7 = findViewById(R.id.home_pgp_card_TV7);

        try {

            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject userdata = result.getJSONObject(0);

            name = userdata.getString(Config.KEY_NAME);
            mid = userdata.getString(Config.KEY_MID);
            mobile = userdata.getString(Config.KEY_MOBILE);
            profile_image = userdata.getString(Config.PROFILE_IMAGE_URL);
            payment_status_home = userdata.getString(Config.KEY_PAYMENT_STATUS_HOME);
            app_version = userdata.getString(Config.KEY_APP_VERSION);
            pgp_option1 = userdata.getString(Config.KEY_APP_PGP1);
            pgp_option2 = userdata.getString(Config.KEY_APP_PGP2);
            pgp_option3 = userdata.getString(Config.KEY_APP_PGP3);
            pgp_option4 = userdata.getString(Config.KEY_APP_PGP4);
            pgp_option5 = userdata.getString(Config.KEY_APP_PGP5);
            pgp_option6 = userdata.getString(Config.KEY_APP_PGP6);
            pgp_option7 = userdata.getString(Config.KEY_APP_PGP7);
            asangathit_option = userdata.getString(Config.KEY_APP_ASANGATHIT);
            entrepreneurship_option = userdata.getString(Config.KEY_APP_ENTREPRENEURSHIP);

  //         Creating a shared preference
           SharedPreferences sharedPreferences = Home.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

//            //Creating editor to store values to shared preferences
           SharedPreferences.Editor editor = sharedPreferences.edit();

//            //Adding values to editor
           editor.putString(Config.PAYMENT_MID, mid);
            editor.putString(Config.USER_MOBILE, mobile);
           editor.putString(Config.PAYMENT_STATUS, payment_status_home);
           editor.putString(Config.PAYMENT_MOBILE, mobile);
//            editor.putString(Config.ATTEMPT1_SHARED_PREF, level1attempt);
//            editor.putString(Config.ATTEMPT2_SHARED_PREF, level2attempt);
            editor.commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (payment_status_home.equals("0"))
        {

            paymnetAlertShimmer.setVisibility(View.VISIBLE);
            paymnetAlertShimmer.startShimmerAnimation();
            paymnetAlertShimmer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Home.this, PayMembershipFee.class);
                    startActivity(intent);
                }
            });

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_add_leap_member).setVisible(false);
            nav_Menu.findItem(R.id.nav_leap_verify).setVisible(false);
            nav_Menu.findItem(R.id.nav_referral).setVisible(false);

            // Add Member Card
            add_member_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(homeCoordinatorLayout, "जारी रखने के लिए कृपया सदस्यता शुल्क का भुगतान करें", Snackbar.LENGTH_LONG).show();
                }
            });

            // Sampark Card
            third_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(homeCoordinatorLayout, "जारी रखने के लिए कृपया सदस्यता शुल्क का भुगतान करें", Snackbar.LENGTH_LONG).show();
                }
            });


//            paymnetAlertShimmer.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//                    String mid = sharedPreferences.getString(Config.PAYMENT_MID,"");
//                    String mobile = sharedPreferences.getString(Config.PAYMENT_MOBILE,"");
//                    Intent intent = new Intent(Home.this, PaymentDue.class);
//                    intent.putExtra("mid",mid);
//                    intent.putExtra("mobile",mobile);
//                    startActivity(intent);
//                }
//            });
//            Intent intent = new Intent(Home.this, PaymentDue.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("mid",mid);
//            intent.putExtra("name",name);
//            intent.putExtra("mobile",mobile);
//            startActivity(intent);
//            finish();
        }
        else {

            // Add Member Card
            add_member_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Home.this, Step1.class);
                    startActivity(intent);
                }
            });

            // Sampark Card
            third_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Home.this, LeapMemberList.class);
                    startActivity(intent);
                }
            });

            //Hide the Navigation Menu Item (Membership Fee Payment)
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_fee_payment).setVisible(false);
        }

        if(!versionName.equals(app_version)){

                appUpdateDialog();

        }

//        // PGP Options Cards Display Flags
//
//        if(pgp_option1.equals("Yes")){
//            PGPTV.setText("राजनीति एवं समाज सेवा");
//        }
//        else {
//            pgp_card.setVisibility(View.GONE);
//        }
//
//        if(pgp_option2.equals("Yes")){
//            pgp2CV.setVisibility(View.VISIBLE);
//            PGPTV2.setText("बढ़ती जनसंख्या की रोकथाम");
//        }
//
//        if(pgp_option3.equals("Yes")){
//            pgp3CV.setVisibility(View.VISIBLE);
//            PGPTV3.setText("नयी खेती समृद्ध किसान");
//        }
//
//        if(pgp_option4.equals("Yes")){
//            pgp4CV.setVisibility(View.VISIBLE);
//            PGPTV4.setText("युवा आंत्रेप्रेन्योर");
//        }
//
//
//        if(pgp_option5.equals("Yes")){
//            pgp5CV.setVisibility(View.VISIBLE);
//            PGPTV5.setText("महिलाओ को बराबरी");
//        }
//
//
//        if(pgp_option6.equals("Yes")){
//            pgp6CV.setVisibility(View.VISIBLE);
//            PGPTV6.setText("हर बेघर को निशुल्क आवास");
//        }
//
//
//        if(pgp_option7.equals("Yes")){
//            pgp7CV.setVisibility(View.VISIBLE);
//            PGPTV7.setText("जल एवं पर्यावरण रक्षा");
//        }
//


//        // Asangathit Union Cards Display Flags
//        switch (asangathit_option){
//
//            case "Rajasthan Vaahan Chaalak Sangathan" :
//                SecondTV.setText("राजस्थान वाहन चालक संगठन");
//                break;
//
//            case "Rajasthan Delivery Lions" :
//                SecondTV.setText("राजस्थान डिलीवरी लायंस");
//                break;
//
//            case "E Rickshaw Shramik Union" :
//                SecondTV.setText("इ-रिक्शा श्रमिक यूनियन");
//                break;
//
//            case "Haat Kaarobari Union" :
//                SecondTV.setText("हाट कारोबारी यूनियन");
//                break;
//
//            case "No" :
//                second_card.setVisibility(View.GONE);
//                break;
//        }

        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmerAnimation();
        linearLayout.setVisibility(View.VISIBLE);
        navname.setText(name);
        navmid.setText(mid);
        nametv_home.setText(name);
        Glide.with(this)
                .load(profile_image)
                .asBitmap()
                .into(imageView);
        Glide.with(this)
                .load(profile_image)
                .asBitmap()
                .into(homeImageView);
        //n = name.toString();
        emid = mid.toString();
       // mob = mobile.toString();
    }

    //Logout User
    //The Main Logout function
    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("लॉगआउट करना चाहते हैं?");
        alertDialogBuilder.setPositiveButton("हाँ।",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.MID_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        finishActivity(0);
                        //Starting login activity
                        Intent intent = new Intent(Home.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("नहीं",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void appUpdateDialog(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("एप्लिकेशन का उपयोग करने के लिए एप्लिकेशन को अपडेट करें");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("अपडेट करें",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.vaibhav.alakh"); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });


        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void showPaymentAlert(){
        Home.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setMessage("आपका सदस्यता शुल्क बकाया है।")
                        .setCancelable(false)
                        .setPositiveButton("ठीक।", new DialogInterface.OnClickListener() {
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
