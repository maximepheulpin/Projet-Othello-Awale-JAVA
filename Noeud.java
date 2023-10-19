package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Noeud {
    private Joueur joueur;
    private Plateau etat_jeu;

    private String position;

    private List<Noeud> enfants;

    private int gain=0;

    private Noeud pere=null;

    private int hauteur=0;

    public Noeud(Plateau etat_jeu,Joueur joueur,String position){
        this.etat_jeu=etat_jeu;
        this.joueur=joueur;
        this.position=position;
        enfants=new ArrayList<>();

    }

    public int getHauteur(){
        if(this==null){
            return 0;
        }
        return hauteur;
    }

    public void setHauteur(int n){
        if(this!=null){
            hauteur=n;
        }
    }
    public void setPere(Noeud pere){
        if(this!=null){
            this.pere=pere;
        }
    }


    public Noeud getPere(){
        return pere;
    }


    public void setGain(int g){
        gain=g;
    }

    public int max(){
        if(this==null) {
            return 0;
        }
        if(!enfants.isEmpty()){
            int temp=enfants.get(0).gain;
            for(Noeud n:enfants){
                if(n.gain>=temp){
                    temp=n.gain;
                }
            }
            return temp;
        }
        return gain;
    }


    public int min(){
        if(this==null) {
            return 0;
        }
        if(!enfants.isEmpty()){
            int temp=enfants.get(0).gain;

            for(Noeud n:enfants){
                if(n.gain<=temp){
                    temp=n.gain;
                }
            }
            return temp;
        }
        return gain;
    }
    public Joueur getJoueur() {
        return joueur;
    }

    public Plateau getEtat_jeu() {

        return etat_jeu;
    }

    public int getGain(){
        if(this==null){
            return 0;
        }
        return gain;
    }

    public List<Noeud> getEnfants() {
        return enfants;
    }

    public String getPosition() {
        return position;
    }

    public void ajouterEnfants(Noeud n) {
        enfants.add(n);
    }

    public String toString(){

        return "noeud : { " + "clé : "+position+ " , niveau : "+hauteur+ " , gain : "+gain+", enfant :::> "+enfants+ " <:::" +"}";
    }
}


