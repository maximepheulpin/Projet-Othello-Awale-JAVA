package modele;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IA_Naive implements IA{

    private String nom;
    private String couleur;


    public IA_Naive(String nom, String couleur) {
        this.nom = nom;
        this.couleur = couleur;

    }
    @Override

    public String fonctionnement(Map<String, List<String>> coupPossible, Plateau p){
        int interval_coup=(int)(Math.random()*coupPossible.size());
        List<String> ListCoup=coupPossible.keySet().stream().toList();
        System.out.println(ListCoup);
        String coup="";
        if(coupPossible.size()>0){
            coup=ListCoup.get(interval_coup);
        } else{
            coup="P";
        }
        return coup;
    }

    @Override
    public String fonctionnement(Plateau plateau, String coupPrecedent, String historique) {
        return null;
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
