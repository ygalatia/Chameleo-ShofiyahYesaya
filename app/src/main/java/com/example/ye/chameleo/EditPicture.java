package com.example.ye.chameleo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ye.chameleo.Adapter.FiltersAdapter;
import com.example.ye.chameleo.Adapter.LocationStickerAdapter;
import com.example.ye.chameleo.Adapter.StickersAdapter;
import com.example.ye.chameleo.Model.Filters;
import com.example.ye.chameleo.Model.LocationSticker;
import com.example.ye.chameleo.Model.Stickers;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DeleteIconEvent;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.FlipHorizontallyEvent;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;
import com.xiaopo.flying.sticker.ZoomIconEvent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EditPicture extends AppCompatActivity {

    ImageView imgEditPic;
    String filePath;
    public static Glide glid;
    private List<Filters> filtersList = new ArrayList<>();
    private FiltersAdapter filtersAdapter;
    RecyclerView recyclerPanel;
    private Bitmap bmp;
    private StickerView stickerView;
    private Button btnFilter, btnSticker, btnLocation, btnSavePhoto, btnDownload, btnBack;
    private StickersAdapter stickersAdapter;
    private List<Stickers> stickersList = new ArrayList<>();
    private List<LocationSticker> locationStickerList = new ArrayList<>();
    private LocationStickerAdapter locationStickerAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_picture);
        imgEditPic = findViewById(R.id.img_edited);

        btnFilter = findViewById(R.id.btn_filters);
        btnSticker = findViewById(R.id.btn_stickers);
        btnLocation = findViewById(R.id.btn_location);
        btnSavePhoto = findViewById(R.id.btnSavePhoto);
        btnBack = findViewById(R.id.btnCloseEditPage);

        Intent i = getIntent();
        filePath = i.getStringExtra("filePath");

        stickerView = (StickerView) findViewById(R.id.stickerView);
        filtersList = Filters.listAll(Filters.class);

        definingStickerMethod();

        recyclerPanel =  findViewById(R.id.rv_listFilters);
        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerPanel.setLayoutManager(rvLayoutManager);
        recyclerPanel.setItemAnimator(new DefaultItemAnimator());

        rvFilters();

        btnSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvStickers();
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 rvFilters();
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvLocation();
            }
        });

        btnSavePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhoto();
            }
        });

        btnDownload = findViewById(R.id.btnDownloadPage);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StickerDownloadPage.class);
                startActivity(i);

            }
        });

        loadpic();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void definingStickerMethod() {
        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_close_white_18dp),
                BitmapStickerIcon.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent());

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_scale_white_18dp),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_flip_white_18dp),
                BitmapStickerIcon.RIGHT_TOP);
        flipIcon.setIconEvent(new FlipHorizontallyEvent());

        BitmapStickerIcon heartIcon =
                new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.sticker_ic_close_white_18dp),
                        BitmapStickerIcon.LEFT_BOTTOM);
        //heartIcon.setIconEvent(new HelloIconEvent());

        stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon));
        stickerView.setBackgroundColor(Color.WHITE);
        stickerView.setLocked(false);
        stickerView.setConstrained(true);

        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {
                Log.d("Sticker", "onStickerAdded");
            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                //stickerView.removeAllSticker();
                if (sticker instanceof TextSticker) {
                    ((TextSticker) sticker).setTextColor(Color.RED);
                    stickerView.replace(sticker);
                    stickerView.invalidate();
                }
                Log.d("Sticker", "onStickerClicked");
            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {
                Log.d("Sticker", "onStickerDeleted");
            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {
                Log.d("Sticker", "onStickerDragFinished");
            }

//            @Override
//            public void onStickerTouchedDown(@NonNull Sticker sticker) {
//                Log.d("Sticker", "onStickerTouchedDown");
//            }


            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {
                Log.d("Sticker", "onStickerZoomFinished");
            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {
                Log.d("Sticker", "onStickerFlipped");
            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {
                Log.d("Sticker", "onDoubleTapped: double tap will be with two click");
            }
        });
    }

    public void addDownloadedSticker(Bitmap bitmap)
    {
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        stickerView.addSticker(new DrawableSticker(bd));
    }

    private void savePhoto() {
       checkFolderExist();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File file = new File(Environment.getExternalStorageDirectory(), "/ChameleoPics" + "/chm_" + timeStamp + ".png");
        stickerView.save(file);
        Toast.makeText(EditPicture.this, "saved in " + file.getAbsolutePath(),
                Toast.LENGTH_SHORT).show();
    }

    public void checkFolderExist(){
        String path = String.valueOf(Environment.getExternalStorageDirectory()) + "/ChameleoPics";
        try {
            File ruta_sd = new File(path);
            File folder = new File(ruta_sd.getAbsolutePath());
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }
            if (success) {
                //Toast.makeText(EditPicture.this, "Folder Created", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Log.e("ERROR", "Error On Creating Folder");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0)
        {
            if(resultCode == RESULT_OK)
            {
                Toast.makeText(this, "Ready To Place Ur Sticker", Toast.LENGTH_SHORT).show();
                String locName = data.getStringExtra("LocName");
                String locType = data.getStringExtra("LocType");
                placeLocStickers(locName, locType);

            }
        }
    }

    private void placeLocStickers(String locName, String locType) {
        Log.d("Sticker", "Placing Your Sticker");
        String path = "";
        if(locType.equals("park"))
        {
            path = "drawable/locpark_250";
            int icon = getApplicationContext().getResources().getIdentifier(path, "drawable", getApplicationContext().getPackageName());

            Drawable sticker = ContextCompat.getDrawable(this, icon);
            stickerView.addSticker(
                    new TextSticker(getApplicationContext())
                            .setDrawable(sticker)
                            .setText(locName+"\n")
                            .setMaxTextSize(15)
                            .setTextColor(Color.WHITE)
                            .resizeText()
                    ,Sticker.Position.TOP);
        }else if(locType.equals("church"))
        {
            path = "drawable/locchurch_250";
            int icon = getApplicationContext().getResources().getIdentifier(path, "drawable", getApplicationContext().getPackageName());

            Drawable sticker = ContextCompat.getDrawable(this, icon);
            stickerView.addSticker(
                    new TextSticker(getApplicationContext())
                            .setDrawable(sticker)
                            .setText(locName+"\n")
                            .setMaxTextSize(15)
                            .setTextColor(Color.WHITE)
                            .resizeText()
                    ,Sticker.Position.TOP);
        }else if(locType.equals("mosque"))
        {
            path = "drawable/locmosque_250";
            int icon = getApplicationContext().getResources().getIdentifier(path, "drawable", getApplicationContext().getPackageName());

            Drawable sticker = ContextCompat.getDrawable(this, icon);
            stickerView.addSticker(
                    new TextSticker(getApplicationContext())
                            .setDrawable(sticker)
                            .setText(locName+"\n")
                            .setMaxTextSize(15)
                            .setTextColor(Color.WHITE)
                            .resizeText()
                    ,Sticker.Position.TOP);
        }
    }

    private void rvLocation() {
        locationStickerList = LocationSticker.listAll(LocationSticker.class);
        locationStickerAdapter = new LocationStickerAdapter(EditPicture.this, locationStickerList);
        recyclerPanel.setAdapter(locationStickerAdapter);
        locationStickerAdapter.notifyDataSetChanged();
    }

    private void rvStickers() {
        stickersList = Stickers.listAll(Stickers.class);
        stickersAdapter = new StickersAdapter(EditPicture.this, stickersList);
        recyclerPanel.setAdapter(stickersAdapter);
        stickersAdapter.notifyDataSetChanged();
    }

    private void rvFilters() {
        filtersAdapter = new FiltersAdapter(EditPicture.this, filtersList);
        recyclerPanel.setAdapter(filtersAdapter);
        filtersAdapter.notifyDataSetChanged();
    }

    private void loadpic() {
        glid.clear(imgEditPic);
        glid.with(this)
                .load(filePath)
                .fitCenter()
                .placeholder(R.color.cardview_dark_background)
                .into(imgEditPic);
        // = imgEditPic.
    }

    public void addSticker(String stickerPath)
    {
        int icon = getApplicationContext().getResources().getIdentifier(stickerPath, "drawable", getApplicationContext().getPackageName());
        Drawable sticker = ContextCompat.getDrawable(this, icon);
        stickerView.addSticker(new DrawableSticker(sticker));
    }

    public void editpic(int pos)
    {
        switch (pos){
            case 1:
                normal_effect();
                break;
            case 2:
               polaroid_effect();
                break;
            case 3:
                grayscale_effect();;
                break;
            case 4:
                photocopy_effect();
                break;
            case 5:
                sephia_effect();
                break;
            case 6:
                sephia2_effect();
                break;
            case 7:
                negative_effect();;
                break;
            default:
                break;
        }


    }

    private void sephia2_effect() {
        final float[] Sephia = {
                1,      0,      0,      0,      0, // red
                0,      0.8f,   0,      0,      0, // green
                0,      0,      0.5f,   0,      0, // blue
                0,      0,      0,      0.5f,   0,  // alpha
                0,      0,      0,      0,      1
        };

        imgEditPic.setColorFilter(new ColorMatrixColorFilter(Sephia));
    }

    private void photocopy_effect() {
        final float[] Photocopy = {
                1.5f,      1.5f,      1.5f,      0,      0, // red
                1.5f,      1.5f,      1.5f,      0,      0,
                1.5f,      1.5f,      1.5f,      0,      0,
                0,      0,      0,       1,     0,
                -1f,      -1f,      -1f,      0,      1f,
        };

        imgEditPic.setColorFilter(new ColorMatrixColorFilter(Photocopy));
    }

    private void polaroid_effect() {
        final float[] Polaroid = {
                1.438f, -0.062f, -0.062f, 0,0, // red
                0.122f, 1.378f, -0.122f, 0, 0, // green
                0.016f, -0.016f, 1.483f, 0, 0, // blue
                0,     0,     0, 1f,   0,  // alpha
                0.03f, 0.05f, -0.02f, 0, 1f
        };

        imgEditPic.setColorFilter(new ColorMatrixColorFilter(Polaroid));
    }

    private void sephia_effect() {

        ColorMatrix colorMatrix_Sepia = new ColorMatrix();
        colorMatrix_Sepia.setSaturation(0);

        ColorMatrix colorScale = new ColorMatrix();
        colorScale.setScale(1, 1, 0.8f, 1);

        colorMatrix_Sepia.postConcat(colorScale);

        ColorFilter ColorFilter_Sepia = new ColorMatrixColorFilter(
                colorMatrix_Sepia);

        imgEditPic.setColorFilter(ColorFilter_Sepia);
    }

    private void normal_effect() {
        imgEditPic.clearColorFilter();
        //imgEditPic.setColorFilter();
    }

    private void negative_effect() {
        final float[] NEGATIVE = {
                -1.0f,     0,     0,    0, 255, // red
                0, -1.0f,     0,    0, 255, // green
                0,     0, -1.0f,    0, 255, // blue
                0,     0,     0, 1.0f,   0  // alpha
        };

        imgEditPic.setColorFilter(new ColorMatrixColorFilter(NEGATIVE));
    }

    private void grayscale_effect() {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        imgEditPic.setColorFilter(filter);
        Log.d("Grayscale", "Grayscale Selected");
    }
}
