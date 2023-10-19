package modele;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Arbre {
    private Noeud arbre;


    public Arbre(Plateau p,String position,String couleur){

        Plateau copie=p.copiePlateau();

        Map<String, List<String>> copieCoupPossibles = copie.coupPotentiel(couleur);
        String couleurTrad;

        if (couleur.equals("⚪")) {
            couleurTrad = "blanc";
        } else {
            couleurTrad = "noir";
        }

        arbre=new Noeud(copie,new Joueur("Ombre",couleurTrad),position);
    }

    public Noeud getArbre(){
        return arbre;
    }



    public void extension(Noeud noeud,int profondeurr){
        noeud.setHauteur(profondeurr);
        //On créer un plateau par rapport au noeud placé.
        Plateau simulation = noeud.getEtat_jeu().copiePlateau();
        String couleurAdversaire ;
        String cc;
        --profondeurr;
        //System.out.println(profondeurr);

        //System.out.println("simulation 1");

        //System.out.println(noeud.getJoueur().getNom());
        //System.out.println(noeud.getJoueur().nb_pions());

        //System.out.println(simulation);

        //System.out.println("simulation 2");

        //On regarde les coups du Joueur opposé.
        Map<String, List<String>> simulationCoupPossiblesAdversaire = new HashMap<>();

        //Condition qui permet d'alterner Joueur courant/opposé.
        if (noeud.getJoueur().getCouleur().equals("⚪")) {
            couleurAdversaire = "noir";
            cc="⚫";
        } else {
            couleurAdversaire = "blanc";
            cc="⚪";
        }

        //On regarde les coups du Joueur opposé
        simulationCoupPossiblesAdversaire.putAll(simulation.coupPotentiel(cc));
        //System.out.println(noeud.getPosition()+" : "+noeud.getJoueur().getCouleur()+ " : " + cc + " : " +simulationCoupPossiblesAdversaire.keySet());

        //Pour tout les coups possible du Joueur, on créer un plateau et on simule le coup.
        if(profondeurr>0){
            for (String poss : simulationCoupPossiblesAdversaire.keySet()) {
                Plateau simulation_bis = simulation.copiePlateau();
                simulation_bis.ChangementCouleur(cc, poss, simulationCoupPossiblesAdversaire.get(poss));
                Noeud enfant=new Noeud(simulation_bis,new Joueur("shadow",couleurAdversaire),poss);

                noeud.ajouterEnfants(enfant);
            }
            for (Noeud p:noeud.getEnfants()) {
                //System.out.println("enfant 1");
                //System.out.println(p.getEtat_jeu());
                //System.out.println("enfant 2");

                extension(p,profondeurr);
                if (p.getHauteur()==1){
                    //System.out.println("Pere : "+noeud.getPere().getPosition());
                    p.setGain(_fonctionEvaluation(p));
                }
            }

        }
        //On parcourt tout les enfants du noeud et on applique récursivement la méthode.
    }



    public void minmax(Noeud n){
        boolean min;
        if(n.getJoueur().getNom().equals("Ombre")){
            min=true;
        }
        else {
            min=false;
        }

        for(Noeud enfant:n.getEnfants()){
            if(enfant.getEnfants().size()==0){
                if(min){
                    n.setGain(n.max());
                }
                else{
                    n.setGain(n.min());
                }
            }
            else{
                minmax(enfant);
                n.setGain(n.max());
            }
        }
    }

    public String choixMinmax(){

        int valeur=arbre.getGain();
        for(Noeud enfant: arbre.getEnfants()){
            if(enfant.getGain()==valeur){
                return enfant.getPosition();
            }
        }
        return "P";
    }


    //Fonction d'évaluation simple.

    private int _fonctionEvaluation(Noeud noeud) {
        int score = 0;
        int n = noeud.getEtat_jeu().getN();
        int p = noeud.getEtat_jeu().getP();
        if (noeud.getEtat_jeu().nb_pionPlace() == n * p - 1) {
            if (noeud.getEtat_jeu().coupPotentiel(noeud.getJoueur().getCouleur()).isEmpty() && noeud.getPosition().equals("P")) {
                return -1000;
            } else {
                return 1000;
            }
        } else {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= p; j++) {
                    if (noeud.getEtat_jeu().getCase(i,j).equals(noeud.getJoueur().getCouleur())) {
                        if ((i == 1 || i == noeud.getEtat_jeu().n) && (j == 1 || j == noeud.getEtat_jeu().p)) {
                            return 11;
                        } else if (i == 1 || i == noeud.getEtat_jeu().n || j == 1 || j == noeud.getEtat_jeu().p) {
                            return 6;
                        } else {
                            return 1;
                        }
                    }
                }
            }
        }
        return score;
    }
}

