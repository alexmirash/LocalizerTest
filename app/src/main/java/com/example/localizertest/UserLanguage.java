package com.example.localizertest;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Mirash
 */

public enum UserLanguage {
    ENGLISH {
        @Override
        public String getLocale() {
            return "en";
        }
    },
    FRENCH {
        @Override
        public String getLocale() {
            return "fr";
        }
    },
    RUS {
        @Override
        public String getLocale() {
            return "ru";
        }
    },
    UKRAINE {
        @Override
        public String getLocale() {
            return "ua";
        }
    },
    SPAIN {
        @Override
        public String getLocale() {
            return "es";
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
            if (locale.equals(language.getLocale())) {
                return language;
            }
        }
        return ENGLISH;
    }

    public static UserLanguage random() {
        return values()[new Random().nextInt(values().length)];
    }

    public abstract String getLocale();


    public String getLanguageId() {
        return getLocale();
    }

    @Override
    public String toString() {
        return LocaleHelper.getDisplayLanguage(getLocale());
    }
}
