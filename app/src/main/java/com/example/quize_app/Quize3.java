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

public class Quize3 extends AppCompatActivity {

    // Composants de l'interface
    private Button bNext;
    private RadioGroup rg;
    private RadioButton rb;
    private TextView timerText;
    private TextView playerNameText; // ✅ Ajout du champ pour afficher le nom

    // Données
    private int score;
    private final String correctAnswer = "Léonard de Vinci";
    private String playerName = "";

    // Chronomètre
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 30000; // 30 secondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quize3);

        // Initialisation des vues
        bNext = findViewById(R.id.next_button);
        rg = findViewById(R.id.options_group);
        timerText = findViewById(R.id.timer_text);
        playerNameText = findViewById(R.id.player_name_text); // Assure-toi d’avoir ce TextView dans le layout XML

        // Récupérer les données de l'activité précédente
        score = getIntent().getIntExtra("score", 0);
        playerName = getIntent().getStringExtra("player_name");

        // Affichage du nom
        if (playerName != null && !playerName.isEmpty()) {
            playerNameText.setText("Joueur : " + playerName);
        }

        // Lancement du chronomètre
        startTimer();

        // Gestion du bouton Suivant
        bNext.setOnClickListener(v -> {
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

                // Passer à Quize4 avec score et nom
                Intent intent = new Intent(Quize3.this, Quize4.class);
                intent.putExtra("score", score);
                intent.putExtra("player_name", playerName); // ✅ transmission du nom
                startActivity(intent);
                finish();
            }
        });
    }

    // Méthode de chronomètre
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
                Toast.makeText(Quize3.this, "Temps écoulé !", Toast.LENGTH_SHORT).show();

                // Passer automatiquement à Quize4 avec score et nom
                Intent intent = new Intent(Quize3.this, Quize4.class);
                intent.putExtra("score", score);
                intent.putExtra("player_name", playerName); // ✅ transmission du nom
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
