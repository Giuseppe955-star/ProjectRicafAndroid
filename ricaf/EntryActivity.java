package com.domicilio.ricaf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        // controlla se c'è una preferenza in memoria
        Preferenze mPref = new Preferenze(this);
        //Se esiste un valore per l'etichetta username -> Allora vado in HomeActiviy
        //Altrimenti vado in LoginActivity
        // è diverso da vuoto
        if(!mPref.getStringByLabel("id").equals("")) {
            //Vado in HomeActivity se sei loggato
            // Se abbiamo il file nella Preferenze andiamo direttmante alla start Activtiy
            //
            Utente.getInstance().SetUtenteFromPref(this);

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            // altrimenti vado alla LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }


    }
}
