package com.example.sctracer2;

// 싱글톤 패턴
public class AccessConfig {

    private static volatile AccessConfig accessConfig = null;

    public static AccessConfig getInstance() {

        if(accessConfig == null) {

            synchronized (AccessConfig.class) {

                if(accessConfig == null) {

                    accessConfig = new AccessConfig();

                }

            }

        }

        return accessConfig;

    }

    private String uri = "/access";
    private boolean cache = false;
    private String callback = "_getPromotion";


    public String geturi() {

        return uri;

    }

    public boolean getcache() {

        return getcache();

    }

    public String getcallback() {

        return callback;

    }


    public void seturi(String uri) {

        this.uri = uri;

    }

    public void setcache(boolean cache) {

        this.cache = cache;

    }

    public void setcallback(String callback) {

        this.callback = callback;

    }

}
