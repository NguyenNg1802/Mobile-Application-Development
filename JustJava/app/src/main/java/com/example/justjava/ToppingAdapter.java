package com.example.justjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ToppingAdapter extends ArrayAdapter<Topping> {
    Context context;
    List<Topping> list = new ArrayList<>();


    public ToppingAdapter(@NonNull Context context, int resource, List<Topping> list) {
        super(context, resource, list);
        this.context = context;
        this.list.addAll(list);
    }

    public static class ToppingHolder {
        private CheckBox checkBox;
        private TextView textView;
        private TextView toppingPrice;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ToppingHolder holder = new ToppingHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.topping_row,null);

            holder.checkBox = convertView.findViewById(R.id.checkbox);
            holder.textView = convertView.findViewById(R.id.topping_name);
            holder.toppingPrice = convertView.findViewById(R.id.topping_price);

            holder.checkBox.setOnCheckedChangeListener((MainActivity)context);

            convertView.setTag(holder);
        }

        else {
            holder = (ToppingHolder) convertView.getTag();
        }

        Topping topping = list.get(position);
        holder.toppingPrice.setText("$"+topping.getPrice()+"/each");
        holder.textView.setText(topping.getTopping());
        holder.checkBox.setTag(list);

        return convertView;
    }
}
