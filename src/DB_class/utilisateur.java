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
public class utilisateur {
    
    protected int id, droits;
    protected String email, password, nom, prenom;
    
    public utilisateur (int _id, String _email, String _nom, String _prenom, String _password, int _droits) {
        this.id = _id;
        this.nom = _nom;
        this.prenom = _prenom;
        this.email = _email;
        this.password = _password;
        this.droits = _droits;
    }
    
    public utilisateur(utilisateur _tmpUtilisateur) {
        this.id = _tmpUtilisateur.getID();
        this.nom = _tmpUtilisateur.getNom();
        this.prenom = _tmpUtilisateur.getPrenom();
        this.email = _tmpUtilisateur.getEMail();
        this.password = _tmpUtilisateur.getPassword();
        this.droits = _tmpUtilisateur.getDroits();
    }
    
    public utilisateur (int id)
    {
        this(Modele.getUtilisateur(id)); 
    }
    
    public int getID () { return this.id; }
    public String getNom () { return this.nom; }
    public String getPrenom () { return this.prenom; }
    public String getEMail () { return this.email; }
    public String getPassword () { return this.password; }
    public int getDroits () { return this.droits; }
    
    @Override
    public String toString() {
        return "[" + id + ", " + nom + ", " + prenom + ", " + email + ", " + password + ", " + droits + "]";
    }
    
}
