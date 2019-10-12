package com.mehedi.user.ubarparkingapps;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by User on 5/28/2019.
 */

public class Utils {
    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public String GetAndroidId(Context context){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public String GetAndroidDeviceModel(){
        return Build.MODEL;
    }

    public String GetAndroidDeviceManufacturer(){
        return Build.MANUFACTURER;
    }

    public String GetAndroidDevice(){
        return Build.DEVICE;
    }
    public String GetAndroidVersion(){
        return Build.VERSION.RELEASE;
    }
}
