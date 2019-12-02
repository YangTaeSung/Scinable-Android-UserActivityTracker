package com.example.sctracker;

import android.content.Intent;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.sctracker.Scinable.campaign;
import static com.example.sctracker.Scinable.cookie;
import static com.example.sctracker.Scinable.cookieEnabled;


public class Util {

    public void go(String NextActivity) {

        /*
        자바스크립트를 이용해서 특정 URL로 접속했을 때 다른 URL로 이동시킬 수 있습니다,
        ex) window.location.href = 'http://www.abc.com/';
        (여기서는 액티비티의 변환이 이루어져야 됨)
        location.href는 객체의 속성이며, loaction.replace()는 메서드(함수)로 작동된다.
        href는 페이지를 이동하는 것이기 때문에 뒤로가기 버튼을 누른경우 이전 페이지로 이동이 가능하지만,
        replace는 현재 페이지를 새로운 페이지로 덮어 씌우기 때문에 이전 페이지로 이동이 불가능하다.
        href는 일반적인 페이지 이동시 이용을 하면 되고,
        replace의 경우는 이전페이지로 접근이 필요없는경우 보안상 덮어씌우는 것도 괜찮을듯 하다.
        */

        // 현재 액티비티에서 매개변수로 받은 액티비티로 전환?

    }


    // URL 인코딩은, URL 스트링에 있는 텍스트를, 모든브라우저에서 똑바로 전송하기 위해 존재한다.
    public static String encodeURI(String val) {

        // sc.js에서는 encodeURIComponent를 사용하는데 java의 URLEncoder와 미묘한 차이 발생
        // encodeURIComponent()함수에서 encode()함수의 수정을 통해 javascript의 encodeURIComponent와 같은 동작 실행
        return encodeURIComponent(val);

    }


    public static String encodeURIComponent(String val) {

        String result = null;

        try {

            result = URLEncoder.encode(val, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e) {

            result = val;

        }

        return result;
    }


    // 브라우저의 버전을 리턴하는 함수. Android sdk의 버전을 출력해야 하는가
    /*public int msie() {

        return 0;

    }*/


    // createXMLHttp : XMLHttpRequest() 객체를 생성.
    // 정상적으로 생성되면 객체를 리턴. 아래의 함수에서 객체가 존재하면 open, send함
    // JS에서 XMLHttpRequest는 Android에서 HTTPURLConnection이나 OKHTTP등이 있다고 함.
    /* public void readScriptFile(String src) {

    } */


    // HTMLElement.offsetWidth 읽기 전용 속성은 정수로 요소의 레이아웃 폭을 돌려줍니다.
    // var intElemOffsetWidth = element.offsetWidth;
    // element가 HTML요소로 예상됨.
    /* public int elementWidth() {

        if(msie() < 10) {

            return element.offsetWidth;

        }
        else {

            return element.clientWidth;

        }

    } */


    public String trim(String value) {

        String result = null;

        try {

            result = URLEncoder.encode(value, "UTF-8")
                    .replace(" ", "");
            // 공백 제거
            // 임의로 코드 작성, 시험해볼 것
            // 역슬래시 하나 더해주면 escape 문자 처리 가능
            // 해당 문자열 앞에 (?i)를 사용하면 대소문자 구분 안함

        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e) {

            result = value;

        }

        return result;

    }


    public static String defaultString() {
        return "";
    }


    public static String defaultString(String value) {
        return value;
    }


    /*
    replace의 대체되어야 하는 문자열 표현들 ex) \s+ 는 공백, \d+ 는 숫자
    예와 같은 의미들이 있을 것 같은데 모르기 떄문에 일단은 문자 그대로 표현해놓음
    */
    /*
    public String adjustCss(String val, String suggest) {

        String val1 = null;
        String val2 = null;

        try {

            val1 = URLEncoder.encode(val, "UTF-8")
                    .replace("(?i)<B>", SuggestConfig.FontStart);
            val2 = URLEncoder.encode(val1, "UTF-8")
                    .replace("(?i)<\\/B>", SuggestConfig.FontEnd);
            // 역슬래시 하나 더해줘서 escape 문자 처리
            // 해당 문자열 앞에 (?i)를 사용하면 대소문자 구분 안함
            // 위에 있는 Config는 suggest에 있는거. FontStart와 End는 못찾음.
            // 일단 Font가 들어가는걸로 봐서 안드로이드에서 구현 안해도 될 것 같음.

        }
        // This exception should never occur.
        catch (UnsupportedEncodingException e) {

            return val;

        }

        return val2;

    }

    public String removeBTag(String str) {

        String result = null;

        try {

            result = URLEncoder.encode(str, "UTF-8")
                    .replace("(?i)<\\/*B>", "");

        }
        catch (UnsupportedEncodingException e) {

            return str;
        }

        return result;

    }


    // 호출되는 경우가 없어서 구현 보류
    public String htmlEscape() {

        return "";

    }

    */

    public static String paramValues;


    // location.search : URL의 쿼리 문자열 부분을 반환
    // ex) www.abc.com?aaasssddd=e@e.e 이면  ?aaasssddd=e@e.e 반환
    // split : 문자열을 하위 문자열 배열로 나눕니다.
    // URL 쿼리 문자열을 ParamName과 ParamValue값으로 나누어 result에 저장
    // 구현 보류, URLEncode는 구현함. 우선 액티비티로 어떻게 구현할지 생각하기
    public String getQueryString() {

        String[] result;
        return "";

    }


    // 위와 같이 구현 보류
    public String getQueryArray() {

        return "";

    }


    public static String today() {

        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
        Date time = new Date();
        String day = format1.format(time);
        return day;

    }

    /*

    public String getParameter(String name) {

        if(Util.paramValues == null) {

            Util.paramValues = Util.getQueryString();

        }

    }

    */

    // 쿠키값을 리턴해주는 메소드. Document.cookie는 모든 쿠키가 저장되어 있는데
    // IndexOf()로 시작 위치를 찾고 len으로 길이 찾고 end지점 찾아서 리턴해줘
    // 리턴할 때 '키=값'이런 형식으로 리턴
    public static String getCookie(String name) {

        if(Scinable.cookieEnabled) {

            // cookieEnabled가 true인 상태면 SharedPreferences에 쿠키가 있는 상태니까
            // 찾아서 뽑아주기만 하면 돼, js코드는 과정이 조금 긴데 간단하게 할 수 있을 듯.

            // 위에 주석이 처음 한 생각, cookie라는 변수를 Scinable에 만들었고,
            // 그걸 쿠키로 써. SharedPreferences는 데이터를 서버에 전송할 때 쓰기.
            // 사용자가 이미 SharedPreferences쓰면서 쿠키의 역할(자동로그인 같은거)을 하고 있는
            // 상황은 추후에 생각하기.

            int start = Scinable.cookie.indexOf(name + "=");
            int len = start + name.length() + 1; // indexOf()하면 1 적게 출력

            // 만약 입력값이 문자열에 없다면 리턴 값은 -1이다.
            if(start == -1) {

                return "";

            }

            // len번째 인덱스부터 ";"이 가장 빨리 나오는 인덱스 번호
            int end = Scinable.cookie.indexOf(";", len);

            if(end == -1) {

                // 쿠키의 마지막은 세미콜론 없이 끝나기 때문에 세미콜론이 보이지 않으면
                // 쿠키전체의 길이가 찾고있는 값의 마지막 부분
                end = Scinable.cookie.length();

            }

            return cookie.substring(len,end); // unescape()함수 무시 -> 디코딩 함수

        } else {

            return "";

        }

    }


    // 쿠키 생성 메소드. 생성할 쿠키의 이름, 쿠키의 값, 만료시간 세 개가 파라미터
    // expires는 밀리초시간으로 들어옴. 만료 시간을 받음.
    public static void setCookie(String name, String value, long expires) {

        // cookieEnabled같은 경우에 js에서는 document에서 자동으로 확인
        // 여기서는 직접 설정을 구현해야함. ( SharedPreferences 확인 및 이용 )
        if(cookieEnabled) {
            // escape()함수 더이상 사용 안함, encodeURI()나 encodeURIComponent() 사용할 것.
            // 두 함수 모두 위에 구현해놓음.
            String str = name + "=" + encodeURI(value) + ";";

             /*

            if(Scinable.domainName != null) {

               str += " domain=" + Scinable.domainName + ";";

            }

            str += " path=/";

            앱 내에서 도메인과 경로는 쓰이지 않기 때문에 쿠키의 형태는 " 이름=값;만료시간=만료시간 "

            */

            if (expires != 0) {

                // 1970년 1월 1일 기준부터 지금까지의 밀리초시간을 구하고 만료시간을 더한다.
                // 더한 값으로 날짜를 계산하여 str 변수에 추가한다.
                /* 1970/1/1 이후로의 시간을 밀리초로 계산
                 * System.currentTimeMillis();
                 * 출력 형식 GMTString
                 * toGMTString()메소드 안드로이드에서 사용 불가
                 * 포맷으로 형식 지정 */

                SimpleDateFormat format1 = new SimpleDateFormat("E, d MMM yyyy hh:mm:ss GMT");
                Calendar calendar = Calendar.getInstance();
                expires += calendar.getTimeInMillis(); // 현재시간 밀리초값을 만료시간과 더하기
                Date timegmt = new Date(expires); // 해당 밀리초값을 날짜로 변환
                str += "; expires=" + format1.format(timegmt);

            }

            Scinable.cookie = str;

            // " 이름=값;만료시간=만료시간 "의 형태로 쿠키 생성

        }

    }


    // 위 함수에서 세 번째 파라미터가 존재하지 않을 때
    public static void setCookie(String name, String value) {

        String str = name + "=" + encodeURI(value) + ";";

        /*
        if (Scinable.domainName != null) {

            str += " domain=" + Scinable.domainName + ";";

        }

        str += " path=/";
         */

        Scinable.cookie = str;

    }


    // Math.round : 입력값을 반올림 한 수와 가장 가까운 정수값을 출력합니다.
    // Math.random : 0 이상 1 미만의 부동소숫점 의사 난수를 출력합니다.
    public static long createUUID() {

        return Math.round(2147283647 * Math.random());

    }


    public static String getVid() {

        if(Scinable.offline == "off") {

            return null; // 모호한 부분. js에서는 return 0; 형태. 임의로 null 지정

        }

        // vid가 Scinable에 등록되어 있을 때, Scinable.vid를 그대로 리턴.
        if(Scinable.vid != null) {

            return Scinable.vid;

        }
        // vid가 없을 때 새로 생성함과 동시에 Scinable.vid에 등록하고 그대로 리턴.
        else{
            if(cookieEnabled) {
                // getParameter가 필요하고 getParameter는 getQueryString이 필요한데
                // getQueryString이 페이지 URL의 쿼리를 반환하는거기 때문에
                // 안드로이드에서 구현 불가.
                // 간단하게 Scinable.vid가 있으면 그대로 쓰고 없으면 createUUID로 만듦.
                // 만들면서 pageView나 newVisit이나 활성화시켜줌
                // ___cv쓰는거는 일단 넘김

                String cv = Util.getCookie("___cv");
                String[] cvArr = {};
                String cookieCampaign = "";
                String cookieChannel = "";

                if(cv != null) {



                }

            /*

            ////////////////
            // new visit
            ////////////////
            String cv = Util.getCookie("___cv"); // 쿠키를 가져오기
            String[] cvArr = {};
            String cookieCampaign = "";
            String cookieChannel = "";

            // cv는 "___cv=value" 형태.
            if (cv == null) {

                // update cu(visit date, frequency)
                Scinable.frequency = Integer.toString(Integer.parseInt(Scinable.frequency) + 1);
                Util.setCU(Scinable.uid, Util.today(), Scinable.frequency);

                // create cv
                Scinable.vid = Long.toString(Util.createUUID());
                Scinable.visitTime = new Date().getTime();
                Scinable.pageView = 1;
                Scinable.newVisit = 1;

                // sciCampaign이랑 sciChannel 나오는거 일단 문자열로 대충 처리.
                cv = Scinable.vid + "." + Scinable.preVisitDate + "."
                        + Scinable.visitTime

            } else {

                Scinable.vid = cvArr[0];

            } */

            }
        }

        return "";

    } // 일단 넘김.




    public static String getUid() {

        if (Scinable.offline == "off") {

            return null;

        }

        if (Scinable.uid != null) {

            return Scinable.uid;

        } else {

            if (cookieEnabled == true) {

                String[] cuArr = Util.getCU();

                Scinable.uid = cuArr[0];
                Scinable.preVisitDate = cuArr[1];
                Scinable.frequency = cuArr[2];

                return Scinable.uid;

            } else {

                Scinable.uid = Long.toString(Util.createUUID());
                Scinable.preVisitDate = Util.today();
                Scinable.frequency = "0";

                return Scinable.uid;

            }
        }
    }


    public static String[] getCU() {

        boolean needSet = false;

        String[] cuArr = {};
        String cu = Util.getCookie("___cu");

        if(cu != null) {

            cuArr = cu.split("."); // cu를 split

            if(cuArr.length == 3) { // split한 요소가 3개일 때

                if(!Util.isNumeric(cuArr[0])) {

                    needSet = true;
                    cuArr[0] = Long.toString(Util.createUUID());

                }

                if(!Util.isNumeric(cuArr[1])) {

                    needSet = true;
                    cuArr[1] = Util.today();

                }

                if(!Util.isNumeric(cuArr[2])) {

                    needSet = true;
                    cuArr[2] = "1";

                }

            } else { // split한 요소가 3개가 아닐 때,

                needSet = true;
                cuArr[0] = (Long.toString(Util.createUUID()));
                cuArr[1] = Util.today();
                cuArr[2] = "1";

            }

        } else { // cu가 없을 때

            needSet = true;
            cuArr[0] = (Long.toString(Util.createUUID()));
            cuArr[1] = Util.today();
            cuArr[2] = "1";

        }

        if(needSet) { // 위의 if문들에 해당되는게 없으면 needSet은 false인 상태

            Util.setCU(cuArr[0], cuArr[1], cuArr[2]);

        }

        return cuArr;

    }


    public static String setCU(String uid, String cday, String freq) {

        if(!Util.isNumeric(uid)) {

            uid = Long.toString(Util.createUUID());

        }

        if(!Util.isNumeric(cday)) {

            cday = Util.today();

        }

        if(!Util.isNumeric(freq)) {

            freq = "1";

        }

        String cu = uid + "." + cday + "." + freq;

        Util.setCookie("___cu", cu, Config.cuExpire);

        return cu;

    }


    public static String getCK() {

        if(Scinable.ck != null) {

            return Scinable.ck;

        } else {

            String ck = "";
            String cc = Util.getCookie("___cc");
            String[] carr = cc.split(".");

            if(carr.length == 4) {

                ck = carr[0];

            }

            Scinable.ck = ck;
            return ck;

        }

    }


    // parseFloat() : 문자열을 실수로 변환
    // isNaN() : 매개변수가 숫자인지 검사하는 함수
    // isFinite() : 변수가 유한한지 검사하는 함수
    public static boolean isNumeric(String val) {

        float f = Float.parseFloat(val);
        // Double.isFinite()는 SDK version 24 이상부터 사용가능.
        // 현재 minSDK 설정이 15이기 때문에 작동불가 -> isInfinite()함수 사용
        // Javascript의 논리연산자의 사용에서 String끼리의 논리연산과
        // Boolean끼리의 논리연산 헷갈리지 말 것. 본 함수는 Boolean을 return하기 때문에
        // 그대로 사용하면 됨.
        return (!Double.isNaN(f) && Double.isInfinite(f));

    }


    // getFullYear() 메소드는 지정된 날짜의 연도 (1000년에서 9999년 사이의 날짜에 대한 네 자리 숫자)
    // 를 리턴합니다.
    // Date() 객체를 생성 시 매개변수를 지정하지 않으면 생성 당시를 기준으로 Date 객체가 생성됩니다.
    // Math.floor() : 주어진 숫자와 같거나 작은 정수 중에서 가장 큰 수를 반환합니다.
    // 매개변수 birthday는 8자리 숫자로 입력합니다.
    public static int getAge(String birthday) {

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // 원래 0~11이라 +1 해주는데 제대로 바뀐듯
        int date = calendar.get(Calendar.DATE);

        // 정상적으로 나이 출력 확인 완료한 식
        int today = year * 10000 + month * 100 + 100 + date;

        return ((today - Integer.parseInt(birthday))/10000);

    }


    public static int getAgeGroupKey(String birthday) {

        if(!isNumeric(birthday)) {
            return 0;
        }

        int age = getAge(birthday);

        if(age < 20) {
            return 2;
        } else if(age < 30) {
            return 3;
        } else if(age < 40) {
            return 4;
        } else if(age < 50) {
            return 5;
        } else if(age < 60) {
            return 6;
        } else if(age < 70) {
            return 7;
        } else if(age < 80) {
            return 8;
        } else {
            return 9;
        }

    }


    // Cookie 있는 부분 보류
    public static void setR(String cname, String val, Long expire) {

        String c = Util.getCookie(cname);

        if(c != null) {

            String[] cs = c.split(".");


            if(cs.length > 10) {

                // sc.js에서 shift()함수로 cs 배열의 첫번째 요소를 제거하는데 Java로 이를 구현하기
                // 위해서 ArrayList를 사용한다.
                List<String> list = new ArrayList<>(Arrays.asList(cs));
                list.remove(0);
                cs = list.toArray(new String[list.size()]);

            }

            // sc.js에서 push()함수로 배열의 마지막 요소에 val을 추가한다. 이를 구현하기 위해서
            // ArrayList를 사용한다.
            List<String> list = new ArrayList<>(Arrays.asList(cs));
            list.add(val);
            cs = list.toArray(new String[list.size()]);
            // c = String.join(".",list); join함수 쓰려면 API레벨26 이상이어야 가능.

            // join함수 대신 반복문으로 구현
            for (int i = 0; i < cs.length; i++) {

                c += cs[i] + ".";

            }

            // c에서 마지막 "."을 삭제해줘야한다.
            StringBuffer c1 = new StringBuffer(c);
            c1.deleteCharAt(c.length()-1);
            c = c1.toString(); // 문자열의 마지막 문자를 삭제한 후 c로 반환

        } else {

            c = val;

        }

        Util.setCookie(cname,c,expire);

    }


    // localStorage : Cookie와 비슷한 개념, 용량이 크고 보존기간이 반영구적. 데이터를 브라우저에
    // 저장. 마찬가지로 브라우저 개념이라 일단 넘김.
    // 읽기 전용 localStorage속성은 사용자 로컬의 Storage객체에 접근하게 해줍니다.
    // localStorage는 sessionStorage와 비슷합니다. 유일한 차이점은 localStorage에 저장된 데이터는
    // 만료 기간이 없지만, sessionStorage에 저장된 데이터는 세션이 끝나면(브라우저가 종료되면)
    // 지워진다는 것입니다. 모든 key와 value는 항상 string으로 저장됩니다.
    // (주의하세요, object와 integer 들은 string으로 자동 변환됩니다.)
    public void setViewItem() {

    }


    // localStorage이용하는 메소드. 넘김.
    public void getLs() {

    }


    // 넘김
    public void closeWindow() {

    }


    // 넘김.
    public void closest() {

    }

}
