package com.example.ye.chameleo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ye.chameleo.EditPicture;
import com.example.ye.chameleo.MapsChameleoActivity;
import com.example.ye.chameleo.Model.LocationSticker;
import com.example.ye.chameleo.R;

import java.util.List;

/**
 * Created by Yesaya on 1/26/2018.
 */

public class LocationStickerAdapter extends RecyclerView.Adapter<LocationStickerAdapter.LocStickerViewHolder> {

    private Context context;
    private List<LocationSticker> locationStickerList;

    public LocationStickerAdapter(Context context, List<LocationSticker> locationStickerList) {
        this.context = context;
        this.locationStickerList = locationStickerList;
    }

    @Override
    public LocationStickerAdapter.LocStickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filters, parent, false);
        return new LocationStickerAdapter.LocStickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationStickerAdapter.LocStickerViewHolder holder, final int position) {
        final LocationSticker locSticker = locationStickerList.get(position);
        String uri = "drawable/" + locSticker.getLocSticker();
        int icon = context.getResources().getIdentifier(uri, "drawable", context.getPackageName());
        holder.txtLocName.setText(locSticker.getLocName());
        holder.imgLocSticker.setImageResource(icon);
        holder.imgLocSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MapsChameleoActivity.class);
                i.putExtra("LocType", locSticker.getLocType());
                //context.startActivity(i);
                ((Activity) context).startActivityForResult(i, 0);
            }
        });

    }

    @Override
    public int getItemCount() {
        return locationStickerList.size();
    }

    public class LocStickerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLocSticker;
        TextView txtLocName;
        public LocStickerViewHolder(View itemView) {
            super(itemView);
            imgLocSticker = itemView.findViewById(R.id.img_filters);
            txtLocName = itemView.findViewById(R.id.txt_filtername);

        }
    }
}
