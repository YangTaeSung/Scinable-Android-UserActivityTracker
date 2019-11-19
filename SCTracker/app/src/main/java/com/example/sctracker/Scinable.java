package com.example.sctracker;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import java.util.ArrayList;
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
    private static int frequency = 0;
    private static int newVisit = 0;
    public static String offline = "";
    private static int pageView = 1;


    // Define customField as Object
    // private static ArrayList<String> customField = new ArrayList<>();

    /* This variable for Request function */
    // 의미 파악 후 Activity와 연관짓기
    String fullUrl;
    String headLoc;
    String scriptId;

    /*
    // 사용자가 이전에 사이트를 방문하지 않은 경우 브라우저에는 사이트에
    캐시 된 파일이 없으므로 서버에서 모든 파일을 가져옵니다.
    // 사용자가 사이트를 방문한 적이 있는 경우 브라우저는 웹 서버에서
    HTML 페이지를 검색하지만 정적 자산(JavaScript,CSS,이미지)에 대해서는 캐시를 참조합니다.

    Scinable.Request.prototype.buildScriptTag = function(browerCache) {
            this.scriptObj = document.createElement("script");
            this.scriptObj.setAttribute("type", "text/javascript");
            this.scriptObj.setAttribute("charset", "utf-8");
            this.scriptObj.setAttribute("id", this.scriptId);
            if(browerCache) {
                this.scriptObj.setAttribute("src", this.fullUrl);
            } else {
                this.scriptObj.setAttribute("src", this.fullUrl + '&_r=' + Math.floor(Math.random() * 0x7fffffff));
            }
        }

     */

    // <getElementsByTagsName> : 문서의 요소 콜렉션을 NodeList로 리턴
    // js파일에서는 첫번째 요소(item(0))을 가져옴
    // 앱에서는 Url X
    // 정보들을 받아와서 서버와 통신하는 작업
    public void Request(String fullUrl) {

        this.fullUrl = fullUrl;
    }


    /*

    AJAX : AJAX란 비동기 자바스크립트와 XML (Asynchronous JavaScript And XML)을 말합니다.
    간단히 말하면, 서버와 통신하기 위해 XMLHttpRequest 객체를 사용하는 것을 말합니다. JSON, XML, HTML 그리고
    일반 텍스트 형식 등을 포함한 다양한 포맷을 주고 받을 수 있습니다. AJAX의 강력한 특징은 페이지
    전체를 리프레쉬 하지 않고서도 수행 되는 "비동기성"입니다. 이러한 비동기성을 통해 사용자의
    Event가 있으면 전체 페이지가 아닌 일부분만을 업데이트 할 수 있게 해줍니다.
    AJAX의 주요 두가지 특징 : 페이지 새로고침 없이 서버에 요청, 서버로부터 데이터를 받고 작업을 수행

     */

    /*

    XMLHttpRequest(XHR) 객체는 웹 브라우저가 서버와 상호작용하기 위하여 사용됩니다.
    전체 페이지의 새로고침없이도 URL 로부터 데이터를 받아올 수 있습니다. 웹브라우저가 백그라운드에서 서버와 계속 통신합니다.
    이는 웹 페이지가 사용자가 하고 있는 것을 방해하지 않으면서 페이지의 일부를 업데이트할 수 있도록 해줍니다.
    XMLHttpRequest 는 AJAX 프로그래밍에 주로 사용됩니다.
    XMLHttpRequest 는 이름으로만 봐서는 XML 만 받아올 수 있을 것 같아 보이지만,
    모든 종류의 데이터를 받아오는데 사용할 수 있습니다. 또한 HTTP 이외의 프로토콜도 지원합니다(file 과 ftp 포함).

    현재 대부분의 주요 웹 브라우저는 XMLHttpRequest 객체를 내장하고 있습니다.
    이러한 XMLHttpRequest 객체를 생성하는 방법은 브라우저의 종류에 따라 다음과 같이 나눠집니다.
    1. XMLHttpRequest 객체를 이용한 방법
    2. ActiveXObject 객체를 이용한 방법
    익스플로러 7과 그 이상의 버전, 크롬, 파이어폭스, 사파리, 오페라에서는 XMLHttpRequest 객체를 사용합니다.
    하지만 익스플로러의 구형 버전인 5와 6 버전에서는 ActiveXObject 객체를 사용해야 합니다.

    // XMLHttpRequest() 생성자는 XMLHttpRequest 를 초기화합니다. 다른 모든 메소드 호출 이전에 호출되어야 합니다.
    // XMLHttpRequest.onreadystatechange : readyState 어트리뷰트가 변경될 때마다 호출되는 이벤트핸들러입니다.
    // XMLHttpRequest.readyState : readyState == 4 은 작업이 완전히 완료됨을 나타냅니다.
    // XMLHttpRequest.open(method, url) : get 또는 post 메소드 및 url을 지정하여 요청을 엽니다.
    // XMLHttpRequest.send() : 요청을 보냅니다. 요청이 비동기인 경우(기본값), 이 메소드는 요청이 보내진 즉시 반환합니다.


    Older Browsers (IE5 and IE6)
    Old versions of Internet Explorer (5/6) use an ActiveX object instead of the XMLHttpRequest object:

    * variable = new ActiveXObject("Microsoft.XMLHTTP"); *
    To handle IE5 and IE6, check if the browser supports the XMLHttpRequest object, or else create an ActiveX object:

    < Example >
    if (window.XMLHttpRequest) {
      // code for modern browsers
       xmlhttp = new XMLHttpRequest();
    } else {
      // code for old IE browsers
      xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    // 위의 코드는 이 코드와 비슷한 내용. 브라우저 실행에 관련한 내용인 듯
    Scinable.Ajax.createXMLHttp = function(){
            var request;
            try {
                request = new XMLHttpRequest();
            } catch (trymicrosoft) {
                try {
                    request =  new ActiveXObject("Msxml2.XMLHTTP");
                } catch (othermicrosoft) {
                    try {
                        request = new ActiveXObject("Microsoft.XMLHTTP");
                    } catch (failed) {
                        request = null;
                    }
                }
            }
            return request;
        }

      // XMLHttpRequest.responseText : 응답받은 데이터를 스트링으로 리턴한다.
      // callback : 콜백 함수의 동작 방식은 일종의 식당 자리 예약과 같습니다.
      일반적으로 맛집을 가면 사람이 많아 자리가 없습니다. 그래서 대기자 명단에 이름을 쓴 다음에 자리가 날 때까지 주변 식당을 돌아다니죠.
      만약 식당에서 자리가 생기면 전화로 자리가 났다고 연락이 옵니다. 그 전화를 받는 시점이 여기서의 콜백 함수가 호출되는 시점과 같습니다.
      손님 입장에서는 자리가 날 때까지 식당에서 기다리지 않고 근처 가게에서 잠깐 쇼핑을 할 수도 있고 아니면
      다른 식당 자리를 알아볼 수도 있습니다.

      즉 responseText로 받은 스트링 데이터를 jsonObject에 저장한 후 callback으로 jsonObject를 돌려놓기

     */






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

    }



 }
