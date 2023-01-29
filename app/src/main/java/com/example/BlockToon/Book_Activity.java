package com.example.BlockToon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class Book_Activity extends AppCompatActivity {

    Intent intent;
    String title;
    static String writerAdd;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_);

        intent = getIntent();
        title = intent.getExtras().getString("title");
        writerAdd = intent.getExtras().getString("writerAdd");
        Log.d("hi",writerAdd);

        bundle = new Bundle();
        bundle.putString("title",title);
        EpisodeFragment episodeFragment = new EpisodeFragment();
        episodeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , episodeFragment).commit();


    }
}
