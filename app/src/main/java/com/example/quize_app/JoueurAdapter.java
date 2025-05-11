package com.example.quize_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class JoueurAdapter extends RecyclerView.Adapter<JoueurAdapter.JoueurViewHolder> {

    private List<Joueur> joueurList;
    private Context context;

    public JoueurAdapter(List<Joueur> joueurList) {
        this.joueurList = joueurList;
    }

    @Override
    public JoueurViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_joueur, parent, false);
        return new JoueurViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JoueurViewHolder holder, int position) {
        Joueur joueur = joueurList.get(position);
        holder.nomJoueur.setText(joueur.getNom());
        holder.scoreJoueur.setText(String.valueOf(joueur.getScore()));

        // Action pour le bouton supprimer
        holder.btnSupprimer.setOnClickListener(v -> {
            // Supprimer le joueur de Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("joueurs")
                    .whereEqualTo("nom", joueur.getNom())  // Recherche par nom
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Trouver le document et le supprimer
                            String docId = queryDocumentSnapshots.getDocuments().get(0).getId();
                            db.collection("joueurs").document(docId).delete()
                                    .addOnSuccessListener(aVoid -> {
                                        // Supprimer du RecyclerView
                                        joueurList.remove(position);
                                        notifyItemRemoved(position);
                                        Toast.makeText(context, "Joueur supprimé", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(context, "Erreur de suppression", Toast.LENGTH_SHORT).show());
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(context, "Erreur de suppression", Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    public int getItemCount() {
        return joueurList.size();
    }

    public class JoueurViewHolder extends RecyclerView.ViewHolder {

        TextView nomJoueur;
        TextView scoreJoueur;
        Button btnSupprimer;

        public JoueurViewHolder(View itemView) {
            super(itemView);
            nomJoueur = itemView.findViewById(R.id.nom_joueur);
            scoreJoueur = itemView.findViewById(R.id.score_joueur);
            btnSupprimer = itemView.findViewById(R.id.btn_supprimer);
        }
    }
}
