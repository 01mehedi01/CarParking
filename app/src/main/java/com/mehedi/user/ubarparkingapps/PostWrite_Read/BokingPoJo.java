package com.mehedi.user.ubarparkingapps.PostWrite_Read;

public class BokingPoJo {
    private String Boking;
    private String BokingdriverUserID;

    private  String date;
    private  String fast;
    private  String last;

    public BokingPoJo(String boking, String bokingdriverUserID, String date, String fast, String last) {
        Boking = boking;
        BokingdriverUserID = bokingdriverUserID;
        this.date = date;
        this.fast = fast;
        this.last = last;
    }

    public BokingPoJo() {
    }

    public String getBoking() {
        return Boking;
    }

    public void setBoking(String boking) {
        Boking = boking;
    }

    public String getBokingdriverUserID() {
        return BokingdriverUserID;
    }

    public void setBokingdriverUserID(String bokingdriverUserID) {
        BokingdriverUserID = bokingdriverUserID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFast() {
        return fast;
    }

    public void setFast(String fast) {
        this.fast = fast;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
