package com.example.localizertest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Mirash
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context base) {
        Log.d(LocaleHelper.TAG, getClass().getSimpleName() + ": attachBaseContext");
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserLanguage lang = LocaleHelper.getCurrentLanguage(this);

        RadioGroup radioGroup = findViewById(R.id.radio_group);
        radioGroup.post(new Runnable() {
            @Override
            public void run() {
                RadioButton button = (RadioButton) radioGroup.getChildAt(lang.ordinal());
                button.setChecked(true);
            }
        });


        findViewById(R.id.button_eng).setOnClickListener(v -> {
            LocaleHelper.checkUpdateLanguageId(MainActivity.this, UserLanguage.ENGLISH, result -> {
                if (result) {
                    recreate();
                }
            });
        });
        findViewById(R.id.button_french).setOnClickListener(v -> {
            LocaleHelper.checkUpdateLanguageId(MainActivity.this, UserLanguage.FRENCH, result -> {
                if (result) {
                    recreate();
                }
            });
        });
        findViewById(R.id.button_rus).setOnClickListener(v -> {
            LocaleHelper.checkUpdateLanguageId(MainActivity.this, UserLanguage.RUS, result -> {
                if (result) {
                    recreate();
                }
            });
        });
        findViewById(R.id.button_ua).setOnClickListener(v -> {
            LocaleHelper.checkUpdateLanguageId(MainActivity.this, UserLanguage.UKRAINE, result -> {
                if (result) {
                    recreate();
                }
            });
        });
    }
}
