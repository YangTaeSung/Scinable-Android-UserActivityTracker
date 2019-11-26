package com.example.sctracker;

public class RecommendConfig {

    String host = null;
    String accessUri = "/recommend/access";
    String recommendUri = "/recommend";
    String rankUri = "/recommend/rank";
    String limit = "10";
    int paramItemCount = 2;
    String callback = "_getRecommend";
    boolean cache = true;

    public void callback(String result) {

        if(result != null) {

            callback(result);

        }

    }


}
