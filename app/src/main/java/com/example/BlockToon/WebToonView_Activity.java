package com.example.BlockToon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.squareup.picasso.Picasso;


/* 웹툰 조회 */
public class WebToonView_Activity extends AppCompatActivity {

    Intent intent;
    String webHashKey;

    ImageView webtoonIMG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_toon_view);
        intent = getIntent();
        webHashKey = intent.getStringExtra("webHashKey");
        Log.d("test",webHashKey);

        /*webtoonIMG = (ImageView) findViewById(R.id.imageView4);
        webtoonIMG.setFocusable(false);*/


        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        Picasso.get().load("https://ipfs.io/ipfs/"+webHashKey).into(photoView);
        //Picasso.get().load("https://ipfs.io/ipfs/"+webHashKey).into(webtoonIMG);

        //PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(webtoonIMG);
        //photoViewAttacher.update();




    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
