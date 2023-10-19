package main;

import controleur.Controleur;
import vue.Ihm;

public class Main {
    public static void main(String[] args) {

        Ihm ihm = new Ihm();
        Controleur controleur = new Controleur(ihm);
        controleur.jouer();
    }
}