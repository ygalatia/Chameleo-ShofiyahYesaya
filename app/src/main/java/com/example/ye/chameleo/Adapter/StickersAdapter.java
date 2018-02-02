package com.example.ye.chameleo.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ye.chameleo.EditPicture;
import com.example.ye.chameleo.Model.Stickers;
import com.example.ye.chameleo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Yesaya on 1/24/2018.
 */

public class StickersAdapter extends RecyclerView.Adapter<StickersAdapter.StickerViewHolder> {

    private Context context;
    private List<Stickers> listSticker;
    Bitmap b;


    public StickersAdapter(Context context, List<Stickers> listSticker) {
        this.context = context;
        this.listSticker = listSticker;
    }

    @Override
    public StickersAdapter.StickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filters, parent, false);
        return new StickersAdapter.StickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StickersAdapter.StickerViewHolder holder, int position) {
        final Stickers stickers = listSticker.get(position);

        if(!stickers.getStickerDetails().equals("downloaded"))
        {
            String uri = "drawable/" + stickers.getStickerPhoto();
            int icon = context.getResources().getIdentifier(uri, "drawable", context.getPackageName());
            holder.imgSticker.setImageResource(icon);
        }else if (stickers.getStickerDetails().equals("downloaded"))
        {
            File directory = context.getDir("ChameleoSticker", Context.MODE_PRIVATE);
            File file = new File(directory, stickers.getStickerPhoto()+".png");
            try {
                b = BitmapFactory.decodeStream(new FileInputStream(file));
                holder.imgSticker.setImageBitmap(b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        holder.txtSticker.setText(stickers.getStickerName());
        holder.imgSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!stickers.getStickerDetails().equals("downloaded"))
                {
                    ((EditPicture)context).addSticker("drawable/" + stickers.getStickerPhoto());
                }
                else{
                    ((EditPicture)context).addDownloadedSticker(b);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSticker.size();
    }

    public class StickerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSticker;
        TextView txtSticker;
        public StickerViewHolder(View itemView) {
            super(itemView);
            imgSticker = itemView.findViewById(R.id.img_filters);
            txtSticker = itemView.findViewById(R.id.txt_filtername);
        }
    }
}
