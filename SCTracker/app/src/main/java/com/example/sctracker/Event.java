package com.example.sctracker;

public class Event extends Scinable {

        String add = ""; // variable 'add' is supposed that string type

        if (window.addEventListener) {
            add = GoAddEventListener(type, func, false);
        } else {
            add = GoAttachEvent(element, type, func);
        }

    public String GoAddEventListener (String type, String func,boolean tf){
        return element.addEventListner(type, func, false);
    }

    public String GoAttachEvent(String element, String type, String func) {
        return element.attachEvent("on" + type, func);
    }

    public void Stop(String event) {
        String evt = event; // Global variable?
        if(evt.stopPropagation) {
            evt.stopPropagation();
        }
        else
            evt.cancelBubble = true;
    }

}
