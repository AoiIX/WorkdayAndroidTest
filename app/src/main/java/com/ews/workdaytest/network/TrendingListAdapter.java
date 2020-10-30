package com.ews.workdaytest.network;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ews.workdaytest.MainActivity;
import com.ews.workdaytest.R;
import com.ews.workdaytest.model.TrendingDataModel;
import com.ews.workdaytest.utils.Constants;

import java.util.List;

public class TrendingListAdapter extends RecyclerView.Adapter<TrendingListAdapter.ViewHolder> {
    private static final String TAG = "TRENDING_ADAPTER";
    private List<TrendingDataModel> mData;
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    public TrendingListAdapter(Context context, List<TrendingDataModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_trending, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TrendingDataModel image = mData.get(position);
        holder.displayName.setText(image.title);
        holder.rating.setText("ID: " + image.rating);
        holder.source.setText("Ver. " + image.source);

        Glide.with(context)
                .load(Constants.NETWORK.IMAGE_URL + image.id + "/200.gif")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(new RequestOptions().override(1080, 1080))
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView displayName;
        ImageView image;
        TextView rating;
        TextView source;

        ViewHolder(View itemView) {
            super(itemView);
            displayName = itemView.findViewById(R.id.display_name);
            image = itemView.findViewById(R.id.image);
            rating = itemView.findViewById(R.id.rating);
            source = itemView.findViewById(R.id.source);
        }
    }
}