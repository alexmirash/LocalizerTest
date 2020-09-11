package com.example.localizertest;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

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
            Configuration configuration = getResources().getConfiguration();
            configuration.setTo(overrideConfiguration);
            Locale locale = LocaleHelper.getCurrentLanguage(this).getLocale(null);
            configuration.setLayoutDirection(locale);
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Locale currentLocale = configuration.getLocales().get(0);
                if (currentLocale == null) {
                    getResources().getConfiguration().setLocales(new LocaleList(locale));
                }
            }
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setLayoutDirection(LocaleHelper.isConfigLTR(getResources())
                ? View.LAYOUT_DIRECTION_LTR : View.LAYOUT_DIRECTION_RTL);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        radioGroup = findViewById(R.id.radio_group);
        radioGroup.post(this::selectActualButton);
        for (UserLanguage language : UserLanguage.values()) {
            applyLangButtonClickListener(language);
        }
    }

    private void selectActualButton() {
        UserLanguage lang = LocaleHelper.getCurrentLanguage(this);
        RadioButton button = (RadioButton) radioGroup.getChildAt(lang.ordinal());
        button.setChecked(true);
    }

    private void applyLangButtonClickListener(@NonNull UserLanguage language) {
        RadioButton button = new RadioButton(this);
        radioGroup.addView(button);
        button.setText(language.getName());
        button.setOnClickListener(v ->
                LocaleHelper.checkUpdateLanguageId(MainActivity.this, language, result -> {
                    if (result) {
                        recreate();
/*                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);*/
                    } else {
                        selectActualButton();
                    }
                }));
    }
}
