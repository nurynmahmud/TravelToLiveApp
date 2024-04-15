package com.example.traveltolive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    RelativeLayout rl, rl2, rl3, rl4;
    EditText searchEditText;
    ImageView searchIcon;
    ListView suggestionListView;
    ArrayAdapter<String> suggestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize views
        searchEditText = findViewById(R.id.searchEditText);
        searchIcon = findViewById(R.id.searchIcon);
        suggestionListView = findViewById(R.id.suggestionListView);
        // Hide the suggestionListView initially
        suggestionListView.setVisibility(View.GONE);
        // Set click listener for the search icon
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle visibility of suggestionListView
                if (suggestionListView.getVisibility() == View.VISIBLE) {
                    // If already visible, hide it
                    suggestionListView.setVisibility(View.GONE);
                } else {
                    // If hidden, show it
                    suggestionListView.setVisibility(View.VISIBLE);
                }
                // Perform search or other actions as needed
                performSearch();
            }
        });


        // Initialize suggestion list and adapter
        String[] suggestions = {"Kuala Lumpur,Malaysia", "Bangkok,Thailand", "Hanoi,Vietnam"};

        // Create an ArrayAdapter with the original list of suggestions
        final ArrayAdapter<String> suggestionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, suggestions);

// Set the adapter to the ListView
        suggestionListView.setAdapter(suggestionAdapter);

// Set item click listener for the ListView
        suggestionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the clicked suggestion
                String clickedSuggestion = (String) parent.getItemAtPosition(position);

                // Perform navigation based on the clicked suggestion
                Intent intent;
                switch (clickedSuggestion) {
                    case "Kuala Lumpur,Malaysia":
                        intent = new Intent(MainActivity2.this, KualaLumpur.class);
                        startActivity(intent);
                        break;
                    case "Bangkok,Thailand":
                        intent = new Intent(MainActivity2.this, Bangkok.class);
                        startActivity(intent);
                        break;
                    case "Hanoi,Vietnam":
                        intent = new Intent(MainActivity2.this, Hanoi.class);
                        startActivity(intent);
                        break;
                    // Add more cases for other suggestions as needed
                    default:
                        // Handle default case or do nothing
                        break;
                }
            }
        });

        // Set text change listener for the searchEditText to filter suggestions
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter suggestions based on the search input
                suggestionAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

// Set focus change listener for the searchEditText to show/hide suggestionListView
        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Show suggestionListView when searchEditText gains focus
                if (hasFocus) {
                    suggestionListView.setVisibility(View.VISIBLE);
                } else {
                    // Hide suggestionListView when searchEditText loses focus
                    suggestionListView.setVisibility(View.GONE);
                }
            }
        });

        rl = findViewById(R.id.layout1);
        rl2 = findViewById(R.id.layout2);
        rl3 = findViewById(R.id.layout3);
        rl4 = findViewById(R.id.layout4);

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, List_attraction.class);
                startActivity(intent);
            }
        });

        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, List_Accommodation.class);
                startActivity(intent);
            }
        });

        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, List_shopping.class);
                startActivity(intent);
            }
        });

        rl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, LIst_eat.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.currency) {
                    startActivity(new Intent(getApplicationContext(), Currency.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                if (id == R.id.home) {
                    return true;
                }
                if (id == R.id.translate) {
                    startActivity(new Intent(getApplicationContext(), Translator.class));
                    overridePendingTransition(0, 0);
                    return true;
                }

                if (id == R.id.settings) {
                    startActivity(new Intent(getApplicationContext(), Settings.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
    }

    private void performSearch() {
        String searchText = searchEditText.getText().toString().trim();

        // Perform search logic here, e.g., show search results in a new activity
        if (!searchText.isEmpty()) {
            // Start a new activity to show search results
            // Replace YourSearchResultsActivity.class with the activity you want to start
            startActivity(new Intent(MainActivity2.this, KualaLumpur.class));
        } else {
            Toast.makeText(MainActivity2.this, "Please enter a search query", Toast.LENGTH_SHORT).show();
        }
    }
}