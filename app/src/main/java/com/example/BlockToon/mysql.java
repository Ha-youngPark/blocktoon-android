package com.example.BlockToon;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class mysql extends AsyncTask<String, Void, String> {     // mysql 동작 class    --  로그인 아이디 비밀번호 확인

    protected String tempUserId;
    protected String cert;

    @Override
    protected String doInBackground(String... arg0) {

        String result = new String();

        try {
            //String userId = arg0[0]; //  아이디
            //String password = arg0[1];  // 비밀번호
            //String link = "http://" + IP_ADDRESS + "/testSearch.php?userId=" + userId + "&password=" + password;  // php 링크
            String link = arg0[0];
            tempUserId = arg0[1];
            URL phpUrl = new URL(link);
            HttpURLConnection conn = (HttpURLConnection)phpUrl.openConnection();

            if ( conn != null ) {
                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);
                if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    while ( true ) {
                        String line = br.readLine();
                        if ( line == null )
                            break;
                        result = line;
                    }
                    br.close();
                }
                conn.disconnect();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        cert = result;
        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        cert = result;  // cert     해당 값 존재 1   없으면 null
    }
}
