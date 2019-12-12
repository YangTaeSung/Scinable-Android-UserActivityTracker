package com.example.sctracker2;

import java.util.ArrayList;

// 싱글톤 패턴
public class Trans {

    private static volatile Trans trans = null;

    public static Trans getInstance() {

        if(trans == null) {

            synchronized (Trans.class) {

                if(trans == null) {

                    trans = new Trans();

                }

            }

        }

        return trans;

    }


    private String req = null;
    private String type = "C";
    private ArrayList<String> order = new ArrayList<>();
    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<String> member = new ArrayList<>();
    private ArrayList<String> claim = new ArrayList<>();

    public String gettype() {

        return type;

    }

    public ArrayList<String> getorder() {

        return order;

    }

    public ArrayList<String> getitems() {

        return items;

    }

    public ArrayList<String> getmember() {

        return member;

    }

    public ArrayList<String> getclaim() {

        return claim;

    }


    public void settype(String type) {

        this.type = type;

    }

    public void setorder(ArrayList<String> order) {

        this.order = order;

    }

    public void setitems(ArrayList<String> items) {

        this.items = items;

    }

    public void setmember(ArrayList<String> member) {

        this.member = member;

    }

    public void setclaim(ArrayList<String> claim) {

        this.claim = claim;

    }


    public void addorder(String item) {

        order.add(item);

    }

    public void additems(String item) {

        items.add(item);

    }

    public void addmember(String item) {

        member.add(item);

    }

    public void addclaim(String item) {

        claim.add(item);

    }

    public void clearorder() {

        order.clear();

    }

    public void clearitems() {

        items.clear();

    }

    public void clearmember() {

        member.clear();

    }

    public void clearclaim() {

        claim.clear();

    }

}
