package com.example.BlockToon;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.util.concurrent.ExecutionException;

public class login extends AppCompatActivity {
    private String IP_ADDRESS = MainActivity.IP_ADDRESS;
    protected static String userId;

    protected static String accountKey;
    protected static String privateKey;
    protected static String ether;
    private String link;

    AlertDialog.Builder alert;

    MainActivity mainActivity;

    String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mainActivity = (MainActivity)MainActivity.activity;




        Intent intent = getIntent();
        userId = intent.getExtras().getString("userId");  // 로그인한 아이디 수신


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new ListFragment()).commit();




        String encodeUserId = null;
        try {
            encodeUserId = URLEncoder.encode(userId,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        link = "http://" + IP_ADDRESS + "/MyPage.php?userId=" + encodeUserId;  // php 링크

        mysql temp = new mysql();
        temp.execute(link, userId);     // 아이디에 맞는 개인키 추출

        while(true){
            if(temp.tempUserId != null && temp.cert != null){    // 스레드 동작 시간 대기
                break;
            }
        }
        accountKey = temp.cert.substring(0,temp.cert.indexOf(","));
        privateKey = temp.cert.substring(temp.cert.indexOf(",")+1);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainActivity.finish();
            }
        },500);


//이더 잔액 조회
        Web3jBalance web3jBalance = new Web3jBalance();
        try {
            ether = web3jBalance.execute(accountKey).get(); // 이더
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Log.d("ether",ether);



    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_list:
                            selectedFragment = new ListFragment();
                            tag = "list";
                            break;
                        case R.id.nav_addWebtoon:
                            selectedFragment = new AddWebtoonFragment();
                            tag = "add";
                            break;
                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            tag = "setting";
                            break;
                        case R.id.nav_exit:
                            selectedFragment = null;
                            tag = "exit";
                            break;

                    }

                    if(tag.equals("exit")){
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(login.this);
                        builder.setTitle("알림");
                        builder.setMessage("어플을 종료하시겠습니까?");
                        builder.setNegativeButton("취소", null);
                        builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        });
                        builder.show();
                        return true;
                    }else  {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                        return true;
                    }


                }

            };



    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this,R.style.AlertDialogStyle);
        builder.setMessage("종료하시겠습니까?");
        builder.setNegativeButton("취소" , null);
        builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.show();
    }
}


