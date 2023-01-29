package com.example.BlockToon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.security.PrivateKey;
import java.util.List;
import java.util.concurrent.ExecutionException;

/*웹툰과 에피소드 등록 */

public class AddWebtoonFragment extends Fragment {

    EditText Title;
    EditText WebThumbHashKey;
    ImageButton Complete;

    EditText WebToonTitle; //에피소드 등록의 웹툰 제목
    EditText EpisodeTitle; //에피소드 제목
    EditText EpisodeThumbHashKey; //에피소드 썸네일 해시키
    EditText WebToonHashKey; //웹툰 해시키
    EditText Cost; //가격
    ImageButton Complete2;


    private String PrivateKey = login.privateKey;
    private String UserId = login.userId; // 작가 이름
    private String AccountKey = login.accountKey ;//작가 계좌 주소


    protected static String etherBalance; // 이더 잔액



    AlertDialog.Builder alert; //빈칸 확인


    List<String> TempEpisodeList;


    public static Context context;


    View v;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alert = new AlertDialog.Builder(getActivity());
        context = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add, container, false);

        Title = (EditText) v.findViewById(R.id.webtoonTitle);
        WebThumbHashKey = (EditText) v.findViewById(R.id.WebThumbHash);
        Complete = (ImageButton) v.findViewById(R.id.Complete);

        WebToonTitle = (EditText) v.findViewById(R.id.webtoonTitle2);
        EpisodeTitle = (EditText) v.findViewById(R.id.EpisodeTitle);
        EpisodeThumbHashKey = (EditText) v.findViewById(R.id.EpisodeThumbHash);
        WebToonHashKey = (EditText) v.findViewById(R.id.WebtoonHash);
        Cost = (EditText) v.findViewById(R.id.cost);
        Complete2 = (ImageButton) v.findViewById(R.id.Complete2);




        /*웹툰 등록 부분*/
        Complete.setOnClickListener(new View.OnClickListener() {    // 웹툰 등록 완료버튼
            @Override
            public void onClick(View v) {


                if (Title.getText().toString().isEmpty() || WebThumbHashKey.getText().toString().isEmpty()) {  // 제목이나 해시키가 빈칸이면
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();     //닫기
                        }
                    });
                    alert.setMessage("웹툰 정보를 입력해주세요.");
                    alert.show();


                } else { //빈칸이 아니면
                    //딜레이주기.

                    if (Float.parseFloat(etherBalance) <= 0) {
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();     //닫기
                            }
                        });
                        alert.setMessage("이더 잔액이 부족 합니다.");
                        alert.show();

                    } else {

                        if (!ListFragment.title.contains(Title.getText().toString())) { // 입력한 웹툰의 제목과 같은 이름의 웹툰이 존재 하지 않으면 등록
                            Web3jAddWebtoon addWebtoon = new Web3jAddWebtoon();    // 블록체인에 웹툰 정보입력
                            addWebtoon.execute(PrivateKey, UserId, Title.getText().toString(), WebThumbHashKey.getText().toString(), AccountKey); //작가 id,제목,썸네일해시키,작가계좌주소


                        } else { //같은 이름의 웹툰이 존재 하면.
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();     //닫기
                                }
                            });

                            alert.setMessage("같은 이름의 웹툰이 존재합니다.");
                            alert.show();
                        }
                    }
                }


            }
        });


        /*에피소드 등록 부분*/

        Complete2.setOnClickListener(new View.OnClickListener() {    // 에피소드 등록 완료버튼

            @Override
            public void onClick(View v) { //빈칸이 있으면

                if (WebToonTitle.getText().toString().isEmpty() || EpisodeTitle.getText().toString().isEmpty() || EpisodeThumbHashKey.getText().toString().isEmpty() || WebToonHashKey.getText().toString().isEmpty() || Cost.getText().toString().isEmpty()) {
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();     //닫기
                        }
                    });
                    alert.setMessage("에피소드 정보를 입력해주세요.");
                    alert.show();


                } else { //빈칸이 없으면
                    if (Float.parseFloat(etherBalance) <= 0) {
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();     //닫기
                            }
                        });
                        alert.setMessage("이더 잔액이 부족 합니다.");
                        alert.show();

                    } else {


                        //해당 제목의 웹툰이 존재하고 그 제목을 가진 에피소드 리스트의 크기가 0이면 null 추가하고 등록 크기가 1이상이면 그냥 등록.

                        if (ListFragment.title.contains(WebToonTitle.getText().toString())) { // 해당하는 제목의 웹툰이 존재하면

                            Web3jLoadEpisodeTitle web3jLoadEpisodeTitle = new Web3jLoadEpisodeTitle(); //해당 제목의 에피소드 제목의 리스트를 만든다.
                            try {
                                TempEpisodeList = web3jLoadEpisodeTitle.execute(PrivateKey, WebToonTitle.getText().toString()).get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (TempEpisodeList.size() == 0) { //해당 웹툰 제목의 에피소드 리스트의 크기가 0이면

                                Web3jAddEpisode web3jAddEpisode = new Web3jAddEpisode();
                                Web3jAddEpisode web3jAddEpisode1 = new Web3jAddEpisode();

                                web3jAddEpisode.execute(PrivateKey, WebToonTitle.getText().toString(), "null", "null", "null", "0");
                                web3jAddEpisode1.execute(PrivateKey, WebToonTitle.getText().toString(), EpisodeTitle.getText().toString(), EpisodeThumbHashKey.getText().toString(), WebToonHashKey.getText().toString(), Cost.getText().toString());//개인키,웹툰제목,에피소드제목,에피소드썸네일 해시키, 웹툰 해시키, 가격


                            } else { //해당 웹툰 제목의 에피소드 리스트의 크기가 1이상이면
                                Web3jAddEpisode web3jAddEpisode = new Web3jAddEpisode();
                                web3jAddEpisode.execute(PrivateKey, WebToonTitle.getText().toString(), EpisodeTitle.getText().toString(), EpisodeThumbHashKey.getText().toString(), WebToonHashKey.getText().toString(), Cost.getText().toString());

                            }
                        } else { //해당하는 웹툰이 존재하지 않으면
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();     //닫기
                                }
                            });
                            alert.setMessage("해당하는 웹툰이 존재하지 않습니다.");
                            alert.show();
                        }
                    }
                }
            }
        });
        return v;
    }

}

