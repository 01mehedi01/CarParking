package com.mehedi.user.ubarparkingapps.PostWrite_Read;

public class bokingPojoClass {

    private String Boking;
    private String BokingdriverUserID;

    private  String date;
    private  String fast;
    private  String last;


    public bokingPojoClass(String boking, String bokingdriverUserID, String date, String fast, String last) {
        Boking = boking;
        BokingdriverUserID = bokingdriverUserID;
        this.date = date;
        this.fast = fast;
        this.last = last;
    }

    public bokingPojoClass() {
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

}
