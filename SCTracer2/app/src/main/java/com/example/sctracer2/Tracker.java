package com.example.sctracer2;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Tracker extends Activity {

    Scinable scinable = Scinable.getInstance();
    ScinableConfig scinableConfig = ScinableConfig.getInstance();
    Trans trans = Trans.getInstance();
    Access access = Access.getInstance();
    AccessConfig accessConfig = AccessConfig.getInstance();
    Param param = Param.getInstance();
    Util util = new Util();


    public void _setAccount(String... p) {

        scinable.setaccountId(p[1]);

    }


    public void _setDebug(String... p) {

        scinable.setdebug(Boolean.valueOf(p[1]));

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


    public void _setLanguage(String... p) {

        scinable.setlanguage(p[1]);

    }


    public void _setSessionCookieTimeout(String... timeout) {

        scinableConfig.setcvExpire(Long.parseLong(timeout[1]));
        scinableConfig.setczExpire(Long.parseLong(timeout[1]));

    }


    public void _setVisitorCookieTimeout(String... timeout) {

        scinableConfig.setcuExpire(Long.parseLong(timeout[1]));
        scinableConfig.setccExpire(Long.parseLong(timeout[1]));

    }


    // access
    public void _trackPageview() {

        if(!scinable.getcookieEnabled()) {

            return;

        }

        String vid = util.getVid();
        String uid = util.getUid();

        String ck = "";
        String ak = "0";
        String gk = "0";
        String cl = "";

        String cc = util.getCookie("___cc");
        String[] carr = cc.split(".");
        if(carr.length == 4) {
            ck = carr[0];
            ak = carr[1];
            gk = carr[2];
            cl = carr[3];
        }

        String json = "";

        if(trans.getorder().size() > 0) {

            json = scinable.getOrderData();

        } else if(trans.getmember().size() > 0) {

            json = scinable.getMemberData();

        } else if(trans.getclaim().size() > 0) {

            json = scinable.getClaimData();

        }

        int sdt = (int)((new Date().getTime() - scinable.getvisitTime()) / 1000);

        Scinable scinable = new Scinable();

        // App size measurement for &sr in urlarr
        int width;
        int height;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        String[] urlarr = { /* "http://",
                            scinableConfig.gethost(),
                            accessConfig.geturi(), */ // 파라미터만 전달.
                            "?vid=", vid,
                            "&uid=", uid,
                            "&ua=", "", /* userAgent속성은 브라우저가 서버로 보낸 사용자 에이전트 헤더를 반환합니다.*/
                            "&p=", scinable.getPageUrl(), /* pageUrl 안씀 */
                            "&dt=", util.encodeURIComponent(scinable.getPageTitle()),
                            "&cgk=", Integer.toString(access.getcgk()),
                            "&cgv=", Integer.toString(access.getcgv()),
                            "&cgc=", util.encodeURIComponent(access.getcgc()),
                            "&pid=", util.encodeURIComponent(access.getid()),
                            "&pgid=", util.encodeURIComponent(access.getgroupid()),
                            "&pat=", util.encodeURIComponent(access.gettype()),
                            "&sr=", width + "x" + height,
                            "&la=", scinable.getLang(),
                            "&fr=", scinable.getFreq(),
                            "&pv=", scinable.getPreVisitDate(),
                            "&ref=", "",// 필요 없음/* encodeURIComponent(Scinable.getReferrer()), */
                            "&cid=", scinable.getcampaign(),
                            "&ck=", util.encodeURIComponent(ck),
                            "&ak=", ak,
                            "&gk=", gk,
                            "&cl=", util.encodeURIComponent(cl),
                            "&nv=", Integer.toString(scinable.getnewVisit()),
                            "&at=", scinable.getoffline(),
                            "&cc=", util.encodeURIComponent(cc),
                            "&aid=", scinable.getaccountId(),
                            "&jd=", util.encodeURIComponent(json),
                            "&eid=", scinable.getchannel(),
                            "&spv=", Integer.toString(scinable.getpageView()),
                            "&sdt=", Integer.toString(sdt),
                            "&vp=",".", util.getCookie("___cvp"),".",
                            "&up=",".", util.getCookie("___cup"),".",
                            "&vc=",".", util.getCookie("___cvc"),"."
        };

        Iterator<Map.Entry<String,String>> cf = scinable.getcustomField().entrySet().iterator();
        while(cf.hasNext()) {

            Map.Entry<String,String> entry = (Map.Entry<String,String>)cf.next();

            String cp = entry.getValue();

            if(cp != null) {

                List<String> list = new ArrayList<>(Arrays.asList(urlarr));
                list.add("$cp");
                list.add(entry.getKey());
                list.add("=");
                list.add(util.encodeURIComponent(cp));
                urlarr = list.toArray(new String[list.size()]);

            }

        }

        // join함수 : 배열의 항목들을 String으로 이어붙임.
        String url = join(urlarr,"");

        // 통신 부분, needsdomready 사용 안하고 바로 전송하는 것으로
        try {

            scinable.sendToServer(url);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }


    // join함수가 직접 구현
    public String join(String[] arr, String div) {

        String result = "";

        for(int i = 0; i < arr.length;i++) {

            result += arr[i];

            if(i==arr.length-1) {

                break;

            }

            result += div;

        }

        return result;

    }


    public void _setCampaignParameter(String... p) {

        param.setcampaign(p[1]);

    }


    // 매개변수를 사용하지 않지만 가변인자는 매개변수없이 호출해도 상관없음.
    public void _setOffline(String... p) {

        scinable.setoffline("off");

    }


    public void _setCampaign(String... p) {

        scinable.setcampaign(p[1]);

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


    public void _setPage(String url, String title, String id, String groupId, String type) {

        access.seturl(url);
        access.settitle(title);
        access.setid(id);
        access.setgroupid(groupId);
        access.settype(type);

    }


    public void _setPage(String id, String groupId) {

        access.setid(id);
        access.setgroupid(groupId);

    }


    // 원래 매개변수는 배열형식으로 받지만 임의로 다수의 String 형식으로 받도록 구현.
    // 2번째, 4번째 매개변수는 선택적. 선택하지 않을 때 사용자는 공백("")을 입력한다고 가정
    // 쿠키로 등록하기 전 인자로 받은 배열 요소는 [ 회원ID.해당생년월일그룹.성별.등급 ] 의 형식
    // (String customerId, String birthDate, String gender, String customerLevel)
    public void _setCustomVar(String... p) {

        if(scinable.getcookieEnabled()) {

            String con = "";

            con = p[1]; // 1st

            if(p[2] != "") {

                con += "." + util.getAgeGroupKey(p[2]); // 2nd

            }

            con += "." + p[3]; // 3rd

            if(p[4] != "") {

                con += "." + p[4]; // 4th

            }

            util.setCookie("___cc", con, scinableConfig.getccExpire());

        }

    }


    // opt_conversionValue와 reserveList는 선택사항
    // opt_conversionValue를 선택하지 않을 시, 0으로 입력한다고 가정.
    // reserveList가 없을 경우 ""로 매개변수 전달
    // js에서는 opt_conversionValue가 Option으로 나타나지만
    // 유인물에서는 필수항목으로 정의됨. 필수항목으로 작성
    // (int conversionId, int opt_conversionValue, String reserveList1, String reserveList2, String reserveList3, String reserveList4, String reserveList5)
    public void _setConversion(String... p) {

        access.setcgk(Integer.parseInt(p[1])); // 1st

        if(Integer.parseInt(p[2]) != 0) {

            access.setcgv(Integer.parseInt(p[2])); // 2nd

        }

        String[] reserveList = {p[3], p[4], p[5], p[6], p[7]};

        String cgcList = "";

        // Join함수 대신 구현. 매개변수를 특이하게 받기 때문에 만들어놓은 Join함수 사용 불가
        for(int i = 0; i < reserveList.length; i++) {

            if(reserveList[i]!="") {

                cgcList += reserveList[i];

                if(i < 4) {

                    cgcList += ";";

                }

            }

        }

        access.setcgc(cgcList);
        util.setR("___cvc", p[1],scinableConfig.getcvExpire());

    }


    //
    public static void _setCustomField() {



    }


    // shopId, payType, customId, adress, opt_customSet은 선택옵션
    // 선택옵션이 선택되지 않았을 경우 공백("")을 입력받는 것으로 가정한다.
    // (String orderId, String shopId, String payType, int totalItemPrice, int totalItemCost, int totalAmount, String customerId, String address,
    //   String opt_customSet1, String opt_customSet2, String opt_customSet3, String opt_customSet4, String opt_customSet5)
    public void _addTrans(String... p) {

        trans.settype("C");
        trans.addorder(p[1]);

        if(p[2] != "") {

            trans.addorder(p[2]);

        }

        if(p[3] != "") {

            trans.addorder(p[3]);

        }

        trans.addorder(p[4]);
        trans.addorder(p[5]);
        trans.addorder(p[6]);

        if(p[7] != "") {

            trans.addorder(p[7]);

        }

        if(p[8] != "") {

            trans.addorder(p[8]);

        }

        if(p[9] != "") {

            trans.addorder(p[9]);

        }

        if(p[10] != "") {

            trans.addorder(p[10]);

        }

        if(p[11] != "") {

            trans.addorder(p[11]);

        }

        if(p[12] != "") {

            trans.addorder(p[12]);

        }

        if(p[13] != "") {

            trans.addorder(p[13]);

        }

    }


    // name, category, itemShopId, opt_customSet, opt_itemGroupId는 선택항목.
    // 선택항목을 선택하지 않았을 경우 사용자는 공백 ("")을 입력한다고 가정한다.
    // (String subOrderId, String itemId, String name, String category,
    //   String itemShopId, int count, int unitPrice, int unitCost, int price,
    //   int cost, String opt_customSet1, String opt_customSet2,
    //   String opt_customSet3, String opt_customSet4, String opt_customSet5,
    //   String opt_itemGroupId)
    public void _addItem(String... p) {

        trans.additems(p[1]);
        trans.additems(p[2]);

        if(p[3] != "") {

            trans.additems(p[3]);

        }

        if(p[4] != "") {

            trans.additems(p[4]);

        }

        if(p[5] != "") {

            trans.additems(p[5]);

        }

        trans.additems(p[6]);
        trans.additems(p[7]);
        trans.additems(p[8]);
        trans.additems(p[9]);
        trans.additems(p[10]);

        if(p[11] != "") {

            trans.additems(p[11]);

        }

        if(p[12] != "") {

            trans.additems(p[12]);

        }

        if(p[13] != "") {

            trans.additems(p[13]);

        }

        if(p[14] != "") {

            trans.additems(p[14]);

        }

        if(p[15] != "") {

            trans.additems(p[15]);

        }

        if(p[16] != "") {

            trans.additems(p[16]);

        }

    }


    // customerId와 mailYn만 필수
    // 선택적인 항목은 공백("")입력 시 선택하지 않은 것으로 가정
    // (String customerId, String address, String gender, String birthDate,
    //  String customerLevel, String email, String lastName, String firstName,
    //  String mailYn, String opt_customSet1, String opt_customSet2,
    //  String opt_customSet3, String opt_customSet4, String opt_customSet5)
    public void _addMember(String... p) {

        trans.settype("C");

        trans.addmember(p[1]);

        if(p[2] != "") {

            trans.addmember(p[2]);

        }

        if(p[3] != "") {

            trans.addmember(p[3]);

        }

        if(p[4] != "") {

            trans.addmember(p[4]);

        }

        if(p[5] != "") {

            trans.addmember(p[5]);

        }

        if(p[6] != "") {

            trans.addmember(p[6]);

        }

        if(p[7] != "") {

            trans.addmember(p[7]);

        }

        if(p[8] != "") {

            trans.addmember(p[8]);

        }

        trans.addmember(p[9]);

        trans.addmember("1");

        if(p[10] != "") {

            trans.addmember(p[10]);

        }

        if(p[11] != "") {

            trans.addmember(p[11]);

        }

        if(p[12] != "") {

            trans.addmember(p[12]);

        }

        if(p[13] != "") {

            trans.addmember(p[13]);

        }

        if(p[14] != "") {

            trans.addmember(p[14]);

        }

    }


    // (String customerId, String address, String gender, String birthDate,
    //  String customerLevel, String email, String lastName, String firstName,
    //  String mailYn, String opt_customSet1, String opt_customSet2,
    //  String opt_customSet3, String opt_customSet4, String opt_customSet5)
    // Update 메소드지만 js코드와 type만 다르고, addmember()와 똑같이 push하는 동작을 함.
    public void _updateMember(String... p) {

        trans.settype("U");

        trans.addmember(p[1]);

        if(p[2] != "") {

            trans.addmember(p[2]);

        }

        if(p[3] != "") {

            trans.addmember(p[3]);

        }

        if(p[4] != "") {

            trans.addmember(p[4]);

        }

        if(p[5] != "") {

            trans.addmember(p[5]);

        }

        if(p[6] != "") {

            trans.addmember(p[6]);

        }

        if(p[7] != "") {

            trans.addmember(p[7]);

        }

        if(p[8] != "") {

            trans.addmember(p[8]);

        }

        trans.addmember(p[9]);

        trans.addmember("1");

        if(p[10] != "") {

            trans.addmember(p[10]);

        }

        if(p[11] != "") {

            trans.addmember(p[11]);

        }

        if(p[12] != "") {

            trans.addmember(p[12]);

        }

        if(p[13] != "") {

            trans.addmember(p[13]);

        }

        if(p[14] != "") {

            trans.addmember(p[14]);

        }

    }


    public void _cancelMember(String... p) {

        trans.settype("W");
        trans.addmember(p[1]);

    }


    // (String orderId, String subOrderId, String claimType, String customerId,
    //  String itemId, String name, String category, int claimCount,
    //  int unitPrice, int unitCost,int price, int cost, String opt_customset1,
    //  String opt_customset2, String opt_customset3, String opt_customset4,
    //  String opt_customset5)
    public void _addClaim(String... p) {

        trans.settype("C");

        trans.addclaim(p[1]);
        trans.addclaim(p[2]);

        if(p[3] != "") {

            trans.addclaim(p[3]);

        }

        trans.addclaim(p[4]);
        trans.addclaim(p[5]);

        if(p[6] != "") {

            trans.addclaim(p[6]);

        }

        if(p[7] != "") {

            trans.addclaim(p[7]);

        }

        trans.addclaim(p[8]);
        trans.addclaim(p[9]);
        trans.addclaim(p[10]);
        trans.addclaim(p[11]);
        trans.addclaim(p[12]);

        if(p[13] != "") {

            trans.addclaim(p[13]);

        }

        if(p[14] != "") {

            trans.addclaim(p[14]);

        }

        if(p[15] != "") {

            trans.addclaim(p[15]);

        }

        if(p[16] != "") {

            trans.addclaim(p[16]);

        }

        if(p[17] != "") {

            trans.addclaim(p[17]);

        }

    }


    // 만약 가변인자 외에도 다른 매개변수가 더 있다면 가변인자는 마지막에 선언해야 한다.
    // 유인물에 하나 더 있는데 넘김.
    public void _trackCart(String... p) {

        ArrayList<String> cart = new ArrayList<>();

        cart.add("cart");

        for(String s : p) {

            cart.add(s);

        }

        sendItems(cart);

    }


    // 유인물에 하나 더 있는데 넘김.
    public void _trackFavorite(String... p) {

        ArrayList<String> favorite = new ArrayList<>();

        favorite.add("favorite");

        for(String s : p) {

            favorite.add(s);

        }

        sendItems(favorite);

    }


    public void sendItems(ArrayList<String> arg) {

        if(scinable.getcookieEnabled() != false) {

            String ck = util.getCK();

            if(ck != null) {

                String[] url = {
                        //document.location.protocol=="https:"?"https://":"http://",
                        //Scinable.Config.host,
                        //Scinable.Access.Config.uri, // 파라미터만 전달
                        "?vid=", util.getVid(),
                        "&uid=", util.getUid(),
                        //'&ua=', encodeURIComponent(navigator.userAgent),
                        //'&p=', encodeURIComponent(Scinable.getPageUrl()),
                        "&ck=", util.encodeURIComponent(ck),
                        "&nv=", Integer.toString(scinable.getnewVisit()),
                        "&aid=", scinable.getaccountId()
                };

                List<String> urllist = new ArrayList<>(Arrays.asList(url));
                urllist.add("&at=" + arg.get(0)); // cart, favorite

                // sendItems의 매개변수 arg는 _trackFavorite함수나 _trackCart함수에서 매개변수로 받은 목록이다.
                // 2개의 항목은 필수로 입력된다. [cart || favorite],[add || delete]
                // 원래 js코드에서는 매개변수를 받는 두 가지 방법에 대한 구현이 모두 있는데
                // 우선 한가지만 구현
                if(arg.size() >= 2) { // if문은 사실 의미 없음. 추후에 다른 방법의 구현을 위해 사용

                    urllist.add("&ct=" + arg.get(1)); // calltype

                    ArrayList<String> items = new ArrayList<>();

                    // 현재 arg의 "items" 요소는 js코드 상에서 arr로 옮겨간 상황
                    // 안드로이드 상에서 "items" 요소만 빼내서 구현할 수 없기 때문에
                    // arr를 사용하지 않음. 그래서 for문의 i는 3부터 시작
                    // arr[0]은 cart/favorite, arr[1]은 action
                    // arr[2]부터 items 요소
                    items.add(util.encodeURIComponent(arg.get(2)));

                    for(int i = 3; i < arg.size(); i++) {

                        items.add(";");
                        items.add(util.encodeURIComponent(arg.get(i)));

                    }

                    urllist.add("&its=" + join(items.toArray(new String[items.size()]),""));

                }

                String urlFinal = join(urllist.toArray(new String[urllist.size()]),"");

                // 통신 부분, needsdomready 사용 안하고 바로 전송하는 것으로
                try {

                    scinable.sendToServer(urlFinal);

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

    }


    public static int eventCnt = 0;

    // (String category, String action, String opt_label, int opt_value)
    public void _trackEvent(String... p) {

        if(scinable.getcookieEnabled()) {

            if(++eventCnt < 10) {

                String[] url = {

                        //document.location.protocol=="https:"?"https://":"http://",
                        //Scinable.Config.host,
                        //Scinable.Access.Config.uri, // 파라미터만 전달
                        "?vid=", util.getVid(),
                        "&uid=", util.getUid(),
                        //"&ua=", encodeURIComponent(navigator.userAgent),
                        //'&p=', encodeURIComponent(Scinable.getPageUrl()),
                        //'&dt=', encodeURIComponent(Scinable.getPageTitle()),
                        "&nv=", Integer.toString(scinable.getnewVisit()),
                        //'&hn=', encodeURIComponent(document.domain),
                        "&aid=", scinable.getaccountId(),
                        "&at=", "event"

                };

                List<String> urllist = new ArrayList<>(Arrays.asList(url));

                urllist.add("&e1=" + util.encodeURIComponent(p[0]));
                urllist.add("&e2=" + util.encodeURIComponent(p[1]));

                if(p[2] != "") {

                    urllist.add("&e1=" + util.encodeURIComponent(p[2]));

                }

                if(p[3] != "") {

                    urllist.add("&e1=" + util.encodeURIComponent(p[3]));

                }

                String urlFinal = join(urllist.toArray(new String[urllist.size()]),"");

                // 통신 부분, needsdomready 사용 안하고 바로 전송하는 것으로
                try {

                    scinable.sendToServer(urlFinal);

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

    }

}



