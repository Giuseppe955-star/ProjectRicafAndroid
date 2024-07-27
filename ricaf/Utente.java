package com.domicilio.ricaf;

import android.content.Context;

public class Utente {

    private static Utente instance = new Utente();

    private String id  = "";
    private String nome  = "";
    private String cognome  = "";
    private String email  = "";
    private String luogo  = "";

    public static Utente getInstance() {
        return instance;
    }

    public static void setInstance(Utente instance) {
        Utente.instance = instance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }


    // Metodo salvare le preferenze nella classe Utente
    public void SetUtenteFromPref(Context _context){

        Preferenze mPref = new Preferenze(_context);
        this.id = mPref.getStringByLabel("id");
        this.nome = mPref.getStringByLabel("nome");
        this.cognome = mPref.getStringByLabel("cognome");
        this.email = mPref.getStringByLabel("email");
        this.luogo  = mPref.getStringByLabel("luogo");

    }

}
