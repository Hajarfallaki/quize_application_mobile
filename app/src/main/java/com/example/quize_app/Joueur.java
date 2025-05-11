package com.example.quize_app;

public class Joueur {
    private String nom;
    private int score;

    public Joueur() {
        // Constructeur par défaut nécessaire pour Firestore
    }

    public Joueur(String nom, int score) {
        this.nom = nom;
        this.score = score;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
