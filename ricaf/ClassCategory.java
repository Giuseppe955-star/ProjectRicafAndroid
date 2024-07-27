package com.domicilio.ricaf;

public class ClassCategory {

    private String Titolo = "";
    private String Immagine = "";
    private int Posizione = 0;
    private String id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClassCategory(String titolo, String immagine, int posizione, String id ) {
        Titolo = titolo;
        Immagine = immagine;
        Posizione = posizione;
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
}


