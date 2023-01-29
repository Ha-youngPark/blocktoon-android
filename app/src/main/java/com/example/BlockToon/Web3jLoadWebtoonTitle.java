package com.example.BlockToon;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import java.util.List;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

/*웹툰 제목 조회*/

public class Web3jLoadWebtoonTitle extends AsyncTask<String,Void,List<String>> {

    List<String> result;

    @Override
    protected List<String> doInBackground(String... args) {
        try {


            Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io/v3/27fe5667c98041f7b79c924833f38de7")); // Infura로 ropsten 테스트넷에 연결
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            Credentials credentials = Credentials.create(args[0]); //내 개인키

            BlockToon contract =  BlockToon.load(com.example.BlockToon.Web3j.contractAddress,web3j,credentials, GAS_PRICE, GAS_LIMIT); // 컨트랙트 호출
            result = contract.Webtoon_getWebtoonTitle().send(); // 웹툰 제목 조회 함수 호출



        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
