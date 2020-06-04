/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_class;

import java.util.ArrayList;
import hyperplanning.*;
/**
 *
 * @author Cyrille
 */
public class seance {
    
    private int id, semaine, etat;
    private customDate date, heureDebut, heureFin;
    //private ArrayList<Object> enseignants, groupes, salles;
    private cours cours;
    private Type_cours type;
    private ArrayList<groupe> groupes;
    private ArrayList<salle> salles;
    private ArrayList<utilisateur> enseignants;
    
    public seance(
            int _id, 
            int _semaine,
            String _date,
            String _debut,
            String _fin,
            int _etat,
            cours _cours,
            Type_cours _type,
            ArrayList<groupe> _groupes,
            ArrayList<salle> _salles,
            ArrayList<utilisateur> _enseignants
    ) {
        this(
            _id,
            _semaine,
            new customDate("jour", _date),
            new customDate("heure", _debut),
            new customDate("heure", _fin),
            _etat,
            _cours,
            _type,
            _groupes,
            _salles,
            _enseignants
        );
    }
    
    public seance(
            int _id, 
            int _semaine,
            customDate _date,
            customDate _debut,
            customDate _fin,
            int _etat,
            cours _cours,
            Type_cours _type,
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
    public customDate getDate() { return this.date; }
    public customDate getDebut() {  return this.heureDebut; }
    public customDate getFin() { return this.heureFin; }
    public int getEtat() { return this.etat; }
    public String getEtatString() {
        switch (etat) {
            case 0:
                return "En cours de validation";
            case 1:
                return "Validé";
            case 2:
                return "Annulé";
            default:
                return "Erreur";
        }
    }
    public cours getCours() { return this.cours; }
    public Type_cours getType() { return this.type; }
    public ArrayList<groupe> getGroupes() { return this.groupes; }
    public ArrayList<salle> getSalles() { return this.salles; }
    public ArrayList<utilisateur> getEnseignants() { return this.enseignants; }
    
    @Override
    public String toString() {
        return "["+ id +", "+ semaine +", "+ date +", "+ heureDebut +", "+ heureFin +", "+ etat +", "+ cours.getNom() +", "+ type.getType() + ", " + groupes + ", " + salles + ", " + enseignants + "]";
    };
    
    
    public double duration() {
        return Controlleur.heureToDouble(heureFin.toString()) - Controlleur.heureToDouble(heureDebut.toString());
    }
}
