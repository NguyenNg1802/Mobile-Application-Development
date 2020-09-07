package com.example.nguyen.lab1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PC on 2/28/2019.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;

    private final Integer[] imageIDarray;

    private final String[] channelArray;
    LayoutInflater inflater;

    public CustomAdapter(Context context, String[] channelArray, Integer[] imageIDarray){
        this.context = context;
        this.channelArray = channelArray;
        this.inflater = (LayoutInflater.from(context));
        this.imageIDarray = imageIDarray;
    }


    @Override
    public int getCount() {
        return channelArray.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.list_of_channels_row_layout, null);
        TextView channelName = (TextView) view.findViewById(R.id.text_channel_name);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        channelName.setText(channelArray[i]);
        icon.setImageResource(imageIDarray[i]);
        return view;
    }
}
