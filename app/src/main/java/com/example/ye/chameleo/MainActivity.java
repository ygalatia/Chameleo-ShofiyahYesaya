package com.example.ye.chameleo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ye.chameleo.Model.Filters;
import com.example.ye.chameleo.Model.LocationSticker;
import com.example.ye.chameleo.Model.Stickers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btnGallery, btnCamera;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            insertpreloadfilter();
            insertpreloadsticker();
            insertpreloadedloc();
            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkandAskPermission();
        }

        btnGallery = findViewById(R.id.btnEdit);
        btnCamera = findViewById(R.id.btnCamera);


        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Gallery.class);
                startActivity(i);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CapturedCamera.class);
                startActivity(i);
            }
        });
    }

    private void  insertpreloadsticker()
    {
        Stickers addSticker = new Stickers(1, "Burger", "sticker_burger1", "preloaded");
        addSticker.save();

        Stickers addSticker2 = new Stickers(2, "Game Over", "sticker_gameover1", "preloaded");
        addSticker2.save();

        Stickers addSticker3 = new Stickers(3, "Heart 1", "sticker_heart1", "preloaded");
        addSticker3.save();

        Stickers addSticker4 = new Stickers(4, "Kawaii", "sticker_kawaii1", "preloaded");
        addSticker4.save();

        Stickers addSticker5 = new Stickers(5, "#LOL", "sticker_lol1", "preloaded");
        addSticker5.save();

        Stickers addSticker6 = new Stickers(6, "OMG! 1", "sticker_omg1", "preloaded");
        addSticker6.save();

        Stickers addSticker7 = new Stickers(7, "OMG! 2", "sticker_omg2", "preloaded");
        addSticker7.save();

        Stickers addSticker8 = new Stickers(8, "OMG! 3", "sticker_omg3", "preloaded");
        addSticker8.save();
    }

    private void insertpreloadfilter() {
        Filters addfilter = new Filters(1, "Normal", "normal");
        addfilter.save();

        Filters addfilter2 = new Filters(2, "Polaroid", "polaroid");
        addfilter2.save();

        Filters addfilter3 = new Filters(3, "Grayscale", "blacknwhite");
        addfilter3.save();

        Filters addfilter4 = new Filters(4, "Photocopy", "bnw");
        addfilter4.save();

        Filters addfilter5 = new Filters(5, "Sephia 1", "sephia1");
        addfilter5.save();

        Filters addfilter6 = new Filters(6, "Sephia 2", "sephia2");
        addfilter6.save();

        Filters addfilter7 = new Filters(7, "Negative", "negative");
        addfilter7.save();
    }

    private void insertpreloadedloc()
    {
        LocationSticker loc1 = new LocationSticker(1, "Park", "locpark_250", "park");
        loc1.save();

        LocationSticker loc2 = new LocationSticker(2, "Church", "locchurch_250", "church");
        loc2.save();

        LocationSticker loc3 = new LocationSticker(3, "Mosque", "locmosque_250", "mosque");
        loc3.save();
    }

    private void checkandAskPermission() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("Storage");


        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 0; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }

            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                }
                else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }
}
