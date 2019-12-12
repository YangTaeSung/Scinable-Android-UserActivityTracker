package com.example.sctracker2;

import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;

// 싱글톤 패턴
public class Scinable extends AppCompatActivity {

    private static volatile Scinable scinable = null;

    private Scinable() {}

    public static Scinable getInstance() {

        if(scinable == null) {

            synchronized (Scinable.class) {

                if(scinable == null) {

                    scinable = new Scinable();

                }

            }

        }

        return scinable;

    }


    // "_host"는 ECIntelligence 서버
    private String _host = "192.168.1.19";
    private String cookie = null; // *
    private boolean cookieEnabled = false;
    private String accountId = "";
    private String language = null;
    private boolean debug = false;
    private String domainName = null;
    private String uid = null;
    private String vid = null;
    private String ck = null;
    private String campaign = "";
    private String channel = "";
    private String preVisitDate = null;
    private String frequency = null;
    private int newVisit = 0;
    private String offline = "";
    private int pageView = 1;
    private long visitTime;
    private HashMap<String, String> customField = new HashMap<>();

    ScinableConfig scinableConfig = ScinableConfig.getInstance();
    Access access = Access.getInstance();
    Trans trans = Trans.getInstance();
    Util util = new Util();


    public String get_host() {

        return _host;

    }

    public String getcookie() {

        return cookie;

    }

    public boolean getcookieEnabled() {

        return cookieEnabled;

    }

    public String getaccountId() {

        return accountId;

    }

    public String getlanguage() {

        return language;

    }

    public boolean getdebug() {

        return debug;

    }

    public String getdomainName() {

        return domainName;

    }

    public String getuid() {

        return uid;

    }

    public String getvid() {

        return vid;

    }

    public String getck() {

        return ck;

    }

    public String getcampaign() {

        return campaign;

    }

    public String getchannel() {

        return channel;

    }

    public String getpreVisitDate() {

        return preVisitDate;

    }

    public String getfrequency() {

        return frequency;

    }

    public int getnewVisit() {

        return newVisit;

    }

    public String getoffline() {

        return offline;

    }

    public int getpageView() {

        return pageView;

    }

    public long getvisitTime() {

        return visitTime;

    }

    public HashMap<String, String> getcustomField() {

        return customField;

    }



    public void set_host(String _host) {

        this._host = _host;
        scinableConfig.sethost(); // 같은 내용이기 때문에 Scinable클래스에서 둘 다 설정

    }

    public void setcookie(String cookie) {

        this.cookie = cookie;

    }

    public void setcookieEnabled(boolean cookieEnabled) {

        this.cookieEnabled = cookieEnabled;

    }

    public void setaccountId(String accountId) {

        this.accountId = accountId;

    }

    public void setlanguage(String language) {

        this.language = language;

    }

    public void setdebug(boolean debug) {

        this.debug = debug;

    }

    public void setdomainName(String domainName) {

        this.domainName = domainName;

    }

    public void setuid(String uid) {

        this.uid = uid;

    }

    public void setvid(String vid) {

        this.vid = vid;

    }

    public void setck(String ck) {

        this.ck = ck;

    }

    public void setcampaign(String campaign) {

        this.campaign = campaign;

    }

    public void setchannel(String channel) {

        this.channel = channel;

    }

    public void setpreVisitDate(String preVisitDate) {

        this.preVisitDate = preVisitDate;

    }

    public void setfrequency(String frequency) {

        this.frequency = frequency;

    }

    public void setnewVisit(int newVisit) {

        this.newVisit = newVisit;

    }

    public void setoffline(String offline) {

        this.offline = offline;

    }

    public void setpageView(int pageView) {

        this.pageView = pageView;

    }

    public void setvisitTime(long visitTime) {

        this.visitTime = visitTime;

    }

    public void setcustomField(HashMap<String, String> customField) {

        this.customField = customField;

    }


    public String getOrderData() {

        if(debug) {

            String cz = util.getCookie("___cz");

            if (trans.getorder().get(0) == cz) {

                return "";

            } else {

                util.setCookie("___cz", trans.getorder().get(0), scinableConfig.getczExpire());

            }
        }

        String[] rowArr = {trans.gettype()};
        List<String> arrayList = new ArrayList<>();
        int i = 0;

        while(i < trans.getitems().size()) {

            String order = rowArr[0] + ", " + trans.getorder().get(0);

            for (int j = 1; j < trans.getorder().size(); ++j) {

                order += ", " + trans.getorder().get(j);

            }

            order += ", " + trans.getitems().get(i);

            arrayList.add(order);

            i++;

        }

        String jstr = "{\"type\":\"order\", \"data\":" + arrayList.toString() + "}";

        trans.clearorder();
        trans.clearitems();

        return jstr;

    }




    public String getMemberData() {

        if(getdebug() == false) {

            if(trans.gettype() == "C") {

                String cz = util.getCookie("___cz");

                if(trans.getmember().get(0) == cz) {

                    return null;

                } else {

                    util.setCookie("___cz", trans.getmember().get(0), scinableConfig.getczExpire());

                }

            }

        }

        // 두 배열의 요소들을 arr배열에 통합한다.
        // sc.js의 concat함수 구현
        String[] rowArr = {trans.gettype()};
        List<String> list = new ArrayList(Arrays.asList(rowArr));
        list.addAll(trans.getmember());

        // stringify()는 ["John","Peter","Sally","Jane"] 이런 형태로 출력.
        // 위와같은 형태로 출력하려면 Arrays.toString()
        String jstr = "{\"type\":\"member\", \"data\":" + list.toString() + "}";

        trans.clearmember();
        return jstr;

    }


    public String getClaimData() {

        String[] arr = {};
        List<String> arrlist =new ArrayList(Arrays.asList(arr));
        String[] rowArr = {trans.gettype()};

        for(int i = 0; i < trans.getclaim().size(); ++i) {

            /*
            String[] rowArr = {trans.gettype()};
            List<String> list = new ArrayList(Arrays.asList(rowArr));
            list.addAll(Arrays.asList(trans.getclaim().get(i)));
            rowArr = list.toArray(new String[list.size()]);
            */
            arrlist.add(rowArr[0]);
            arrlist.add(trans.getclaim().get(i));

        }

        String jstr = "{\"type\":\"claim\", \"data\":" + arrlist.toString() + "}";

        trans.clearclaim();
        return jstr;

    }


    public void _getPromotion(String[] result) {

        for ( int idx = 0; idx < result.length; idx++) {

            String promotion = result[idx];

            // 넘김.

        }

    }


    // 아래 getPreVisitDate() 함수에서 frequency와 preVisitDate값이 같은 타입이어야 하기 때문에
    // frequency변수도 String으로 바꿈.
    public String getFreq() {

        if (getfrequency() != null) {

            return getfrequency();

        } else {

            util.getVid();

            if(getfrequency() != null) {

                return getfrequency();

            } else {

                return "1";

            }

        }

    }


    public String getPreVisitDate() {

        if(getpreVisitDate() != null) {

            return getpreVisitDate();

        } else {

            util.getVid();

            if(getfrequency() != null) {

                return getfrequency();

            } else {

                return util.today();

            }

        }

    }


    // 현재 페이지의 URL을 출력하는 함수, 액티비티 경로 및 이름을 출력한다고 가정
    // Access.url을 반환하는 것으로 보아 Access.url은 사용자의 현재 페이지
    public String getPageUrl() {

        if(access.geturl() != null) {

            return access.geturl();

        } else {

            // 현재 액티비티 정보가 Access.url에 저장되어 있지 않을 때
            // js에서는 함수가 존재하여 그것을 이용
            return getClass().getSimpleName();

        }

    }


    // 웹에서는 url과 title 두 가지를 요구. url은 없고 title은 클래스이름 출력
    public String getPageTitle() {

        if(access.gettitle() != null) {

            return access.gettitle();

        } else {
            return getClass().getName();
        }

    }


    // 링크를 통해 현재 페이지로 이동 시킨, 전 페이지의 URI 정보를 반환.
    // 구현할 필요 없을 듯. 일단 넘김.
    /* public static void getReferrer() {

        String referrer = "";
        return "";

    } */


    public String getLang() {

        if(getlanguage() != null) {

            return util.encodeURIComponent(getlanguage());

        } else {
            /*
            Locale.getDefault().getLanguage()       ---> en
            Locale.getDefault().getISO3Language()   ---> eng
            Locale.getDefault().getCountry()        ---> US
            Locale.getDefault().getISO3Country()    ---> USA
            Locale.getDefault().getDisplayCountry() ---> United States
            Locale.getDefault().getDisplayName()    ---> English (United States)
            Locale.getDefault().toString()          ---> en_US
            Locale.getDefault().getDisplayLanguage()---> English
            */
            return Locale.getDefault().getDisplayLanguage();

        }

    }

 }
