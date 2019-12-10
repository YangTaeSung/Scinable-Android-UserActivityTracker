package com.example.sctracker2;

// 싱글톤 패턴
public class Access {

    private static volatile Access access = null;

    public static Access getInstance() {

        if(access == null) {

            synchronized (Access.class) {

                if(access == null) {

                    access = new Access();

                }

            }

        }

        return access;

    }

    private String req = null;
    private String url = null; // 사용자의 현재 페이지
    private String title = null;
    private String id = "";   // id
    private String groupid = ""; // group id
    private String type = "";
    private int cgk; // conversion goal key
    private int cgv; // conversion goal value
    private String cgc = ""; // conversion goal custom


    public String getreq() {

        return req;

    }

    public String geturl() {

        return url;

    }

    public String gettitle() {

        return title;

    }

    public String getgroupid() {

        return getgroupid();

    }

    public String getid() {

        return id;

    }

    public String gettype() {

        return type;

    }

    public int getcgk() {

        return cgk;

    }

    public int getcgv() {

        return cgv;

    }

    public String getcgc() {

        return cgc;

    }



    public void setreq(String req) {

        this.req = req;

    }

    public void seturl(String url) {

        this.url = url;

    }

    public void settitle(String title) {

        this.title = title;

    }

    public void setid(String id) {

        this.id = id;

    }

    public void setgroupid(String groupid) {

        this.groupid = groupid;

    }

    public void settype(String type) {

        this.type = type;

    }

    public void setcgk(int cgk) {

        this.cgk = cgk;

    }

    public void setcgv(int cgv) {

        this.cgv = cgv;

    }

    public void setcgc(String cgc) {

        this.cgc = cgc;

    }


}

