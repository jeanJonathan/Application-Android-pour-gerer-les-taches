package com.example.totolistreprise;

public class Categorie {
    private long idC;
    private String nomC;

    public Categorie(long idV, String nomV) {
        this.idC = idV;
        this.nomC = nomV;
    }

    public Categorie(String nomV) {
        this.idC = -1;
        this.nomC = nomV;
    }

    public long getIdC() {
        return idC;
    }

    public void setIdC(long idC) {
        this.idC = idC;
    }

    public String getNomC() {
        return nomC;
    }

    public void setNomC(String nomC) {
        this.nomC = nomC;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "idC=" + idC +
                ", nomC='" + nomC + '\'' +
                '}';
    }
}
