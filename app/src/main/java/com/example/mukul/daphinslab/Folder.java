package com.example.mukul.daphinslab;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class Folder extends AppCompatActivity {

    ArrayList<Bucket> getImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getImages = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.folderimages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);




        getSupportActionBar().setTitle(getIntent().getStringExtra("foldername"));

        String foldername = getIntent().getStringExtra("foldername");

        for(Bucket b:getImageBuckets(this)){

            if(b.getFoldername().equals(foldername)){

                getImages.add(b);
            }


        }

        for(Bucket b : getImages){

            Log.e("Images",b.getImagepath());
        }

        recyclerView.setAdapter(new FolderAdapter(this, getImages, new FolderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Bucket item, ImageView imageView) {
                File file = new File(item.getImagepath());


                Intent i = new Intent(Folder.this,ImageShow.class);
                i.putExtra("shared",file.getName());
                i.putExtra("path",item.getImagepath());

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(Folder.this,imageView,file.getName());
                startActivity(i,optionsCompat.toBundle());

            }
        }));



    }


    public static ArrayList<Bucket> getImageBuckets(Context mContext){
        ArrayList<Bucket> buckets = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String [] projection = {MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA};

        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        if(cursor != null){
            File directory;
            while (cursor.moveToNext()){
                String bucketPath = cursor.getString(cursor.getColumnIndex(projection[0]));
                String Image = cursor.getString(cursor.getColumnIndex(projection[1]));
                directory = new File(Image);


                buckets.add(new Bucket(directory.getParent(),Image,bucketPath));
            }
            cursor.close();
        }
        return buckets;
    }

}
