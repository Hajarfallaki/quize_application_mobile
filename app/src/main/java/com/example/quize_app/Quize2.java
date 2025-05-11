package com.example.quize_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Quize2 extends AppCompatActivity {

    private Button bNext;
    private RadioGroup rg;
    private RadioButton rb;
    private TextView timerText;
    private TextView playerNameText;

    private int score;
    private final String correctAnswer = "Canberra";

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 30000; // 30 secondes

    private String playerName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quize2);

        // Initialisation des vues
        bNext = findViewById(R.id.next_button);
        rg = findViewById(R.id.options_group);
        timerText = findViewById(R.id.timer_text);
        playerNameText = findViewById(R.id.player_name_text);

        // Récupérer les données de l'activité précédente
        score = getIntent().getIntExtra("score", 0);
        playerName = getIntent().getStringExtra("player_name"); // ✅ même clé

        // Afficher le nom du joueur
        if (playerName != null && !playerName.isEmpty()) {
            playerNameText.setText("Joueur : " + playerName);
        }

        // Démarrer le compte à rebours
        startTimer();

        // Gérer le clic sur le bouton "Suivant"
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

                Intent intent = new Intent(Quize2.this, Quize3.class);
                intent.putExtra("score", score);
                intent.putExtra("player_name", playerName); // ✅ même clé
                startActivity(intent);
                finish();
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                int seconds = (int) (timeLeftInMillis / 1000);
                timerText.setText(String.format("00:%02d", seconds));
            }

            @Override
            public void onFinish() {
                timerText.setText("Temps écoulé !");
                Toast.makeText(Quize2.this, "Temps écoulé !", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Quize2.this, Quize3.class);
                intent.putExtra("score", score);
                intent.putExtra("player_name", playerName); // ✅ même clé
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
