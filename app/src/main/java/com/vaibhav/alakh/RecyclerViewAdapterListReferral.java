package com.vaibhav.alakh;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class RecyclerViewAdapterListReferral extends RecyclerView.Adapter<RecyclerViewAdapterListReferral.ViewHolder> {
    Context context;
    public String str,mobi;

    long totalSize = 0;
    // LogCat tag
    private static final String TAG = RecyclerViewAdapterListReferral.class.getSimpleName();

    List<GetDataAdapterListReferral> getDataAdapter;

    ImageLoader imageLoader1;

    public RecyclerViewAdapterListReferral(List<GetDataAdapterListReferral> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;

    }

    @Override
    public RecyclerViewAdapterListReferral.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_referral_list, parent, false);

        RecyclerViewAdapterListReferral.ViewHolder viewHolder = new RecyclerViewAdapterListReferral.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapterListReferral.ViewHolder Viewholder, int position) {

        final GetDataAdapterListReferral getDataAdapter1 =  getDataAdapter.get(position);

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


        Viewholder.whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = Viewholder.MidView.getText().toString();
                mobi = Viewholder.MobileView.getText().toString();

                //WhatsApp Action Intent

                String mobil = Viewholder.MobileView.getText().toString();
                String url = "https://api.whatsapp.com/send?phone=91"+mobil;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
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
        public ImageView whatsApp;

        public ViewHolder(View itemView) {

            super(itemView);

            NameView = (TextView) itemView.findViewById(R.id.ref_name) ;
            networkImageView =  (NetworkImageView) itemView.findViewById(R.id.ref_member_image) ;
            MobileView = (TextView) itemView.findViewById(R.id.ref_mobile) ;
            MidView = (TextView) itemView.findViewById(R.id.ref_id) ;
            whatsApp = (ImageView) itemView.findViewById(R.id.ref_whatsapp);

        }
    }




}
