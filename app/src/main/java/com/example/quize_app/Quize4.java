package com.example.quize_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Quize4 extends AppCompatActivity {

    // Déclaration des composants
    private Button bNext;
    private RadioGroup rg;
    private RadioButton rb;
    private TextView timerText;
    private TextView playerNameText;

    // Données
    private int score;
    private final String correctAnswer = "Mercure";
    private String playerName = "";

    // Chronomètre
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 30000; // 30 secondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quize4); // Assure-toi que ce fichier XML existe et contient les bons IDs

        // Récupération des données de l'activité précédente
        score = getIntent().getIntExtra("score", 0);
        playerName = getIntent().getStringExtra("player_name");

        // Initialisation des composants graphiques
        bNext = findViewById(R.id.next_button);
        rg = findViewById(R.id.options_group);
        timerText = findViewById(R.id.timer_text);
        playerNameText = findViewById(R.id.player_name_text); // ⚠️ Ce TextView doit être présent dans le layout

        // Affichage du nom du joueur
        if (playerName != null && !playerName.isEmpty()) {
            playerNameText.setText("Joueur : " + playerName);
        }

        // Lancer le chronomètre
        startTimer();

        // Gestion du clic sur "Suivant"
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rg.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(),
                            "Vous devez choisir une réponse", Toast.LENGTH_SHORT).show();
                } else {
                    rb = findViewById(rg.getCheckedRadioButtonId());
                    String selectedText = rb.getText().toString().trim();

                    if (selectedText.equalsIgnoreCase(correctAnswer)) {
                        score++;
                    }

                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }

                    // Passer à Quize5 avec le score et le nom du joueur
                    Intent intent = new Intent(Quize4.this, Quize5.class);
                    intent.putExtra("score", score);
                    intent.putExtra("player_name", playerName);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                timerText.setText(String.format("00:%02d", seconds));
            }

            @Override
            public void onFinish() {
                timerText.setText("Temps écoulé !");
                Toast.makeText(Quize4.this, "Temps écoulé !", Toast.LENGTH_SHORT).show();

                // Passer automatiquement à Quize5 avec le score et le nom du joueur
                Intent intent = new Intent(Quize4.this, Quize5.class);
                intent.putExtra("score", score);
                intent.putExtra("player_name", playerName);
                startActivity(intent);
                finish();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
