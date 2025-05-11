package com.example.quize_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class JoueursActivity extends AppCompatActivity {

    private RecyclerView recyclerJoueurs;
    private JoueurAdapter joueurAdapter;
    private List<Joueur> joueurList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joueurs);

        // Référence RecyclerView
        recyclerJoueurs = findViewById(R.id.recycler_joueurs);
        recyclerJoueurs.setLayoutManager(new LinearLayoutManager(this));

        // Liste des joueurs
        joueurList = new ArrayList<>();
        joueurAdapter = new JoueurAdapter(joueurList);
        recyclerJoueurs.setAdapter(joueurAdapter);

        // Récupération des joueurs depuis Firestore
        FirebaseFirestore.getInstance().collection("joueurs")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        joueurList.clear();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String nom = document.getString("nom");
                            Long score = document.getLong("score");

                            // Log pour debug
                            Log.d("FirestoreDebug", "Document récupéré : nom=" + nom + ", score=" + score);

                            if (nom != null && score != null) {
                                Joueur joueur = new Joueur(nom, score.intValue());
                                joueurList.add(joueur);
                            }
                        }
                        joueurAdapter.notifyDataSetChanged();
                    } else {
                        Log.w("FirestoreDebug", "Aucun document trouvé dans la collection 'joueurs'.");
                        Toast.makeText(JoueursActivity.this, "Aucun joueur trouvé", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreDebug", "Erreur lors de la récupération des joueurs : ", e);
                    Toast.makeText(JoueursActivity.this, "Erreur lors de la récupération des joueurs", Toast.LENGTH_SHORT).show();
                });

        // Référence au bouton de retour à la page de score
        Button btnRetourScore = findViewById(R.id.btn_retour_score);

        // Ajouter un listener au bouton pour revenir à la page de score
        btnRetourScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une nouvelle intention pour revenir à la page de score (par exemple, ScoreActivity)
                Intent intent = new Intent(JoueursActivity.this, Score.class);
                startActivity(intent);
                // Facultatif : fermer l'activité actuelle si tu ne veux pas que l'utilisateur puisse revenir en arrière
                finish();
            }
        });
    }
}
