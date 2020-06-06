package Modele;

import Controlleur.customDate;
import Controlleur.Controlleur;
import java.util.ArrayList;

/**
 *
 * @author KASYC Cyrille
 * @author LECOEUR Titouan
 * @author RASSOUW Clement
 */
public class seance {
    
    private int id, semaine, etat;
    private customDate date, heureDebut, heureFin;
    private cours cours;
    private Type_cours type;
    private ArrayList<groupe> groupes;
    private ArrayList<salle> salles;
    private ArrayList<utilisateur> enseignants;
    
    /**
     * Créer une séance a partir de ce qui est passé en parametres
     * @param _id (int)
     * @param _semaine (int)
     * @param _date (String)
     * @param _debut (String)
     * @param _fin (String)
     * @param _etat (int)
     * @param _cours (cours)
     * @param _type (Type_cours)
     * @param _groupes (ArrayList(groupe))
     * @param _salles (ArrayList(salle))
     * @param _enseignants (ArrayList(utilisateur))
     */
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
    
    /**
     * Créer une séance a partir de ce qui est passé en parametres
     * @param _id (int)
     * @param _semaine (int)
     * @param _date (customDate)
     * @param _debut (customDate)
     * @param _fin (customDate)
     * @param _etat (int)
     * @param _cours (cours)
     * @param _type (Type_cours)
     * @param _groupes (ArrayList(groupe))
     * @param _salles (ArrayList(salle))
     * @param _enseignants (ArrayList(utilisateur))
     */
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
    
    /**
     * Créer une séance a partir de son ID en allant chercher les informations dans la BDD
     * @param id (int)
     */
    public seance(int id) {
        this(ModeleSQL.getSeance(id));
    }
    
    /**
     * Copie la séance passée en parametres dans celle actuelle
     * @param _tmpSalle 
     */
    private seance(seance _tmpSeance) {
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
    
    /**
     * 
     * @return id de la séance (int)
     */
    public int getID() { return this.id; }
    
    /**
     * 
     * @return Semaine de la séance (int)
     */
    public int getSemaine() { return this.semaine; }
    
    /**
     * 
     * @return Date de la séance (customDate)
     */
    public customDate getDate() { return this.date; }
    
    /**
     * 
     * @return l'heure de début (customDate)
     */
    public customDate getDebut() {  return this.heureDebut; }
    
    /**
     * 
     * @return l'heure de fin (customDate)
     */
    public customDate getFin() { return this.heureFin; }
    
    /**
     * 
     * @return l'état (validée, annulée, etc) sous forme de int
     */
    public int getEtat() { return this.etat; }
    
    /**
     * 
     * @return l'état (validée, annulée, etc) sous forme de string
     */
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
    
    /**
     * 
     * @return (cours)
     */
    public cours getCours() { return this.cours; }
    
    /**
     * 
     * @return (Type_cours)
     */
    public Type_cours getType() { return this.type; }
    
    /**
     * 
     * @return ArrayList(groupe) liste des groupes participants a la séance
     */
    public ArrayList<groupe> getGroupes() { return this.groupes; }
    
    /**
     * 
     * @return ArrayList(salle) liste des salles de la séance
     */
    public ArrayList<salle> getSalles() { return this.salles; }
    
    /**
     * 
     * @return ArrayList(utilisateur) liste des enseignants de la séance
     */
    public ArrayList<utilisateur> getEnseignants() { return this.enseignants; }
    
    /**
     * 
     * @return Transforme la classe en string pour l'afficher
     */
    @Override
    public String toString() {
        return "["+ id +", "+ semaine +", "+ date +", "+ heureDebut +", "+ heureFin +", "+ etat +", "+ cours.getNom() +", "+ type.getType() + ", " + groupes + ", " + salles + ", " + enseignants + "]";
    };
    
    /**
     * Permet d'avoir la durée du cours en heure décimale (2h30 = 2,5) (double)
     * @return (double)
     */
    public double duration() {
        return Controlleur.heureToDouble(heureFin.toString()) - Controlleur.heureToDouble(heureDebut.toString());
    }
}
