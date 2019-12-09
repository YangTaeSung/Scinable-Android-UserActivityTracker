package com.example.sctracer2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Tracker tracker = new Tracker();

        tracker._setAccount("tsy0668");
        tracker._setLanguage("English");
        tracker._trackPageview();

    }

}
