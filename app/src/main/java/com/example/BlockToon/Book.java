package com.example.BlockToon;

import android.webkit.WebView;

public class Book {

    private String Title;
    private String WebHashKey;
    private String Writer;
    private String WriterAddress;

    public Book() {
    }

    public Book(String title, String webHashKey, String writer, String writerAddress) {
        Title = title;
        WebHashKey = webHashKey;
        Writer = writer;
        WriterAddress = writerAddress;
    }

    public String getTitle() {
        return Title;
    }

    public String getWriter() { return Writer;}

    public String getWebHashKey() {return WebHashKey;}

    public String getWriterAddress() {return WriterAddress;}

    public void setTitle(String title) {
        Title = title;
    }

    public void setWriter(String writer) { Writer = writer;}

    public void setWebHashKey(String webHashKey) {WebHashKey = webHashKey;}

    public void setWriterAddress(String writerAddress){ WriterAddress = writerAddress;}
}
