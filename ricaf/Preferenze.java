package com.domicilio.ricaf;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferenze {
    // Salvano in maniera permanente delle variabili

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;


    private Context mContext;

    public Preferenze (Context context) {
        settings = context.getSharedPreferences(Global.PREFS_FILE, 0);
        editor = settings.edit();
    }
    // prelevare il valore della label = valore
    public String getStringByLabel (String _label) {

        String result = "";
        if(settings.contains(_label)) {
            result = settings.getString(_label, "");
        }
        return result;
    }
    // put string label = valore scrivere il valore di una label
    public void putStringByLabel(String _label, String _value) {

        editor.putString(_label, _value);
        editor.commit(); // Scrive le modifiche appese
    }

}
