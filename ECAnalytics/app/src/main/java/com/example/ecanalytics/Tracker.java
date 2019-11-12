package com.example.ecanalytics;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Tracker extends Object {

    private Map<String, String> map = new HashMap<>();
    private Trans trans = new Trans();

    Tracker() {
    }

    public String getAppId() {
        return map.get("appId");
    }

    private void setAppId(String appId) {
        map.put("appId", appId);
    }

    public String getAccountId() {
        return map.get("accountId");
    }

    private void setAccountId(String accountId) {
        map.put("accountId", accountId);
    }

    public String getLanguage() {
        return map.get("language");
    }

    private void setLanguage(String language) {
        map.put("language", language);
    }

    public String getCampaignParameter(){
        return map.get("campaignParameter");
    }

    private void setCampaignParameter(String campaignParameter){
        map.put("campaignParameter", campaignParameter);
    }

    public void trackEvent() {

    }

    //trackpageView에 해당하는메소드
    public void trackView() {

    }

    //trackView를 호출전용 push
    @SuppressLint("LongLogTag")
    public void push(String trigger) {
        //사용자가 push("trackView") 명령어를 입력할 시
        if (trigger.equals("trackView")) {
            this.trackView();   //trackView메소드 실행
        } else {
            //잘못된 입력일 시 에러메시지 출력
            Log.e("Error Message: Check your command push(", trigger + ")");
        }
    }

    //cancelMember를 제외한 setLanguage등 1개의 인자를 받는 push명령어 메소드
    @SuppressLint("LongLogTag")
    public void push(String methodName, String argument) {

        switch (methodName) {
            case "setLanguage":
                this.setLanguage(argument);
                break;
            case "setAccountId":
                this.setAccountId(argument);
                break;
            case "setAppId":
                this.setAppId(argument);
                break;
            case "setCampaignParameter":
                this.setCampaignParameter(argument);
                break;
            default:
                Log.e("Error Message: Check your command push(", methodName + ", value)");
                break;

        }
    }

    //가변인자를 이용해 javaScript와 같이 동적인 배열로 받는다 사용자의 메소드 명으로 switch문을 사용해 각각의 메소드 호출
    @SuppressLint("LongLogTag")
    public void push(String methodName, String... arguments) {

        switch (methodName) {
            case "addMember":
                trans.type = 'C';
                trans.setMember(arguments);
                break;
            case "updateMember":
                trans.type = 'U';
                trans.setMember(arguments);
                break;
            case "cancelMember":
                trans.type = 'W';
                trans.setMember(arguments);
                break;
            case "addItem":
                trans.addItem(arguments);
                break;
            case "addClaim":
                trans.type = 'C';
                //trans.addClaim(arguments);
                break;
            case "addTrans":
                trans.type = 'C';

                break;
            default:
                Log.e("Error Message: Check your command push(", methodName + ", values)");
                break;
        }
    }
}
