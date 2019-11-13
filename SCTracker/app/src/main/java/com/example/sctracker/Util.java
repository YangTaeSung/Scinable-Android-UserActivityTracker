package com.example.sctracker;

import android.content.Intent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/*

    replace의 대체되어야 하는 문자열 표현들 ex) \s+ 는 공백, \d+ 는 숫자
    예와 같은 의미들이 있을 것 같은데 모르기 떄문에 일단은 문자 그대로 표현해놓음

 */
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

    public String encodeURI(String val) {
        // sc.js에서는 encodeURIComponent를 사용하는데 java의 URLEncoder와 미묘한 차이 발생
        // encodeURIComponent()함수에서 encode()함수의 수정을 통해 javascript의 encodeURIComponent와 같은 동작 실행
        return encodeURIComponent(val);
    }

    public static String encodeURIComponent(String val) {
        String result = null;

        try
        {
            result = URLEncoder.encode(val, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e)
        {
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
        else
        {
            return element.clientWidth;
        }
    }

    public String trim(String value) {
        String result = null;
        try
        {
            result = URLEncoder.encode(value, "UTF-8")
                    .replace(" ", "");
            // 공백 제거라서 임의로 코드 작성, 시험해볼 것
            // 역슬래시 하나 더해줘서 escape 문자 처리
            // 해당 문자열 앞에 (?i)를 사용하면 대소문자 구분 안함
        }
        // This exception should never occur.
        catch (UnsupportedEncodingException e)
        {
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

    public String adjustCss(String val, String suggest) {
        String val1 = null;
        String val2 = null;
        try
        {
            val1 = URLEncoder.encode(val, "UTF-8")
                    .replace("(?i)<B>", ConfigSuggest.FontStart);
            val2 = URLEncoder.encode(val1, "UTF-8")
                    .replace("(?i)<\\/B>", ConfigSuggest.FontEnd);
            // 역슬래시 하나 더해줘서 escape 문자 처리
            // 해당 문자열 앞에 (?i)를 사용하면 대소문자 구분 안함
            // 위에 있는 Config는 suggest에 있는거. 아직 구현 안함.
        }
        // This exception should never occur.
        catch (UnsupportedEncodingException e)
        {
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

    public static String getVid() {
        if(Scinable.offline == "off") {
            return Scinable.offline; // 모호한 부분. 임의로 지정
        }

        if(Scinable.vid != null) {
            return Scinable.vid;
        } else {
            if(Scinable.cookieEnabled) {
                return getUid();

                // 여기서부터 다시 시작
            }
        }
    }

    public static String getUid() {

    }

}
