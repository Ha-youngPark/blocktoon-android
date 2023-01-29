package com.example.BlockToon;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

/*블록체인에 에피소드 등록*/
public class Web3jAddEpisode extends AsyncTask<String,Void,Void> {

    ProgressDialog asyncDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        asyncDialog = new ProgressDialog(AddWebtoonFragment.context,R.style.progressDialog);
        asyncDialog.setMessage("에피소드 등록 중 입니다.");
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setCancelable(false);
        asyncDialog.show();
    }

    protected Void doInBackground(String... args) {
        try {

            Web3j web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/27fe5667c98041f7b79c924833f38de7")); // Infura로 ropsten 테스트넷에 연결
            Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            Credentials credentials = Credentials.create(args[0]); //내 개인키

            BlockToon contract = BlockToon.load(com.example.BlockToon.Web3j.contractAddress, web3, credentials, GAS_PRICE, GAS_LIMIT);

            //BigInteger temp = new BigInteger(args[5]);


            contract.addEpisode(args[1],args[2],args[3],args[4],args[5]).send(); //웹툰제목,에피소드제목, 에피소드 썸네일 해시키,웹툰해시키,가격을 받아서 에피소드를 등록하는 함수 호출*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        asyncDialog.dismiss();
    }
}
