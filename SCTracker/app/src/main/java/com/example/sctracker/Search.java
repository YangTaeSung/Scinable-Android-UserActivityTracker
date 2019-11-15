package com.example.sctracker;

public class Search {

    public void callback(String result) {

        if(result != null) {

            // eval(Scinable.Search.Config.callback) + "(result)");
            // 원래는 eval로 구현되어 있는데 본 코드로 사용하지 않는 이유 모르겠음.
            // 그리고 SearchConfig에 callback함수가 없고 callback변수만 있음.
            SearchConfig.callback(result);

        }

    }

}
