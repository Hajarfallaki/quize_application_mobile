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

    // Données
    private int score;
    private final String correctAnswer = "Léonard de Vinci";

    // Chronomètre
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 30000; // 30 secondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quize3);

        // Récupérer le score de l'activité précédente
        score = getIntent().getIntExtra("score", 0);

        // Initialisation des vues
        bNext = findViewById(R.id.next_button);
        rg = findViewById(R.id.options_group);
        timerText = findViewById(R.id.timer_text); // Assure-toi d'avoir ce TextView dans ton layout XML

        // Lancement du chronomètre
        startTimer();

        // Gestion du bouton Suivant
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

                    // Passer à Quize4
                    Intent intent = new Intent(Quize3.this, Quize4.class);
                    intent.putExtra("score", score);
                    startActivity(intent);
                    finish();
                }
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

                // Passer automatiquement à Quize4
                Intent intent = new Intent(Quize3.this, Quize4.class);
                intent.putExtra("score", score);
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
