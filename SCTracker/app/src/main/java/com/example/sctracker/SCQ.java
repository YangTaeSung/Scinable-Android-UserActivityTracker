package com.example.sctracker;


import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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


    public static void sendToServer(String param) throws IOException {

        URL url = new URL("http://" + Scinable._host + "/insert.php");

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setReadTimeout(5000); //5초안에 응답이 오지 않으면 예외가 발생합니다.
        httpURLConnection.setConnectTimeout(5000); //5초안에 연결이 안되면 예외가 발생합니다.
        httpURLConnection.setRequestMethod("POST"); //요청 방식을 POST로 합니다.
        httpURLConnection.connect();

        OutputStream outputStream = httpURLConnection.getOutputStream();
        // 전송할 데이터가 저장된 변수를 이곳에 입력합니다. 인코딩을 고려해줘야 합니다.
        outputStream.write(param.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();

    }


    // Queue사용
    // _scq.push호출 시 큐에 저장한 후에 _trackPageview가 호출될 시에 일괄적으로 tracking 시작.
    public void push(String... p) {

        functionNameAndValue.offer(p);

        if(p[0] == "_trackPageview") {

            needsDomReady = true;

            while(!functionNameAndValue.isEmpty()) {

                tracking(functionNameAndValue.poll());

            }

            needsDomReady = false;

        }

    }


    /* 비동기 구현 처음 한거, 복잡함

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

