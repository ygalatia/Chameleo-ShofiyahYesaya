package com.example.ye.chameleo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.ye.chameleo.Adapter.GalleryAdapter;
import com.example.ye.chameleo.Adapter.MediaQuery;
import com.example.ye.chameleo.Model.ImageItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gallery extends AppCompatActivity {

    private MediaQuery mediaQuery;
    private List<ImageItem> imageItemList;
    private RecyclerView recyclerView;
    private GalleryAdapter imageAdapter;

    Button btnBack, btnEditPic;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        btnBack = findViewById(R.id.btnBacktoHome);
        btnEditPic = findViewById(R.id.btnEditSelectedPhoto);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setTitle("");
        myToolbar.setSubtitle("");

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.darkOrange));

        progressDialog = new ProgressDialog(Gallery.this);
        progressDialog.setMessage("Loading Stickers Data");
        progressDialog.show();

        initMedia();

        progressDialog.dismiss();

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

    private void initMedia(){
        recyclerView= (RecyclerView) findViewById(R.id.rv_gallery);
        imageItemList=new ArrayList<ImageItem>();
        mediaQuery=new MediaQuery(this);
        imageItemList=mediaQuery.getAllImage();
//        Log.d("ImageList","Count:"+imageItemList.size());
//        //Comparator<Date> cmp = Collections.reverseOrder();
//        Collections.sort(imageItemList);
        imageAdapter=new GalleryAdapter(imageItemList,this);
        GridLayoutManager glm = new GridLayoutManager(this, 2);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                switch (imageAdapter.getItemViewType(position)) {
                    case 0:
                        return 2;
                    case 1:
                        return 1;
                    case 2:
                        return 2;
                    default:
                        return 1;
                }

            }
        });
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(imageAdapter);

    }


}
