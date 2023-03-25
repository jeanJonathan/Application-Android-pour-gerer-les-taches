package com.example.totolistreprise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
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
    private Spinner spinCategorie;
    private Button ajouterTache;
    private Button validerTache;

    //On travaillera pas sur ses fonctionnalite
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
        ajouterTache = findViewById(R.id.bAjouter);
        validerTache = findViewById(R.id.bTacheFaite);
        nouvelleCategorie = findViewById(R.id.bNouvelleCategorie);
        supprimerTache = findViewById(R.id.bSuprimerTache);
        spinCategorie = findViewById(R.id.spinTaches);

        //Pour reccuperer les categorie de la liste des categorie
        //Important d'initialiser les objets
        uneTacheDAO = new TacheDAO(this);
        uneCategorieDAO= new CategorieDAO(this);
        listeDesChekB = new ArrayList<CheckBox>(); // On initialise lstChk pour creer une liste d'objet de type checkBox
        // Récupérer la liste des catégories depuis la base de données
        listeCategorie = uneCategorieDAO.getCategories(); // Liste des taches est initialisee avec un tableau de Categorie

        ajouterTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creation de la tache
                long idCategorie;
                String libelleTache;
                //getIdC() pour reccuperer l'id qui correspond a celui stocker dans la bd
                //getSelectedItemPosition() pour reccuperer la position du spinner selectionner
                //listeCategorie.get(spinTaches.getSelectedItemPosition()) retourne une categorie de type Categorie
                idCategorie = listeCategorie.get(spinCategorie.getSelectedItemPosition()).getIdC();
                libelleTache = saisieTache.getText().toString();
                //Creation de la tache ok
                Tache uneTache = new Tache(libelleTache,idCategorie);

                //Ajout de la tache a la liste des taches, uneTacheDAO joue le role d'un objet du Controleur TacheDAO
                uneTacheDAO.addTache(uneTache);
                laListeTaches = uneTacheDAO.getTaches();

                //Appel de la methode pour afficher proprement le contenue de liste des taches dans le LinearLayout
                afficherTaches();

                //On supprime la tache saisie (remise a zero)
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

        //
        nouvelleCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent unIntent = new Intent(getApplicationContext(),NouvelleCategorie.class);
                startActivity(unIntent);
            }
        });
        // Ajouter la nouvelle catégorie si elle a été créée depuis NouvelleCategorie
        long nouvelleCategorieId = getIntent().getLongExtra("nouvelle_categorie_id", -1);
        if (nouvelleCategorieId != -1) {
            Categorie nouvelleCategorie = uneCategorieDAO.getCategorie(nouvelleCategorieId);
            if (nouvelleCategorie != null) {
                listeCategorie.add(nouvelleCategorie);
            }
        }
        //On boucle a nouveau pour reccuperer les noms des categories
        ArrayList<String> listeNomsCategorie = new ArrayList<String>();
        for (Categorie categorie : listeCategorie) {
            listeNomsCategorie.add(categorie.getNomC());
        }

        //Log.d("test",listeCategorie.toString());
        // Instancier un ArrayAdapter pour le Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeNomsCategorie);
        //ArrayAdapter<Categorie> adapter = new ArrayAdapter<Categorie>(this, android.R.layout.simple_spinner_item,listeNomsCategorie);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Ajouter l'Adapter au Spinner
        spinCategorie.setAdapter(adapter);

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
                //Sppression de l'element cocher
                uneTacheDAO.delTache(laListeTaches.get(i));
            }
        }
        //recharge lstTache
        laListeTaches = uneTacheDAO.getTaches();
        afficherTaches();
    }
}