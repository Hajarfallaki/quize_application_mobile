package com.example.quize_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Score extends AppCompatActivity {

    TextView resultText, percentageText, playerNameText;
    ProgressBar scoreProgressBar;
    Button btnTryAgain, btnLogout, btnShowLeaderboard, btnAfficherJoueurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // Initialisation des vues
        resultText = findViewById(R.id.result_text);
        percentageText = findViewById(R.id.percentage_text);
        playerNameText = findViewById(R.id.player_name_text);
        scoreProgressBar = findViewById(R.id.score_progress_bar);
        btnTryAgain = findViewById(R.id.btn_try_again);
        btnLogout = findViewById(R.id.btn_logout);
        btnShowLeaderboard = findViewById(R.id.btn_show_leaderboard);
        btnAfficherJoueurs = findViewById(R.id.btnAfficherJoueurs); // Bouton pour afficher la liste des joueurs

        // Récupération du score final
        int finalScore = getIntent().getIntExtra("score", 0);
        resultText.setText("Votre score final est : " + finalScore + " / 5");

        // Calcul et affichage du pourcentage
        int percentage = (int) ((finalScore / 5.0) * 100);
        percentageText.setText(percentage + " %");

        // Mise à jour de la barre de progression
        scoreProgressBar.setMax(100);
        scoreProgressBar.setProgress(percentage);

        // Mise à jour du score dans Firestore
        updatePlayerScore(finalScore);

        // Affichage du nom du joueur
        displayPlayerName();

        // Bouton : Recommencer
        btnTryAgain.setOnClickListener(v -> {
            Intent intent = new Intent(Score.this, Quize1.class);
            intent.putExtra("score", 0);
            startActivity(intent);
            finish();
        });

        // Bouton : Déconnexion
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Score.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Bouton : Classement
        btnShowLeaderboard.setOnClickListener(v -> {
            Intent intent = new Intent(Score.this, LeaderboardActivity.class);
            startActivity(intent);
        });

        // ✅ Bouton : Afficher les joueurs
        btnAfficherJoueurs.setOnClickListener(v -> {
            Intent intent = new Intent(Score.this, JoueursActivity.class);
            startActivity(intent);
        });
    }

    private void updatePlayerScore(int score) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show();
            return;
        }

        // Utilise le nom du joueur pour récupérer son ID dans la base de données
        String playerName = getIntent().getStringExtra("player_name"); // Récupérer le nom du joueur
        String playerId = playerName.toLowerCase(); // Utiliser le nom comme ID pour retrouver le joueur

        db.collection("joueurs").document(playerId)
                .update("score", score)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(Score.this, "Score mis à jour avec succès", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(Score.this, "Erreur lors de la mise à jour du score", Toast.LENGTH_SHORT).show());
    }

    private void displayPlayerName() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show();
            return;
        }

        String playerName = getIntent().getStringExtra("player_name"); // Récupérer le nom du joueur
        String playerId = playerName.toLowerCase(); // Utiliser le nom comme ID pour retrouver le joueur

        db.collection("joueurs").document(playerId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String playerNameFromDb = documentSnapshot.getString("nom");
                        if (playerNameFromDb != null) {
                            playerNameText.setText("Bravo !!! " + playerNameFromDb);
                        }
                    } else {
                        Toast.makeText(Score.this, "Nom du joueur non trouvé", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Score.this, "Erreur lors de la récupération du nom", Toast.LENGTH_SHORT).show();
                });
    }
}
