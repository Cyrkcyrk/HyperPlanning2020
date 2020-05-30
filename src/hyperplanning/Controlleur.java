/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning;
import DB_class.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

/**
 *
 * @author Cyrille
 */
public class Controlleur {
    
    
    
    public static double heureToDouble(String stringHeure)
    {
        String pattern = "(\\d+):(\\d+)(:\\d+)?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(stringHeure);
        
        double retour = 0;
        if(m.find()){
            double dec = Integer.parseInt(m.group(2));
            dec = dec/60;
            retour = Integer.parseInt(m.group(1)) + dec;
        }
        else
        {
            System.out.println("Pas trouvé");
        }
        return retour;
    }
    
    public static String heureToString(Date heure) {
        String h = new SimpleDateFormat("HH:mm").format(heure);
        return h;
    }
    
    public static String dateToString (Date jour) {
        String d = DateFormat.getDateInstance(DateFormat.FULL, new Locale("fr","FR")).format(jour);
        return d;
    }
    
    public static Date stringToHeure(String inputHours) {
        Date heure;
        
        try {
            heure = new SimpleDateFormat("HH:mm:ss", new Locale("FR", "fr")).parse(inputHours);
        } catch (Exception e) {
            System.out.println(e);
            heure = new Date();
        }
        return heure;
    }
    
    public static Date stringToDate(String inputDate) {
        Date jour;
        try {
            jour = new SimpleDateFormat("yyyy/MM/dd").parse(inputDate);
        } catch (Exception e) {
            System.out.println(e);
            jour = new Date();
        }
        return jour;
    }
    
    public static ArrayList<String> isSeanceGood(seance maSeance) {
        ArrayList<String> returnArray = new ArrayList<String>();
        
        ArrayList<utilisateur> _enseignants = maSeance.getEnseignants();
        for(int i=0; i<_enseignants.size(); i++) {
            
            boolean Reponse = Modele.isUserAvailable( _enseignants.get(i).getID(), maSeance.getDate(), maSeance.getDebut(), maSeance.getFin(), maSeance.getID());
            System.out.println(Reponse);
            if(!Reponse) {
                returnArray.add("Enseignant: " + _enseignants.get(i).getPrenom() + " " + _enseignants.get(i).getNom() + " n'est pas disponible sur l'entieretée du cours.");
            }
            
            boolean EnseigneMatiere = false;
            ArrayList<cours> _matieres = Modele.EnseignantMatieres(_enseignants.get(i).getID());
            for(int j=0; j<_matieres.size(); j++) {
                if(_matieres.get(j).getID() == maSeance.getCours().getID()) {
                    EnseigneMatiere = true;
                }
            }
            
            if(!EnseigneMatiere) {
                returnArray.add("Enseignant:  " + _enseignants.get(i).getPrenom() + " " + _enseignants.get(i).getNom() + " n'enseigne pas cette matière.");
            }
        }
        
        ArrayList<salle> _salles = maSeance.getSalles();
        for(int i=0; i<_salles.size(); i++) {
            if(!Modele.isClassAvailable( _salles.get(i).getID(), maSeance.getDate(), maSeance.getDebut(), maSeance.getFin(), maSeance.getID())) {
                returnArray.add("Salle: " + _salles.get(i).getSite() + " " + _salles.get(i).getNom() + " n'est pas disponible sur l'entieretée du cours.");
            }
        }
        
        ArrayList<groupe> _groupes = maSeance.getGroupes();
        for(int i=0; i<_groupes.size(); i++) {
            if(!Modele.isGroupeAvailable( _groupes.get(i).getID(), maSeance.getDate(), maSeance.getDebut(), maSeance.getFin(), maSeance.getID())) {
                returnArray.add("Groupe: " + _groupes.get(i).getPromotion() + " " + _groupes.get(i).getNom() + " n'est pas disponible sur l'entieretée du cours.");
            }
        }
        
        
        return returnArray;
    }
    
    public static void saveSeance(seance s) {
        if(s.getID() <= 0) {
            Modele.InsererSeance(s);
        }
        else{
            Modele.ChangerSeance(s);
        }
    }
}
