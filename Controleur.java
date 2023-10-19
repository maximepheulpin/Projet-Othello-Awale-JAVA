package controleur;

import vue.Ihm;
import modele.*;

import java.util.*;

public class Controleur {
    private final Ihm ihm;
    private Joueur joueur1;
    private Joueur joueur2;
    private Plateau plateau;

    private Awale plat;
    private Map<String, String> historique = new HashMap<>();

    private String coupPrecedent="";

    private IA ia;
    public Controleur(Ihm ihm) {

        this.ihm = ihm;
        joueur1 = new Joueur(ihm.getNom_joueur1(), "noir");
        if (ihm.getChoixMode().equals("1")) {
            joueur2 = new Joueur(ihm.getNom_joueur2(), "blanc");
        } else if (ihm.getChoixMode().equals("2")) {
            ia = new IA_Naive("Ordinateur", "blanc");
            joueur2 = new Joueur(ia.getNom(), ia.getCouleur());

        } else if (ihm.getChoixMode().equals("3")) {
            ia = new IA_Minimax("Ordinateur", "blanc");
            joueur2 = new Joueur(ia.getNom(), ia.getCouleur());

        } else if (ihm.getChoixMode().equals("4")) {
            joueur2 = new Joueur(ihm.getNom_joueur2(), "blanc");
        }
    }

    public void ctlChangementCouleur(Joueur joueur, String pos, Map<String, List<String>> mesCoup) {
        plateau.ChangementCouleur(joueur.getCouleur(), pos, mesCoup.get(pos));
    }

    private int tourOthello(int cpt, Joueur joueur) {
        String pos;
        String couleur;
        couleur = joueur.getCouleur();
        ihm.tour(joueur.getNom(), !joueur.getNom().equals("Ordinateur"));
        if (joueur.getNom().equals("Ordinateur")) {
            pos = "";
            if (ihm.getChoixMode().equals("2")) {
                pos = ia.fonctionnement(plateau.coupPotentiel(couleur), getPlateau());
                ihm.IAnotif(pos);
            } else if (ihm.getChoixMode().equals("3")) {
                pos=ia.fonctionnement(plateau,coupPrecedent,historique.get(coupPrecedent));
                //System.out.println("arbre : "+a.getArbre());
                //System.out.println("choix de l'ia: "+pos);
                //pos = ((Minimax) joueur).construct(plateau,3,null);
                ihm.IAnotif(pos);
            }
        } else {
            pos = ihm.getChoix();
        }

        if (pos.equals("P") && plateau.coupPotentiel(couleur).isEmpty()) {
            cpt++;
            coupPrecedent=pos;
            historique.put(pos,couleur);
            return cpt;
        } else if (pos.equals("P") && !plateau.coupPotentiel(couleur).isEmpty()) {
            ihm.passage_error();
        } else {
            if (!plateau.coupPotentiel(couleur).containsKey(pos)) {
                ihm.position_erreur();
            } else {
                historique.put(pos,couleur);
                //System.out.println(plateau.coupPotentiel(couleur));
                ctlChangementCouleur(joueur, pos, plateau.coupPotentiel(couleur));
                joueur.ajouterPion(pos);
                //System.out.println(joueur.getNom());

                //System.out.println(plateau.calcul_nb_pions(couleur));
                cpt++;
            }
        }
        coupPrecedent=pos;

        return cpt;
    }

    // methode principal qui permet le déroulement du jeu répartissant les saisis
    // des joueurs depuis l'ihm
    // vers les différentes méthodes du modele

    public void othello(Joueur j1, Joueur j2) {
        int debut = 1;
        while (debut == 1) {
            int cpt = 0;
            j1.ajouterPion("4D");
            j2.ajouterPion("4E");
            j1.ajouterPion("5D");
            j2.ajouterPion("5E");

            ihm.setChoixDimension();
            plateau = new Plateau(ihm.getChoixDimension_x(), ihm.getChoixDimension_y());
            plateau.afficher();

            // Boucle permettant le deroulement de la partie tant que les deux joueurs on
            // des coups possibles sur le plateau
            while ((!plateau.coupPotentiel(j1.getCouleur()).isEmpty()
                    || !plateau.coupPotentiel(j2.getCouleur()).isEmpty())
                    && (plateau.nb_pionPlace() < ihm.getChoixDimension_x() * ihm.getChoixDimension_y())) {
                ihm.afficher_plateau(plateau.toString());
                ihm.etat_joueur(j1.getNom(), plateau.calcul_nb_pions(j1.getCouleur()));
                ihm.etat_joueur(j2.getNom(), plateau.calcul_nb_pions(j2.getCouleur()));
                if (cpt % 2 == 0) {
                    cpt = tourOthello(cpt, j1);

                } else {
                    cpt = tourOthello(cpt, j2);
                }
            }

            ihm.afficher_plateau(plateau.toString());
            if ((plateau.calcul_nb_pions(j1.getCouleur())) > plateau.calcul_nb_pions(j2.getCouleur())) {
                j1.Augmenter_Nb_partie();
                ihm.score(j1.getNom(), plateau.calcul_nb_pions(j1.getCouleur()),
                        plateau.calcul_nb_pions(j2.getCouleur()));
            } else if ((plateau.calcul_nb_pions(j1.getCouleur())) < plateau.calcul_nb_pions(j2.getCouleur())) {
                j2.Augmenter_Nb_partie();
                ihm.score(j2.getNom(), plateau.calcul_nb_pions(j2.getCouleur()),
                        plateau.calcul_nb_pions(j1.getCouleur()));
            } else {
                ihm.score2();
            }
            ihm.score_final();
            debut = ihm.res_partie(j1.getNom(), j2.getNom(),
                    j1.getPartie_gagnées(), j2.getPartie_gagnées());
        }
    }

    public void awale(Joueur j1, Joueur j2) {
        int debut = 1;
        while (debut == 1) {
            plat = new Awale();
            int cpt = 0;

            while (!(plat.coupPotentielAwale(j1.getCouleur()).isEmpty() || plat.coupPotentielAwale(j2.getCouleur()).isEmpty())) {
                if (j1.getNb_graines() > 24 || j2.getNb_graines() > 24) {
                    break;
                }
                // plat.actualise_nb_graines(j1,1); plat.actualise_nb_graines(j2,2);
                System.out.println(plat);
                System.out.println(j1.getNom() + " possede " + j1.getNb_graines() + " graines");
                System.out.println(j2.getNom() + " possede " + j2.getNb_graines() + " graines");
                if (cpt % 2 == 0) {
                    cpt = tourAwale(cpt, j1);
                } else {
                    cpt = tourAwale(cpt, j2);
                }
            }

            if (j1.getNb_graines() > j2.getNb_graines()) {
                j1.setNb_graines(48 - j2.getNb_graines());
                j1.Augmenter_Nb_partie();
                ihm.score(j1.getNom(), j1.getNb_graines(), j2.getNb_graines());
            } else if (j1.getNb_graines() < j2.getNb_graines()) {
                j1.setNb_graines(48 - j1.getNb_graines());
                j2.Augmenter_Nb_partie();
                ihm.score(j2.getNom(), j2.getNb_graines(), j1.getNb_graines());
            } else {
                ihm.score2();
            }
            ihm.score_final();
            debut = ihm.res_partie(j1.getNom(), j2.getNom(), j1.getPartie_gagnées(), j2.getPartie_gagnées());
        }
    }

    private int tourAwale(int cpt, Joueur joueur) {
        int pos;
        String couleur;
        couleur = joueur.getCouleur();
        ihm.tourAwaleIhm(joueur.getNom());
        pos = ihm.getChoixAwale() - 1;
        int nvpos = 0;
        if (couleur.equals("⚫")) {
            nvpos = 1;
        }
        String posDuJoueur = "" + nvpos + "" + (pos);
        if (!(plat.coupPotentielAwale(couleur).isEmpty())) {
            if (plat.coupPotentielAwale(couleur).get(couleur).contains(posDuJoueur)) {
                if (plat.parcoursGraines(pos, couleur, joueur)) {
                    ctlDeplacerGraines(pos, couleur, joueur);
                    cpt++;
                } else {
                    ihm.position_erreur();
                }
            } else {
                ihm.position_erreur();
            }
        } else {
            ihm.passerTourAwale();
            cpt++;
        }
        return cpt;
    }

    private void ctlDeplacerGraines(int pos, String couleur, Joueur j) {
        plat.parcoursGraines(pos, couleur, j);
    }

    public void jouer() {
        if (ihm.getChoixMode().equals("1") || ihm.getChoixMode().equals("2") || ihm.getChoixMode().equals("3")) {
            othello(joueur1, joueur2);
        } else {
            awale(joueur1, joueur2);
        }
    }

    public Plateau getPlateau() {
        return plateau;
    }
}
