package com.example.localizertest;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
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
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT <= 25) {
            getResources().getConfiguration().setTo(overrideConfiguration);
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserLanguage lang = LocaleHelper.getCurrentLanguage(this);

        RadioGroup radioGroup = findViewById(R.id.radio_group);
        radioGroup.post(() -> {
            RadioButton button = (RadioButton) radioGroup.getChildAt(lang.ordinal());
            button.setChecked(true);
        });
        applyLangButtonClickListener(R.id.button_eng, UserLanguage.ENGLISH);
        applyLangButtonClickListener(R.id.button_french, UserLanguage.FRENCH);
        applyLangButtonClickListener(R.id.button_rus, UserLanguage.RUS);
        applyLangButtonClickListener(R.id.button_ua, UserLanguage.UKRAINE);
    }

    private void applyLangButtonClickListener(@IdRes int buttonId, @NonNull UserLanguage language) {
        findViewById(buttonId).setOnClickListener(v ->
                LocaleHelper.checkUpdateLanguageId(MainActivity.this, language, result -> {
                    if (result) {
                        recreate();
                    }
                }));
    }
}
