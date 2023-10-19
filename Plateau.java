package modele;

import java.util.*;

public class Plateau {
    public final String[][] plateau;
    private Character[] lettres = {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', ' '};
    public Map<String, String> pionsPlace;
    private Map<String, List<String>[]> ListeAdj;//Liste de l'ensemble de pion adjacent au pion joueur avec List Ligne[...],Colonne[...],Diagonale1[..],Diagonale2[...]
    public final int n,p;


    public Plateau(int n,int p) {
        this.n=n;
        this.p=p;
        plateau = new String[n+2][p+2];
        String[] para_l=new String[]{" ", " A", " B ", "C ", "D ", "E ", "F ", "G ", "H ", ""};
        plateau[0][0] = " ";
        plateau[n+1][0] = " ";
        plateau[0][p+1] = "";
        plateau[n+1][p+1] = "";
        for(int k=1;k<=p;k++){
            plateau[0][k] = para_l[k];
            plateau[n+1][k] = para_l[k];
        }
        for (int i = 1; i < n+1; i++) {
            plateau[i][0] = ""+i;
            plateau[i][p+1] = ""+i;
            for (int j = 1; j < p+1; j++) {
                plateau[i][j] = "\uD83D\uDFE9";
            }
        }
        pionsPlace = new HashMap<>();
        ListeAdj = new HashMap<>();
        int pas_x=n/2;
        int pas_y=p/2;
        placer(getStringid(new int[]{pas_x,pas_y}), "⚫");
        placer(getStringid(new int[]{pas_x,pas_y+1}), "⚪");
        placer(getStringid(new int[]{pas_x+1,pas_y}), "⚪");
        placer(getStringid(new int[]{pas_x+1,pas_y+1}), "⚫");
    }

    public String toString() {
        StringBuilder affichage = new StringBuilder();
        affichage.append("---------------"+"\n");
        for (String[] S: plateau) {
            for (String s : S) {
                affichage.append(s);
            }
            affichage.append("\n");
        }
        affichage.append("---------------"+"\n");
        return affichage.toString();
    }

    public int[] getIdpos(String pos) {
        int Ligne = Character.getNumericValue(pos.charAt(0));
        int Colonne = 0;
        for (int i = 0; i < lettres.length; i++) {
            if (lettres[i] == pos.charAt(1)) {
                Colonne = i;
            }
        }
        return new int[]{Ligne, Colonne};
    }


    private String getStringid(int[] idpos) {
        return "" + idpos[0] + lettres[idpos[1]];
    }


    //détermine l'ensemble des positions présentes sur la mm ligne de la position demandée
    private List<String> getLigne(String pos) {
        int Ligne = getIdpos(pos)[0];
        List<String> ListesL = new ArrayList<>();
        for (int j = 1; j < p+1; j++) {
            ListesL.add(getStringid(new int[]{Ligne, j}));
        }
        return ListesL;
    }

    //détermine l'ensemble des positions présentes sur la mm colonne de la position demandée
    private List<String> getColonne(String pos) {
        int Colonne = getIdpos(pos)[1];

        List<String> ListesC = new ArrayList<>();

        for (int i = 1; i < n+1; i++) {
            ListesC.add(getStringid(new int[]{i, Colonne}));
        }
        return ListesC;
    }


    public <K, V> Map<K, V> copieMap(Map<K, V> original) {
        Map<K, V> copy = new HashMap<>();
        for (Map.Entry<K, V> entry : original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue());
        }
        return copy;
    }

    //détermine l'ensemble des positions présentes sur la 1ere diagonale de la position demandée
    private List<String> getDia1(String pos) {//haut-->bas
        int i = getIdpos(pos)[0], j = getIdpos(pos)[1];
        List<String> ListesD1 = new ArrayList<>();
        while (true) {
            if (i > n) {
                break;
            }
            if (j < 1) {
                break;
            }
            i++;
            j--;
        }
        i--;
        j++;
        while (true) {
            if (i < 1) {
                break;
            }
            if (j > p) {
                break;
            }
            ListesD1.add(getStringid(new int[]{i--, j++}));
        }
        return ListesD1;
    }

    //détermine l'ensemble des positions présentes sur la 2eme diagonale de la position demandée
    private List<String> getDia2(String pos) {//bas-->haut
        int i = getIdpos(pos)[0], j = getIdpos(pos)[1];
        List<String> ListesD2 = new ArrayList<>();
        while (true) {
            if (i > n) {
                break;
            }
            if (j > p) {
                break;
            }
            i++;
            j++;
        }
        i--;
        j--;
        while (true) {
            if (i < 1) {
                break;
            }
            if (j < 1) {
                break;
            }
            ListesD2.add(getStringid(new int[]{i--, j--}));
        }
        return ListesD2;
    }


    // création de la matrice d'adjacence de la position demandée par le joueur
    private void ajouter_listeAdj(String pos) {
        List<String> colonne = getColonne(pos);
        List<String> ligne = getLigne(pos);
        List<String> diag1 = getDia1(pos);
        List<String> diag2 = getDia2(pos);
        List<String>[] pionAdj = new List[]{ligne, colonne, diag1, diag2};

        ListeAdj.put(pos, pionAdj);
    }

    public void placer(String pos, String couleur) {
        int Ligne = getIdpos(pos)[0];
        int Colonne = getIdpos(pos)[1];
        ajouter_listeAdj(pos);
        plateau[Ligne][Colonne] = couleur;
        pionsPlace.put(pos, couleur);
    }

    //permet l'affichage de la matrice d'adjacence des pions posés sur la plateau
    public void afficher() {
        StringBuilder s = new StringBuilder();
        Set<String> fgh = new HashSet<>(pionsPlace.keySet());
        for(String t: fgh){
            s.append(getCouleur(t)).append(" ").append(t).append("--> ").append(getIdpos(t)[0]).append(getIdpos(t)[1]).append(" : ");
            for (List<String> r:ListeAdj.get(t)) {
                    s.append(r.toString());
                }
            s.append("\n");
        }
        //System.out.println(s);
    }

    //obtenir la couleur à partir de la position d'un pion
    private String getCouleur(String pos) {
        int i=getIdpos(pos)[0];
        int j=getIdpos(pos)[1];
        return plateau[i][j];
    }

    //retourne le nbs de pions posés sur le plateau du joueur


    public int nb_pionPlace(){
        return pionsPlace.size();
    }

    public Map<String, List<String>> coupPotentiel(String couleur) {
        List<String> potentiality = new ArrayList<>();
        List<String> peutChanger = new ArrayList<>();
        int index_element;
        int index_debut;
        Map<String,List<String>> coupP=new HashMap<>();

        // Ajoute pions de la meme couleur que le coup considérer dans l'ensemble "potentiality"
        for (String e : pionsPlace.keySet()) {
            if (getCouleur(e).equals(couleur)) {
                potentiality.add(e);
            }
        }
        for(int i=1;i<n+1;i++){
            for(int j=1;j<p+1;j++){
                if(plateau[i][j].equals("\uD83D\uDFE9")){
                    String pos = getStringid(new int[]{i,j});
                    List<String> pionAChanger = new ArrayList<>();
                    for (String coup : potentiality) {
                        List<String>[] Liste = ListeAdj.get(coup);
                        for (List<String> L : Liste) {
                            if (L.contains(pos)) {
                                List<String> composante = L;
                                index_element = composante.indexOf(pos);
                                index_debut = composante.indexOf(coup);
                                List<String> temp=new ArrayList<>();
                                if (0 <= index_element && index_element < p && index_element > index_debut) {
                                    for (int n = index_element - 1; n > index_debut; n--) {
                                        String position=composante.get(n);
                                        if (getCouleur(position).equals("\uD83D\uDFE9") || getCouleur(position).equals(couleur)) {
                                            temp.clear();
                                            break;
                                        } else {
                                            temp.add(position);
                                        }
                                    }
                                } else if (0 <= index_element && index_element < p && index_element < index_debut) {
                                    for (int m = index_element + 1; m < index_debut; m++) {
                                        String position=composante.get(m);
                                        if (getCouleur(position).equals("\uD83D\uDFE9") || getCouleur(position).equals(couleur)) {
                                            temp.clear();
                                            break;
                                        } else {
                                            temp.add(position);
                                        }
                                    }
                                }
                                pionAChanger.addAll(temp);
                            }
                        }

                    }
                    if(!pionAChanger.isEmpty()){
                        coupP.put(pos,pionAChanger);
                        peutChanger.add(pos);
                    }
                }
            }
        }
        // Pour chaque pion, vérifier s'il peut changer des pions adverses
        return coupP;
    }

    //calcul le nbs de pions sur le plateau, en tps réel de chaque joueur
    public int calcul_nb_pions(String couleur){
        int cpt = 0;
        for (String a : pionsPlace.keySet()){
            if (pionsPlace.get(a).equals(couleur)){
                cpt++;
            }
        }
        return cpt;
    }

    public void ChangementCouleur(String couleur, String pos,List<String> aChanger) {
        placer(pos, couleur);
        for (String coup : aChanger) {
            placer(coup, couleur);
        }
    }

    public  Plateau copiePlateau(){
        Plateau cp= new Plateau(n,p);

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= p; j++) {
                cp.plateau[i][j] = plateau[i][j];
            }
        }

        cp.lettres=Arrays.copyOf(lettres,lettres.length);
        cp.pionsPlace=new HashMap<>();
        cp.ListeAdj=new HashMap<>();

        for(String key:ListeAdj.keySet()){
            cp.ListeAdj.put(key,ListeAdj.get(key));
        }
        for(String key:pionsPlace.keySet()){
            cp.pionsPlace.put(key,pionsPlace.get(key));
        }
        return cp;
    }

    public String getCase(int i, int j) {
        return plateau[i][j];
    }

    public int getN(){
        return n;
    }

    public int getP() {
        return p;
    }
}