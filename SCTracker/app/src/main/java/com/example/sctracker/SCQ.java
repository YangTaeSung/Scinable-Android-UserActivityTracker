package com.example.sctracker;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

// _scq 객체화, 싱글톤 패턴 사용
// 일반적인 싱글톤 패턴 사용시 다중스레드에서 2개 이상의 인스턴스가 생성되는 문제가 생길 수 있다.
// DCL(Double-Checking Locking)을 써서 동기화 되는 부분을 줄임
// 처음에만 동기화가 되고 나중에는 동기화가 되지 않음.
// LinkedHashMap 사용 : 일반 HashMap은 순서가 보장되지 않음. LinkedHashMap은 FIFO 형식을 따름.
public class SCQ extends Tracker {

    private static volatile SCQ _scq = null;

    private HashMap<String, String[]> list = new LinkedHashMap<>();

    private String functionName;
    private String[] items = {};

    private SCQ() { }

    public static SCQ getInstance() {

        if(_scq==null) {

            synchronized (SCQ.class) {

                if(_scq==null) {

                    _scq = new SCQ();

                }

            }

        }

        return _scq;

    }


    public void push(String functionName, String... arguments) {

        this.functionName = functionName;

        if(arguments[0] != null) {

            list.put(functionName, arguments);

        }

        if(functionName == "_trackPageView") {

            Tracker.needsDomReady = true; // 서버 전송 시작 알림 플래그

        }

        if(needsDomReady == true) {

            functionTrack();

        }

    }

    private void functionTrack() {

        Set set = list.keySet();
        Iterator iterator = set.iterator();

        while(iterator.hasNext()) {

            String key = (String)iterator.next();
            String[] value = list.get(key);

            tracking(key, value);

        }

        _trackPageview();

        needsDomReady = false;

    }

}

