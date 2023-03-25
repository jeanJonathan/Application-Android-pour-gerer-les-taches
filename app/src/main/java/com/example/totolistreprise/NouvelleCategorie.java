package com.example.totolistreprise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class NouvelleCategorie extends AppCompatActivity {

    private EditText saisieNouvelleCategorie;
    private Button ajouterNouvelleCategorie;

    private CategorieDAO uneCategorieDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_categorie);

        saisieNouvelleCategorie = findViewById(R.id.eNouvelleCategorie);
        ajouterNouvelleCategorie = findViewById(R.id.bAjouterNouvelleCategorie);

        //On initialise uneCategorie
        uneCategorieDAO = new CategorieDAO(this);

        ajouterNouvelleCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nouvelleCategorie = saisieNouvelleCategorie.getText().toString();
                if (!nouvelleCategorie.isEmpty()) {
                    // Ajouter la nouvelle catégorie dans la base de données

                    //Parcours de la boucle for pour compter le nombre de categorie
                    //Prendre le nombre total et faire + 1 pour definir l'id de la nouvelle categorie a ajouter dans la bd
                    // Ajouter la nouvelle catégorie dans la base de données
                    long idNouvelleCategorie = uneCategorieDAO.ajouterCategorie(new Categorie(nouvelleCategorie));

                    //Je passe l'id a Intent de MainActivity
                    // Créer un nouvel objet Intent pour passer l'ID à MainActivity
                    Intent intent = new Intent(NouvelleCategorie.this, MainActivity.class);
                    intent.putExtra("nouvelle_categorie_id", idNouvelleCategorie);
                    startActivity(intent);

                    // Effacer le texte de l'EditText
                    saisieNouvelleCategorie.setText("");

                }
            }

        });

    }
}