package modele;

import java.util.*;

public class Awale {

    private String[][] plat;

    public Map<String, Integer> grainesPlace;
    private List<String> parcours = Arrays.asList("00", "10", "11", "12", "13", "14", "15", "05", "04", "03", "02", "01");
    private List<String> rangée_j1 = Arrays.asList("10", "11", "12", "13", "14", "15");
    private List<String> rangée_j2 = Arrays.asList("05", "04", "03", "02", "01", "00");

    public Awale() {
        plat = new String[2][6];
        grainesPlace = new TreeMap<>();
        String pos = "";
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                pos = "" + i + "" + j;
                grainesPlace.put(pos, 4);
                plat[i][j] = pos;
            }
        }
    }

    public void setVal(Map<String, Integer> l, String pos, int val) {
        for (String s : l.keySet()) {
            if (s.equals(pos)) {
                l.put(pos, val);
            }
        }
    }

    private boolean isAffame(String couleur, String lastPosElement) {
        int cptGraines = 0;
        if (couleur.equals("⚫") && lastPosElement.equals("00")) {
            for (int j = 0; j < 6; j++) {
                String pos = "0" + j;
                if (grainesPlace.get(pos) == 1 || grainesPlace.get(pos) == 2) {
                    cptGraines++;
                }
            }
        } else if (couleur.equals("⚪") && lastPosElement.equals("15")) {
            for (int j = 0; j < 6; j++) {
                String pos = "1" + j;
                if (grainesPlace.get(pos) == 1 || grainesPlace.get(pos) == 2) {
                    cptGraines++;
                }
            }
        }
        return (cptGraines == 6);
    }
    public boolean parcoursGraines(int pos, String couleurJ, Joueur j) {
        // Il faut récup quel joueur joue (joueur 1 ou 2)
        int nvpos = 0;
        Map<String, List<String>> v1 = new HashMap<>();
        Map<String, List<String>> v2 = new HashMap<>();
        Map<String, Integer> temp;
        Map<Integer, Map<String, Integer>> cptGraines;

        v1.put(couleurJ, rangée_j2);
        // v2.put(couleurJ, rangée_j1);
        if (couleurJ.equals("⚫")) {
            nvpos = 1;
            v1.put(couleurJ, rangée_j1);
            // v2.put(couleurJ, rangée_j2);
        }
        String posDuJoueur = "" + nvpos + "" + pos;
        // System.out.println("posDuJoueur : " + posDuJoueur);
        int nbGrainesEnlevees = grainesPlace.get(posDuJoueur);
        // System.out.println("nbGrainesEnlevees : " + nbGrainesEnlevees);
        int indiceDepart = parcours.indexOf(posDuJoueur) + 1;
        int nbGrainesRestantes = nbGrainesEnlevees / 11;
        int cpt = nbGrainesEnlevees + nbGrainesRestantes;
        temp = new HashMap<>();
        cptGraines = new HashMap<>();

        for (int i = 0; i < nbGrainesEnlevees + nbGrainesRestantes; i++) {
            int index = (indiceDepart + i) % parcours.size();
            String element = parcours.get(index);

            temp = new HashMap<>();
            temp.put(element, grainesPlace.get(element));
            cptGraines.put(cpt, temp);

            if (!(element.equals(posDuJoueur))) {
                // System.out.println(element);
                if (!(isAffame(couleurJ,parcours.get((parcours.indexOf(posDuJoueur) + nbGrainesEnlevees + nbGrainesRestantes)%parcours.size())))) {
                    if (cpt == 1) {
                        if ((grainesPlace.get(element) == 1 || grainesPlace.get(element) == 2) && !(v1.get(couleurJ).contains(element))) {
                            j.ajouter_graines(grainesPlace.get(element) + 1);
                            setVal(grainesPlace, element, 0);
                            cpt++;
                            for (int k = 0 ; k < cptGraines.size() - 1; k++) {
                                if (((cptGraines.get(cpt)).get(parcoursMap(cptGraines, cpt)) == 1
                                        || (cptGraines.get(cpt)).get(parcoursMap(cptGraines, cpt)) == 2)
                                        && !(v1.get(couleurJ).contains(parcoursMap(cptGraines, cpt)))) {
                                    j.ajouter_graines(grainesPlace.get(parcoursMap(cptGraines, cpt)));
                                    setVal(grainesPlace, parcoursMap(cptGraines, cpt), 0);
                                    cpt++;
                                }
                                // v1.get(couleur).contains(""+nvpos))
                            }
                        } else {
                            setVal(grainesPlace, element, grainesPlace.get(element) + 1);
                        }
                    } else {
                        setVal(grainesPlace, element, grainesPlace.get(element) + 1);
                    }
                } else {
                    return false;
                }
            }
            // System.out.println(element + " : " + cpt);
            --cpt;
        }
        System.out.println(cptGraines);
        setVal(grainesPlace, posDuJoueur, 0);
        return true;
    }

    public String parcoursMap(Map<Integer, Map<String, Integer>> m, int indice) {
        for (Integer i : m.keySet()) {
            if (i == indice) {
                for (String s : m.get(i).keySet()) {
                    return s;
                }
            }
        }
        return "";
    }

    public Map<String, List<String>> coupPotentielAwale(String couleurJoueur) {
        Map<String, List<String>> listARetourner = new HashMap<>();
        List<String> temp = new ArrayList<>();

        if (couleurJoueur.equals("⚫")) {
            for (int j = 0; j < 6; j++) {
                if ((grainesPlace.get(plat[1][j])) != 0) {
                    temp.add("1" + (j));

                }
            }
        } else if (couleurJoueur.equals("⚪")) {
            for (int j = 0; j < 6; j++) {
                if ((grainesPlace.get(plat[0][j])) != 0) {
                    temp.add("0" + (j));
                }
            }
        }

        listARetourner.put(couleurJoueur, temp);
        return listARetourner;
    }
    /*
     * public void actualise_nb_graines(Joueur j, int numero) {
     * String pos = "";
     * int cpt = 0;
     * if (numero == 1) {
     * pos += 1;
     * } else {
     * pos += 0;
     * }
     * String postmp = "";
     * for (int i = 0; i < 6; i++) {
     * postmp = pos + "" + i;
     * cpt += grainesPlace.get(postmp);
     * }
     * j.ajouter_pions(cpt);
     * }
     */

    public String afficher() {
        String pos = "{";
        int cpt = 0;
        for (String s : grainesPlace.keySet()) {
            if (cpt == 6) {
                pos += "\n";
                cpt = 0;
            }
            pos += " " + s + " : " + grainesPlace.get(s) + ";";
            cpt++;
        }
        return pos + "}";
    }

    public String toString() {
        int cpt = 0;
        String affichage = "";
        affichage += ("-----------------" +
                "--------" + "\n");
        for (String[] S : plat) {
            for (String s : S) {
                affichage += ("| " + grainesPlace.get(s) + " ");
                if (cpt == 5) {
                    affichage += ("|");
                }
                cpt++;
            }
            affichage += ("\n");
            cpt = 0;
        }
        affichage += ("-----------------" +
                "--------" + "\n");
        return affichage;
    }
}
