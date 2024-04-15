package com.example.traveltolive;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Find the CardView by its ID
        CardView cardView = findViewById(R.id.cardview);

        // Set OnClickListener to the CardView
        cardView.setOnClickListener(view -> {
            // Intent to navigate to another activity
            Intent intent = new Intent(Settings.this, UserManualActivity.class);
            startActivity(intent);
        });
    }
}
