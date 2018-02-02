package com.example.ye.chameleo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ye.chameleo.Model.ImageItem;
import com.example.ye.chameleo.R;
import com.example.ye.chameleo.SelectedPicturePreview;

import java.util.List;

/**
 * Created by YE on 02/01/2018.
 */

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ImageItem> videoList;
    Context context;
    private final static int FADE_DURATION = 1000;
    public static Glide glid;
    String name;
    Bundle bundle=new Bundle();
    private static final int TYPE_ITEM = 1;

    public GalleryAdapter(List<ImageItem> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_gallery, parent, false);

        VideoViewHolder holder = new VideoViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageItem mediaObject=getItem(position);
        ((VideoViewHolder) holder).vImage.setImageResource(R.color.cardview_dark_background);
        ((VideoViewHolder) holder).vFilePath = mediaObject.getDATA();
        ((VideoViewHolder) holder).vName = mediaObject.getDISPLAY_NAME();
        ((VideoViewHolder) holder).vCreated = mediaObject.getCREATED();
        ((VideoViewHolder) holder).context = context;
        ((VideoViewHolder) holder).position = position;

        glid.clear(((VideoViewHolder) holder).vImage);
        glid.with(context)
                .load(mediaObject.getDATA())
                .centerCrop()
                .placeholder(R.color.cardview_dark_background)
                .crossFade()
                .into(((VideoViewHolder) holder).vImage);

        setScaleAnimation(((VideoViewHolder) holder).vImage);

    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        int itemCount = videoList.size();
        return itemCount;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == getItemCount() - 1;
    }

    protected ImageItem getItem(int position) {
        return  videoList.get(position);
    }

    private int getItemPosition(int position){
        return position;
    }

    private class VideoViewHolder extends RecyclerView.ViewHolder {
        protected ImageView vImage;
        protected String vFilePath, vName, vCreated;
        protected  Context context;
        protected int position;
        protected Button btnEditPic;
        public VideoViewHolder(View v) {
            super(v);
            vImage = (ImageView)  v.findViewById(R.id.media_img_bck);
            btnEditPic = (Button) v.findViewById(R.id.btnEditSelectedPhoto);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(vFilePath));
//                    intent.setDataAndType(Uri.parse(vFilePath), "image/*");
//                    context.startActivity(intent);

                    Intent i = new Intent(v.getContext(), SelectedPicturePreview.class);
                    i.putExtra("filePath", vFilePath);
                    i.putExtra("fileName", vName);
                    i.putExtra("fileCreated", vCreated);
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}
