package com.example.totolistreprise;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class CategorieDAO {

    private static String base = "BDTodoList";
    private static int version = 1;
    private BdSQLiteOpenHelper accesBD;

    public CategorieDAO(Context ct){
        accesBD = new BdSQLiteOpenHelper(ct, base, null, version);
    }

    public Categorie getCategorie(long idC){
        Categorie laCategorie = null;
        Cursor curseur;
        curseur = accesBD.getReadableDatabase().rawQuery("select * from categorie where idC="+idC+";",null);
        if (curseur.getCount() > 0) {
            curseur.moveToFirst();
            laCategorie = new Categorie(idC,curseur.getString(1));
        }
        return laCategorie;
    }

    public ArrayList<Categorie> getCategories(){
        Cursor curseur;
        String req = "select * from categorie;";
        curseur = accesBD.getReadableDatabase().rawQuery(req,null);
        return cursorToCategorieArrayList(curseur);
    }

    private ArrayList<Categorie> cursorToCategorieArrayList(Cursor curseur){
        ArrayList<Categorie> listeCategorie = new ArrayList<Categorie>();
        long idC;
        String nomC;

        curseur.moveToFirst();
        while (!curseur.isAfterLast()){
            idC = curseur.getLong(0);
            nomC = curseur.getString(1);
            listeCategorie.add(new Categorie(idC,nomC));
            curseur.moveToNext();
        }
        return listeCategorie;
    }

    //Methode pour ajouter une nouvelle Categorie dans la base de donnee
    public long ajouterCategorie(Categorie categorie) {
        ContentValues values = new ContentValues();
        values.put("nomC", categorie.getNomC());
        return accesBD.getWritableDatabase().insert("categorie", null, values);
    }

}

