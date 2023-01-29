package com.example.BlockToon;

import android.os.AsyncTask;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigInteger;
import java.util.PrimitiveIterator;

public class Web3jBalance extends AsyncTask<String,Void,String> {
    EthGetBalance ethGetBalance;
    String result;


    @Override
    protected String doInBackground(String... args) {
        try {
            Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io/v3/27fe5667c98041f7b79c924833f38de7")); // Infura로 ropsten 테스트넷에 연결
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();

            ethGetBalance = web3j.ethGetBalance(args[0], DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger wei = ethGetBalance.getBalance();

            result = Convert.fromWei(wei.toString(), Convert.Unit.ETHER).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String tempResult) {
        AddWebtoonFragment.etherBalance = tempResult;  // 로그인 계정 잔액 조회
    }
}