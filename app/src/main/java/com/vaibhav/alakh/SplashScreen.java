package com.vaibhav.alakh;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.onesignal.OneSignal;


//import com.onesignal.OneSignal;

public class SplashScreen extends AppCompatActivity {
   //ImageView imageView;

//    AppUpdateManager appUpdateManager;
    int UPDATE_REQUEST_CODE = 1;
    private static final String TAG = "SpalshScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

          //Transparent Status Bar
        Window w = getWindow(); // in Activity's onCreate() for instance
     w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

//        imageView = (findViewById(R.id.loader_image_spalsh_screen));
//        Glide.with(this).load(R.drawable.preloadblue).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(imageView);


        // [START retrieve_current_token]
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        //Toast.makeText(SplashScreen.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        // [END retrieve_current_token]


        // Splash screen timer
        int SPLASH_TIME_OUT = 2500;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

//
//        inAppUpdate();


    }

//    private void inAppUpdate() {
//        // Creates instance of the manager.
//        appUpdateManager = AppUpdateManagerFactory.create(this);
//
//        // Returns an intent object that you use to check for an update.
//        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
//
//        // Checks that the platform will allow the specified type of update.
//        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
//            @Override
//            public void onSuccess(AppUpdateInfo appUpdateInfo) {
//
//                Log.e("AVAILABLE_VERSION_CODE", appUpdateInfo.availableVersionCode()+"");
//                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                        // For a flexible update, use AppUpdateType.FLEXIBLE
//                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
//                    // Request the update.
//
//                    try {
//                        appUpdateManager.startUpdateFlowForResult(
//                                // Pass the intent that is returned by 'getAppUpdateInfo()'.
//                                appUpdateInfo,
//                                // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
//                                AppUpdateType.IMMEDIATE,
//                                // The current activity making the update request.
//                                SplashScreen.this,
//                                // Include a request code to later monitor this update request.
//                                UPDATE_REQUEST_CODE);
//                    } catch (IntentSender.SendIntentException ignored) {
//
//                    }
//                }
//            }
//        });
//
//        appUpdateManager.registerListener(installStateUpdatedListener);
//
//    }
//
//    //lambda operation used for below listener
//    InstallStateUpdatedListener installStateUpdatedListener = installState -> {
//        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
//            popupSnackbarForCompleteUpdate();
//        } else
//            Log.e("UPDATE", "Not Downloaded Yet");
//    };
//
//
//    private void popupSnackbarForCompleteUpdate() {
//
//        Snackbar snackbar =
//                Snackbar.make(
//                        findViewById(android.R.id.content),
//                        "Update Almost Finished!",
//                        Snackbar.LENGTH_INDEFINITE);
//        //lambda operation used for below action
//        snackbar.setAction(this.getString(R.string.restart), view ->
//                appUpdateManager.completeUpdate());
//        snackbar.setActionTextColor(getResources().getColor(R.color.white));
//        snackbar.show();
//    }
}
