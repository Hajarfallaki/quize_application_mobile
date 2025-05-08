package com.example.quize_app;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etemail, etpassword;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser les éléments de l'interface utilisateur
        etemail = findViewById(R.id.email);
        etpassword = findViewById(R.id.password);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        // Gestionnaire de clic pour le bouton "Sign in"
        button.setOnClickListener(v -> {
            String email = etemail.getText().toString().trim();
            String password = etpassword.getText().toString().trim();

            // Validation des champs
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérification des informations d'identification
            if (email.equals("hajar") && password.equals("2002")) {
                Intent intent = new Intent(MainActivity.this, Quize1.class); // Redirection vers Quiz1
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Login or password Incorrect", Toast.LENGTH_SHORT).show();
            }
        });

        // Gestionnaire de clic pour le texte "to register click here"
        textView.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Redirecting to Register Page...", Toast.LENGTH_SHORT).show();
            // Vous pouvez ajouter ici une redirection vers une autre activité si nécessaire
        });
    }
}