package com.example.sctracker;

public class Config extends Scinable {

    String language = "ja";
    String host = _host; // "_host" is global variable but i can't find that
    int cvExpire = 1800000;
    long cuExpire = 63072000000; // Don't know why this error occur
    long ccExpire = 63072000000;
    long czExpire = 63072000000;


}
