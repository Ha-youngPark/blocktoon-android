package com.example.BlockToon;
//에피소드 어댑터

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

//에피소드 용 어댑터


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    public static Context mContext;
    public  static List<Episode> mData;

    Dialog myDialog;

    public String privatekey = login.privateKey;

    public static String tempHash;




    public RecyclerViewAdapter(Context mContext, List<Episode> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }





    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.episode_item,viewGroup,false);
        final MyViewHolder vHolder = new MyViewHolder(v);


        //Dialog ini

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_episode);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        vHolder.item_episode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //에피소드 클릭시 다이알로그 띄우는 onclick

                TextView dialog_title_tv = (TextView) myDialog.findViewById(R.id.dialog_title_id);
                final TextView dialog_episodetitle_tv = (TextView) myDialog.findViewById(R.id.dialog_episodetitle_id);
                ImageView dialog_img = (ImageView) myDialog.findViewById(R.id.imageView3);
                Button dialog_btn = (Button) myDialog.findViewById(R.id.dialog_btn_id);


                dialog_title_tv.setText(mData.get(vHolder.getAdapterPosition()).getTitle());
                dialog_episodetitle_tv.setText(mData.get(vHolder.getAdapterPosition()).getEpisode());

                tempHash = mData.get(vHolder.getAdapterPosition()).getWebToonHashKey();

                dialog_img.setFocusable(false);
                Picasso.get().load("https://ipfs.io/ipfs/"+mData.get(vHolder.getAdapterPosition()).getEpisodeThumbHashKey()).fit().into(dialog_img);

                dialog_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { // 다이알로그창에서 결제 버튼 클릭시 웹툰 화면 띄우는 onclick
                        if(Float.parseFloat(login.ether ) > Float.parseFloat(mData.get(vHolder.getAdapterPosition()).getCost()) ){
                            Web3jTransaction web3jTransaction = new Web3jTransaction(); //이더 결제
                            web3jTransaction.execute(privatekey,Book_Activity.writerAdd,mData.get(vHolder.getAdapterPosition()).getCost());
                            //web3jTransaction.cancel(true); // AsyncTack 종료

                        }else{
                            AlertDialog.Builder alert = new AlertDialog.Builder(mContext,R.style.AlertDialogStyle);
                            alert.setPositiveButton("확인",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();     //닫기
                                }
                            });
                            alert.setMessage("이더잔액이 부족합니다.");
                            alert.setCancelable(false);
                            alert.show();

                        }


                    }
                });
                myDialog.show();



            }
        });



        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

            myViewHolder.tv_title.setText(mData.get(i).getTitle());
            myViewHolder.tv_episode.setText(mData.get(i).getEpisode());
            myViewHolder.tv_cost.setText(mData.get(i).getCost()+" 이더");

            myViewHolder.iv_episodethumbnail.setFocusable(false);
            Picasso.get().load("https://ipfs.io/ipfs/"+ mData.get(i).getEpisodeThumbHashKey()).fit().into(myViewHolder.iv_episodethumbnail);




    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        private LinearLayout item_episode;
        private TextView tv_title;
        private TextView tv_episode;
        private TextView tv_cost;
        private ImageView iv_episodethumbnail; //에피소드 썸네일 이미지뷰




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_episode = (LinearLayout) itemView.findViewById(R.id.episode_item) ;
            tv_title = (TextView) itemView.findViewById(R.id.name_webtoon);
            tv_episode =(TextView) itemView.findViewById(R.id.name_episode);
            tv_cost = (TextView) itemView.findViewById(R.id.cost_episode);
            iv_episodethumbnail = (ImageView) itemView.findViewById(R.id.imageView2);

        }
    }
}
