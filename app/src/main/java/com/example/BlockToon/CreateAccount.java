package com.example.BlockToon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.service.autofill.Validator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

// 필요 파일 IdCheck.php   insertData.php
public class CreateAccount extends AppCompatActivity{
    private String IP_ADDRESS = MainActivity.IP_ADDRESS;
    private String link;
    mysql temp;

    //phpdo task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        final EditText userId = (EditText) findViewById(R.id.userId);  // 아이디
        final EditText password = (EditText) findViewById(R.id.password);  // 비밀번호
        final EditText accountKey = (EditText) findViewById(R.id.accountKey);  // 개인키
        final EditText privateKey = (EditText) findViewById(R.id.privateKey);  // 개인키

        Button Check = (Button) findViewById(R.id.Check);  // 아이디 중복확인
        ImageButton Complete = (ImageButton) findViewById(R.id.Complete); // 회원가입 완료
        ImageButton Cancel = (ImageButton) findViewById(R.id.Cancel); // 취소 - 로그인 창으로 돌아가기

        Check.setOnClickListener(new View.OnClickListener() {    // 아이디 중복 확인 버튼
            AlertDialog.Builder alert = new AlertDialog.Builder(CreateAccount.this);
            public void onClick(View v) {
                if (userId.getText().toString().isEmpty()) { // 아이디 입력 확인
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();     //닫기
                        }
                    });
                    alert.setMessage("항목을 입력해주세요.");
                    alert.show();
                }else{   // 아이디 입력하였으면 mysql 연동 후 중복확인
                    String encodeUserId = null;
                    try {
                        encodeUserId = URLEncoder.encode(userId.getText().toString(),"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    link = "http://" + IP_ADDRESS + "/IdCheck.php?userId=" + encodeUserId;  // 아이디 중복확인
                    temp = new mysql();
                    temp.execute(link,userId.getText().toString());
                    //Toast.makeText(getApplicationContext(), link, Toast.LENGTH_SHORT).show();
                    // task = new CreateAccount.phpdo();
                    // task.execute(userId.getText().toString());   // mysql 동작
                    //Toast.makeText(getApplicationContext(), "중복확인 중입니다.", Toast.LENGTH_LONG).show();

                    while(true){
                        if(temp.tempUserId != null && temp.cert != null){
                            break;
                        }
                    }

                    if(temp.cert.equals("1")){   // 아이디 이미 존재 // 중복
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();     //닫기
                            }
                        });
                        alert.setMessage("이미 존재하는 아이디입니다.");
                        alert.show();
                    }else{   // 중복 없음
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();     //닫기
                            }
                        });
                        alert.setMessage("사용 가능한 아이디입니다.");
                        alert.show();
                    }
                }
            }
        });

        Complete.setOnClickListener(new View.OnClickListener() {    // 완료버튼
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CreateAccount.this);
                if (userId.getText().toString().isEmpty() || password.getText().toString().isEmpty() || accountKey.getText().toString().isEmpty() || privateKey.getText().toString().isEmpty()) {  // 정보 입력 확인
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();     //닫기
                        }
                    });
                    alert.setMessage("정보를 입력해주세요.");
                    alert.show();
                } else {  // 아이디 비밀번호 개인키 입력하였으면 해당 id 중복확인 체크 후 mysql 연동 후 삽입
                    if(temp.tempUserId.equals(userId.getText().toString()) && temp.cert.equals("")){   // 해당 id 중복확인 체크
                        //InsertData task = new InsertData();
                        String encodeUserId = null;
                        String encodePassword = null;
                        String encodeAccountKey = null;
                        String encodePrivateKey = null;
                        try {
                            encodeUserId = URLEncoder.encode(userId.getText().toString(),"UTF-8");
                            encodePassword = URLEncoder.encode(password.getText().toString(),"UTF-8");
                            encodeAccountKey = URLEncoder.encode(accountKey.getText().toString(),"UTF-8");
                            encodePrivateKey = URLEncoder.encode(privateKey.getText().toString(),"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        link = "http://" + IP_ADDRESS + "/insertData.php?userId=" + encodeUserId + "&password=" + encodePassword + "&accountKey=" + encodeAccountKey + "&privateKey=" + encodePrivateKey;  // php 링크
                        temp = new mysql();
                        temp.execute(link, userId.getText().toString());
                        //Toast.makeText(getApplicationContext(), link, Toast.LENGTH_SHORT).show();

                        //task.execute("http://" + IP_ADDRESS + "/insertTest.php", userId.getText().toString(), password.getText().toString(), privateKey.getText().toString());
                        //        Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                        finish();
                    }else{    // 중복확인 미완료
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();     //닫기
                            }
                        });
                        alert.setMessage("아이디 중복확인을 해주세요.");
                        alert.show();
                    }
                }
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {    // 취소 - 로그인 창으로 돌아가기
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
