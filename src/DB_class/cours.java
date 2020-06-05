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
    
    /**
     * Créer un cours (Informatique, Electronique...)
     * @param _id id du type cours dans la BDD
     * @param _type String désignant le type de cours
     */
    public cours(int _id, String _cours) {
        this.id = _id;
        this.nom = _cours;
    }
    
    /**
     * Créer un cours (Informatique, Electronique...)
     * @param _id id du type cours dans la BDD
     */
    public cours(int id) {
        this(Modele.getCours(id));
    }
    
    /**
     * Copie le cours passé en parametre dans celui là
     * @param _tmpCours Type cours
     */
    public cours (cours _tmpCours) {
        this.id = _tmpCours.getID();
        this.nom = _tmpCours.getNom();
    }
    
    /**
     * 
     * @return id du cours (int)
     */
    public int getID () { return this.id; }
    /**
     * 
     * @return Nom du cours (String)
     */
    public String getNom () { return this.nom; }
    
    @Override
    public String toString() {
        return "[" + id + ", " + nom + "]";
    }
}
