package com.example.tkfinalproject.Utility;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;

import java.util.Locale;

/**
 * The LocaleHelper class provides utility methods for setting the locale of the application.
 * This helps in changing the language dynamically within the app.
 */
public class LocaleHelper {

    /**
     * Sets the locale for the application context.
     *
     * @param context The application context.
     * @param languageCode The language code to set (e.g., "en" for English, "he" for Hebrew).
     */
    public static void setLocale(Context context, String languageCode) {
        // Create a new Locale object with the provided language code
        Locale locale = new Locale(languageCode);
        // Set the default locale to the newly created locale
        Locale.setDefault(locale);

        // Get the resources and configuration from the context
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

        // Set the locale in the configuration
        configuration.setLocale(locale);

        // Create a LocaleList with the new locale and set it as default
        LocaleList localeList = new LocaleList(locale);
        LocaleList.setDefault(localeList);
        configuration.setLocales(localeList);

        // Update the context configuration with the new locale settings
        context.createConfigurationContext(configuration);
    }
}
