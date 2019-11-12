package com.example.sctracker;

public class Config extends Scinable {

    String language = "ja";
    String host = _host;
    // js파일 상에선 int지만 Tracker에서 czExpire와 같은 매개변수를 공유, 타입 같아야 함
    public static long cvExpire = 1800000;
    public static long cuExpire = 63072000000L; // long은 숫자 뒤에 L을 붙여 표현
    public static long ccExpire = 63072000000L;
    public static long czExpire = 63072000000L;


}
