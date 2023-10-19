package vue;



import java.util.InputMismatchException;
import java.util.Scanner;

public class Ihm {

    private String nom_joueur1;
    private String nom_joueur2;
    private String choix;
    private int choixAwale;
    private final Character[] lettres = {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', ' '};
    private String choixMode;
    private int choixDimension_x;
    private int choixDimension_y;

    public Ihm() {

        while(true) {
            try {
                System.out.print("Nom du joueur 1 : ");
                nom_joueur1 = new Scanner(System.in).next();
                if(nom_joueur1.equals("Ordinateur")){
                    throw new Exception("Nom indisponible ! ");
                }
                System.out.print("A quel jeu voulez vous jouer ? (Othello / Awale) ");
                String input = new Scanner(System.in).next().toUpperCase();
                if (input.equals("AWALE")) {
                    choixMode = "4";
                    System.out.print("Nom du joueur 2 : ");
                    nom_joueur2 = new Scanner(System.in).next();
                    break;
                }
                else if (input.equals("OTHELLO")) {
                    System.out.print("Contre qui voulez vous jouer? (J2 / IA) ");
                    input = new Scanner(System.in).next().toUpperCase();
                    if (input.equals("J2")) {
                        choixMode = "1";
                        System.out.print("Nom du joueur 2 : ");
                        nom_joueur2 = new Scanner(System.in).next();
                        if(nom_joueur2.equals("Ordinateur") || nom_joueur2.equals(nom_joueur1)){
                            throw new Exception("Nom indisponible ! ");
                        }
                        break;
                    } else if (input.equals("IA")) {
                        while (true) {
                            System.out.print("Contre quel IA souhaitez-vous jouer ? Minimax ou Naive (M/N) ? ");
                            String input1 = new Scanner(System.in).next().toUpperCase();
                            if (input1.equals("N")) {
                                System.out.println("Vous avez selectionné l'IA Naïve ! ");
                                choixMode = "2";
                                break;
                            } else if (input1.equals("M")) {
                                choixMode = "3";
                                System.out.println("Vous avez selectionné l'IA Minimax !");
                                break;

                            }  //else {
                            //    throw new Exception("Valeur saisie incorrecte, Veuillez réitérer : ");
                            //}
                        }
                        break;
                    }
                }
                else {
                    throw new Exception("Valeur saisie incorrecte, Veuillez réitérer : ");}
            }
            catch (Exception e){
                System.out.print(e.getMessage());
            }
        }
    }


    //permet de récupérer la position demandée par le joueur
    public void tour(String nom_joueur, boolean b){
        if(b){
            style(); //style affichage en console
            Scanner choix=new Scanner(System.in);
            System.out.print(nom_joueur+" à vous de jouer. Saisir une ligne entre 1 et "+choixDimension_x+" suivie d’une\n" +
                    "lettre entre A et "+lettres[choixDimension_y]+" (ex : 3D) ou P pour passer son tour : ");
            this.choix = choix.next().toUpperCase();
        }
    }

    public void tourAwaleIhm(String nom_joueur){
        style(); //style affichage en console
        Scanner choixAwale=new Scanner(System.in);
        System.out.print(nom_joueur+" à vous de jouer.\n");
        int val;

        while (true) {
            System.out.print("Veuillez saisir un entier entre 1 et 6 : ");
            if (choixAwale.hasNextInt()) { // vérifie si l'utilisateur a saisi un entier
                val = choixAwale.nextInt();
                if (val > 0 && val <= 6) {
                    break;
                }
            } else {
                choixAwale.next();
            }
        }
        this.choixAwale = val;
    }

    private int test_dim(Scanner dim) throws Exception {
        int i=0;
        if(!(dim.hasNextInt())){
            throw new InputMismatchException("Vous n'avez pas saisi d'entier, veuillez recommencer : ");
        }
        else {
            i=dim.nextInt();
            if(i%2!=0){
                throw new Exception("Vous avez choisi un entier impair, veuillez saisir un entier pair : ");
            }
            else if(i<4){
                throw new Exception("Vous avez choisi un entier inferieur à 4, veuillez saisir un entier superieur ou égal à 4 : ");
            }
            else if(i>8){
                throw new Exception("Vous avez choisi un entier superieur à 8, veuillez saisir un entier inferieur ou égal à 8 : ");
            }
        }
        return i;
    }

    public void setChoixDimension(){
        while(true){
            try{
                System.out.println("Veuillez regler la taille du plateau :");

                System.out.print("x : ");
                Scanner dim=new Scanner(System.in);
                int n=test_dim(dim);
                choixDimension_x=n;

                System.out.print("y : ");
                dim=new Scanner(System.in);
                n=test_dim(dim);
                choixDimension_y=n;
                break;

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }
    public void style(){
        System.out.println("--------------------"); //style affichage en console
    }

    //affichage du nbs de pions sur le plateau du joueur selectionné
    public void etat_joueur(String joueur1, int pions){
        System.out.println(joueur1+" a "+pions+" pions placé");

    }

    // Si 0 saisit alors résumé des scores, si 1 permet de relancer une partie
    public int res_partie(String nom_joueur1,String nom_joueur2, int j1, int j2) {
        Scanner choix = new Scanner(System.in);
        int val = 0;
        while (true) {
            try {
                do {
                    val = choix.nextInt();
                    if (val == 0) {
                        if (j1 == j2) {
                            System.out.println("ex æquo entre " + nom_joueur1 + " et " + nom_joueur2);
                        } else {
                            System.out.println(nom_joueur1 + " a gagné : " + j1 + " partie(s)");
                            System.out.println(nom_joueur2 + " a gagné : " + j2 + " partie(s)");
                        }
                        break;
                    } else if (val == 1) {
                        System.out.println("Vous avez décidés de refaire une partie.");
                        break;
                    }
                    System.out.println("Valeur saisie incorrecte");
                } while (val != 0 || val != 1);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Valeur saisie incorrecte, veuillez saisir un entier.");
                choix.next();
            }
        }
        return val;
    }

    public void position_erreur () {
        Exception e = new Exception("Position incorrect.");
        System.out.println(e.getMessage());
    }



    public void passage_error() {
        Exception e = new Exception("Passage de tour impossible, un coup est possible");
        System.out.println(e.getMessage());
    }

    public void score(String j, int nbPionsJoueurGagnant, int nbPionsJoueurPerdant){
        System.out.println("Partie terminée !" + "\n" +  "Victoire de : " + j + " avec " +
                nbPionsJoueurGagnant + " pions");
    }
    public void score2(){
        System.out.println("Partie terminée ! Ex æquo");
    }

    public void score_final(){
        System.out.println("Pour rejouer saisissez 1 sinon 0");
    }

    //méthodes Get et affichage du plateau
    public String getNom_joueur1() {
        return nom_joueur1;
    }

    public String getNom_joueur2() {
        return nom_joueur2;
    }

    public String getChoixMode(){
        return choixMode;
    }
    public String getChoix() {
        // if(choix.equals("P")){
        //  System.out.println("Vous passez votre tour");
        // }
        return choix;
    }

    public int getChoixAwale() {
        return choixAwale;
    }

    public void IAnotif(String pos){
        if(pos.equals("P")){
            System.out.println("L'ordinateur passe son son tour");
        }
        else{
            System.out.println("L'ordinateur a joué " + pos);
        }
    }

    public void afficher_plateau(String plateau){
        System.out.println("\n"+plateau);
    }

    public void passerTourAwale() {
        System.out.println("Vous passez votre tour");
    }

    public int getChoixDimension_x(){
        return choixDimension_x;
    }

    public int getChoixDimension_y(){
        return choixDimension_y;
    }
}
