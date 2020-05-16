/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_class;

import java.util.ArrayList;
import hyperplanning.Modele;
/**
 *
 * @author Cyrille
 */
public class seance {
    
    private int id, semaine;
    private String date, heureDebut, heureFin, etat, cours, type;
    //private ArrayList<Object> enseignants, groupes, salles;
    ArrayList<groupe> groupes;
    
    
    public seance(
            int _id, 
            int _semaine,
            String _date,
            String _debut,
            String _fin,
            String _etat,
            String _cours,
            String _type,
            ArrayList<groupe> _groupes
    ) {
        this.id         = _id;
        this.semaine    = _semaine;
        this.date       = _date;
        this.heureDebut = _debut;
        this.heureFin   = _fin;
        this.etat       = _etat;
        this.cours      = _cours;
        this.type       = _type;
        this.groupes    = _groupes;
    }
    
    public seance(int id) {
        this(Modele.getSeance(id));
    }
    
    private seance(DB_class.seance _tmpSeance) {
        this.id         = _tmpSeance.getID();
        this.semaine    = _tmpSeance.getSemaine();
        this.date       = _tmpSeance.getDate();
        this.heureDebut = _tmpSeance.getDebut();
        this.heureFin   = _tmpSeance.getFin();
        this.etat       = _tmpSeance.getEtat();
        this.cours      = _tmpSeance.getCours();
        this.type       = _tmpSeance.getType();
        this.groupes    = _tmpSeance.getGroupes();
    }
    
    public int getID() { return this.id; }
    public int getSemaine() { return this.semaine; }
    public String getDate() { return this.date; }
    public String getDebut() {  return this.heureDebut; }
    public String getFin() { return this.heureFin; }
    public String getEtat() { return this.etat; }
    public String getCours() { return this.cours; }
    public String getType() { return this.type; }
    public ArrayList<groupe> getGroupes() { return this.groupes; }
    
    @Override
    public String toString() {
        return "["+ id +", "+ semaine +", "+ date +", "+ heureDebut +", "+ heureFin +", "+ etat +", "+ cours +", "+ type + "]";
    };

    /*public seance(int id) {
        seance _tmpSeance = Modele.getSeance(id);
        this.id         = _tmpSeance.getID();
        this.semaine    = _tmpSeance.getSemaine();
        this.date       = _tmpSeance.getDate();
        this.heureDebut = _tmpSeance.getDebut();
        this.heureFin   = _tmpSeance.getFin();
        this.etat       = _tmpSeance.getEtat();
        this.cours      = _tmpSeance.getCours();
        this.type       = _tmpSeance.getType();
    }*/
}
