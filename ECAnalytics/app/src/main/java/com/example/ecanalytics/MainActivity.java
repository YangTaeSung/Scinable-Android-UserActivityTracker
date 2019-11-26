package com.example.ecanalytics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent pushIntent = getIntent();
        setContentView(R.layout.activity_main);

        Scinable sds = new Scinable(getApplicationContext(), pushIntent.getExtras());
        sds.push("setAccountId", "hiro");
        sds.push("setHost", "naver.com");
        sds.push("setLanguage", "ko");
        sds.push("addMember", "M1", "address", "남", "19941030", "99", "scinable.com", "kang", "seokmin", "1", "{1: '100', 2: '200'}");
        sds.push("setConversion", "2", "3000","{1: '100,200', 2: '500', 5: '220'}");

        sds.push("trackView");

    }

}
