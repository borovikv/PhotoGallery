package com.github.borovic.photogallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by vborovic on 3/3/17.
 */

class QueryPreferences {
    private static final String PREF_SEARCH_QUERY = "searchQuery";
    private static final String PREF_LAST_RESULT_ID = "lastResultId";
    private static final String PREF_IS_ALARM_ON = "isAlarmOn";

    static String getStoredQuery(Context context) {
        return getString(context, PREF_SEARCH_QUERY);
    }

    private static String getString(Context context, String key) {
        return getDefaultSharedPreferences(context).getString(key, null);
    }

    private static SharedPreferences getDefaultSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    static void setStoredQuery(Context context, String query) {
        setString(context, PREF_SEARCH_QUERY, query);
    }

    private static void setString(Context context, String key, String value) {
        getDefaultSharedPreferences(context).edit().putString(key, value).apply();
    }

    static String getLastResultId(Context context) {
        return getString(context, PREF_LAST_RESULT_ID);
    }

    static void setLastResultId(Context context, String id) {
        setString(context, PREF_SEARCH_QUERY, id);
    }

    static boolean isAlarmOn(Context context) {
        return getDefaultSharedPreferences(context).getBoolean(PREF_IS_ALARM_ON, false);
    }

    static void setAlarmOn(Context context, boolean isOn) {
        getDefaultSharedPreferences(context).edit().putBoolean(PREF_IS_ALARM_ON, isOn).apply();
    }
}
