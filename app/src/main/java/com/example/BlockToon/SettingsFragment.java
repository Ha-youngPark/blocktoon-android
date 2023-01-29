package com.example.BlockToon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
// 필요 파일 MyPage.php
public class SettingsFragment extends Fragment {
    private String IP_ADDRESS = MainActivity.IP_ADDRESS;
    private String tempUserId = login.userId;
    private String tempPrivateKey = login.privateKey;
    private String tempAccountKey = login.accountKey;
    private String tempEther = login.ether;
    private String link;
    View v;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_settings, container, false);

        final EditText userId = (EditText) v.findViewById(R.id.userId);  // 아이디
        final EditText password = (EditText) v.findViewById(R.id.password);  // 비밀번호
        final EditText accountKey = (EditText) v.findViewById(R.id.accountKey); //계정키
        final EditText privateKey = (EditText) v.findViewById(R.id.privateKey);  // 개인키
        final TextView ether = (TextView) v.findViewById(R.id.ether); //이더 잔액
        ImageButton Complete = (ImageButton) v.findViewById(R.id.Complete); // 회원가입 완료


        userId.setText(tempUserId); // 아이디 적혀있는 상태
        userId.setEnabled(false);  // 아이디는 변경 불가

        privateKey.setText(tempPrivateKey);  // 개인키 적혀있는 상태

        accountKey.setText(tempAccountKey); // 계정키 적혀있는 상태

        ether.setText("이더 잔액 : " + tempEther);//이더 잔액
        ether.setEnabled(false);




        Complete.setOnClickListener(new View.OnClickListener() {    // 완료버튼
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                if (password.getText().toString().isEmpty() || privateKey.getText().toString().isEmpty()||accountKey.getText().toString().isEmpty()) {  // 정보 입력 확인
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();     //닫기
                        }
                    });
                    alert.setMessage("정보를 입력해주세요.");
                    alert.show();
                } else {  // 비밀번호 개인키 입력하였으면 mysql 연동 후 정보 업데이트

                    String encodeUserId = null;
                    String encodePassword = null;
                    String encodePrivateKey = null;
                    String encodeAccountKey = null;
                    try {
                        encodeUserId = URLEncoder.encode(userId.getText().toString(),"UTF-8");
                        encodePassword = URLEncoder.encode(password.getText().toString(),"UTF-8");
                        encodePrivateKey = URLEncoder.encode(privateKey.getText().toString(),"UTF-8");
                        encodeAccountKey = URLEncoder.encode(accountKey.getText().toString(),"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    link = "http://" + IP_ADDRESS + "/Update.php?userId=" + encodeUserId + "&password=" + encodePassword + "&privateKey=" + encodePrivateKey +"&accountKey=" + encodeAccountKey;  // php 링크

                    mysql temp = new mysql();
                    temp.execute(link, userId.getText().toString());  //  업데이트

                    password.setText("");    // 비밀번호 텍스트 공백
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();     //닫기
                        }
                    });
                    alert.setMessage("정보수정을 완료하였습니다.");
                    alert.show();
                }
            }
        });
        return v;
    }
}
