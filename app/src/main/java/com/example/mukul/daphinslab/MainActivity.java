package com.example.mukul.daphinslab;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.szugyi.circlemenu.view.CircleLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    int i ;

    String currentFolder = "";
    String currentFolderName = "";
     CircleImageView centerImage;

    ArrayList<Bucket> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        CircleLayout circleLayout = (CircleLayout) findViewById(R.id.circularview);
        centerImage = (CircleImageView) findViewById(R.id.centerImage);
        final TextView foldername = (TextView) findViewById(R.id.folderName);


        images = new ArrayList<>();
        images = getImageBuckets(this);
        final ArrayList <Bucket> sortedImages = new ArrayList<>();
        String prev = "hfiewhfbweuhcwamumw9efcy9ohe";
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        Collections.sort(images, new Comparator<Bucket>() {
            @Override
            public int compare(Bucket o1, Bucket o2) {


                return o1.getDirectory().compareTo(o2.getDirectory());
            }
        });
        for(Bucket b : images){

            if(!b.getDirectory().equals(prev)){
                prev = b.getDirectory();
                sortedImages.add(b);

            }


        }
        LinearLayout.LayoutParams lprams = new LinearLayout.LayoutParams(
                100,
                100);
         i = 0 ;
        for(final Bucket b:sortedImages){

            final CircleImageView imageView = new CircleImageView(this);
            imageView.setId(i);



            imageView.setLayoutParams(lprams);

            final int index = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.i("TAG", "The index is" +String.valueOf(index) + b.getDirectory());

                }
            });


            Glide.with(this).load(b.getImagepath()).asBitmap().centerCrop().into(imageView);
            circleLayout.addView(imageView);




            Log.e("Tag", b.getDirectory());
            i++;
        }
        currentFolderName= sortedImages.get(0).getFoldername();
        Glide.with(MainActivity.this).load(sortedImages.get(0).getImagepath()).asBitmap().centerCrop().into(centerImage);

        foldername.setText(currentFolderName);


        circleLayout.setOnItemSelectedListener(new CircleLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view) {

                currentFolder = sortedImages.get(view.getId()).getDirectory();
                currentFolderName = sortedImages.get(view.getId()).getFoldername();
                Glide.with(MainActivity.this).load(sortedImages.get(view.getId()).getImagepath()).asBitmap().centerCrop().into(centerImage);

                foldername.setText(currentFolderName);
                Log.e("error",String.valueOf(currentFolder));


            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentFolder.equals("")){
                    Intent i = new Intent(MainActivity.this,Folder.class);
                    i.putExtra("folderpath",currentFolder);
                    i.putExtra("foldername",currentFolderName);
                    startActivity(i);}

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
