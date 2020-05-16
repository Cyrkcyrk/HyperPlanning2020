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
public class groupe {
    String Nom, Promotion;
    int id;
    
    public groupe (int _id, String _Nom, String _Promotion) {
        this.id = _id;
        this.Nom = _Nom;
        this.Promotion = _Promotion;
    }
    
    public groupe(groupe _tmpGroupe) {
        this.id = _tmpGroupe.getID();
        this.Nom = _tmpGroupe.getNom();
        this.Promotion = _tmpGroupe.getPromotion();
    }
    
    public groupe (int id)
    {
        this(Modele.getGroupe(id));
    }
    
    public int getID () { return this.id; }
    public String getNom () { return this.Nom; }
    public String getPromotion () { return this.Promotion; }
    
    @Override
    public String toString() {
        return "[" + this.id + ", " + this.Nom + ", " + this.Promotion + "]";
    }
    
}
