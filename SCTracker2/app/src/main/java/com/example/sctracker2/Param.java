package com.example.sctracker2;

// 싱글톤 패턴
public class Param {

    private static volatile Param param = null;

    public static Param getInstance() {

        if(param == null) {

            synchronized (Param.class) {

                if(param == null) {

                    param = new Param();

                }

            }

        }

        return param;

    }

    private String discard = "sci_dc";
    private String refl = "sci_refl";
    private String eciCampaign = "sci_campaign";
    private String campaign = null;
    private String channel = "sci_ch";

    public String getdiscard() {

        return discard;

    }

    public String getrefl() {

        return refl;

    }

    public String geteciCampaign() {

        return eciCampaign;

    }

    public String getcampaign() {

        return campaign;

    }

    public String getchannel() {

        return channel;

    }


    public void setdiscard(String discard) {

        this.discard = discard;

    }

    public void setrefl(String refl) {

        this.refl = refl;

    }

    public void seteciCampaign(String eciCampaign) {

        this.eciCampaign = eciCampaign;

    }

    public void setcampaign(String campaign) {

        this.campaign = campaign;

    }

    public void setchannel(String channel) {

        this.channel = channel;

    }

}
