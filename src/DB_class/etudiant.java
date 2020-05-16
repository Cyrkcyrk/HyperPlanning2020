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
public class etudiant extends utilisateur {
    private int numeroEtudiant;
    private groupe Groupe;
    
    
    public etudiant(utilisateur _tmpUtilisateur, int _numeroEtudiant, groupe _Groupe) {
        super(_tmpUtilisateur);
        this.numeroEtudiant = _numeroEtudiant;
    }
    
    public etudiant(int _id, String _email, String _nom, String _prenom, String _password, int _droits, int _numeroEtudiant, groupe _Groupe) {
        super(_id, _email, _nom, _prenom, _password, _droits);
        this.numeroEtudiant = _numeroEtudiant;
        this.Groupe = _Groupe;
    }
    
    public etudiant(int id) {
        this(Modele.getEtudiant(id));
    }
    
    public etudiant (etudiant _tmpEtudiant) {
        super(_tmpEtudiant.getID(),
              _tmpEtudiant.getEMail(),
              _tmpEtudiant.getNom(),
              _tmpEtudiant.getPrenom(),
              _tmpEtudiant.getPassword(),
              _tmpEtudiant.getDroits());
        
        this.numeroEtudiant = _tmpEtudiant.getNumeroEtudiant();
        this.Groupe = _tmpEtudiant.getGroupe();
    }
    
    public int getNumeroEtudiant () { return this.numeroEtudiant; }
    public groupe getGroupe () { return this.Groupe; }
    
    @Override
    public String toString() {
        return "[" + id + ", " + nom + ", " + prenom + ", " + email + ", " + password + ", " + droits + ", " + numeroEtudiant + ", " + Groupe + "]";
    }
}
