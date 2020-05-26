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
    
    
    public Type_cours(int _id, String _type) {
        this.id = _id;
        this.type = _type;
    }
    
    public Type_cours(int id) {
        this(Modele.getTypeCours(id)); 
    }
    
    public Type_cours(Type_cours _tmpCours) {
        this.id = _tmpCours.getID();
        this.type = _tmpCours.getType();
    }
    
    public int getID() { return this.id; }
    public String getType() { return this.type; }
    
    @Override
    public String toString() {
        return "[" + id + ", " + type + "]";
    }
}
