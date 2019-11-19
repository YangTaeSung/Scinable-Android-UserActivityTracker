package com.example.sctracker;

// This config is belong to Scinable class

public class Config extends Scinable {

    String language = "ja";
    String host = _host;

    // js파일 상에선 int지만 Tracker에서 czExpire와 같은 매개변수를 공유, 타입 같아야 함
    // long은 숫자 뒤에 L을 붙여 표현
    public static long cvExpire = 1800000;
    public static long cuExpire = 63072000000L; // 30min(30 * 60 * 1000)
    public static long ccExpire = 63072000000L; // 2years(2 * 365 * 24 * 60 * 1000)
    public static long czExpire = 63072000000L;


}
