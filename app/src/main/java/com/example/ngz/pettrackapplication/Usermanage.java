package com.example.ngz.pettrackapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

public class Usermanage {

    private final String Key_username = "nueng";
    private final String TAG = "mahanakorn";

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public Usermanage(Context context) {
        sharedPref = context.getSharedPreferences(Key_username, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public boolean checkLoginValidate(String email) {
        String realEmail = sharedPref.getString(Key_username, "");

        if ((!TextUtils.isEmpty(email)) && email.equalsIgnoreCase(realEmail)) {
            return true;
        }
        return false;
    }

    public boolean registerUser(String email) {

        if (TextUtils.isEmpty(email)) {
            return false;
        }

        editor.putString(Key_username, email);
        Log.d(TAG,"name:" + Key_username);
        return editor.commit();
    }

    public void removeKey() {
        editor.remove(Key_username);
        Log.d(TAG,"name remove:" + Key_username);
        editor.apply();
    }
}
