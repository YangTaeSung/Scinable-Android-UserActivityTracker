package com.example.sctracker;


// 이벤트 생성을 JavaScript처럼 함수로 만들 수 있는가
public class Event extends Scinable {

    public void add(String element, String type, String func) {

        // 넘김

    }


    public void Stop(String event) {

        String evt = event; // || window.event 모름

        if(evt.stopPropagation) {

            evt.stopPropagation();

        }
        else {

            evt.cancelBubble = true;

        }

    }

}
