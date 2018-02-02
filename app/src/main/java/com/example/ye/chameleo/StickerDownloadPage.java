package com.example.ye.chameleo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ye.chameleo.Adapter.StickerDownloadAdapter;
import com.example.ye.chameleo.Model.StickerDownload;
import com.example.ye.chameleo.Model.Stickers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StickerDownloadPage extends AppCompatActivity {

    private RecyclerView rvStickerDownload;
    private DatabaseReference mDatabase;
    private FirebaseDatabase fDatabase;
    private List<StickerDownload> stickerDownloads;
    ProgressDialog progressDialog;
    Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_download_page);

        fDatabase = FirebaseDatabase.getInstance();
        mDatabase = fDatabase.getReference("StickerDownload");
        
        btnBack = findViewById(R.id.btnCloseDownloadPage);

        rvStickerDownload = findViewById(R.id.rv_downloadSticker);

        progressDialog = new ProgressDialog(StickerDownloadPage.this);
        progressDialog.setMessage("Loading Stickers Data");
        progressDialog.show();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    stickerDownloads = new ArrayList<>();
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                        StickerDownload value = dataSnapshot1.getValue(StickerDownload.class);
                        StickerDownload sticker = new StickerDownload();
                        sticker.setStickerFile(value.getStickerFile());
                        sticker.setStickerID(value.getStickerID());
                        sticker.setStickerName(value.getStickerName());
                        sticker.setStickerPath(value.getStickerPath());
                        stickerDownloads.add(sticker);
                    }

                    loadDownloadPage();

                    progressDialog.dismiss();
                    Log.d("FirebaseDatabase", "Data Retrieved");
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FirebaseDatabase", "Error Retrieve Data");
            }
        });
        
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDLPage();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeDLPage();
    }

    private void closeDLPage() {
        finish();
    }

    public void saveToInternalStorage(Bitmap bitmapImage, String pathName, String saveName){

        progressDialog = new ProgressDialog(StickerDownloadPage.this);
        progressDialog.setMessage("Saving Stickers Data to Your Device");
        progressDialog.show();

        Stickers chkSticker = new Stickers();

        if(chkSticker.find(Stickers.class, "sticker_name = ?", saveName).size() == 0) {

            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("ChameleoSticker", Context.MODE_PRIVATE);
            // Create imageDir
            File mypath = new File(directory, pathName + ".png");

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Stickers addNew = new Stickers(100, saveName, pathName, "downloaded");
            addNew.save();

            Toast.makeText(this, saveName+" Sticker is Downloaded", Toast.LENGTH_SHORT).show();

            progressDialog.dismiss();
        }
        else{
            progressDialog.dismiss();
            Toast.makeText(this, "Sticker Exist!", Toast.LENGTH_SHORT).show();
        }


    }

    public void loadDownloadPage()
    {

        StickerDownloadAdapter rvAdapter = new StickerDownloadAdapter(stickerDownloads, StickerDownloadPage.this);
        RecyclerView.LayoutManager mLayout = new GridLayoutManager(StickerDownloadPage.this, 2);
        rvStickerDownload.setLayoutManager(mLayout);
        rvStickerDownload.setItemAnimator(new DefaultItemAnimator());
        rvStickerDownload.setAdapter(rvAdapter);
    }



    public void saveDownloadedSticker(String stickerName, String stickerPath)
    {

    }
}
