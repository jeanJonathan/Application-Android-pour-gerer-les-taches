package com.example.totolistreprise;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

public class TacheDAO {


    private static String base = "BDTodoList";
    private static int version = 1;
    private BdSQLiteOpenHelper accesBD;

    public TacheDAO(Context ct){
        accesBD = new BdSQLiteOpenHelper(ct, base, null, version);

    }

    public Tache getTache(long idT){
        Tache laTache = null;
        Cursor curseur;
        curseur = accesBD.getReadableDatabase().rawQuery("select * from tache where idT="+idT+";",null);
        if (curseur.getCount() > 0) {
            curseur.moveToFirst();
            laTache = new Tache(idT,curseur.getString(1), curseur.getLong(2));
        }
        return laTache;
    }

    public void addTache(Tache uneTache){
        Log.d("testLog","insert into tache(libelleT,idC) values('"+uneTache.getLibelleT()+"',"+uneTache.getIdC()+");");
        accesBD.getWritableDatabase().execSQL("insert into tache(libelleT,idC) values('"+uneTache.getLibelleT()+"',"+uneTache.getIdC()+");");
        accesBD.close();
    }

    public void delTache(Tache uneTache){
        Log.d("testLog","delete from tache where idT="+uneTache.getIdT()+";");
        accesBD.getWritableDatabase().execSQL("delete from tache where idT="+uneTache.getIdT()+";");
        accesBD.close();
    }

    public ArrayList<Tache> getTaches(){
        Cursor curseur;
        String req = "select * from tache;";
        curseur = accesBD.getReadableDatabase().rawQuery(req,null);
        return cursorToTacheArrayList(curseur);
    }
    public ArrayList<Tache> getTachesByCategorie(String nomC){
        Cursor curseur;
        String req = "select * from tache, categorie where tache.idC = categorie.idC and nomC='"+nomC+"';";
        curseur = accesBD.getReadableDatabase().rawQuery(req,null);
        return cursorToTacheArrayList(curseur);
    }



    private ArrayList<Tache> cursorToTacheArrayList(Cursor curseur){
        ArrayList<Tache> listeTaches = new ArrayList<Tache>();
        long idT;
        String libelleT;
        long idC;

        curseur.moveToFirst();
        while (!curseur.isAfterLast()){
            idT = curseur.getLong(0);
            libelleT = curseur.getString(1);
            idC = curseur.getLong(2);
            listeTaches.add(new Tache(idT,libelleT,idC));
            curseur.moveToNext();
        }

        return listeTaches;
    }


}

