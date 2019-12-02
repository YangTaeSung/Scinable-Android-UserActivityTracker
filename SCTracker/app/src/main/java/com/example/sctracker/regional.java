package com.example.sctracker;
/*
Scinable.regional은 따로 속성을 지정하는 코드가 없음. 배열 객체로 선언되어 있지만 우선 객체로 생성
(배열 객체로 뽑아내는건 메인함수에서 하는거니까), regional배열객체에서 속성값에 따라(en,ja) closeText가 달라짐
closeText는 정확한 의미는 모르겠으나 뒤에서 사용되는 부분이 있고, 아마 regional 객체의 속성으로
선언해야 될 것 같음. 배열의 속성인 'en','ja'로 구분하여 closeText 값을 준다는 것이
자바 구현으로 어떠한 의미인지 헷갈리는 상황, 그냥 우선은 객체 생성할 때 인자로 "en" 혹은 "ja"를 주고 생성하도록
생성자 구현, regional은 배열이 아닌가 다시 생각도 했지만 배열 안에 속성이 있는게 말이 안되는거 같아서
(배열주소는 숫자이고 속성과 매치되어야 하는데 js파일에서는 속성이 문자열인데 배열주소 역할을 하려고
하니까)일단 넘김 -> 해쉬맵으로 구현해봐
 */
public class regional extends Scinable {

    public regional(String enja) {

        String closeText = "";

        if (enja == "en") {

            closeText = "Close";

        }
        else if (enja == "ja") {

            closeText = "閉じる";

        }
        else {

            closeText = "";

        }

    }

}



