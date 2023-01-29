package com.example.BlockToon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/*웹툰 목록 어댑터*/

public class RecyclerViewAdapter_Book extends RecyclerView.Adapter<RecyclerViewAdapter_Book.MyViewHolder> {

    private Context mContext;
    private List<Book> mData;
    public static AlertDialog.Builder builder ;
    public static AlertDialog alert3 ;

    public RecyclerViewAdapter_Book(Context mContext, List<Book> mData) {
        this.mContext = mContext;
        this.mData = mData;

        builder = new AlertDialog.Builder(mContext,R.style.AlertDialogStyle);
        alert3 = builder.create();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_book,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.tv_book_title.setText(mData.get(i).getTitle());
        myViewHolder.tv_book_witer.setText(mData.get(i).getWriter());

        myViewHolder.img_book_thumbnail.setFocusable(false);
        Picasso.get().load("https://ipfs.io/ipfs/"+mData.get(i).getWebHashKey()).fit().into(myViewHolder.img_book_thumbnail);


        //Set Click listener
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){


                alert3.setMessage("로딩 중 입니다.");
                alert3.show();
                Intent intent = new Intent(mContext, Book_Activity.class);
                intent.putExtra("title",mData.get(i).getTitle());
                intent.putExtra("writerAdd",mData.get(i).getWriterAddress());
                //Start the Activity
                mContext.startActivity(intent);




            }
        });

    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_book_title;
        ImageView img_book_thumbnail; //웹툰 썸네일을 표시하기 위한 이미지뷰
        TextView tv_book_witer;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_book_title = (TextView) itemView.findViewById(R.id.book_title_id);
            img_book_thumbnail =(ImageView) itemView.findViewById(R.id.imageView);
            tv_book_witer = (TextView) itemView.findViewById(R.id.book_writer_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


        }
    }
}



