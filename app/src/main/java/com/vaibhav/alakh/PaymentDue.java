package com.vaibhav.alakh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentDue extends AppCompatActivity {
    String mid,mobile,name;
    TextView nameTV, midTV, mobileTV, logoutTV;
    Button paymentBTN;
    ImageView backImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_due);

        Intent intent = getIntent();

//        mid = intent.getStringExtra("mid");
//        mobile = intent.getStringExtra("mobile");
            name = intent.getStringExtra("name");
       // String str = membershipET.getText().toString();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mid = sharedPreferences.getString(Config.MID_SHARED_PREF,"");
        mobile = sharedPreferences.getString(Config.USER_MOBILE,"");

        Toast.makeText(this, mobile, Toast.LENGTH_SHORT).show();

        nameTV =findViewById(R.id.payment_due_name);
        midTV = findViewById(R.id.payment_due_mid);
        mobileTV = findViewById(R.id.payment_due_mobile);
        logoutTV = findViewById(R.id.payment_due_logout);
        paymentBTN = findViewById(R.id.payment_due_pay_button);
        //backImage = findViewById(R.id.payment_due_back_button);

        nameTV.setText(name);
        midTV.setText("सदस्यता आईडी: "+mid);
        mobileTV.setText("मोबाइल नंबर: "+mobile);

        logoutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                Intent intent = new Intent(PaymentDue.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        paymentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://seekho.xyz/pgp-android/api/payments/web/index.php?membership_id="+mid+"&mobile="+mobile); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

//        backImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
    }
}
