package com.example.sctracker;

public class Tracker {

    GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);


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


        // 여기서부터 다시
    }
}
