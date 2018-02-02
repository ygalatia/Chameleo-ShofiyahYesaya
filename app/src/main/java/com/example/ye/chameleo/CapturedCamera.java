package com.example.ye.chameleo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CapturedCamera extends AppCompatActivity {

    ImageView imgCaptured;
    private Uri imageUri = null;
    private String imageurl = null;
    Button btnEdit;
    Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captured_camera);

        checkFolderExist();

        imgCaptured = findViewById(R.id.img_capturedPhoto);
        open();

        btnEdit = findViewById(R.id.btnEditCapturedPhoto);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditPicture.class);
                i.putExtra("filePath", imageurl);
                startActivity(i);
            }
        });
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
                Toast.makeText(CapturedCamera.this, "Folder Created", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Log.e("ERROR", "Error On Creating Folder");
        }
    }

    public void open(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File file = new File(Environment.getExternalStorageDirectory(), "/ChameleoPics" + "/chm_" + timeStamp + ".png");
        imageUri = Uri.fromFile(file);
        imageurl = String.valueOf(imageUri);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//// TODO Auto-generated method stub
//        super.onActivityResult(requestCode, resultCode, data);
//        Bitmap bp = (Bitmap) data.getExtras().get("data");
//        imgCaptured.setImageBitmap(bp);
        Bitmap thumbnail = null;
        //try {
            //thumbnail = MediaStore.Images.Media.getBitmap(
                    //getContentResolver(), imageUri);
            Glide.with(this).load(imageUri).fitCenter().into(imgCaptured);
            //imgCaptured.setImageBitmap(thumbnail);
            //imageurl = getRealPathFromURI(imageUri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

//    public String getRealPathFromURI(Uri contentUri) {
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }

}
