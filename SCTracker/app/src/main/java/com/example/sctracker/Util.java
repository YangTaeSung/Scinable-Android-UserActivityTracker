package com.example.sctracker;

public class Util {
    public void go(String val) {
        /*
        location.href는 객체의 속성이며, loaction.replace()는 메서드(함수)로 작동된다.
        href는 페이지를 이동하는 것이기 때문에 뒤로가기 버튼을 누른경우 이전 페이지로 이동이 가능하지만,
        replace는 현재 페이지를 새로운 페이지로 덮어 씌우기 때문에 이전 페이지로 이동이 불가능하다.
        href는 일반적인 페이지 이동시 이용을 하면 되고,
        replace의 경우는 이전페이지로 접근이 필요없는경우 보안상 덮어씌우는 것도 괜찮을듯 하다.

        */
        location.href = val;
    }
    // 해석 안되서 일단 넘어감.

    public  static String getVid() {
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
