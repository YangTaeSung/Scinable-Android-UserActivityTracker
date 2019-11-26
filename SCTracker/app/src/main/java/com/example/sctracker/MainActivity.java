package com.example.sctracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        사용자가 scq에 데이터를 집어넣을 형식
        */
        // [ url, title, id, groupId, type ] or [ id, groupId ]
        Scinable.valueArray = new String[]{"","aa","ff"};
        Scinable._scq.put("_setAccount",Scinable.valueArray);


        // 그냥 함수로 바로 쏠까 생각도 해봄.
        Scinable.setAccount(Scinable.valueArray)

    }

    // MainActivity 생성과 동시에 SharedPreferences에서 frequency 1증가해야됨.
    // (frequency가 방문 빈도를 말하는게 맞다면)

}
