package com.example.sctracker;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Locale;

public class Scinable {

    // "_host"는 ECIntelligence or 서버
    public static String _host = "localhost:8080";

    public static String cookie = null; // *
    public static boolean cookieEnabled = false;
    // SharedPreferences로 아이디 정보 저장한 후 cookieEnabled가 필요할때마다
    // 해당하는 아이디값을 검색해서 확인하는 메소드를 만들어서 true,false에 변화를 주던지 아예 없애던지
    private static String scinableObject = "_scq"; //
    private static ArrayList<String> _scq = new ArrayList<>(); //

    public static String accountId = "";
    public static String language = null;
    public static boolean debug = false;
    public static String domainName = null;
    public static String uid = null;
    public static String vid = null;
    private static String ck = null;
    private static String campaign = "";
    private static String channel = "";
    private static String preVisitDate = null;
    private static String frequency = null;
    private static int newVisit = 0;
    public static String offline = "";
    private static int pageView = 1;


    // customField는 객체로 정의해 놓음
    // private static ArrayList<String> customField = new ArrayList<>();

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


    public String getOrderdata() {

        if(debug) {

            String cz = Util.getCookie("___cz");

            if(Trans.order[0] == cz) {

                return "";

            } else {

                Util.setCookie("___cz",Trans.order[0], Config.czExpire)

            }

            String[] arr;
            int orderLength = Trans.order.length;
            int itemsLength = Trans.items.length;

            for ( int i = 0; i < Trans.items.length; ++i) {

                String[] rowArr = {Trans.type};
                /* 이후 concat() 메소드를 실행하면 Array 속 자료형이 다름. 넘김.*/

            }

        }

        return "";

    }


    public static void _getPromotion(String[] result) {

        for ( int idx = 0; idx < result.length; idx++) {

            String promotion = result[idx];

            // 넘김.

        }

    }


    // 아래 getPreVisitDate() 함수에서 frequency와 preVisitDate값이 같은 타입이어야 하기 때문에
    // frequency변수도 String으로 바꿈.
    public String getFreq() {

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


    public String getPreVisitDate() {

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
            return getClass().getName();

        }

    }


    // 웹에서는 url과 title 두 가지를 요구. url은 액티비티 이름으로 대체했으나
    // title은 미정.
    public String getPageTitle() {

        if(Access.title != null) {

            return Access.title;

        } else {
            return "";
        }

    }


    // 링크를 통해 현재 페이지로 이동 시킨, 전 페이지의 URI 정보를 반환.
    // 구현할 필요 없을 듯. 일단 넘김.
    public void getReferrer() {

        String referrer = "";

    }


    public String getLang() {

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
            Locale.getDefault().getDisplayLanguage();

        }

    }


 }
