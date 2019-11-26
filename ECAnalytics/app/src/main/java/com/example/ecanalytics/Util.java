package com.example.ecanalytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Util extends Config{

    private Bundle bundle;
    private SharedPreferences spref;
    private SharedPreferences.Editor editor;
    private String paramValues;

    public Util(){
        setAccess("req", null);
        setAccess("url", null);
        setAccess("title", null);
        setAccess("id", "");
        setAccess("groupId", "");
        setAccess("type", "");
        setAccess("cgk", ""); //conversion goal key
        setAccess("cgv", ""); //conversion goal value
        setAccess("cgc", ""); //conversion goal custom
    }

    public Util(Context context, Bundle bundle){
        this();
        this.bundle = bundle;
        spref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = spref.edit();
    }

    protected void setR(String cname, String val, long expire){
        String c = getSpref(cname);

        if(c != ""){
            String[] cs = c.split("\\.");
            String[] newcs = new String[cs.length + 1];

            int i = 1;
            if(cs.length > 10){
                while(i < cs.length){
                    newcs[i - 1] = cs[i];
                    i++;
                }
            }
            newcs[i] = val;

            c = join(cs ,".");

        }
        else{
            c = val;
        }
        this.setSpref(cname, c, expire);
    }

    private String today(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(date);
    }

    private int getAge(String birthday){
        Date today = new Date();
        int resultday = (today.getYear() + 1900)*10000+today.getMonth()*100+100+today.getDate();

        return (int) Math.floor((resultday-Integer.parseInt(birthday))/10000);
    }

    protected int getAgeGroupKey(String birthday){
        int age = getAge(birthday);

        if(age > 90) return 9;

        return age / 10 < 1 ? 2 : age / 10 + 1;
    }

    //setCookie와 같은 기능
    protected void setSpref(String name, String value, long expire){
        editor.putString(name, value);
        editor.putLong(name + "_", System.currentTimeMillis() + expire);
        editor.commit();
    }

    protected String getSpref(String name){
        long expires = spref.getLong(name + "_", 0L);
        if(System.currentTimeMillis() < expires) {
            return spref.getString(name, "");
        }
        return "";
    }

    private String createUUID(){
        return String.valueOf(Math.round(2147483647 * Math.random()));
    }

    protected String setCU(String uid, String cday, String freq){

        String cu = uid + "." + cday + "." + freq;

        this.setSpref("___cu", cu, cuExpire);

        return cu;
    }

    protected String[] getCU(){
        boolean needSet = false;
        String[] cuArr = new String[3];
        String cu = this.getSpref("___cu");

        if(cu != null){
            cuArr = cu.split("\\.");
            if(cuArr.length != 3){
                needSet = true;
                cuArr = new String[3];
                cuArr[0] = this.createUUID();
                cuArr[1] = this.today();
                cuArr[2] = "1";
            }
        }
        else{
            needSet = true;
            cuArr[0] = this.createUUID();
            cuArr[1] = this.today();
            cuArr[2] = "0";
        }

        if(needSet){
            this.setCU(cuArr[0], cuArr[1], cuArr[2]);
        }

        return cuArr;
    }

    protected String getUid(){
        if(uid == null){
            String[] cuArr = getCU();
            this.uid = cuArr[0];
            this.preVisitDate = cuArr[1];
            this.frequency = cuArr[2];
        }

        return this.uid;
    }

    protected String getVid() {
        if(vid != null){
            return vid;
        }
        else{
            this.getUid();

            String cv = getSpref("___cv");
            String[] cvArr = new String[6];
            String cookieCampaign = "";
            String cookieChannel = ""; // 추후 변수명도 변경예정

            if(cv != ""){
                cvArr = cv.split("\\.");
                Log.d("cv is : ", cv);
                if(cvArr.length > 2){
                    //캠페인정보
                    cookieCampaign = cvArr[2];
                }
                if(cvArr.length > 5){
                    //채널정보
                    cookieChannel = cvArr[5] ;
                }
            }

            String sciCampaign = "";

            if(getConfig("campaign") != null){
                sciCampaign = getConfig("campaign").toString();
            }
            else{
                if(bundle != null) {
                    sciCampaign = bundle.getString("eciCampaign");
                    if (sciCampaign == null && bundle.getString("campaign") != null) {
                        sciCampaign = bundle.getString("campaign");
                    }
                }
            }

            if(sciCampaign != null){
                if(sciCampaign != cookieCampaign){
                    cv = null;
                }
                setConfig("campaign", sciCampaign);
            }
            else{
                if(cookieCampaign != null){
                    setConfig("campaign", cookieCampaign);
                }
            }

            String sciChannel = "";
            if(getConfig("channel") != null){
                sciChannel = getConfig("channel").toString();
            }
            else{
                if(bundle != null) {
                    sciChannel = bundle.getString("channel");
                }
            }

            if(sciChannel != null){
                if(sciChannel != cookieChannel){
                    cv = null;
                }
                setConfig("channel", sciChannel);
            }
            else{
                if(cookieChannel != null){
                    setConfig("channel", cookieChannel);
                }
            }
            //////////////////
            //new visit
            //////////////////

            if(cv == ""){
                this.frequency = String.valueOf(Integer.parseInt(frequency) + 1);
                this.setCU(uid, this.today(), this.frequency);

                Date d = new Date();
                //create cv
                vid = this.createUUID();
                setConfig("visitTime", d.getTime());
                setConfig("pageView", 1);
                setConfig("newVisit", 1);

                cv = vid + "." + preVisitDate + "." + sciCampaign + "." + getConfig("visitTime").toString() + ".1." + sciChannel;
            }
            else{
                vid = cvArr[0];

                if(cvArr.length < 5){
                    Date d = new Date();
                    setConfig("visitTime", d.getTime());
                    setConfig("pageView", 1);
                }
                else{
                    setConfig("visitTime", cvArr[3]);
                    setConfig("pageView", Integer.parseInt(cvArr[4]) + 1);
                }

                cv = vid + "." + preVisitDate + "." + sciCampaign + "." + getConfig("visitTime").toString() + "." + getConfig("pageView").toString() + "." + sciChannel;
            }

            setSpref("___cv", cv, cvExpire);
            return vid;
        }
    }

    public String join(String[] args, String point){

        String result = args[0];

        for(int i = 1; i < args.length; i++){
           result += (point + args[i]);

        }

        return result;
    }

}
