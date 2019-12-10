package com.example.sctracker2;

// 싱글톤 패턴
public class ScinableConfig {

    private static volatile ScinableConfig scinableConfig = null;

    public static ScinableConfig getInstance() {

        if(scinableConfig == null) {

            synchronized (ScinableConfig.class) {

                if(scinableConfig == null) {

                    scinableConfig = new ScinableConfig();

                }

            }

        }

        return scinableConfig;

    }

    Scinable scinable = Scinable.getInstance();

    private String language = "ja";
    private String host = scinable.get_host();

    // js파일 상에선 int지만 Tracker에서 czExpire와 같은 매개변수를 공유, 타입 같아야 함
    // long은 숫자 뒤에 L을 붙여 표현
    private long cvExpire = 1800000;
    private long cuExpire = 63072000000L; // 30min(30 * 60 * 1000)
    private long ccExpire = 63072000000L; // 2years(2 * 365 * 24 * 60 * 1000)
    private long czExpire = 63072000000L;


    public String getlanguage() {

        return language;

    }

    public String gethost() {

        return host;

    }

    public long getcvExpire() {

        return cvExpire;

    }

    public long getcuExpire() {

        return cuExpire;

    }

    public long getccExpire() {

        return ccExpire;

    }

    public long getczExpire() {

        return czExpire;

    }



    public void setlanguage(String language) {

        this.language = language;

    }

    /* Scinable클래스의 _host가 여기의 host. Scinable클래스의 host를 설정할 때에
        ScinableConfig클래스의 host도 할당되도록 설정 */
    public void sethost() {

       this.host = scinable.get_host();

    }


    public void setcvExpire(long cvExpire) {

        this.cvExpire = cvExpire;

    }

    public void setcuExpire(long cuExpire) {

        this.cuExpire = cuExpire;

    }

    public void setccExpire(long ccExpire) {

        this.ccExpire = ccExpire;

    }

    public void setczExpire(long czExpire) {

        this.czExpire = czExpire;

    }

}
