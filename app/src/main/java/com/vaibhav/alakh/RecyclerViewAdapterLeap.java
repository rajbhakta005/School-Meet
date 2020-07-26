package com.vaibhav.alakh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RecyclerViewAdapterLeap extends RecyclerView.Adapter<RecyclerViewAdapterLeap.ViewHolder> {
    Context context;
    public String str,mobi;

    ProgressDialog loading;

    private static final String VERIFY_URL = "https://seekho.xyz/pgp-android/api/confirm-leap.php";
    long totalSize = 0;
    // LogCat tag
    private static final String TAG = RecyclerViewAdapterLeap.class.getSimpleName();

    List<GetDataAdapterLeap> getDataAdapter;

    ImageLoader imageLoader1;

    public RecyclerViewAdapterLeap(List<GetDataAdapterLeap> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;

    }

    @Override
    public RecyclerViewAdapterLeap.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_verify_leap, parent, false);

        RecyclerViewAdapterLeap.ViewHolder viewHolder = new RecyclerViewAdapterLeap.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapterLeap.ViewHolder Viewholder, int position) {

        final GetDataAdapterLeap getDataAdapter1 =  getDataAdapter.get(position);

        imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();

        imageLoader1.get(getDataAdapter1.getImage(),
                ImageLoader.getImageListener(
                        Viewholder.networkImageView,//Server Image
                        R.drawable.loadingbg,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        Viewholder.networkImageView.setImageUrl(getDataAdapter1.getImage(), imageLoader1);

        Viewholder.NameView.setText(getDataAdapter1.getName());

        Viewholder.MobileView.setText(getDataAdapter1.getMobile());

        Viewholder.MidView.setText(getDataAdapter1.getMid());


        Viewholder.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = Viewholder.MidView.getText().toString();
                mobi = Viewholder.MobileView.getText().toString();

                loginUser();

                Viewholder.progressBar.setVisibility(View.VISIBLE);
                Viewholder.acceptBtn.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView NameView, MobileView, MidView;
        public NetworkImageView networkImageView ;
        public Button acceptBtn;
        public ProgressBar progressBar;

        public ViewHolder(View itemView) {

            super(itemView);

            NameView = (TextView) itemView.findViewById(R.id.leap_name) ;
            networkImageView =  (NetworkImageView) itemView.findViewById(R.id.leap_member_image) ;
            MobileView = (TextView) itemView.findViewById(R.id.leap_mobile) ;
            MidView = (TextView) itemView.findViewById(R.id.leap_id) ;
            acceptBtn = (Button) itemView.findViewById(R.id.leap_accept);
            progressBar = (ProgressBar) itemView.findViewById(R.id.leap_verify_progress);

        }
    }



    private void loginUser() {

        String membership_id = str;
        String mobile = mobi;

        login(membership_id,mobile);
    }

    private void login(final String membership_id, final  String mobile) {

        String urlSuffix = "?membership_id=" + membership_id + "&mobile=" + mobile;
        class RegisterUser extends AsyncTask<String, Void, String> {



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading.setMessage("कृपया सदस्यता सत्यापित करने की प्रतीक्षा करें");
//                loading.setCancelable(false);
//                loading.show();
            }

            @Override
            protected void onPostExecute(final String result) {

                super.onPostExecute(result);
//                loading.dismiss();
                  Toast.makeText(context,result,Toast.LENGTH_LONG).show();
                String b = result.trim();
                String a = "success";

                    Toast.makeText(context, "Membership ID has been Activated Successfully", Toast.LENGTH_SHORT).show();

            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;

                try {

                    URL url = new URL(VERIFY_URL + s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                } catch (Exception e) {
                    return null;
                }
            }

        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }


}
