package com.example.sctracker;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import java.util.ArrayList;
public class Scinable {

    boolean cookieEnabled = true; //
    private static String scinableObject = "_scq"; //
    private static ArrayList<String> _scq = new ArrayList<>(); //

    private static String accountId = "";
    private static String language = null;
    private static boolean debug = false;
    private static String uid = null;
    private static String vid = null;
    private static String ck = null;
    private static String campaign = "";
    private static String channel = "";
    private static String preVisitDate = null;
    private static int frequency = 0;
    private static int newVisit = 0;
    private static String offline = "";
    private static int pageView = 1;
    // Define customField as Object
    // private static ArrayList<String> customField = new ArrayList<>();

    /* This variable for Request function */
    String fullUrl;
    String headLoc;
    String scriptId;

    public void Request(String fullUrl) {
        this.fullUrl = fullUrl;
    }
 }
