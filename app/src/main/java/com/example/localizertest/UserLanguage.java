package com.example.localizertest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Locale;

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
    HEBREW {
        @Override
        public String getLanguageCode() {
            return "iw";
        }
    },
    CHINESE_SIMPLIFIED {
        @Override
        public String getLanguageId() {
            return "zh_CN";
        }

        @Override
        public String getLanguageCode() {
            return "zh";
        }

        @NonNull
        @Override
        public Locale getLocale(Locale currentLocale) {
            return Locale.SIMPLIFIED_CHINESE;
        }

        @Override
        public String getName() {
            return "简体中文";
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
        public Locale getLocale(Locale currentLocale) {
            return Locale.TRADITIONAL_CHINESE;
        }

        @Override
        public String getName() {
            return "繁體中文";
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

    @NonNull
    public Locale getLocale(Locale currentLocale) {
        return new Locale(getLanguageCode(), currentLocale == null ? "" : currentLocale.getCountry());
    }

    public String getLanguageId() {
        return getLanguageCode();
    }

    public abstract String getLanguageCode();

    public String getName() {
        return LocaleHelper.getDisplayLanguage(getLocale(null));
    }

    @NonNull
    @Override
    public String toString() {
        return LocaleHelper.getDisplayLanguage(getLocale(null));
    }
}

