package com.example.BlockToon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

/* 이더 결제 */
public class Web3jTransaction extends AsyncTask<String,Void,Void> {

    ProgressDialog asyncDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        asyncDialog = new ProgressDialog(RecyclerViewAdapter.mContext,R.style.progressDialog);
        asyncDialog.setMessage("결제 중 입니다.");
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setCancelable(false);
        asyncDialog.show();

    }

    @Override
    protected Void doInBackground(String... args) {
        try {
            Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io/v3/27fe5667c98041f7b79c924833f38de7")); // Infura로 ropsten 테스트넷에 연결
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            Credentials credentials = Credentials.create(args[0]); //내 개인키
            TransactionReceipt receipt = Transfer.sendFunds(web3j,credentials,args[1], new BigDecimal(args[2]), Convert.Unit.ETHER).sendAsync().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        asyncDialog.dismiss();

        Intent intent = new Intent (RecyclerViewAdapter.mContext, WebToonView_Activity.class); // 웹툰 조회 액티비티로 이동
        intent.putExtra("webHashKey",RecyclerViewAdapter.tempHash);
        RecyclerViewAdapter.mContext.startActivity(intent);

    }
};