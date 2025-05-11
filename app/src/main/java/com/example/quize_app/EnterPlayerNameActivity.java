package com.example.quize_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class EnterPlayerNameActivity extends AppCompatActivity {

    private EditText playerNameEditText;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);

        // Initialisation des vues
        playerNameEditText = findViewById(R.id.player_name_edittext);
        startButton = findViewById(R.id.start_button);

        startButton.setOnClickListener(v -> {
            String playerName = playerNameEditText.getText().toString().trim();

            if (playerName.isEmpty()) {
                Toast.makeText(this, "Veuillez entrer un nom", Toast.LENGTH_SHORT).show();
            } else {
                // Enregistrer le nom du joueur dans Firestore
                savePlayerName(playerName);

                // Passer au quiz
                Intent intent = new Intent(this, Quize1.class);
                intent.putExtra("player_name", playerName); // Passer le nom du joueur
                startActivity(intent);
            }
        });
    }

    private void savePlayerName(String playerName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show();
            return;
        }

        // Utilisation du nom du joueur comme identifiant unique
        String playerId = playerName.toLowerCase(); // Nom du joueur comme identifiant

        Map<String, Object> joueur = new HashMap<>();
        joueur.put("nom", playerName);
        joueur.put("score", 0); // Score initial
        joueur.put("created_by", user.getUid()); // Pour savoir quel utilisateur a ajouté ce joueur

        // Ajouter ou mettre à jour le joueur avec un ID unique basé sur le nom
        db.collection("joueurs")
                .document(playerId) // Utiliser playerId pour garantir un joueur unique
                .set(joueur) // Utilise set() pour remplacer les données existantes si le joueur existe déjà
                .addOnSuccessListener(documentReference ->
                        Toast.makeText(this, "Joueur ajouté avec succès", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show()
                );
    }
}
