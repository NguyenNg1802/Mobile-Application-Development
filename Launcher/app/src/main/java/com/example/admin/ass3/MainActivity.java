package com.example.admin.ass3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list_layout);
        final ArrayList<AppData> appDataArr = new ArrayList<>();

        GridView gridView = findViewById(R.id.listView);
        CustomListAdapter adapter=new CustomListAdapter(this, appDataArr);
        gridView.setAdapter(adapter);

        //Get AppDatas and insert into GridView
        List<PackageInfo> packageInfos=getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo:packageInfos)
        {
            PackageManager packageManager = getPackageManager();
            String appName=packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
            String packageName = packageInfo.packageName;
            String versionName = packageInfo.versionName;
            //Check if app launchable
            if (packageManager.getLaunchIntentForPackage(packageName) != null) {
                //Get application info
                try {
                    Drawable icon = getPackageManager().getApplicationIcon(packageName);
                    AppData appData = new AppData(packageName,appName,icon, versionName);
                    adapter.add(appData);

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        //Set onItemClickListener
        gridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String packageName = appDataArr.get(position).getPackageName();
                        Intent i = getPackageManager().getLaunchIntentForPackage(packageName);
                        startActivity(i);
                    }
                });
    }
}
