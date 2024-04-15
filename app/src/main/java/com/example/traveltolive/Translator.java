package com.example.traveltolive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.util.ArrayList;
import java.util.Locale;

public class Translator extends AppCompatActivity {

    private Spinner fromSpinner, toSpinner;
    private TextInputEditText sourceEdt;
    private ImageView micTV;
    private MaterialButton translateBtn;
    private TextView translatedTV;
    String[] fromLanguages = {"From", "Pashto", "Armenian","Arabic","Azerbaijani","Bengali","Bhutanese","Malay", "English",
            "Khmer","Mandarin","Greek","Turkish","Russian","Chinese","French","Portuguese","Georgian","Azeri","Hindi","Urdu","Malayalam","Indonesian",
            "Persian","Hebrew","Japanese","Kazakh","Korean","Kyrgyz","Lao","Dhivehi","Mongolian",
            "Burmese","Nepali","Filipino","Tamil","Sinhala","Tajik","Thai","Tetum","Turkmen",
            "Uzbek","Vietnamese"};

    String[] toLanguages = {"To", "Pashto", "Armenian","Arabic","Azerbaijani","Bengali","Bhutanese","Malay", "English",
            "Khmer","Mandarin","Greek","Turkish","Russian","Chinese","French","Portuguese","Georgian","Azeri","Hindi","Urdu","Malayalam","Indonesian",
            "Persian","Hebrew","Japanese","Kazakh","Korean","Kyrgyz","Lao","Dhivehi","Mongolian",
            "Burmese","Nepali","Filipino","Tamil","Sinhala","Tajik","Thai","Tetum","Turkmen",
            "Uzbek","Vietnamese"};

    private static final int REQUEST_PERMISSION_CODE = 1;
    int languageCode,fromLanguageCode,toLanguageCode = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);

        fromSpinner = findViewById(R.id.idFromSpinner);
        toSpinner = findViewById(R.id.idToSpinner);
        sourceEdt = findViewById(R.id.idEdtSource);
        micTV = findViewById(R.id.idIVMic);
        translateBtn = findViewById(R.id.idBtnTranslate);
        translatedTV = findViewById(R.id.idTVTranslatedTV);
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromLanguageCode = getLanguageCode(fromLanguages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter fromAdapter = new ArrayAdapter(this,R.layout.spinner_item,fromLanguages);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromAdapter);

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toLanguageCode = getLanguageCode(toLanguages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

        ArrayAdapter toAdapter = new ArrayAdapter(this,R.layout.spinner_item,toLanguages);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(toAdapter);

        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translatedTV.setText("");
                if (sourceEdt.getText().toString().isEmpty()){
                    Toast.makeText(Translator.this, "Please enter your text to translate",Toast.LENGTH_SHORT).show();
                } else if (fromLanguageCode==0) {
                    Toast.makeText(Translator.this,"Please select source language",Toast.LENGTH_SHORT).show();
                } else if (toLanguageCode==0) {
                    Toast.makeText(Translator.this, "Please select the language to make translation,",Toast.LENGTH_SHORT).show();

                }else{
                    translateText(fromLanguageCode,toLanguageCode,sourceEdt.getText().toString());
                }
            }
        });
        micTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH) ;
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak to convert into text");
                try {
                    startActivityForResult(i,REQUEST_PERMISSION_CODE);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(Translator.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_PERMISSION_CODE){
            if (resultCode == RESULT_OK && data != null){
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                sourceEdt.setText(result.get(0));
            }
        }
    }

    private void translateText(int fromLanguageCode, int toLanguageCode, String source){
        translatedTV.setText("Downloading Model..");
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(fromLanguageCode)
                .setTargetLanguage(toLanguageCode)
                .build();

        FirebaseTranslator translator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().build();

        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                translatedTV.setText("Translating..");
                translator.translate(source).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        translatedTV.setText(s);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Translator.this, "Fail to translate :"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Translator.this, "Fail to download language Model"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getLanguageCode(String language){
        int languageCode = 0;
        switch (language){

            case "Arabic":
                languageCode = FirebaseTranslateLanguage.AR;
                break;

            case "Bengali":
                languageCode = FirebaseTranslateLanguage.BN;
                break;

            case "Malay":
                languageCode = FirebaseTranslateLanguage.MS;
                break;
            case "English":
                languageCode = FirebaseTranslateLanguage.EN;
                break;

            case "Greek":
                languageCode = FirebaseTranslateLanguage.EL;
                break;
            case "Turkish":
                languageCode = FirebaseTranslateLanguage.TR;
                break;
            case "Russian":
                languageCode = FirebaseTranslateLanguage.RU;
                break;
            case "Chinese":
                languageCode = FirebaseTranslateLanguage.ZH;
                break;
            case "French":
                languageCode = FirebaseTranslateLanguage.FR;
                break;
            case "Portuguese":
                languageCode = FirebaseTranslateLanguage.PT;
                break;
            case "Georgian":
                languageCode = FirebaseTranslateLanguage.KA;
                break;


            case "Hindi":
                languageCode = FirebaseTranslateLanguage.HI;
                break;
            case "Urdu":
                languageCode = FirebaseTranslateLanguage.UR;
                break;

            case "Indonesian":
                languageCode = FirebaseTranslateLanguage.ID;
                break;
            case "Persian":
                languageCode = FirebaseTranslateLanguage.FA;
                break;
            case "Hebrew":
                languageCode = FirebaseTranslateLanguage.HE;
                break;
            case "Japanese":
                languageCode = FirebaseTranslateLanguage.JA;
                break;

            case "Korean":
                languageCode = FirebaseTranslateLanguage.KO;
                break;

            case "Tamil":
                languageCode = FirebaseTranslateLanguage.TA;
                break;


            case "Thai":
                languageCode = FirebaseTranslateLanguage.TH;
                break;

            case "Vietnamese":
                languageCode = FirebaseTranslateLanguage.VI;
                break;
            default:
                languageCode =0;
        }
        return languageCode;
    }
}