package com.example.quize_app;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Quize1  extends AppCompatActivity {

    Button bNext;
    RadioGroup rg;
    RadioButton rb;
    int score = 0;
    String correctAnswer = "Océan Pacifique";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quize1);

        bNext = findViewById(R.id.next_button);
        rg = findViewById(R.id.options_group);

        // Récupération du score depuis l'activité précédente
        score = getIntent().getIntExtra("score", 0);

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rg.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Vous devez choisir une réponse",
                            Toast.LENGTH_SHORT).show();
                } else {
                    rb = findViewById(rg.getCheckedRadioButtonId());
                    if (rb.getText().toString().trim().equalsIgnoreCase(correctAnswer)) {
                        score += 1;
                    }

                    // Aller vers Quiz2 avec le score
                    Intent i1 = new Intent(Quize1.this, Quize2.class);
                    i1.putExtra("score", score);
                    startActivity(i1);
                    finish(); // optionnel : empêche de revenir avec le bouton retour
                }
            }
        });
    }
}
