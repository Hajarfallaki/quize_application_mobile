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

public class Quize3 extends AppCompatActivity {

    // Déclaration des composants
    private Button bNext;
    private RadioGroup rg;
    private RadioButton rb;

    // Score actuel, et réponse correcte attendue
    private int score;
    private final String correctAnswer = "Léonard de Vinci";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quize3); // Assure-toi que ce fichier XML existe

        // Récupération du score depuis l'activité précédente
        score = getIntent().getIntExtra("score", 0);

        // Initialisation des composants graphiques
        bNext = findViewById(R.id.next_button); // Correspond à l'ID du bouton dans ton layout XML
        rg = findViewById(R.id.options_group); // ID du RadioGroup contenant les options

        // Gestion du clic sur "Suivant"
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérifie si une option est sélectionnée
                if (rg.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(),
                            "Vous devez choisir une réponse", Toast.LENGTH_SHORT).show();
                } else {
                    // Récupère la réponse sélectionnée
                    rb = findViewById(rg.getCheckedRadioButtonId());
                    String selectedText = rb.getText().toString().trim();

                    // Compare la réponse choisie avec la bonne réponse
                    if (selectedText.equalsIgnoreCase(correctAnswer)) {
                        score++;
                    }

                    // Passe à l'activité suivante (Quiz4)
                    Intent intent = new Intent(Quize3.this, Quize4.class);
                    intent.putExtra("score", score);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
