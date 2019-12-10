package com.example.sctracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.ColorSpace;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Scinable extends AppCompatActivity {

    // _scq의 push 순서대로 작업을 처리하기 위해 LinkedHashMap 사용
    // 일반적인 HashMap의 경우에는 순서가 보장되지 못한다.
    public static String[] _scq = {};
    //public static Map<String, String[]> _scq = new LinkedHashMap<>();


    // SharedPreferences 사용하여 데이터 일시 저장
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor scinableEditor = sharedPreferences.edit();


    // "_host"는 ECIntelligence 서버
    public static String _host = "192.168.1.19";
    public static String cookie = null; // *
    public static boolean cookieEnabled = false;
    public static String accountId = "";
    public static String language = null;
    public static boolean debug = false;
    public static String domainName = null;
    public static String uid = null;
    public static String vid = null;
    public static String ck = null;
    public static String campaign = "";
    public static String channel = "";
    public static String preVisitDate = null;
    public static String frequency = null;
    public static int newVisit = 0;
    public static String offline = "";
    public static int pageView = 1;
    public static long visitTime;

    public static HashMap<String, String> customField = new HashMap<>();

    /* request와 관련된 함수 */
    // 의미 파악 후 Activity와 연관짓기
    String fullUrl;
    String headLoc;
    String scriptId;


    // <getElementsByTagsName> : 문서의 요소 콜렉션을 NodeList로 리턴
    // js파일에서는 첫번째 요소(item(0))을 가져옴
    // 앱에서는 Url X
    // 정보들을 받아와서 서버와 통신하는 작업
    public void Request(String fullUrl) {

        this.fullUrl = fullUrl;

    }


    public static String getOrderData() {

        if(debug) {

            String cz = Util.getCookie("___cz");

            if(Trans.order.get(0) == cz) {

                return "";

            } else {

                Util.setCookie("___cz",Trans.order.get(0), Config.czExpire);

            }

            String[] arr;
            int orderLength = Trans.order.size();
            int itemsLength = Trans.items.size();

            for ( int i = 0; i < Trans.items.size(); ++i) {

                String[] rowArr = {Trans.type};
                /* 이후 concat() 메소드를 실행하면 Array 속 자료형이 다름. 넘김.*/

            }

        }

        return "";

    }


    public static String getMemberData() {

        if(Scinable.debug == false) {

            if(Trans.type == "C") {

                String cz = Util.getCookie("___cz");

                if(Trans.member.get(0) == cz) {

                    return null;

                } else {

                    Util.setCookie("___cz", Trans.member.get(0), Config.czExpire);

                }

            }

        }

        // 두 배열의 요소들을 arr배열에 통합한다.
        // sc.js의 concat함수 구현
        String arr[];
        String[] rowArr = {Trans.type};
        List<String> list = new ArrayList(Arrays.asList(rowArr));
        list.addAll(Trans.member);
        arr = list.toArray(new String[list.size()]);

        // stringify()는 ["John","Peter","Sally","Jane"] 이런 형태로 출력.
        // 위와같은 형태로 출력하려면 Arrays.toString()
        String jstr = "{\"type\":\"member\", \"data\":" + Arrays.toString(arr) + "}";

        return jstr;

    }


    public static String getClaimData() {

        String[] arr = {};
        List<String> arrlist =new ArrayList(Arrays.asList(arr));

        for(int i = 0; i < Trans.claim.size(); ++i) {

            String[] rowArr = {Trans.type};
            List<String> list = new ArrayList(Arrays.asList(rowArr));
            list.addAll(Arrays.asList(Trans.claim.get(i)));
            rowArr = list.toArray(new String[list.size()]);
            arrlist.addAll(Arrays.asList(rowArr));

        }

        arr = arrlist.toArray(new String[arrlist.size()]);

        String jstr = "{\"type\":\"claim\", \"data\":" + Arrays.toString(arr) + "}";

        return jstr;

    }


    public static void _getPromotion(String[] result) {

        for ( int idx = 0; idx < result.length; idx++) {

            String promotion = result[idx];

            // 넘김.

        }

    }


    // 아래 getPreVisitDate() 함수에서 frequency와 preVisitDate값이 같은 타입이어야 하기 때문에
    // frequency변수도 String으로 바꿈.
    public static String getFreq() {

        if (Scinable.frequency != null) {

            return Scinable.frequency;

        } else {

            Util.getVid();

            if(Scinable.frequency != null) {

                return Scinable.frequency;

            } else {

                return "1";

            }

        }

    }


    public static String getPreVisitDate() {

        if(Scinable.preVisitDate != null) {

            return Scinable.preVisitDate;

        } else {

            Util.getVid();

            if(Scinable.frequency != null) {

                return Scinable.frequency;

            } else {

                return Util.today();

            }

        }

    }


    // 현재 페이지의 URL을 출력하는 함수, 액티비티 경로 및 이름을 출력한다고 가정
    // Access.url을 반환하는 것으로 보아 Access.url은 사용자의 현재 페이지
    public String getPageUrl() {

        if(Access.url != null) {

            return Access.url;

        } else {

            // 현재 액티비티 정보가 Access.url에 저장되어 있지 않을 때
            // js에서는 함수가 존재하여 그것을 이용
            return getClass().getSimpleName();

        }

    }


    // 웹에서는 url과 title 두 가지를 요구. url은 없고 title은 클래스이름 출력
    public String getPageTitle() {

        if(Access.title != null) {

            return Access.title;

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


    public static String getLang() {

        if(Scinable.language != null) {

            return Util.encodeURIComponent(Scinable.language);

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
