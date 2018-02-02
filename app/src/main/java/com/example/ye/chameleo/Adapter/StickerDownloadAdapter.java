package com.example.ye.chameleo.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ye.chameleo.Model.StickerDownload;
import com.example.ye.chameleo.R;
import com.example.ye.chameleo.StickerDownloadPage;

import java.util.List;

/**
 * Created by Yesaya on 1/29/2018.
 */

public class StickerDownloadAdapter extends RecyclerView.Adapter<StickerDownloadAdapter.StickerHolder> {

    List<StickerDownload> mSticker;
    Context context;

    public StickerDownloadAdapter(List<StickerDownload> mSticker, Context context) {
        this.mSticker = mSticker;
        this.context = context;
    }

    @Override
    public StickerDownloadAdapter.StickerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_downloadsticker, parent, false);
        StickerHolder stickerHolder = new StickerHolder(view);
        return stickerHolder;
    }

    @Override
    public void onBindViewHolder(StickerDownloadAdapter.StickerHolder holder, int position) {
        final StickerDownload stickerDownload = mSticker.get(position);
        byte[] decodedString = Base64.decode(stickerDownload.getStickerFile(), Base64.DEFAULT);
        final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imgStickerfile.setImageBitmap(decodedByte);
        holder.txtStickerName.setText(stickerDownload.getStickerName());
        holder.imgStickerfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((StickerDownloadPage)context).saveToInternalStorage(decodedByte, stickerDownload.getStickerPath(), stickerDownload.getStickerName());
            }
        });

    }

    @Override
    public int getItemCount() {
        int arr =0;
        try{
            if(mSticker.size() == 0)
            {
                arr = 0;
            }else{
                arr = mSticker.size();
            }
        }catch(Exception e)
        {

        }

        return arr;
    }

    public class StickerHolder extends RecyclerView.ViewHolder {
        TextView txtStickerName;
        ImageView imgStickerfile;
        public StickerHolder(View itemView) {
            super(itemView);
            txtStickerName = itemView.findViewById(R.id.txt_StickerDownloadName);
            imgStickerfile = itemView.findViewById(R.id.media_sticker);
        }
    }
}
