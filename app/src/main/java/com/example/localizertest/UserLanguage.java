package com.example.localizertest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Locale;
import java.util.Random;

/**
 * @author Mirash
 */

public enum UserLanguage {
    ENGLISH {
        @Override
        public String getLanguageCode() {
            return "en";
        }
    },
    FRENCH {
        @Override
        public String getLanguageCode() {
            return "fr";
        }
    },
    RUS {
        @Override
        public String getLanguageCode() {
            return "ru";
        }
    },
    UKRAINE {
        @Override
        public String getLanguageCode() {
            return "ua";
        }
    },
    CHINESE_SIMPLIFIED {
        @Override
        public String getLanguageCode() {
            return "zh";
        }

        @Override
        public String getLanguageId() {
            return "zh_CN";
        }

        @NonNull
        @Override
        public Locale getLocale(String country) {
            return Locale.SIMPLIFIED_CHINESE;
        }
    },
    CHINESE_TRADITIONAL {
        @Override
        public String getLanguageCode() {
            return "zh";
        }

        @Override
        public String getLanguageId() {
            return "zh_TW";
        }

        @NonNull
        @Override
        public Locale getLocale(String country) {
            return Locale.TRADITIONAL_CHINESE;
        }
    };

    @NonNull
    public static UserLanguage getLanguageByLanguageId(@Nullable String currentLanguageId) {
        if (currentLanguageId == null) {
            currentLanguageId = LocaleHelper.getPhoneLocale().getLanguage();
        }
        for (UserLanguage language : values()) {
            if (currentLanguageId.equals(language.getLanguageId())) {
                return language;
            }
        }
        return ENGLISH;
    }

    @NonNull
    public static UserLanguage getLanguageByLocale(String locale) {
        if (locale == null) {
            return ENGLISH;
        }
        for (UserLanguage language : values()) {
            if (locale.equals(language.getLanguageCode())) {
                return language;
            }
        }
        return ENGLISH;
    }

    public static UserLanguage random() {
        return values()[new Random().nextInt(values().length)];
    }

    public abstract String getLanguageCode();


    public String getLanguageId() {
        return getLanguageCode();
    }

    @NonNull
    public Locale getLocale(String country) {
        return new Locale(getLanguageCode(), country);
    }

    @Override
    public String toString() {
        return LocaleHelper.getDisplayLanguage(getLanguageCode());
    }
}
