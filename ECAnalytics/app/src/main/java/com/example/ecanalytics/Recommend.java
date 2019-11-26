package com.example.ecanalytics;

/*
Scinable.Recommend.Config = {
                host: null,
                accessUri: '/recommend/access',
                recommendUri: '/recommend',
                rankUri: '/recommend/rank',
                limit: '10',
                paramItemCount: 2,
                callback: '_getRecommend',
                cache: true
        }

 */

import java.util.HashMap;
import java.util.Map;

public class Recommend {
    private String index = null;
    private String view = "1";
    private String order = "2";
    Config config = new Config();

    public Recommend(){
        config.setConfig("host", null);
        config.setConfig("accessUri", "/recommend/access");
        config.setConfig("recommendUri", "/recommend");
        config.setConfig("rankUri", "/recommend/rank");
        config.setConfig("limit", "10");
        config.setConfig("paramItemCount", 2);
        config.setConfig("callback", "getRecommend");
        config.setConfig("catch", true);
    }

    protected void callback(String result){
        if(result != null){
            config.setConfig("callback", result);
        }
    }

    protected void getRecommend(String result){

    }
}
