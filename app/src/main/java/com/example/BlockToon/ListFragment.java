package com.example.BlockToon;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kenai.jffi.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;


/*웹툰 목록
* LayoutManager와 Adapter 지정*/

public class ListFragment extends Fragment {

    View v;
    public static List<Book> lstBook;

    RecyclerView myrv;

    public static List<String> writer;
    public static List<String> title;
    public static List<String> thumbnailHashKey;
    public static List<String> writerAddress;

    public String privatekey;


    Web3jLoadWebtoonWriter web3jLoadWebtoonWriter;
    Web3jLoadWebtoonTitle web3jLoadWebtoonTitle;
    Web3jLoadWebtoonThumbnailHasy web3jLoadWebtoonThumbnailHasy;
    Web3jLoadWriterAddress web3jLoadWriterAddress;

    public ListFragment() {


    }


    public void getWriter() { // 작가 이름 리스트를 받아온다.
        try {
            web3jLoadWebtoonWriter = new Web3jLoadWebtoonWriter();
            writer = web3jLoadWebtoonWriter.execute(privatekey).get();


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getTitle() { //웹툰제목 리스트를 받아온다.
        try {

            web3jLoadWebtoonTitle = new Web3jLoadWebtoonTitle();
            title = web3jLoadWebtoonTitle.execute(privatekey).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void getThumbnail() { //웹툰 썸네일 해시값을 받아온다.
        try {

            web3jLoadWebtoonThumbnailHasy = new Web3jLoadWebtoonThumbnailHasy();
            thumbnailHashKey = web3jLoadWebtoonThumbnailHasy.execute(privatekey).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void getWriterAddress() { //작가의 계좌주소를 받아온다.
        try {
            web3jLoadWriterAddress = new Web3jLoadWriterAddress();
            writerAddress = web3jLoadWriterAddress.execute(privatekey).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_list, container, false);
        myrv = (RecyclerView) v.findViewById(R.id.list_recyclerview);
        RecyclerViewAdapter_Book myAdapter = new RecyclerViewAdapter_Book(getContext(), lstBook);
        myrv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        myrv.setAdapter(myAdapter);


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        privatekey = login.privateKey;


        getTitle();
        getThumbnail();
        getWriter();
        getWriterAddress();


        lstBook = new ArrayList<>();


        for (int i = 1; i < title.size(); i++) {
            lstBook.add(new Book(title.get(i), thumbnailHashKey.get(i), writer.get(i), writerAddress.get(i)));
        }


    }

    }








