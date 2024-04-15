package com.example.traveltolive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class List_Accommodation extends AppCompatActivity implements View.OnClickListener{
    public CardView card1, card2, card3,card4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_accommodation);
        card1 = (CardView) findViewById(R.id.c1);
        card2 = (CardView) findViewById(R.id.c2);
        card3 = (CardView) findViewById(R.id.c3);
        card4 = (CardView) findViewById(R.id.c3);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent i;
        int id = v.getId();
        if (id == R.id.c1) {
            i = new Intent(this, Accommodation1.class);
            startActivity(i);

        }
        if (id == R.id.c2){
            i = new Intent(this,Shopping2.class);
            startActivity(i);

        }
        if (id == R.id.c3){
            i = new Intent(this,Shopping1.class);
            startActivity(i);

        }
        if (id == R.id.c4){
            i = new Intent(this,Shopping1.class);
            startActivity(i);

        }

    }
}