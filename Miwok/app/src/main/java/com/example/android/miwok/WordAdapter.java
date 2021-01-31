package com.example.android.miwok;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {
    private Context context;
    private List<Word> list = new ArrayList<>();
    private int color;
    private Word word;

    public WordAdapter(@NonNull Context context, int resource, @NonNull List<Word> objects, int color) {
        super(context, resource, objects);
        this.context = context;
        this.list.addAll(objects);
        this.color = color;
    }


    private static class WordHolder {
        private TextView englishView;
        private TextView miwokView;
        private ImageView image;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WordHolder holder = new WordHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);

            holder.miwokView = (TextView) convertView.findViewById(R.id.text1);
            holder.englishView = (TextView) convertView.findViewById(R.id.text2);
            holder.image = (ImageView) convertView.findViewById(R.id.image);

            convertView.setBackgroundColor(convertView.getResources().getColor(color));

            convertView.setTag(holder);
        }

        else {
            holder = (WordHolder) convertView.getTag();
        }

        word = list.get(position);


        if (word.getImage() != 0) {
            holder.englishView.setText(word.getEnglish());
            holder.miwokView.setText(word.getMiwok());
            holder.image.setImageResource(word.getImage());
        }
        else {
            holder.englishView.setText(word.getEnglish());
            holder.miwokView.setText(word.getMiwok());
            holder.image.setVisibility(convertView.GONE);
        }


        return convertView;
    }
}
