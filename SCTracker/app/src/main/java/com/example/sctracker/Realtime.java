package com.example.sctracker;

public class Realtime {

    public void callback(String result) {

        if(result != null) {

            // eval(Scinable.Realtime.Config.callback) + "(result)");
            // 원래는 eval로 구현되어 있는데 본 코드로 사용하지 않는 이유 모르겠음.
            // 그리고 RealTimeConfig에 callback함수가 없음.
            Realtime.Config.callback(result);

        }

    }

}
