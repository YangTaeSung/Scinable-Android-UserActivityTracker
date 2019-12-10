package com.example.sctracker2;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerWork {

    public void sendToServer(String param) throws IOException {

        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                try {

                    URL url = new URL("http://192.168.1.19/insert.php");

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setReadTimeout(5000); //5초안에 응답이 오지 않으면 예외가 발생합니다.
                    httpURLConnection.setConnectTimeout(5000); //5초안에 연결이 안되면 예외가 발생합니다.
                    httpURLConnection.setRequestMethod("POST"); //요청 방식을 POST로 합니다.
                    httpURLConnection.connect();

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    // 전송할 데이터가 저장된 변수를 이곳에 입력합니다. 인코딩을 고려해줘야 합니다.
                    outputStream.write(params[0].getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();

                    // 3. 응답을 읽습니다.
                    int responseStatusCode = httpURLConnection.getResponseCode();
                    Log.d("SendToServer", "POST response code - " + responseStatusCode);

                    InputStream inputStream;
                    if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                        // 정상적인 응답 데이터
                        inputStream = httpURLConnection.getInputStream();
                    }
                    else{
                        // 에러 발생
                        inputStream = httpURLConnection.getErrorStream();
                    }

                    // 4. StringBuilder를 사용하여 수신되는 데이터를 저장합니다.
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while((line = bufferedReader.readLine()) != null){
                        sb.append(line);
                    }

                    bufferedReader.close();

                    // 5. 저장된 데이터를 스트링으로 변환하여 리턴합니다.
                    return sb.toString();

                } catch (Exception e) {

                    Log.d("Error", "InsertData: Error ", e);

                    return new String("Error: " + e.getMessage());

                }

            }

        };

        asyncTask.execute(param);

    }


    /*
    * OkHttp의 enqueue로 비동기 get 방식 이용.

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
                }
            }

        });

    } */


}
