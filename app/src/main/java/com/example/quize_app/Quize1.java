package com.example.quize_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Quize1 extends AppCompatActivity {

    private Button bNext;
    private RadioGroup rg;
    private RadioButton rb;
    private TextView timerText;
    private TextView playerNameText;

    private CountDownTimer countDownTimer;

    private int score = 0;
    private final String correctAnswer = "Océan Pacifique";

    private final long TOTAL_TIME = 30_000; // 30 secondes
    private final long INTERVAL = 1_000;    // 1 seconde

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quize1);

        // Initialisation des vues
        bNext = findViewById(R.id.next_button);
        rg = findViewById(R.id.options_group);
        timerText = findViewById(R.id.timer_text);
        playerNameText = findViewById(R.id.player_name_text);

        // Récupérer les données de l'intent
        score = getIntent().getIntExtra("score", 0);
        String playerName = getIntent().getStringExtra("player_name");

        // Afficher le nom du joueur
        if (playerName != null && !playerName.isEmpty()) {
            playerNameText.setText("Joueur : " + playerName);
        }

        // Démarrer le compte à rebours
        startTimer();

        // Bouton suivant
        bNext.setOnClickListener(v -> checkAnswerAndGoNext(playerName));
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(TOTAL_TIME, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                timerText.setText(String.format("00:%02d", seconds));
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Temps écoulé !", Toast.LENGTH_SHORT).show();
                checkAnswerAndGoNext(playerNameText.getText().toString().replace("Joueur : ", ""));
            }
        };
        countDownTimer.start();
    }

    private void checkAnswerAndGoNext(String playerName) {
        if (countDownTimer != null) countDownTimer.cancel();

        if (rg.getCheckedRadioButtonId() != -1) {
            rb = findViewById(rg.getCheckedRadioButtonId());
            if (rb.getText().toString().trim().equalsIgnoreCase(correctAnswer)) {
                score++;
            }
        }

        // Passer au quiz suivant
        Intent intent = new Intent(Quize1.this, Quize2.class);
        intent.putExtra("score", score);
        intent.putExtra("player_name", playerName); // ✅ clé cohérente
        startActivity(intent);
        finish();
    }
}
