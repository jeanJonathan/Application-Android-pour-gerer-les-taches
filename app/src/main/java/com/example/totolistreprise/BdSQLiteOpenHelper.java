package com.example.totolistreprise;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BdSQLiteOpenHelper extends SQLiteOpenHelper {

    private String table_categorie="create table categorie ("
            + "idC INTEGER PRIMARY KEY,"
            + "nomC TEXT NOT NULL);";

    private String table_tache="create table tache ("
            + "idT INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "libelleT TEXT NOT NULL,"
            + "idC INTEGER,"
            + "foreign key (idC) references categorie(idC));";

    public BdSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(table_categorie);
        db.execSQL(table_tache);

        db.execSQL("insert into categorie (idC,nomC) values(1,'Maison');");
        db.execSQL("insert into categorie (idC,nomC) values(2,'Travail');");
        db.execSQL("insert into categorie (idC,nomC) values(3,'Loisirs');");
        db.execSQL("insert into categorie (idC,nomC) values(4,'Culture');");
        db.execSQL("insert into categorie (idC,nomC) values(5,'Travaux');");
        db.execSQL("insert into categorie (idC,nomC) values(6,'Famille');");

        db.execSQL("insert into tache (libelleT,idC) values('Repeindre les volets',1);");
        db.execSQL("insert into tache (libelleT,idC) values('Passer la tondeuse',1);");
        db.execSQL("insert into tache (libelleT,idC) values('Exposition photo',4);");
        db.execSQL("insert into tache (libelleT,idC) values('Reserver hotel pour le concert à Bordeaux',4);");
        db.execSQL("insert into tache (libelleT,idC) values('Concert fete de la musique',4);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}

