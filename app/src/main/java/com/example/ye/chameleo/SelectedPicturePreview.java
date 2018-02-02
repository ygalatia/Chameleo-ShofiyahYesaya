package com.example.ye.chameleo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SelectedPicturePreview extends AppCompatActivity {

    String filePath, fileName, fileCreated;
    Context context;
    ImageView imgPreview;
    Button btnBack, btnEditPhoto;
    TextView txtFileName;
    public static Glide glid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_picture_preview);

        Intent i = getIntent();
        filePath = i.getStringExtra("filePath");
        fileName = i.getStringExtra("fileName");
        fileCreated = i.getStringExtra("fileCreated");

        imgPreview = findViewById(R.id.img_selectedPhoto);
        btnBack = findViewById(R.id.btnCloseSelectedPicture);
        btnEditPhoto = findViewById(R.id.btnStartEditPhoto);
        txtFileName = findViewById(R.id.txt_filename);

        if(fileName.length()>25)
        {
            txtFileName.setText(fileName);
        }
        else
        {
            txtFileName.setText(fileName);
        }

//        File imgFile = new  File(filePath);
//
//        if(imgFile.exists()){
//
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//
//            ImageView myImage = (ImageView) findViewById(R.id.img_selectedPhoto);
//
//            myImage.setImageBitmap(myBitmap);
//
//        }

        glid.clear(imgPreview);
        glid.with(this)
                .load(filePath)
                .fitCenter()
                .placeholder(R.color.cardview_dark_background)
                .into(imgPreview);

        btnEditPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditPicture.class);
                i.putExtra("filePath", filePath);
                startActivity(i);
            }
        });

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
}
