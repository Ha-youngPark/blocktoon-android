package com.example.BlockToon;

import android.os.AsyncTask;
import android.view.View;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import java.io.IOException;
import java.util.List;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

/*웹툰 해시키 조회*/

public class Web3jLoadWebtoon extends AsyncTask<String,Void, List<String>>{
    List<String> result;


    @Override
    protected List<String> doInBackground(String... args) {

        Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io/v3/27fe5667c98041f7b79c924833f38de7")); // Infura로 ropsten 테스트넷에 연결
        Web3ClientVersion web3ClientVersion = null;
        try {
            web3ClientVersion = web3j.web3ClientVersion().send();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        Credentials credentials = Credentials.create(args[0]); //내 개인키
        BlockToon contract =  BlockToon.load(com.example.BlockToon.Web3j.contractAddress,web3j,credentials, GAS_PRICE, GAS_LIMIT);

        try {
            result = contract.Episode_getHashKey(args[1]).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
