package com.example.BlockToon;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/*에피소드 목록*/

public class EpisodeFragment extends Fragment {

    View v;

    private RecyclerView myrecyclerview;
   public static List<Episode> lstEpisode;

    List<String> episodeTitle;
    List<String> episodeThumbnailHashKey;
    List<String> episodeCost;
    List<String> webtoonHashKey;

    public String privatekey = login.privateKey; //개인키

    String title; //선택 된 웹툰 타이틀


    TextView emptyEpisode;

    public EpisodeFragment() {
    }



    public void getEpisodeTitle(){ //에피소드의 제목 조회하는 함수
        Web3jLoadEpisodeTitle web3jLoadEpisodeTitle = new Web3jLoadEpisodeTitle();
        try {
            episodeTitle = web3jLoadEpisodeTitle.execute(privatekey,title).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void getEpisodeThumbnailHashKey(){//에피소드의 썸네일 해시키 값을 조회하는 함수
        Web3jLoadEpisodeThumbnailHashKey web3jLoadEpisodeThumbnailHashKey = new Web3jLoadEpisodeThumbnailHashKey();
        try {
            episodeThumbnailHashKey = web3jLoadEpisodeThumbnailHashKey.execute(privatekey,title).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void getEpisodeCost(){//에피소드의 가격을 조회하는 함수
        Web3jLoadEpisodeCost web3jLoadEpisodeCost = new Web3jLoadEpisodeCost();

        try {
            episodeCost = web3jLoadEpisodeCost.execute(privatekey,title).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void getWebToonHashkey(){//웹툰의 해시키를 조회하는 함수
        Web3jLoadWebtoon web3jLoadWebtoon= new Web3jLoadWebtoon();
        try {
            webtoonHashKey = web3jLoadWebtoon.execute(privatekey,title).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_episode,container,false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.episode_recyclerview);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), lstEpisode);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerViewAdapter);

        RecyclerViewAdapter_Book.alert3.dismiss();

        emptyEpisode = (TextView) v.findViewById(R.id.tv_EmptyEp);

        if(lstEpisode.size() >0) {
            emptyEpisode.setVisibility(View.GONE);
        }


        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = getArguments().getString("title");

        getEpisodeTitle();
        getEpisodeCost();
        getEpisodeThumbnailHashKey();
        getWebToonHashkey();

        lstEpisode  = new ArrayList<>();


        for(int i=1;i<episodeTitle.size(); i++){
            lstEpisode.add(new Episode(title,episodeTitle.get(i),String.valueOf(episodeCost.get(i)),episodeThumbnailHashKey.get(i),webtoonHashKey.get(i)));

        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}