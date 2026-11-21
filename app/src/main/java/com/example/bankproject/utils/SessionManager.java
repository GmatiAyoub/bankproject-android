package com.example.bankproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "BankSession";
    private static final String KEY_CUSTOMER_ID = "customerId";
    private static final String KEY_CUSTOMER_NAME = "customerName";

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveSession(Long customerId, String customerName) {
        editor.putLong(KEY_CUSTOMER_ID, customerId);
        editor.putString(KEY_CUSTOMER_NAME, customerName);
        editor.apply();
    }

    public Long getCustomerId() {
        return prefs.getLong(KEY_CUSTOMER_ID, -1L);
    }

    public String getCustomerName() {
        return prefs.getString(KEY_CUSTOMER_NAME, "Client");
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}