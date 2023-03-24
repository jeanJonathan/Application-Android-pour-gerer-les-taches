package com.example.totolistreprise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Declaration des objets graphique
    private LinearLayout listeTaches ;
    private EditText saisieTache;
    private Spinner spinTaches;
    private Button ajouterTache;
    private Button validerTache;
    private Button nouvelleCategorie;
    private Button supprimerTache;

    //Declaration du tableau pour la liste des categorie
    private ArrayList<Categorie> listeCategorie;
    private CategorieDAO uneCategorieDAO;

    //Declaration de l'objet tache DAO
    private TacheDAO uneTacheDAO;
    //
    private ArrayList<Tache> laListeTaches;

    private ArrayList<CheckBox> listeDesChekB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Appel activity_main pour instancier tout les objets graphique manipuler par java
        setContentView(R.layout.activity_main);

        //Liaison des objets graphique au objet java, c'est le seul endroid ou j'utilise les id apres ca on manipule avec les objets graphiques
        listeTaches = findViewById(R.id.lListeTaches);
        saisieTache = findViewById(R.id.eNouvelleTache);
        spinTaches = findViewById(R.id.spinTaches);
        ajouterTache = findViewById(R.id.bAjouter);
        validerTache = findViewById(R.id.bTacheFaite);
        nouvelleCategorie = findViewById(R.id.bNouvelleCategorie);
        supprimerTache = findViewById(R.id.bSuprimerTache);

        //Pour reccuperer les categorie de la liste des categorie
        //Important d'initialiser les objets
        uneTacheDAO = new TacheDAO(this);
        uneCategorieDAO= new CategorieDAO(this);
        listeDesChekB = new ArrayList<CheckBox>(); // On initialise lstChk pour creer une liste d'objet de type checkBox
        listeCategorie = uneCategorieDAO.getCategories();

        ajouterTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Code a executer lors du click
                long idCategorie;
                String libelleTache;
                //getIdC() pour reccuperer l'id qui correspond a celui stocker dans la bd
                //getSelectedItemPosition() pour reccuperer la position du spinner selectionner
                idCategorie = listeCategorie.get(spinTaches.getSelectedItemPosition()).getIdC();
                libelleTache = saisieTache.getText().toString();
                Tache uneTache = new Tache(libelleTache,idCategorie);
                uneTacheDAO.addTache(uneTache);
                laListeTaches = uneTacheDAO.getTaches();
                //Appel de la methode pour afficher la liste des taches
                afficherTaches();
                //Remise a zero
                saisieTache.setText("");
            }
        });

        //Developpement de la fonctionnalite pour valider la tache
        validerTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Code a executer
                supprimerTache();

            }
        });
    }
    //Implementation de la methode afficherTaches
    private void afficherTaches(){
        listeTaches.removeAllViews();
        listeDesChekB.clear();

        for (int i=0; i <laListeTaches.size();i++){
            LinearLayout unLayout = new LinearLayout(MainActivity.this);
            unLayout.setOrientation(LinearLayout.HORIZONTAL);

            CheckBox unCheckBox = new CheckBox(this);
            unCheckBox.setText(laListeTaches.get(i).getLibelleT());
            listeDesChekB.add(unCheckBox);
            TextView unTextView = new TextView(this);
            unTextView.setText(uneCategorieDAO.getCategorie(laListeTaches.get(i).getIdC()).getNomC());
            unTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            unTextView.setGravity(Gravity.RIGHT);
            unTextView.setPadding(20,0,20,0);
            unLayout.addView(unCheckBox);
            unLayout.addView(unTextView);
            listeTaches.addView(unLayout);
        }
    }
    private void supprimerTache(){
        for(int i=0;i<=listeDesChekB.size()-1;i++) {
            if(listeDesChekB.get(i).isChecked()){
                //Sppression de la tache
                uneTacheDAO.delTache(laListeTaches.get(i));

            }
        }
        //recharge lstTache
        laListeTaches = uneTacheDAO.getTaches();
        afficherTaches();
    }
}