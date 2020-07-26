package com.vaibhav.alakh;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    String abc;

    List<GetDataAdapter> getDataAdapter;

    ImageLoader imageLoader1;

    public RecyclerViewAdapter(List<GetDataAdapter> getDataAdapter, Context context){

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 1) {

            final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
            RecyclerViewAdapter.ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(v);
            return viewHolder;
        }
        else {

            final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_first, parent, false);
            RecyclerViewAdapter.ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(v);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder Viewholder, int position) {

        final GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);

        imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();

        imageLoader1.get(getDataAdapter1.getImage(),
                ImageLoader.getImageListener(
                        Viewholder.networkImageView,//Server Image
                        R.drawable.loadingbg,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        Viewholder.networkImageView.setImageUrl(getDataAdapter1.getImage(), imageLoader1);

        Viewholder.TitleView.setText(getDataAdapter1.getTitle());

        Viewholder.VideoURLView.setText(getDataAdapter1.getVideoUrl());

        Viewholder.postIDTV.setText(getDataAdapter1.getPostID());

        Viewholder.DescriptionView.setText(getDataAdapter1.getDescription());

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

                String a = Viewholder.postIDTV.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"https://seekho.xyz/alakh-posts/view.php?id="+a);
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

        public TextView TitleView, VideoURLView, DescriptionView, postIDTV;
        public NetworkImageView networkImageView ;
        public LinearLayout shareL, contactL;
        public FloatingActionButton sharefab;
        public ViewHolder(View itemView) {

            super(itemView);

            TitleView = (TextView) itemView.findViewById(R.id.title) ;
            networkImageView = (NetworkImageView) itemView.findViewById(R.id.VollyNetworkImageView1) ;
            VideoURLView = (TextView) itemView.findViewById(R.id.image_url_view) ;
            DescriptionView = (TextView) itemView.findViewById(R.id.description) ;
            postIDTV = (TextView) itemView.findViewById(R.id.post_idTV) ;

            shareL = itemView.findViewById(R.id.post_shareLL);
            contactL = itemView.findViewById(R.id.post_contactLL);


            TitleView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Bundle b1 = new Bundle();
                    b1.putString("imageurl", VideoURLView.getText().toString());
                    b1.putString("title", TitleView.getText().toString());
                    b1.putString("description", DescriptionView.getText().toString());
                    b1.putString("id", postIDTV.getText().toString());
                    //  Toast.makeText(context,a,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context,PostDetails.class);
                    intent.putExtras(b1);
                    context.startActivity(intent);

                }
            });
            DescriptionView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Bundle b1 = new Bundle();
                    b1.putString("imageurl", VideoURLView.getText().toString());
                    b1.putString("title", TitleView.getText().toString());
                    b1.putString("description", DescriptionView.getText().toString());
                    b1.putString("id", postIDTV.getText().toString());
                    //  Toast.makeText(context,a,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context,PostDetails.class);
                    intent.putExtras(b1);
                    context.startActivity(intent);

                }
            });
            networkImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Bundle b1 = new Bundle();
                    b1.putString("imageurl", VideoURLView.getText().toString());
                    b1.putString("title", TitleView.getText().toString());
                    b1.putString("description", DescriptionView.getText().toString());
                    b1.putString("id", postIDTV.getText().toString());
                    //  Toast.makeText(context,a,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context,PostDetails.class);
                    intent.putExtras(b1);
                    context.startActivity(intent);

                }
            });

        }
    }

}
