package com.example.sctracker;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

import java.lang.reflect.Array;
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

        // join함수 : 배열의 항목들을 String으로 이어붙임.
        String url = join(urlarr,"");

    }


    // join함수가 없기 때문에 직접 구현
    public static String join(String[] arr, String div) {

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


    // 원래 매개변수는 배열형식으로 받지만 임의로 다수의 String 형식으로 받도록 구현.
    // 2번째, 4번째 매개변수는 선택적. 선택하지 않을 때 사용자는 공백("")을 입력한다고 가정
    // 쿠키로 등록하기 전 인자로 받은 배열 요소는 [ 회원ID.해당생년월일그룹.성별.등급 ] 의 형식
    public static void _setCustomVar(String customerId, String birthDate, String gender, String customerLevel) {

        if(cookieEnabled = true) {

            String con = "";

            con = customerId; // 1st

            if(birthDate != "") {

                con += "." + Util.getAgeGroupKey(birthDate); // 2nd

            }

            con += "." + gender; // 3rd

            if(customerLevel != "") {

                con += "." + customerLevel; // 4th

            }

            Util.setCookie("___cc", con, Config.ccExpire);

        }

    }


    // opt_conversionValue와 reserveList는 선택사항
    // opt_conversionValue를 선택하지 않을 시, 0으로 입력한다고 가정.
    // reserveList가 없을 경우 ""로 매개변수 전달
    // js에서는 opt_conversionValue가 Option으로 나타나지만
    // 유인물에서는 필수항목으로 정의됨. 필수항목으로 작성
    public static void _setConversion(int conversionId, int opt_conversionValue, String reserveList1, String reserveList2, String reserveList3, String reserveList4, String reserveList5) {

        Access.cgk = conversionId; // 1st

        if(opt_conversionValue != 0) {

            Access.cgv = opt_conversionValue; // 2nd

        }

        String[] reserveList = {reserveList1, reserveList2, reserveList3, reserveList4, reserveList5};

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

        Access.cgc = cgcList;
        Util.setR("___cvc",Integer.toString(conversionId),Config.cvExpire);

    }


    //
    public static void _setCustomField() {



    }


    // shopId, payType, customId, adress, opt_customSet은 선택옵션
    // 선택옵션이 선택되지 않았을 경우 공백("")을 입력받는 것으로 가정한다.
    public static void _addTrans(String orderId, String shopId, String payType, int totalItemPrice, int totalItemCost, int totalAmount, String customerId, String address, String opt_customSet1, String opt_customSet2, String opt_customSet3, String opt_customSet4, String opt_customSet5) {

        Trans.type = "C";
        Trans.order.add(orderId);

        if(shopId != "") {

            Trans.order.add(shopId);

        }

        if(payType != "") {

            Trans.order.add(payType);

        }

        Trans.order.add(Integer.toString(totalItemPrice));
        Trans.order.add(Integer.toString(totalItemCost));
        Trans.order.add(Integer.toString(totalAmount));

        if(customerId != "") {

            Trans.order.add(customerId);

        }

        if(address != "") {

            Trans.order.add(address);

        }

        if(opt_customSet1 != "") {

            Trans.order.add(opt_customSet1);

        }

        if(opt_customSet2 != "") {

            Trans.order.add(opt_customSet2);

        }

        if(opt_customSet3 != "") {

            Trans.order.add(opt_customSet3);

        }

        if(opt_customSet4 != "") {

            Trans.order.add(opt_customSet4);

        }

        if(opt_customSet5 != "") {

            Trans.order.add(opt_customSet5);

        }

    }


    // name, category, itemShopId, opt_customSet, opt_itemGroupId는 선택항목.
    // 선택항목을 선택하지 않았을 경우 사용자는 공백 ("")을 입력한다고 가정한다.
    public static void _addItem(String subOrderId, String itemId, String name, String category, String itemShopId, int count, int unitPrice, int unitCost, int price, int cost, String opt_customSet1, String opt_customSet2, String opt_customSet3, String opt_customSet4, String opt_customSet5, String opt_itemGroupId) {

        Trans.items.add(subOrderId);
        Trans.items.add(itemId);

        if(name != "") {

            Trans.items.add(name);

        }

        if(category != "") {

            Trans.items.add(category);

        }

        if(itemShopId != "") {

            Trans.items.add(itemShopId);

        }

        Trans.items.add(Integer.toString(count));
        Trans.items.add(Integer.toString(unitPrice));
        Trans.items.add(Integer.toString(unitCost));
        Trans.items.add(Integer.toString(price));
        Trans.items.add(Integer.toString(cost));

        if(opt_customSet1!= "") {

            Trans.items.add(opt_customSet1);

        }

        if(opt_customSet2 != "") {

            Trans.items.add(opt_customSet2);

        }

        if(opt_customSet3 != "") {

            Trans.items.add(opt_customSet3);

        }

        if(opt_customSet4 != "") {

            Trans.items.add(opt_customSet4);

        }

        if(opt_customSet5 != "") {

            Trans.items.add(opt_customSet5);

        }

        if(opt_itemGroupId != "") {

            Trans.items.add(opt_itemGroupId);

        }

    }


    // customerId와 mailYn만 필수
    // 선택적인 항목은 공백("")입력 시 선택하지 않은 것으로 가정
    public static void _addMember(String customerId, String address, String gender, String birthDate,
                                  String customerLevel, String email, String lastName, String firstName,
                                  String mailYn, String opt_customSet1, String opt_customSet2,
                                  String opt_customSet3, String opt_customSet4, String opt_customSet5) {

        Trans.type = "C";

        Trans.member.add(customerId);

        if(address != "") {

            Trans.member.add(address);

        }

        if(gender != "") {

            Trans.member.add(gender);

        }

        if(birthDate != "") {

            Trans.member.add(birthDate);

        }

        if(customerLevel != "") {

            Trans.member.add(customerLevel);

        }

        if(email != "") {

            Trans.member.add(email);

        }

        if(lastName != "") {

            Trans.member.add(lastName);

        }

        if(firstName != "") {

            Trans.member.add(firstName);

        }

        Trans.member.add(mailYn);

        Trans.member.add("1");

        if(opt_customSet1 != "") {

            Trans.member.add(opt_customSet1);

        }

        if(opt_customSet2 != "") {

            Trans.member.add(opt_customSet2);

        }

        if(opt_customSet3 != "") {

            Trans.member.add(opt_customSet3);

        }

        if(opt_customSet4 != "") {

            Trans.member.add(opt_customSet4);

        }

        if(opt_customSet5 != "") {

            Trans.member.add(opt_customSet5);

        }

    }


    public static void _updateMember(String customerId, String address, String gender, String birthDate,
                                     String customerLevel, String email, String lastName, String firstName,
                                     String mailYn, String opt_customSet1, String opt_customSet2,
                                     String opt_customSet3, String opt_customSet4, String opt_customSet5) {

        Trans.type = "U";

        Trans.member.add(customerId);

        if(address != "") {

            Trans.member.add(address);

        }

        if(gender != "") {

            Trans.member.add(gender);

        }

        if(birthDate != "") {

            Trans.member.add(birthDate);

        }

        if(customerLevel != "") {

            Trans.member.add(customerLevel);

        }

        if(email != "") {

            Trans.member.add(email);

        }

        if(lastName != "") {

            Trans.member.add(lastName);

        }

        if(firstName != "") {

            Trans.member.add(firstName);

        }

        Trans.member.add(mailYn);

        Trans.member.add("1");

        if(opt_customSet1 != "") {

            Trans.member.add(opt_customSet1);

        }

        if(opt_customSet2 != "") {

            Trans.member.add(opt_customSet2);

        }

        if(opt_customSet3 != "") {

            Trans.member.add(opt_customSet3);

        }

        if(opt_customSet4 != "") {

            Trans.member.add(opt_customSet4);

        }

        if(opt_customSet5 != "") {

            Trans.member.add(opt_customSet5);

        }

    }


    public static void _cancelMember(String customerId) {

        Trans.type = "W";
        Trans.member.add(customerId);

    }


    public static void _addClaim(String orderId, String subOrderId, String claimType, String customerId, String itemId, String name, String category, int claimCount, int unitPrice, int unitCost,int price, int cost, String opt_customset1, String opt_customset2, String opt_customset3, String opt_customset4, String opt_customset5) {

        Trans.type = "C";

        Trans.claim.add(orderId);
        Trans.claim.add(subOrderId);

        if(claimType != "") {

            Trans.claim.add(claimType);

        }

        Trans.claim.add(customerId);
        Trans.claim.add(itemId);

        if(name != "") {

            Trans.claim.add(name);

        }

        if(category != "") {

            Trans.claim.add(category);

        }

        Trans.claim.add(Integer.toString(claimCount));
        Trans.claim.add(Integer.toString(unitPrice));
        Trans.claim.add(Integer.toString(unitCost));
        Trans.claim.add(Integer.toString(price));
        Trans.claim.add(Integer.toString(cost));

        if(opt_customset1 != "") {

            Trans.claim.add(opt_customset1);

        }

        if(opt_customset2 != "") {

            Trans.claim.add(opt_customset2);

        }

        if(opt_customset3 != "") {

            Trans.claim.add(opt_customset3);

        }

        if(opt_customset4 != "") {

            Trans.claim.add(opt_customset4);

        }

        if(opt_customset5 != "") {

            Trans.claim.add(opt_customset5);

        }

    }


    // 만약 가변인자 외에도 다른 매개변수가 더 있다면 가변인자는 마지막에 선언해야 한다.
    // 유인물에 하나 더 있는데 넘김.
    public static void _trackCart(String actionType, String... itemId) {

        ArrayList<String> cart = new ArrayList<>();

        cart.add("cart");

        cart.add(actionType);

        for(String s : itemId) {

            cart.add(s);

        }

        sendItems(cart);

    }


    // 유인물에 하나 더 있는데 넘김.
    public static void _trackFavorite(String actionType, String... itemId) {

        ArrayList<String> favorite = new ArrayList<>();

        favorite.add("favorite");

        favorite.add(actionType);

        for(String s : itemId) {

            favorite.add(s);

        }

        sendItems(favorite);

    }


    public static void sendItems(ArrayList<String> arg) {

        if(cookieEnabled != false) {

            String ck = Util.getCK();

            if(ck != null) {

                String[] url = {
                        //document.location.protocol=="https:"?"https://":"http://",
                        //Scinable.Config.host,
                        //Scinable.Access.Config.uri,
                        "?vid=", Util.getVid(),
                        "&uid=", Util.getUid(),
                        //'&ua=', encodeURIComponent(navigator.userAgent),
                        //'&p=', encodeURIComponent(Scinable.getPageUrl()),
                        "&ck=", encodeURIComponent(ck),
                        "&nv=", Integer.toString(Scinable.newVisit),
                        "&aid=", Scinable.accountId
                };

                List<String> list = new ArrayList<>(Arrays.asList(url));
                list.add("&at=" + arg.get(0)); // cart, favorite

                if(arg.size() >= 2) {

                    list.add("&ct=" + arg.get(1)); // calltype

                    ArrayList<String> items = new ArrayList<>();

                    // 현재 arg의 "items" 요소는 js에서 arr로 옮겨간 상황
                    // 안드로이드 상에서 "items" 요소만 빼내서 구현할 수 없기 때문에
                    // arr를 사용하지 않음. 그래서 for문의 i는 3부터 시작
                    // arr[0]은 cart/favorite, arr[1]은 action
                    // arr[2]부터 items 요소
                    items.add(encodeURIComponent(arg.get(2)));

                    for(int i = 3; i < arg.size(); i++) {

                        items.add(";");
                        items.add(arg.get(i));

                    }

                    list.add("&its=" + items.join(""));

                }

                list.add(entry.getKey());
                list.add("=");
                list.add(Util.encodeURIComponent(cp));
                urlarr = list.toArray(new String[list.size()]);

            }

        }

    }

}



