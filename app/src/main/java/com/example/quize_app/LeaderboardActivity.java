package com.example.quize_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    ListView leaderboardListView;
    ArrayList<String> leaderboardData;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // 🔐 Vérifie si l'utilisateur est connecté
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class); // ou RegisterActivity selon ton app
            startActivity(intent);
            finish();
            return;
        }

        leaderboardListView = findViewById(R.id.leaderboard_list);
        leaderboardData = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, leaderboardData);
        leaderboardListView.setAdapter(adapter);

        // Charger les scores depuis Firestore
        loadLeaderboard();

        // Gestion du bouton "Retour"
        Button btnReturn = findViewById(R.id.btn_return);
        btnReturn.setOnClickListener(v -> {
            // Retourner à l'activité précédente
            onBackPressed();
        });
    }

    private void loadLeaderboard() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("joueurs")
                .orderBy("score", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    leaderboardData.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String nom = doc.getString("nom");
                        Long score = doc.getLong("score");
                        if (nom != null && score != null) {
                            leaderboardData.add(nom + " : " + score + " points");
                        }
                    }

                    if (leaderboardData.isEmpty()) {
                        leaderboardData.add("Aucun joueur pour l’instant.");
                    }

                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(LeaderboardActivity.this, "Erreur de chargement du leaderboard", Toast.LENGTH_SHORT).show());
    }
}
