package modele;

import java.util.*;

public class Joueur {

    private int nb_graines;
    private final String nom;
    private final String couleur;
    private Set<String> mesPions;
    private int nb_partie_gagnées;


    public Joueur(String nom, String couleur) {
        if (couleur.equals("noir")) {
            this.couleur = "⚫";
        } else {
            this.couleur = "⚪";
        }

        nb_graines = 0;
        this.nom = nom;
        mesPions = new HashSet<>();
        nb_partie_gagnées = 0;
    }


    //ajout d'un pion et actualisation dans les infos du joueur
    public void ajouterPion(String pos) {
        mesPions.add(pos);
    }
    public String getNom() {
        return nom;
    }
    public String getCouleur() {
        return couleur;
    }
    public void Augmenter_Nb_partie(){
        this.nb_partie_gagnées += 1;
    }
    public int getPartie_gagnées(){
        return nb_partie_gagnées;
    }

    public int getNb_graines() {
        return nb_graines;
    }

    public void setNb_graines(int nb_graines) {
        this.nb_graines = nb_graines;
    }

    public void ajouter_graines(int nb){
        this.nb_graines += nb;
    }
}
