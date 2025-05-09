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

public class Quize1 extends AppCompatActivity {

    private Button bNext;
    private RadioGroup rg;
    private RadioButton rb;
    private TextView timerText;
    private CountDownTimer countDownTimer;

    private int score = 0;
    private final String correctAnswer = "Océan Pacifique";

    private final long TOTAL_TIME = 30_000; // 30 secondes
    private final long INTERVAL = 1_000;    // 1 seconde

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quize1);

        bNext = findViewById(R.id.next_button);
        rg = findViewById(R.id.options_group);
        timerText = findViewById(R.id.timer_text); // Assure-toi que ce TextView est dans activity_quize1.xml

        score = getIntent().getIntExtra("score", 0);

        // Démarrer le compte à rebours
        startTimer();

        // Utilisation de lambda pour le bouton "Suivant"
        bNext.setOnClickListener(v -> checkAnswerAndGoNext());
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
                checkAnswerAndGoNext();
            }
        };
        countDownTimer.start();
    }

    private void checkAnswerAndGoNext() {
        if (countDownTimer != null) countDownTimer.cancel();

        if (rg.getCheckedRadioButtonId() != -1) {
            rb = findViewById(rg.getCheckedRadioButtonId());
            if (rb.getText().toString().trim().equalsIgnoreCase(correctAnswer)) {
                score++;
            }
        }

        Intent intent = new Intent(Quize1.this, Quize2.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }
}
