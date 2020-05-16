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
    private ArrayList<groupe> groupes;
    private ArrayList<salle> salles;
    private ArrayList<utilisateur> enseignants;
    
    public seance(
            int _id, 
            int _semaine,
            String _date,
            String _debut,
            String _fin,
            String _etat,
            String _cours,
            String _type,
            ArrayList<groupe> _groupes,
            ArrayList<salle> _salles,
            ArrayList<utilisateur> _enseignants
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
        this.salles     = _salles;
        this.enseignants= _enseignants;
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
        this.salles     = _tmpSeance.getSalles();
        this.enseignants= _tmpSeance.getEnseignants();
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
    public ArrayList<salle> getSalles() { return this.salles; }
    public ArrayList<utilisateur> getEnseignants() { return this.enseignants; }
    
    @Override
    public String toString() {
        return "["+ id +", "+ semaine +", "+ date +", "+ heureDebut +", "+ heureFin +", "+ etat +", "+ cours +", "+ type + ", " + groupes + ", " + salles + ", " + enseignants + "]";
    };
}
