package com.example.sctracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 아래와 같이 싱글톤 패턴의 객체를 생성할 때, (Tracker는 싱글톤이 아니지만 내부에서 다른 싱글톤 객체들 생성)
        // 액티비티 화면이 적상적으로 출력되지 않고 하얀 화면인 상태로 정지됨. 서버로의 데이터 전송도 이루어지지 않음.
        // 우선은 Tracker와 ServerWork를 제외한 모든 클래스들은 싱글톤으로 구현되어 있는 상황
        // Tracker tracker = new Tracker();
        // tracker._setAccount("tsy0668");
        // tracker._setLanguage("English");
        // tracker._trackPageview();

        // Practice for server work
        // 곧장 보내보기
        ServerWork serverWorkd = new ServerWork();
        try {
            serverWorkd.sendToServer("name=Kkk&country=Sky");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
