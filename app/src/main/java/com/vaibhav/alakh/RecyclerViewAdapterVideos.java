package com.vaibhav.alakh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class RecyclerViewAdapterVideos extends RecyclerView.Adapter<RecyclerViewAdapterVideos.ViewHolder> {
    Context context;
    String abc;


    List<GetDataAdapterVideos> getDataAdapter;

    ImageLoader imageLoader1;

    public RecyclerViewAdapterVideos(List<GetDataAdapterVideos> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 1;
        else
            return 2;
    }

    @Override
    public RecyclerViewAdapterVideos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 1) {

            final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_videos_first, parent, false);
            RecyclerViewAdapterVideos.ViewHolder viewHolder = new RecyclerViewAdapterVideos.ViewHolder(v);
            return viewHolder;
        }
        else {

            final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_videos, parent, false);
            RecyclerViewAdapterVideos.ViewHolder viewHolder = new RecyclerViewAdapterVideos.ViewHolder(v);
            return viewHolder;
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapterVideos.ViewHolder Viewholder, int position) {

        final GetDataAdapterVideos getDataAdapter1 =  getDataAdapter.get(position);

        imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();

        imageLoader1.get(getDataAdapter1.getThumbnail(),
                ImageLoader.getImageListener(
                        Viewholder.networkImageView,//Server Image
                        R.drawable.loadingbg,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        Viewholder.networkImageView.setImageUrl(getDataAdapter1.getThumbnail(), imageLoader1);

        Viewholder.TitleView.setText(getDataAdapter1.getTitle());

        Viewholder.URLView.setText(getDataAdapter1.getUrl());

        Viewholder.DescriptionView.setText(getDataAdapter1.getDescription());

        //Viewholder.CategoryView.setText(getDataAdapter1.getCategory());

        Viewholder.IsVideoView.setText(getDataAdapter1.getIsvideo());

        Viewholder.networkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewVideo.class);
                String url = Viewholder.URLView.getText().toString();
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });

        Viewholder.TitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewVideo.class);
                String url = Viewholder.URLView.getText().toString();
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });

        Viewholder.DescriptionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewVideo.class);
                String url = Viewholder.URLView.getText().toString();
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });

        Viewholder.contactL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.whatsapp.com/send?phone=916375509117";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });

        Viewholder.shareL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vurl = Viewholder.URLView.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,vurl);
                intent.setType("text/plain");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView TitleView, URLView, DescriptionView, IsVideoView,CategoryView, ContactUsView;
        public NetworkImageView networkImageView ;
        public LinearLayout shareL, contactL;
        public ImageView whatsappiconIV;
        public ViewHolder(View itemView) {

            super(itemView);

            TitleView = (TextView) itemView.findViewById(R.id.videos_title) ;
            networkImageView = (NetworkImageView) itemView.findViewById(R.id.videos_thumbnail) ;
            URLView = (TextView) itemView.findViewById(R.id.videos_url_view) ;
            DescriptionView = (TextView) itemView.findViewById(R.id.videos_description) ;
            IsVideoView = (TextView) itemView.findViewById(R.id.videos_isvideo);
            shareL = itemView.findViewById(R.id.videos_shareLL);
            contactL = itemView.findViewById(R.id.videos_contactLL);
            //CategoryView = (TextView) itemView.findViewById(R.id.videos_category);



        }
    }

}
