package com.example.traveltolive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Bangkok extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangkok);

        // Initialize bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Set bottom navigation item click listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.currency) {
                    startActivity(new Intent(getApplicationContext(), Currency.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.translate) {
                    startActivity(new Intent(getApplicationContext(), Translator.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                 else if (id == R.id.settings) {
                    startActivity(new Intent(getApplicationContext(), Settings.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

        // Initialize and set click listener for the CardView
        CardView card1 = findViewById(R.id.card1);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Shopping1 activity
                Intent intent = new Intent(Bangkok.this, Shopping1.class);
                startActivity(intent);
            }
        });
    }
}