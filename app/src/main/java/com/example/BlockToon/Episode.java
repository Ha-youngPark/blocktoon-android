package com.example.BlockToon;

import android.webkit.WebSettings;
import android.webkit.WebView;

import java.math.BigInteger;

public class Episode {
    private String Title;
    private String episodeTitle;
    private String Cost;
    private String EpisodeThumbHashKey; //에피소드 썸네일 해시키
    private String WebtoonHashKey; //웹툰 해시키

    public Episode(String title, String episode,String cost, String episodeThumbHashKey, String webtoonHashKey) {
        Title = title;
        this.episodeTitle = episode;
        Cost = cost;
        EpisodeThumbHashKey = episodeThumbHashKey;
        WebtoonHashKey = webtoonHashKey;
    }

    //Getter

    public String getTitle() {
        return Title;
    }

    public String getEpisode() {
        return episodeTitle;
    }

    public String getCost() { return  Cost;}

    public String getEpisodeThumbHashKey() {return  EpisodeThumbHashKey;}

    public String getWebToonHashKey() { return WebtoonHashKey;}

    public void setTitle(String title) {
        Title = title;
    }

    public void setEpisode(String episode) {
        this.episodeTitle = episode;
    }

    public void setCost(String cost) { Cost = cost;}

    public void setEpisodeThumbHashKey(String webToonHashKey) {EpisodeThumbHashKey = webToonHashKey;}

    public void setWebToonHashKey(String webToonHashKey){ WebtoonHashKey = webToonHashKey;}
}
