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
public class Type_cours {
    private int id;
    private String type;
    
    /**
     * Créer un type de cours (CM, TD, TP...)
     * @param _id id du type cours dans la BDD
     * @param _type String désignant le type de cours
     */
    public Type_cours(int _id, String _type) {
        this.id = _id;
        this.type = _type;
    }
    
    /**
     * Créer un type de cours (CM, TD, TP...)
     * @param _id id du type cours dans la BDD=
     */
    public Type_cours(int id) {
        this(Modele.getTypeCours(id)); 
    }
    
    /**
     * Copie le type cours passé en parametre dans celui actuel.
     * @param _tmpCours 
     */
    public Type_cours(Type_cours _tmpCours) {
        this.id = _tmpCours.getID();
        this.type = _tmpCours.getType();
    }
    /**
     * @return l'id du type cours
     */
    public int getID() { return this.id; }
    /**
     * @return String du nom
     */
    public String getType() { return this.type; }
    
    
    @Override
    public String toString() {
        return "[" + id + ", " + type + "]";
    }
}
