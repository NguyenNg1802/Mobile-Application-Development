package com.example.courtcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView score_view_a;
    Button three_point_button_a, two_point_button_a, free_thow_button_a;

    TextView score_view_b;
    Button three_point_button_b, two_point_button_b, free_thow_button_b;

    Button reset_button;

    int score_a = 0;
    int score_b = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//      Team A
        score_view_a = (TextView)findViewById(R.id.score_view_a);
        three_point_button_a = (Button)findViewById(R.id.three_point_button_a);
        two_point_button_a = (Button)findViewById(R.id.two_point_button_a);
        free_thow_button_a = (Button)findViewById(R.id.free_throw_button_a);

        three_point_button_a.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                score_a = Integer.parseInt(score_view_a.getText().toString());
                score_a += 3;
                score_view_a.setText("" + score_a);
            }
        });

        two_point_button_a.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                score_a = Integer.parseInt(score_view_a.getText().toString());
                score_a += 2;
                score_view_a.setText("" + score_a);
            }
        });

        free_thow_button_a.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                score_a = Integer.parseInt(score_view_a.getText().toString());
                score_a += 1;
                score_view_a.setText("" + score_a);
            }
        });


//      Team B

        score_view_b = (TextView)findViewById(R.id.score_view_b);
        three_point_button_b = (Button)findViewById(R.id.three_point_button_b);
        two_point_button_b = (Button)findViewById(R.id.two_point_button_b);
        free_thow_button_b = (Button)findViewById(R.id.free_throw_button_b);

        three_point_button_b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                score_b = Integer.parseInt(score_view_b.getText().toString());
                score_b += 3;
                score_view_b.setText("" + score_b);
            }
        });

        two_point_button_b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                score_b = Integer.parseInt(score_view_b.getText().toString());
                score_b += 2;
                score_view_b.setText("" + score_b);
            }
        });

        free_thow_button_b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                score_b = Integer.parseInt(score_view_b.getText().toString());
                score_b += 1;
                score_view_b.setText("" + score_b);
            }
        });

//      Reset
        reset_button = (Button)findViewById(R.id.reset_button);
        reset_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                score_a = 0;
                score_b = 0;
                score_view_a.setText("" + score_a);
                score_view_b.setText("" + score_b);
            }
        });

    }
}