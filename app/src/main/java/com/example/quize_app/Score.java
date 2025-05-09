package com.example.quize_app;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Score extends AppCompatActivity {

    TextView resultText, percentageText;
    ProgressBar scoreProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        resultText = findViewById(R.id.result_text);
        percentageText = findViewById(R.id.percentage_text);
        scoreProgressBar = findViewById(R.id.score_progress_bar);

        int finalScore = getIntent().getIntExtra("score", 0);

        resultText.setText("Votre score final est : " + finalScore + " / 5");

        // Met à jour la barre de progression
        scoreProgressBar.setProgress(finalScore);

        // Met à jour le pourcentage
        int percentage = (int) ((finalScore / 5.0) * 100);
        percentageText.setText(percentage + "%");
    }
}