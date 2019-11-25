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

import static com.example.sctracker.Scinable.visitTime;
import static com.example.sctracker.Util.encodeURIComponent;

public class Tracker extends Activity {

    public void _setDebug(boolean p) {

        Scinable.debug = p;

    }


    public void _setAccount(String p) {

        Scinable.accountId = p;

    }


    public void _setDomainName(String p) {

        if(p.split(".").length-1 == 1) {

            Scinable.domainName = "." + p;

        }
        else {

            Scinable.domainName = p;

        }

    }


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

}
