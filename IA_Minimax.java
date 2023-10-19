package modele;

import java.util.List;
import java.util.Map;

public class IA_Minimax implements IA {
    private String nom;
    private String couleur;

    public IA_Minimax(String nom, String couleur) {
        this.nom = nom;
        this.couleur = couleur;

    }

    @Override
    public String fonctionnement(Map<String, List<String>> c, Plateau p) {
        return null;
    }


    @Override
    public String fonctionnement(Plateau plateau, String coupPrecedent, String historique) {
        Arbre a = new Arbre(plateau, coupPrecedent, historique);
        a.extension(a.getArbre(), 3);
        a.minmax(a.getArbre());
        return a.choixMinmax();
    }
    @Override

    public String getNom(){
        return nom;
    }
    @Override

    public String getCouleur(){
        return couleur;
    }
}









