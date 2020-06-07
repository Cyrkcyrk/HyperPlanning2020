/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author Cyrille
 */
public class recapitulatif {
    cours monCours;
    int nombreSeance, nbrSeancePassee;
    String duree;
    seance premiere, derniere;
    
    public recapitulatif (cours _monCours, int _nbr, int _passee, String _duree, seance _premiere, seance _derniere) {
        monCours = _monCours;
        nombreSeance = _nbr;
        nbrSeancePassee = _passee;
        duree = _duree;
        premiere = _premiere;
        derniere = _derniere;
    }
    
    public cours getCours() { return monCours; }
    
    public int getNbrSeance() { return nombreSeance; }
    
    public int getNbrSeancePassee() { return nbrSeancePassee; }
    
    public String getDuree() { return duree; }
    
    public seance getPremiere() { return premiere; }
    
    public seance getDerniere() { return derniere; }
    
    
    @Override
    public String toString() {
        return  "[" + monCours + ", "+nombreSeance+", "+duree+", "+premiere+", "+derniere+"]";
    }
    
    
}
