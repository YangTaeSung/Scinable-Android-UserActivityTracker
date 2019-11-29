package com.example.sctracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import static com.example.sctracker.Tracker._setAccount;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // _scq(문자열, 배열) 형식 X 아예 배열로 보내는게 나을 듯.
        // [ url, title, id, groupId, type ] or [ id, groupId ]
        //Scinable.valueArray = new String[]{"","aa","ff"};
        //Scinable._scq.put("_setAccount",Scinable.valueArray);


        // 그냥 함수로 바로 쏘기 X
        //Scinable.setAccount(Scinable.valueArray);


        SCQ _scq = SCQ.getInstance();
        _scq.push("_setAccount","tsy0668");
        _scq.push("_setLanguage","English");



    }

    // MainActivity 생성과 동시에 SharedPreferences에서 frequency 1증가해야됨.
    // (frequency : 방문빈도)

}
