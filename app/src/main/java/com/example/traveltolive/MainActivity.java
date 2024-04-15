package com.example.traveltolive;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declare all components
        btnN = findViewById(R.id.btnExplore);

        // Function for button Next
        btnN.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(i);
        });
    }
}
