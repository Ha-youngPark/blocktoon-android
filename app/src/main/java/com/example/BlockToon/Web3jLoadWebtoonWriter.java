package com.example.BlockToon;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

/*웹툰 작가 이름 조회*/

public class Web3jLoadWebtoonWriter extends AsyncTask<String,Void, List<String>> {
    List<String> result;

    protected List<String> doInBackground(String... args) {
        try {

            Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io/v3/27fe5667c98041f7b79c924833f38de7")); // Infura로 ropsten 테스트넷에 연결
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            Credentials credentials = Credentials.create(args[0]); //내 개인키

            BlockToon contract = BlockToon.load(com.example.BlockToon.Web3j.contractAddress,web3j,credentials, GAS_PRICE, GAS_LIMIT);

            result = contract.Webtoon_getWriter().send(); // 웹툰 작가 조회 함수 호출



        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}

