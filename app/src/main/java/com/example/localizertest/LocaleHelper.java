package com.example.localizertest;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


/**
 * @author Mirash
 */
public class LocaleHelper {
    public static final String TAG = "AppLang";
    private static final String SELECTED_LANGUAGE = "selected_language";

    public static void updateLocale(Context context, UserLanguage language) {
        Log.d(TAG, "updateLocale: " + language.getLanguageId());
        LocaleHelper.setLocale(LocalApp.getInstance(), language);
        LocaleHelper.persistLanguageId(context, language.getLanguageId());
    }

    public static Context onAttach(Context context) {
        String languageId = getPersistedLanguageId(context);
        Log.d(TAG, "onAttach: " + languageId);
        UserLanguage userLanguage;
        if (languageId == null) {
            userLanguage = getUserLanguageFromPhoneLocale();
        } else {
            userLanguage = UserLanguage.getLanguageByLanguageId(languageId);
        }
        return setLocale(context, userLanguage);
    }

    private static Context setLocale(Context context, UserLanguage language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }
        return updateResourcesLegacy(context, language);
    }

    @SuppressLint("ApplySharedPref")
    private static void persistLanguageId(Context context, String languageId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(SELECTED_LANGUAGE, languageId).commit();
    }

    @Nullable
    private static String getPersistedLanguageId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, null);
    }

    public static String getCurrentLanguageId(Context context) {
        String languageId = getPersistedLanguageId(context);
        if (languageId == null) {
            Log.e(TAG, "getCurrentLanguageId is null: we should never be here");
            return getUserLanguageFromPhoneLocale().getLanguageId();
        }
        return languageId;
    }

    @NonNull
    public static UserLanguage getCurrentLanguage(Context context) {
        String languageId = getPersistedLanguageId(context);
        if (languageId == null) {
            Log.e(TAG, "getCurrentLanguage is null: we should never be here");
            return getUserLanguageFromPhoneLocale();
        }
        return UserLanguage.getLanguageByLanguageId(languageId);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, UserLanguage language) {
        Log.d(TAG, "setLocale updateResources: " + language);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = language.getLocale(configuration.getLocales().get(0));
        Locale.setDefault(locale);
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, UserLanguage language) {
        Log.d(TAG, "setLocale updateResourcesLegacy: " + language);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = language.getLocale(configuration.locale);
        configuration.setLocale(locale);
        configuration.locale = locale;
        configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    public static String getSupportedLocalesStr() {
        UserLanguage[] userLanguages = UserLanguage.values();
        List<String> locales = new ArrayList<>(userLanguages.length);
        for (UserLanguage language : userLanguages) {
            locales.add(language.getLanguageCode());
        }
        Collections.sort(locales);
        return TextUtils.join(",", locales);
    }

    @NonNull
    public static Locale getPhoneLocale() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = Resources.getSystem().getConfiguration().getLocales().get(0);
        } else {
            locale = Resources.getSystem().getConfiguration().locale;
        }
        return locale == null ? Locale.ENGLISH : locale;
    }

    @NonNull
    private static UserLanguage getUserLanguageFromPhoneLocale() {
        String systemLocale = getPhoneLocale().getLanguage();
        UserLanguage language = UserLanguage.getLanguageByLocale(systemLocale);
        Log.d(TAG, "getUserLanguageFromPhoneLocale: " + systemLocale + " -> " + language.getLanguageId());
        return language;
    }

    public static String getDisplayLanguage(@NonNull Locale locale) {
        return capitalizeFirstLetter(locale.getDisplayName(locale));
    }

    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static void checkUpdateLanguageId(@NonNull Context context, @NonNull UserLanguage language, ResultCallback resultCallback) {
        String currentLanguageId = LocaleHelper.getCurrentLanguageId(context);
        String languageId = language.getLanguageId();
        Log.d(TAG, "check checkUpdateLanguageId: " + currentLanguageId + " -> " + languageId);
        if (languageId != null && !languageId.equals(currentLanguageId)) {
            new DialogBuilder(context)
                    .setMessage(context.getString(R.string.change_lang) + "\n" + currentLanguageId + " -> " + languageId)
                    .setStartButtonText(context.getString(R.string.cancel))
                    .setEndButtonText(context.getString(R.string.ok))
                    .setCancelable(false)
                    .setStartButtonOnClickListener(v -> {
                        if (resultCallback != null) {
                            resultCallback.onResult(false);
                        }
                    })
                    .setEndButtonOnClickListener(v -> {
                        LocaleHelper.updateLocale(context, language);
                        if (resultCallback != null) {
                            resultCallback.onResult(true);
                        }
                    }).show();
        }
    }
}