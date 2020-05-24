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
public class cours {
    private int id;
    private String nom;
    
    
    public cours(int _id, String _cours) {
        this.id = _id;
        this.nom = _cours;
    }
    
    public cours(int id) {
        this(Modele.getCours(id));
    }
    
    public cours (cours _tmpCours) {
        this.id = _tmpCours.getID();
        this.nom = _tmpCours.getNom();
    }
    
    public int getID () { return this.id; }
    public String getNom () { return this.nom; }
    
    @Override
    public String toString() {
        return "[" + id + ", " + nom + "]";
    }
}
