package com.example.admin.ass3;

import android.graphics.drawable.Drawable;

public class AppData {
    private String packageName;
    private String appName;
    private Drawable image;
    private String versionName;
    public AppData(String packageName,String appName, Drawable image, String versionName) {
        this.packageName = packageName;
        this.appName = appName;
        this.image = image;
        this.versionName = versionName;
    }

    public String getVersionName(){ return versionName;}

    public String getPackageName(){ return packageName;}

    public String getAppName() {
        return appName;
    }

    public Drawable getImage() {
        return image;
    }
}


