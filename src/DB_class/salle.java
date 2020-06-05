/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_class;

import hyperplanning.Modele;

/**
 *
 * @author Cyrille
 */
public class salle {
    private String Nom, Site;
    private int id, Capacite;
    
    /**
     * Créer une salle en fonction des parametres
     * @param _id (int)
     * @param _Nom (String)
     * @param _Capacite (int)
     * @param _Site (String)
     */
    public salle (int _id, String _Nom, int _Capacite, String _Site) {
        this.id = _id;
        this.Nom = _Nom;
        this.Capacite = _Capacite;
        this.Site = _Site;
    }
    
    /**
     * Copie la salle passée en parametres dans celle actuelle
     * @param _tmpSalle 
     */
    public salle(salle _tmpSalle) {
        this.id = _tmpSalle.getID();
        this.Nom = _tmpSalle.getNom();
        this.Capacite = _tmpSalle.getCapacite();
        this.Site = _tmpSalle.getSite();
    }
    
    /**
     * Créer une salle a partir de son ID en allant chercher les informations dans la BDD
     * @param id (int)
     */
    public salle (int id)
    {
        this(Modele.getSalle(id));
    }
    
    /**
     * 
     * @return ID du groupe (int)
     */
    public int getID () { return this.id; }
    
    /**
     * 
     * @return Nom de la salle (P330, G009, etc) (String)
     */
    public String getNom () { return this.Nom; }
    
    /**
     * 
     * @return Capacitée maximum de la salle (int)
     */
    public int getCapacite () { return this.Capacite; }
    
    /**
     * 
     * @return Site sur lequel se trouve la salle (E1, E2, etc) (String)
     */
    public String getSite () { return this.Site; }
    
    @Override
    public String toString() {
        return "[" + this.id + ", " + this.Nom + ", " + this.Capacite + ", " + this.Site + "]";
    }
}
