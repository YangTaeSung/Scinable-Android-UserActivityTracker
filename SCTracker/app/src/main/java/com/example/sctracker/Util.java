package com.example.sctracker;

import android.content.Intent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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
        // 현재 액티비티에서 매개변수로 받은 액티비티로 전환

        Intent intent = new Intent(PresentActivity.this, NextActivity.class);
        startActivity(intent);

    }


    // URL 인코딩은, URL 스트링에 있는 텍스트를, 모든브라우저에서 똑바로 전송하기 위해 존재한다.
    public String encodeURI(String val) {

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


    // 브라우저의 버전을 리턴하는 함수. Android sdk의 버전을 출력해야 하는지
    public int msie() {

        return 0;

    }


    // createXMLHttp : XMLHttpRequest() 객체를 생성.
    // 정상적으로 생성되면 객체를 리턴. 아래의 함수에서 객체가 존재하면 open, send함
    public void readScriptFile(String src) {

        /*
        var xh = Scinable.Ajax.createXMLHttp();
                    if (!xh) {
                        console.log("couldn't create XMLHttpRequest.");
                        return;
                    }
                    xh.onreadystatechange = function() {
                        if (xh.readyState == 4) {
                            if (xh.status != 200) {
                                console.log("Error : status is not 200." + xh.status)
                                return;
                            }
                            (0, eval)(xh.responseText);
                        }
                    };
                    xh.open("GET", src, true);
                    xh.send();
         */

    }


    // HTMLElement.offsetWidth 읽기 전용 속성은 정수로 요소의 레이아웃 폭을 돌려줍니다.
    // var intElemOffsetWidth = element.offsetWidth;
    // element가 무엇을 뜻하는지
    public int elementWidth() {

        if(msie() < 10) {

            return element.offsetWidth;

        }
        else {

            return element.clientWidth;

        }

    }


    public String trim(String value) {

        String result = null;

        try {

            result = URLEncoder.encode(value, "UTF-8")
                    .replace(" ", "");
            // 공백 제거라서 임의로 코드 작성, 시험해볼 것
            // 역슬래시 하나 더해줘서 escape 문자 처리
            // 해당 문자열 앞에 (?i)를 사용하면 대소문자 구분 안함

        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e) {

            result = value;

        }

        return result;

    }


    public String defaultString() {
        return "";
    }


    public String defaultString(String value) {
        return value;
    }


    /*
    replace의 대체되어야 하는 문자열 표현들 ex) \s+ 는 공백, \d+ 는 숫자
    예와 같은 의미들이 있을 것 같은데 모르기 떄문에 일단은 문자 그대로 표현해놓음
    */
    public String adjustCss(String val, String suggest) {

        String val1 = null;
        String val2 = null;

        try {

            val1 = URLEncoder.encode(val, "UTF-8")
                    .replace("(?i)<B>", ConfigSuggest.FontStart);
            val2 = URLEncoder.encode(val1, "UTF-8")
                    .replace("(?i)<\\/B>", ConfigSuggest.FontEnd);
            // 역슬래시 하나 더해줘서 escape 문자 처리
            // 해당 문자열 앞에 (?i)를 사용하면 대소문자 구분 안함
            // 위에 있는 Config는 suggest에 있는거. 아직 구현 안함.

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


    public String today() {

        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
        Date time = new Date();
        String day = format1.format(time);
        return day;

    }


    // 쿠키값을 가져와서 뭘 하려고 할텐데 안드로이드에는 쿠키가 없으니
    // 이 메소드에서 뭘 하는지 알아보고 쿠키 대신에 사용할 껀덕지 찾기
    public String getCookies() {

        return "";

    }


    // 쿠키를 설정해놓고 해당 페이지에서 하는 액션들을 기록하는데 사용할 듯
    public void setCookies() {

    }


    // Math.round : 입력값을 반올림 한 수와 가장 가까운 정수값을 출력합니다.
    // Math.random : 0 이상 1 미만의 부동소숫점 의사 난수를 출력합니다.
    public long createUUID() {

        return Math.round(2147283647 * Math.random());

    }


    public static String getVid() {

        if(Scinable.offline == "off") {

            return ""; // 모호한 부분. js에서는 return 0; 형태. 임의로 지정

        }

        if(Scinable.vid != null) {

            return Scinable.vid;

        }
        else {

            if(Scinable.cookieEnabled) { // 쿠키 사용부분. 수정 필요

                return getUid();

            }

            return getUid();

        }

    }


    public static String getUid() {

        return "";

    }


    public String getCU() {

        return "";

    }


    public String setCU() {

        return "";

    }


    public String getCK() {

        return "";

    } // getVid()부터 여기까지 다 이어진 부분. 보류.


    // parseFloat() : 문자열을 실수로 변환
    // isNaN() : 매개변수가 숫자인지 검사하는 함수
    // isFinite() : 변수가 유한한지 검사하는 함수
    public boolean isNumeric(String val) {

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
    public int getAge(String birthday) {

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);

        // 정상적으로 나이 출력 확인 완료한 식
        int today = year * 10000 + month * 100 + 100 + date;

        return ((today - Integer.parseInt(birthday))/10000);

    }


    public int getAgeGroupKey(String birthday) {

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
    public void setR() {

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
