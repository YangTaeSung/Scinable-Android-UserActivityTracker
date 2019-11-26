package com.example.ecanalytics;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

public class Tracker extends Util {

    private Trans trans;
    private Context context;
    protected String jstr = null;

    Tracker(Context context, Bundle bundle){
        super(context, bundle);
        trans = new Trans(context, bundle);
        this.context = context;
    }

    public String getAppId() {
        return getConfig("appId").toString();
    }

    private void setAppId(String appId) {
        setConfig("appId", appId);
    }

    public String getAccountId() {
        return getConfig("accountId").toString();
    }

    private void setAccountId(String accountId) {
        setConfig("accountId", accountId);
    }

    public String getLanguage() { return getConfig("language").toString(); }

    private void setLanguage(String language) {
        setConfig("language", language);
    }

    public String getActivityTitle(){ return getConfig("activity").toString(); }

    private void setActivityTitle(String activity){ setConfig("activity", activity); }

    public String getCampaignParameter(){
        return getConfig("campaignParameter").toString();
    }

    private void setCampaignParameter(String campaignParameter){
        setConfig("campaignParameter", campaignParameter);
    }

    private void setHost(String host){

        if(host.contains("http://") || host.contains("https://")){
            host = host.substring(host.indexOf("://"));
        }
        setConfig("host", host);
    }

    public String getHost(){ return getConfig("host").toString(); }

    public void trackEvent() {
        //현재 구동중인 서비스목록을 100개까지 획득해온다.
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> list = am.getRunningServices(100);

        for(ActivityManager.RunningServiceInfo info : list) {
            String className = info.service.getClassName();
            String packageName = info.service.getPackageName();
        }
    }

    //trackpageView에 해당하는메소드
    public void trackView() throws JSONException {

        String vid = getVid();
        String uid = getUid();

        String ck = "";
        String ak = "0";
        String gk = "0";
        String cl = "";
        String cc = getSpref("___cc");
        String[] carr = cc.split(",");

        if(carr.length == 4){
            ck = carr[0];
            ak = carr[1];
            gk = carr[2];
            cl = carr[3];
        }

        JSONObject json = new JSONObject();

        if(trans.order.size() > 0){
            json = trans.getOrderrData();
        }
        else if(trans.member.size() > 0){
            json = trans.getMemberData();
        }
        else if(trans.claim.size() > 0){
            json = trans.getClaimData();
        }

        Log.d("JSON data is : ", String.valueOf(json));

        Date date = new Date();
        //int sdt = ((int) date.getTime() - (Integer) getConfig("visitTime")) / 1000;

        String[] urlarr = {
                "https://",
                getHost(),
                "/access",
                "?vid=", vid,
                "?uid=", uid,
                "&dt=", encodeURIComponent(getActivityTitle()),
                "&cgk=", getAccess("cgk"),
                "&cgv=", getAccess("cgv"),
                "&cgc=", encodeURIComponent(getAccess("cgc")),
                "&la=", getLanguage(),
                "&pv=", preVisitDate,
                "&cid=", getConfig("campaign").toString(),
                "&ck=", encodeURIComponent(ck),
                "&ak=", ak,
                "&gk=", gk,
                "&cl=", encodeURIComponent(cl),
                "&cc=", encodeURIComponent(cc),
                "&aid=", getAccountId(),
                "&jd=", encodeURIComponent(String.valueOf(json)),
                "&eid=", getConfig("channel").toString(),
                "&spv=", getConfig("pageView").toString(),
                //"&sdt=", String.valueOf(sdt),
                "&vc=",".", getSpref("___cvc"),"."
        };

        String url =  join(urlarr, "");
        Log.d("url data is : ", url);

        jstr = url;
    }

    //cancelMember와 setLanguage등 1개의 인자를 받는 push명령어 메소드
    @SuppressLint("LongLogTag")
    public void push(String methodName, String argument) {

        switch (methodName) {
            case "setHost":
                this.setHost(argument);
                break;
            case "setLanguage":
                this.setLanguage(argument);
                break;
            case "setAccountId":
                this.setAccountId(argument);
                break;
            case "setActivityTitle":
                this.setActivityTitle(argument);
                break;
            case "setAppId":
                this.setAppId(argument);
                break;
            case "setCampaignParameter":
                this.setCampaignParameter(argument);
                break;
            case "cancelMember":
                trans.type = 'W';
                trans.cancelMember(argument);
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
            case "addItem":
                trans.addItem(arguments);
                break;
            case "addClaim":
                trans.type = 'C';
                trans.addClaim(arguments);
                break;
            case "addTrans":
                trans.type = 'C';
                trans.addTrans(arguments);
                break;
            case "setCustomVar":
                trans.setCustomVar(arguments);
                break;
            case "setConversion":
                trans.setConversion(arguments);
                break;
            default:
                Log.e("Error Message: Check your command push(", methodName + ", values)");
                break;
        }
    }

    //자바의 URLENCODER를 자바스크립트와 동일한 결과가 나오게 변경
    private static String encodeURIComponent(String s)
    {
        String result = null;

        try
        {
            result = URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e)
        {
            result = s;
        }

        return result;
    }
}
