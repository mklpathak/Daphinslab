package com.example.mukul.daphinslab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ImageShow extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_show);
        ImageView imageView = (ImageView) findViewById(R.id.imageShow);
        imageView.setTransitionName(getIntent().getStringExtra("shared"));
        Glide.with(this).load(getIntent().getStringExtra("path")).asBitmap().centerCrop().into(imageView);






    }


}
