package com.example.sctracker;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.sctracker.Scinable.cookie;
import static com.example.sctracker.Scinable.cookieEnabled;
import static com.example.sctracker.Scinable.visitTime;
import static com.example.sctracker.Util.encodeURIComponent;

public class Tracker extends Activity {

    public void _setDebug(boolean p) {

        Scinable.debug = p;

    }


    public static void _setAccount(String p) {

        Scinable.accountId = p;

    }

    /* 도메인 사용 안함
    public void _setDomainName(String p) {

        if(p.split(".").length-1 == 1) {

            Scinable.domainName = "." + p;

        }
        else {

            Scinable.domainName = p;

        }

    }
    */


    public void _setLanguage(String p) {

        Scinable.language = p;

    }


    public void _setSessionCookieTimeout(long timeout) {

        Config.cvExpire = timeout;
        Config.czExpire = timeout;

    }


    public void _setVisitorCookieTimeout(long timeout) {
        Config.cuExpire = timeout;
        Config.ccExpire = timeout;
    }


    // access
    // 이게 불리면 SharedPreferences에 쌓여있던 데이터들을 서버에 정보 보내
    public void _trackPageview() {
        if(!Scinable.cookieEnabled) {
            return;
        }

        String vid = Util.getVid();
        String uid = Util.getUid();

        String ck = "";
        String ak = "0";
        String gk = "0";
        String cl = "";

        String cc = Util.getCookie("___cc");
        String[] carr = cc.split(".");
        if(carr.length == 4) {
            ck = carr[0];
            ak = carr[1];
            gk = carr[2];
            cl = carr[3];
        }

        String json = "";

        if(Trans.order.length > 0) {

            json = Scinable.getOrderData();

        } else if(Trans.member.length > 0) {

            json = Scinable.getMemberData();

        } else if(Trans.claim.length > 0) {

            json = Scinable.getClaimData();

        }

        int sdt = (int)((new Date().getTime() - Scinable.visitTime) / 1000);

        Scinable scinable = new Scinable();

        // App size measurement for &sr in urlarr
        int width;
        int height;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        String[] urlarr = { "protocol", Config.host, AccessConfig.uri,
                            "?vid=", vid,
                            "&uid=", uid,
                            "&ua=", "", /* userAgent속성은 브라우저가 서버로 보낸 사용자 에이전트 헤더를 반환합니다.*/
                            "&p=", scinable.getPageUrl(), /* pageUrl 안씀 */
                            "&dt=", encodeURIComponent(scinable.getPageTitle()),
                            "&cgk=", Access.cgk,
                            "&cgv=", Access.cgv,
                            "&cgc=", Util.encodeURIComponent(Access.cgc),
                            "&pid=", Util.encodeURIComponent(Access.id),
                            "&pgid=", Util.encodeURIComponent(Access.groupid),
                            "&pat=", Util.encodeURIComponent(Access.type),
                            "&sr=", width + "x" + height,
                            "&la=", Scinable.getLang(),
                            "&fr=", Scinable.getFreq(),
                            "&pv=", Scinable.getPreVisitDate(),
                            "&ref=", "",// 필요 없음/* encodeURIComponent(Scinable.getReferrer()), */
                            "&cid=", Scinable.campaign,
                            "&ck=", encodeURIComponent(ck),
                            "&ak=", ak,
                            "&gk=", gk,
                            "&cl=", encodeURIComponent(cl),
                            "&nv=", Integer.toString(Scinable.newVisit),
                            "&at=", Scinable.offline,
                            "&cc=", encodeURIComponent(cc),
                            "&aid=", Scinable.accountId,
                            "&jd=", encodeURIComponent(json),
                            "&eid=", Scinable.channel,
                            "&spv=", Integer.toString(Scinable.pageView),
                            "&sdt=", Integer.toString(sdt),
                            "&vp=",".", Util.getCookie("___cvp"),".",
                            "&up=",".", Util.getCookie("___cup"),".",
                            "&vc=",".", Util.getCookie("___cvc"),"."
        };

        Iterator<Map.Entry<String,String>> cf = Scinable.customField.entrySet().iterator();
        while(cf.hasNext()) {

            Map.Entry<String,String> entry = (Map.Entry<String,String>)cf.next();

            String cp = entry.getValue();

            if(cp != null) {

                List<String> list = new ArrayList<>(Arrays.asList(urlarr));
                list.add("$cp");
                list.add(entry.getKey());
                list.add("=");
                list.add(Util.encodeURIComponent(cp));
                urlarr = list.toArray(new String[list.size()]);

            }

        }

        String url = "";

        // join함수 대신 반복문으로 구현
        for (int i = 0; i < urlarr.length; i++) {

            url += urlarr[i] + "";

        }

        String req

    }


    public static void _setCampaignParameter(String p) {

        Param.campaign = p;

    }


    public static void _setOffline() {

        Scinable.offline = "off";

    }


    public static void _setCampaign(String p) {

        Scinable.campaign = p;

    }

    /* Url 사용 안함
    public static void _setPageUrl(String p) {

        Access.url = p;

    }
    */


    /* 페이지 타이틀을 액티비티 이름으로 대체할 것인가 고민.
    public static void _setPageTitle(String p) {

        Access.title = p;

    }
    */


    public static void _setPage(String url, String title, String id, String groupId, String type) {

        Access.url = url;
        Access.title = title;
        Access.id = id;
        Access.groupid = groupId;
        Access.type = type;

    }

    public static void _setPage(String id, String groupId) {

        Access.id = id;
        Access.groupid = groupId;

    }


    public static void _setCustomVar(String[] arguments) {

        // 배열의 요소는 네 개를 받음.
        // 쿠키로 등록하기 전 인자로 받은 배열 요소는 [ 회원ID.해당생년월일그룹.성별.등급 ] 의 형식
        if(cookieEnabled = true) {

            String con = "";

            for(int i = 0; i < arguments.length; i++) {

                switch(i) {
                    case 0:
                        con = arguments[i];
                    case 1:
                        con = con + "." + Util.getAgeGroupKey(arguments[i]);
                    default:
                        con = con + "." + arguments[i];

                }

            }

            Util.setCookie("___cc", con, Config.ccExpire);

        }

    }

    public static void _setConversion(int coonversionId, int opt_conversionValue) {


    }

}
