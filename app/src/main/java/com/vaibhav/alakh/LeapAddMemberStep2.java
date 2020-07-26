package com.vaibhav.alakh;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mikhaellopez.circularimageview.CircularImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.StrictMode;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

public class LeapAddMemberStep2 extends AppCompatActivity {

    CircularImageView imageView;
    private String filePath = null;
    EditText nameET, addressET, cityET, ownerET;
    int errorColor;
    String name, mobile, address, city, owner, occupation;
    TextView mobiletv, txtPercentage;
    long totalSize = 0;
    ProgressBar progressBar;
    Button submitBTN;
    // LogCat tag
    private static final String TAG = LeapAddMemberStep2.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Code for White Status Bar

        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leap_add_member_step2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);


        getWindow().setStatusBarColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        //Code for Error
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {

            errorColor = ContextCompat.getColor(getApplicationContext(), R.color.errorColor);
        } else {
            errorColor = getResources().getColor(R.color.errorColor);
        }

        // Receiving the data from previous activity
        Intent i = getIntent();

        imageView = findViewById(R.id.step2_member_image_view);

        // image or video path that is captured in previous activity
        filePath = i.getStringExtra("filePath");


        // boolean flag to identify the media type, image or video
        boolean isImage = i.getBooleanExtra("isImage", true);

        if (filePath != null) {
            // Displaying the image or video on the screen
            previewMedia(isImage);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mobile = sharedPreferences.getString(Config.MOBILE_REG_SP, "");


        progressBar = findViewById(R.id.step2_progress_bar);
        txtPercentage = findViewById(R.id.step2_text_percentage);

        mobiletv = findViewById(R.id.step2_mobile_tv);
        mobiletv.setText(mobile);

        nameET = findViewById(R.id.nameET);
        addressET = findViewById(R.id.addressET);
        cityET = findViewById(R.id.cityET);
        ownerET = findViewById(R.id.ownerET);

        submitBTN = findViewById(R.id.submitBTN);

        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateName()) {
                    return;
                }

                if (!validateAddress()) {
                    return;
                }

                if (!validateCity()) {
                    return;
                }

                if (!validateOwner()) {
                    return;
                }

                new UploadFileToServer().execute();
            }
        });


    }

    /**
     * Displaying captured image/video on the screen
     */

    private void previewMedia(boolean isImage) {
        // Checking whether captured media is image or video
        if (isImage) {

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 10;

            final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

            imageView.setImageBitmap(bitmap);

        }
    }

    //Name Validation
    private boolean validateName() {
        if (nameET.getText().toString().trim().isEmpty()) {
            String errorString = "वैध नाम दर्ज करें।";  // your error message
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            nameET.setError(spannableStringBuilder);
            return (false);

        }
        return (true);
    }

    //Address Validation
    private boolean validateAddress() {
        if (addressET.getText().toString().trim().isEmpty()) {
            String errorString = "वैध पता दर्ज करें।";  // your error message
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            addressET.setError(spannableStringBuilder);
            return (false);

        }
        return (true);
    }

    //City Validation
    private boolean validateCity() {
        if (cityET.getText().toString().trim().isEmpty()) {
            String errorString = "वैध शहर दर्ज करें।";  // your error message
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            cityET.setError(spannableStringBuilder);
            return (false);

        }
        return (true);
    }

    //Owner Validation
    private boolean validateOwner() {
        if (ownerET.getText().toString().trim().isEmpty()) {
            String errorString = "वैध मुहल्ला दर्ज करें।";  // your error message
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            ownerET.setError(spannableStringBuilder);
            return (false);

        }
        return (true);
    }


    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);

            txtPercentage.setVisibility(View.VISIBLE);
            // updating percentage value
            txtPercentage.setText("कृपया रजिस्टर करते समय प्रतीक्षा करें - " + String.valueOf(progress[0]) + "%");

            //Set Visibility of Upload Button
            submitBTN.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(Void... params) {

            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL_LEAP);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(filePath);

                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));

                // Extra parameters to pass to server

//               SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//                String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

                name = nameET.getText().toString();
                //ward = wardET.getText().toString();
                //assembly = assemblyET.getText().toString();
                city = cityET.getText().toString();
                address = addressET.getText().toString();
                occupation = ownerET.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                owner = sharedPreferences.getString(Config.MID_SHARED_PREF,"");

                entity.addPart("name", new StringBody(name));
                entity.addPart("mobile", new StringBody(mobile));
                entity.addPart("city", new StringBody(city));
                entity.addPart("owner", new StringBody(owner));
                entity.addPart("occupation", new StringBody(occupation));
                entity.addPart("address", new StringBody(address));


                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "Response from server: " + result);

            // showing the server response in an alert dialog
            showAlert(result);

            super.onPostExecute(result);
        }

    }

    /**
     * Method to show alert dialog
     */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishActivity(0);
                        Intent intent = new Intent(LeapAddMemberStep2.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    // Back arrow click event to go to the parent Activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}
