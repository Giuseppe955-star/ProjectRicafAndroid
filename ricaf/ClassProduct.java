package com.domicilio.ricaf;

import java.math.BigDecimal;

public class ClassProduct {

    private String Titolo = "";
    private String Immagine = "";
    private int Posizione = 0; // Posizione dell'array
    private String id = "";
    private String prezzo= "";

    public String getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTitolo() {
        return Titolo;
    }

    public void setTitolo(String titolo) {
        Titolo = titolo;
    }

    public String getImmagine() {
        return Immagine;
    }

    public void setImmagine(String immagine) {
        Immagine = immagine;
    }


    public int getPosizione() {
        return Posizione;
    }

    public void setPosizione(int posizione) {
        Posizione = posizione;
    }

    public ClassProduct(String titolo, String immagine, int posizione,String id,String prezzo) {
        Titolo = titolo;
        Immagine = immagine;
        Posizione = posizione;
        this.id = id;
        this.prezzo =  prezzo;
    }
}
