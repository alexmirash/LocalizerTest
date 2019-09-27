package com.example.localizertest;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Mirash
 */
public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroup;

    @Override
    protected void attachBaseContext(Context base) {
        Log.d(LocaleHelper.TAG, getClass().getSimpleName() + ": attachBaseContext");
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
            getResources().getConfiguration().setTo(overrideConfiguration);
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserLanguage lang = LocaleHelper.getCurrentLanguage(this);

        radioGroup = findViewById(R.id.radio_group);
        radioGroup.post(() -> {
            RadioButton button = (RadioButton) radioGroup.getChildAt(lang.ordinal());
            button.setChecked(true);
        });
        applyLangButtonClickListener(UserLanguage.ENGLISH);
        applyLangButtonClickListener(UserLanguage.SPAIN);
        applyLangButtonClickListener(UserLanguage.FRENCH);
        applyLangButtonClickListener(UserLanguage.HEBREW);
        applyLangButtonClickListener(UserLanguage.CHINESE_SIMPLIFIED);
        applyLangButtonClickListener(UserLanguage.CHINESE_TRADITIONAL);
    }

    private void applyLangButtonClickListener(@NonNull UserLanguage language) {
        RadioButton button = new RadioButton(this);
        radioGroup.addView(button);
        button.setText(language.getName());
        button.setOnClickListener(v ->
                LocaleHelper.checkUpdateLanguageId(MainActivity.this, language, result -> {
                    if (result) {
                        recreate();
                    }
                }));
    }
}
