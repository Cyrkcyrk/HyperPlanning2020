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
    
    public salle (int _id, String _Nom, int _Capacite, String _Site) {
        this.id = _id;
        this.Nom = _Nom;
        this.Capacite = _Capacite;
        this.Site = _Site;
    }
    
    public salle(salle _tmpSalle) {
        this.id = _tmpSalle.getID();
        this.Nom = _tmpSalle.getNom();
        this.Capacite = _tmpSalle.getCapacite();
        this.Site = _tmpSalle.getSite();
    }
    
    public salle (int id)
    {
        this(Modele.getSalle(id));
    }
    
    public int getID () { return this.id; }
    public String getNom () { return this.Nom; }
    public int getCapacite () { return this.Capacite; }
    public String getSite () { return this.Site; }
    
    @Override
    public String toString() {
        return "[" + this.id + ", " + this.Nom + ", " + this.Capacite + ", " + this.Site + "]";
    }
}
