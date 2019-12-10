package com.example.sctracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import static com.example.sctracker.Tracker._setAccount;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // _scq(문자열, 배열) 형식 X 아예 배열로 보내는게 나을 듯.
        // [ url, title, id, groupId, type ] or [ id, groupId ]
        // Scinable.valueArray = new String[]{"","aa","ff"};
        // Scinable._scq.put("_setAccount",Scinable.valueArray);


        // 그냥 함수로 바로 보내기 X (_scq에 저장한 후에 일괄적인 작업을 위해)
        // Scinable.setAccount(Scinable.valueArray);


        // String... 형식으로 보내기
        SCQ _scq = SCQ.getInstance();
        _scq.push("_setAccount","tsy0668");
        _scq.push("_setLanguage","English");

        try {

            _scq.sendToServer("name=Ha&country=Tae"); // Practice line for server test

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
