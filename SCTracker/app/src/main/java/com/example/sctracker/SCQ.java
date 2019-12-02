package com.example.sctracker;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

// _scq 객체화, 싱글톤 패턴 사용
// 일반적인 싱글톤 패턴 사용시 다중스레드에서 2개 이상의 인스턴스가 생성되는 문제가 생길 수 있다.
// DCL(Double-Checking Locking)을 써서 동기화 되는 부분을 줄임
// 처음에만 동기화가 되고 나중에는 동기화가 되지 않음.

public class SCQ extends Tracker {

    private static volatile SCQ _scq = null;

    protected String requestUrl = "";

    /*
    LinkedHashMap 사용 : 일반 HashMap은 순서가 보장되지 않음. LinkedHashMap은 FIFO 형식을 따름.
    private HashMap<String, String[]> list = new LinkedHashMap<>();

    private String functionName;
    private String[] items = {};

    private SCQ() { }
     */

    private Queue<String[]> functionNameAndValue = new LinkedList<>();

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


    OkHttpClient client = new OkHttpClient();

    // 예외처리 해줘야 됨.
    public void run(String requesturl) {

        Request request = new Request.Builder()
                .url(requesturl)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override public void onFailure(Call call, IOException e) {

                e.printStackTrace();

            }

            @Override public void onResponse(Call call, Response response) {
                /*try (ResponseBody responseBody = response.body()) { // API Level 19 이상 가능
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    System.out.println(responseBody.string());
                }*/
            }

        });

    }


    // Queue사용
    // _scq.push호출 시 큐에 저장한 후에 _trackPageview가 호출될 시에 일괄적으로 tracking 시작.
    // Okhttp에서 자동적으로 비동기통신 지원 functionNameAndValue 필요 없을 듯.
    public void push(String... p) {

        functionNameAndValue.offer(p);

        if(p[0] == "_trackPageview") {

            needsDomReady = true;

            while(!functionNameAndValue.isEmpty()) {

                tracking(functionNameAndValue.poll());

            }

        }

    }


    /*
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
    */


}

