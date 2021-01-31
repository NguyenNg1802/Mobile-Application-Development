package com.example.justjava;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    TextView quantity_text_view;
    TextView price_text_view;
    Button order;
    Button increase_button, decrease_button;
    EditText input_view;
    int current_quant = 0;


    ListView listView;
    HashMap<String, Integer> toppings = new HashMap<>();
    List<Topping> list = new ArrayList<>();
    ToppingAdapter toppingAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_view = (EditText)findViewById(R.id.input_view);
        quantity_text_view = (TextView)findViewById(R.id.quantity_text_view);
        increase_button = (Button)findViewById(R.id.increase_button);
        decrease_button = (Button)findViewById(R.id.decrease_button);
        price_text_view = (TextView)findViewById(R.id.price_text_view);
        order = (Button)findViewById(R.id.order_button);

        //Start create topping list
        toppings.put("Cream", 10);
        toppings.put("Chocola", 20);
        toppings.put("Ginger", 30);

        listView = (ListView)findViewById(R.id.topping_list);
        list = new ArrayList<>();
        for (Map.Entry<String, Integer> e: toppings.entrySet()) {
            list.add(new Topping(e.getKey(), e.getValue()));
        }

        toppingAdapter = new ToppingAdapter(MainActivity.this, R.layout.topping_row, list);
        listView.setAdapter(toppingAdapter);
        //End create topping list


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = input_view.getText().toString();
                createOrderSummary(name, current_quant);
            }
        });

        increase_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                current_quant++;
                displayQuantity(current_quant);
                displayPrice(current_quant);
            }
        });

        decrease_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (current_quant == 0) {
                    return;
                }
                current_quant--;
                displayQuantity(current_quant);
                displayPrice(current_quant);
            }
        });

    }

    public void displayQuantity(int quantity) {
        quantity_text_view.setText("" + quantity);
    }

    public void displayPrice(int quantity) {
        price_text_view.setText(NumberFormat.getCurrencyInstance().format(calculatePrice(quantity)));
    }

    private int calculatePrice(int quantity) {
        int price = quantity * 100;
        for (int i = 0; i < list.size(); i++) {
            Log.d("all items", String.valueOf(list.get(i).getSelected()));
            if (list.get(i).getSelected()) {
                price += list.get(i).getPrice();
            }
        }
        return price;
    }

    private void displayMessage(String s) {
        price_text_view.setText(s);
    }

    private void createOrderSummary(String name, int quantity) {
        int price = calculatePrice(current_quant);
        String priceMessage = "Name: " + name;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSelected()) {
                priceMessage += "\nTopping: " + list.get(i).getTopping();
            }
        }

        priceMessage +=
                "\nQuantity: " + quantity +
                "\nTotal: " + price +
                "\nThank you!";

        composeEmail("123@gmail.com", "Your Milk tea order", priceMessage);


    }

    public void composeEmail(String addresses, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + addresses));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity((getPackageManager())) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int position = listView.getPositionForView(compoundButton);

        if (position != ListView.INVALID_POSITION){

            Topping topping = list.get(position);
            topping.setSelected(b);
            for (int i = 0; i < list.size(); i++) {
                Log.d("all items", String.valueOf(list.get(i).getSelected()));
            }
        }
    }
}