package com.example.totolistreprise;

public class Tache {
    private long idT;
    private String libelleT;
    private long idC;

    public Tache(long idT, String libelleT, long idC) {
        this.idT = idT;
        this.libelleT = libelleT;
        this.idC = idC;
    }

    public Tache(String libelleT, long idC) {
        this.idT=-1;
        this.libelleT = libelleT;
        this.idC = idC;
    }

    public long getIdT() {
        return idT;
    }

    public void setIdT(long idT) {
        this.idT = idT;
    }

    public String getLibelleT() {
        return libelleT;
    }

    public void setLibelleT(String libelleT) {
        this.libelleT = libelleT;
    }

    public long getIdC() {
        return idC;
    }

    public void setIdC(long idC) {
        this.idC = idC;
    }

    @Override
    public String toString() {
        return "Tache{" +
                "idT=" + idT +
                ", libelleT='" + libelleT + '\'' +
                ", idC=" + idC +
                '}';
    }
}
