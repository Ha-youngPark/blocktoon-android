package com.example.BlockToon;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import android.widget.TextView;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

// 필요 파일 login.php
// 서버 IP에 따라 IP_ADDRESS 변경
public class MainActivity extends AppCompatActivity {
    protected static String IP_ADDRESS = "172.20.10.4:8080";//"175.215.111.37:80";  // "121.146.118.68" // php - mysql 연동,  서버 ip 주소
    private String link;


    public static Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView userId = (TextView) findViewById(R.id.userId);   // 아이디
        final TextView password = (TextView) findViewById(R.id.password);  // 비밀번호
        Button login = (Button) findViewById(R.id.login);    // 로그인 버튼
        Button newAccount = (Button) findViewById(R.id.newAccount); // 회원가입 버튼

        activity = MainActivity.this;


        login.setOnClickListener(new View.OnClickListener() {    //  버튼 클릭시 mysql에서 아이디 비밀번호 확인
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);
            AlertDialog.Builder alert2 = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);

            public void onClick(View v) {

                if (userId.getText().toString().isEmpty() || password.getText().toString().isEmpty()) { // 아이디 비밀번호 입력 확인
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();     //닫기
                        }
                    });
                    alert.setMessage("항목을 입력해주세요.");
                    alert.setCancelable(false);
                    alert.show();
                } else {   // 아이디 비밀번호 입력하였으면 mysql 연동 후 조회
                    String encodeUserId = null;
                    String encodePassword = null;

                    try {
                        encodeUserId = URLEncoder.encode(userId.getText().toString(), "UTF-8");
                        encodePassword = URLEncoder.encode(password.getText().toString(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } //  인코딩
                    link = "http://" + IP_ADDRESS + "/login.php?userId=" + encodeUserId + "&password=" + encodePassword;  // php 링크

                    mysql temp = new mysql();
                    temp.execute(link, userId.getText().toString());

                    while (true) {

                        if (temp.tempUserId != null && temp.cert != null) {    // 스레드 동작 시간 대기
                            break;
                        }
                    }


                    if (temp.cert.equals("1")) {   // 아이디 비밀번호 맞음

                        alert2.setMessage("로딩 중 입니다.");
                        alert2.setCancelable(false);
                        alert2.show();

                        Intent intent = new Intent(getApplicationContext(), login.class);  // 로그인 이후 창 띄우기
                        intent.putExtra("userId", temp.tempUserId);    // userId 값 송신
                        startActivity(intent);


                    } else {   // 아이디 비밀번호 틀림
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();     //닫기
                            }
                        });
                        alert.setMessage("사용자 정보가 맞지 않습니다.");
                        alert.setCancelable(false);
                        alert.show();
                    }
                }


            }
        });

        newAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAccount.class);  //  회원가입 창 띄우기
                startActivity(intent);
            }
        });


    }
}
