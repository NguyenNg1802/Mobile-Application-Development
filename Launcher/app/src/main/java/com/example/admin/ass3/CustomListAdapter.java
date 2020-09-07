package com.example.admin.ass3;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.ass3.R;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<AppData> {
    private Activity context;
    private ArrayList<AppData> appDataArrs;

    public CustomListAdapter(Activity context, ArrayList<AppData> appDataArrs) {
        super(context, R.layout.list_item, appDataArrs);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.appDataArrs = appDataArrs;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_item, null,true);

        TextView txtTitle = rowView.findViewById(R.id.app_name);
        ImageView imageView = rowView.findViewById(R.id.app_icon);
        TextView txtVersion = rowView.findViewById(R.id.app_version);
        txtTitle.setText(appDataArrs.get(position).getAppName());
        txtVersion.setText(appDataArrs.get(position).getVersionName());
        imageView.setImageDrawable(appDataArrs.get(position).getImage());
        return rowView;
    };
}



