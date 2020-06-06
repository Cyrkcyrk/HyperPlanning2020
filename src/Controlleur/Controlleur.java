package Controlleur;

import Vue.*;
import Modele.ModeleSQL;
import Modele.groupe;
import Modele.seance;
import Modele.cours;
import Modele.salle;
import Modele.utilisateur;
import Vue.ConnexionPanel;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;

/**
 *
 * @author KASYC Cyrille
 * @author LECOEUR Titouan
 * @author RASSOUW Clement
 */
public class Controlleur {
    private final Controlleur ceControlleur = this;
    private final Vue maVue;
    private JPanel monEDT;
    private SearchPanel controlPanel;
    private utilisateur connectedUser = null;
    private boolean affichageGrille = true;
    private String EtatAAfficher = "1";
    
    
    private String SelectedEDT = "self";
    private int SelectedSemaine, SelectedYear,
                selectedSalleID = 0,
                selectedEnseignantID = 0,
                selectedGroupeID = 0;
                
    /**
     * Créer un controlleur (gestionnaire de tout le projet)
     */
    public Controlleur() {
        
        SelectedSemaine = (int) Integer.parseInt(new SimpleDateFormat("w").format(new Date()));
        SelectedYear = (int) Integer.parseInt(new SimpleDateFormat("YYYY").format(new Date()));
        
        maVue = new Vue();
        maVue.changeMainPanel(new JScrollPane(new ConnexionPanel(this)));
    }
    
    /**
     * 
     * @return Retourne la vue (Vue)
     */
    public Vue getVue() { return maVue; }
    
    /**
     * 
     * @return les droits de l'utilisateur connecté (int)
     */
    public int getDroits() { 
        if(connectedUser == null) 
            return 0;
        else
            return connectedUser.getDroits();
    }
    
    /**
     * Affiche les informations détaillées dans la vue de la séance passée en parametres
     * @param s (seance)
     */
    public void ShowSeanceInformations(seance s) {
         maVue.changeRightPanel(new SeancePanel(this, s, "rightPanel"));
    }
    
    /**
     * Ferme le panel de droite dans la vue
     */
    public void closeRightPanel() {
        maVue.closeRightPanel();
    }
    
    /**
     * Ferme le panel de gauche dans la vue
     */
    public void closeLeftPanel() {
        maVue.closeLeftPanel();
    }
    
    /**
     * Ouvre le panel d'edition/création de séance dans la vue et y met les informations de la séance passée en parametres
     * @param s séance a modifier
     */
    public void editSeance(seance s) {
        JScrollPane _tmp = new JScrollPane(new SeanceCreation(this, 200, s), 
                                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        maVue.changeLeftPanel(_tmp);
        maVue.closeRightPanel();
    }
    
    /**
     * Ouvre le panel d'édition/création de séances dans la vue.
     */
    public void createNewSeance() {
        JScrollPane _tmp = new JScrollPane(new SeanceCreation(this, 200), 
                                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        maVue.changeLeftPanel(_tmp);
    }
    /**
     * Sauvegarde la séance passée en parametres dans la BDD si elle est valide.
     * @param s (séance) a sauvegarder
     */
    public void saveSeance(seance s) {
        
        ArrayList<String> erreur = Controlleur.isSeanceGood(s);
        if(erreur.size() <= 0) {
            System.out.println("Pas d'erreurs");
            if(s.getID() <= 0) {
                ModeleSQL.InsererSeance(s);
            }
            else{
                ModeleSQL.ChangerSeance(s);
            }
            
            refreshTimetable();
            maVue.closeLeftPanel();
        }
        else {
            ShowError(erreur);
        }
    }
    
    /**
     * Change l'état de la séance passée en parametres par l'état passé en parametres
     * @param s Séance a modifier
     * @param _etat Etat a mettre (int)
     */
    public void changerEtatSeance(seance s, int _etat) {
        
        if(_etat == 1) {
            ArrayList<String> erreur = Controlleur.isSeanceGood(s);
            if(erreur.size() <= 0) {
                System.out.println("Pas d'erreurs");
                
                ModeleSQL.updateEtatSeance(s.getID(), _etat);
                refreshTimetable();
                maVue.closeLeftPanel();
                maVue.closeRightPanel();
            }
            else {
                ShowError(erreur);
            }
        }
        else if(_etat == 0 || _etat == 2) {
            ModeleSQL.updateEtatSeance(s.getID(), _etat);
            refreshTimetable();
            maVue.closeLeftPanel();
            maVue.closeRightPanel();
        }
        else {
            ShowError("Etat '"+ _etat + "' incorrecte");
        }
    }
    
    /**
     * 
     * @param _conUser (utilisateur) Set l'utilisateur passé en parametres
     */
    public void setConnectedUser(utilisateur _conUser) { connectedUser = _conUser; }
    
    /**
     * 
     * @return (utilisateur) Renvoie l'utilisateur connecté enregistré.
     */
    public utilisateur getConnectedUser() { return connectedUser; }
    
    /**
     * Change l'affichage de la vue une fois connecté
     */
    public void connected() {
        
        //maVue.changeMainControlPanel(new SearchPanel(ModeleSQL.getAllProfs(), "enseignant", 21));
        //maVue.changeMainControlPanel(new SearchPanel(ModeleSQL.getAllSalles(), "salle", SelectedSemaine));
        //maVue.changeMainControlPanel(new SearchPanel(ModeleSQL.getAllGroupes(), "groupe", 21));
        //maVue.changeMainControlPanel(new SearchPanel(21));
        
        maVue.changeNavbar(new NavbarPanel(this));
        refreshControlPanel();
        refreshTimetable();
    }
    
    /**
     * Actualise la barre de recherche/controle au dessus de l'EDT
     */
    public void refreshControlPanel() {
        switch(SelectedEDT) {
            case "self":{
                controlPanel = new SearchPanel(this, SelectedSemaine, SelectedYear);
                break;
            }
            case "salle": {
                controlPanel = new SearchPanel(this, ModeleSQL.getAllSalles(), "salle", selectedSalleID, SelectedSemaine, SelectedYear);
                break;
            }
            case "groupe": {
                controlPanel = new SearchPanel(this, ModeleSQL.getAllGroupes(), "groupe", selectedGroupeID, SelectedSemaine, SelectedYear);
                break;
            }
            case "enseignant": {
                controlPanel = new SearchPanel(this, ModeleSQL.getAllProfs(), "enseignant", selectedEnseignantID, SelectedSemaine, SelectedYear);
                break;
            }
        }
        maVue.changeMainControlPanel(controlPanel);
    }
    
    /**
     * Actualise l'EDT par rapport aux stockages
     */
    public void refreshTimetable() {
        if(affichageGrille)
            refreshTimetableGrille();
        else
            refreshTimetableListe();
            
    }
    
    /**
     * Actualise l'EDT sous un format de grille
     */
    private void refreshTimetableGrille() {
        switch(SelectedEDT) {
            case "self":{
                monEDT = new Timetable(ceControlleur, ModeleSQL.SeanceParUtilisateur(connectedUser.getID(), SelectedSemaine, EtatAAfficher));
                break;
            }
            case "salle": {
                monEDT = new Timetable(ceControlleur, ModeleSQL.SeanceParSalle(selectedSalleID, SelectedSemaine, EtatAAfficher));
                break;
            }
            case "groupe": {
                monEDT = new Timetable(ceControlleur, ModeleSQL.SeanceParGroupe(selectedGroupeID, SelectedSemaine, EtatAAfficher));
                break;
            }
            case "enseignant": {
                monEDT = new Timetable(ceControlleur, ModeleSQL.SeanceParUtilisateur(selectedEnseignantID, SelectedSemaine, EtatAAfficher));
                break;
            }
        }
        maVue.changeMainPanel(new JScrollPane(monEDT));
    }
    
    /**
     * Actualise l'EDT sous forme de liste
     */
    private void refreshTimetableListe() {
        switch(SelectedEDT) {
            case "self":{
                monEDT = new TimetableListe(ceControlleur, ModeleSQL.SeanceParUtilisateur(connectedUser.getID(), SelectedSemaine, EtatAAfficher));
                break;
            }
            case "salle": {
                monEDT = new TimetableListe(ceControlleur, ModeleSQL.SeanceParSalle(selectedSalleID, SelectedSemaine, EtatAAfficher));
                break;
            }
            case "groupe": {
                monEDT = new TimetableListe(ceControlleur, ModeleSQL.SeanceParGroupe(selectedGroupeID, SelectedSemaine, EtatAAfficher));
                break;
            }
            case "enseignant": {
                monEDT = new TimetableListe(ceControlleur, ModeleSQL.SeanceParUtilisateur(selectedEnseignantID, SelectedSemaine, EtatAAfficher));
                break;
            }
        }
        /*String SelectedEDT = "self";
        int SelectedSemaine,
                selectedSalleID = 0,
                selectedEnseignantID = 0,
                selectedGroupeID = 0;*/
            
        //monEDT = new Timetable(ceControlleur, ModeleSQL.SeanceParUtilisateur(13));
        maVue.changeMainPanel(new JScrollPane(monEDT));
    }
    
    /**
     * Set l'id de la salle selectionnée (a afficher)
     * @param _id (int)
     */
    public void setSalleID(int _id) { selectedSalleID = _id;}
    
    /**
     * Set l'id de l'enseignant selectionné (a afficher)
     * @param _id (int)
     */
    public void setEnseignantID(int _id) { selectedEnseignantID = _id;}
    
    /**
     * Set l'id du groupe  selectionné (a afficher)
     * @param _id (int)
     */
    public void setGroupeID(int _id) { selectedGroupeID = _id;}
    
    /**
     * Set l'EDT a afficher (soi même, groupe, salle, enseignant, etc)
     * @param choix (a choisir entre "self", "salle", "groupe", "enseignant")
     */
    public void setSelectedEDT(String choix) { SelectedEDT = choix; }
    
    /**
     * Set la semaine a afficher
     * @param _semaine (int)
     */
    public void setSelectedSemaine(int _semaine) { SelectedSemaine = _semaine;}
    
    /**
     * 
     * @return le numéro de la semaine a afficher (int)
     */
    public int getSelectedSemaine() { return SelectedSemaine; }
    
    /**
     * Set le numéro de l'année a afficher
     * @param _year (int)
     */
    public void setSelectedYear(int _year) { SelectedYear = _year;}
    
    /**
     * 
     * @return le numéro de l'année a afficher (int)
     */
    public int getSelectedYear() { return SelectedYear; }
    
    /**
     * Permet de choisir entre afficher l'EDT en grille ou en liste 
     * @param _type (boolean. true=affiche en grille, false=affiche en liste)
     */
    public void setAffichageType(boolean _type) { affichageGrille = _type; }
    
    /**
     * 
     * @return le boolean qui détermine si on affiche en grille ou en liste
     */
    public boolean getAffichageType() {return affichageGrille; }
    
    /**
     * Set si il faut afficher les cours annulés ou validés (et en cours de validation en fonction des droits)
     * @param AfficherLesAnnullees  (boolean, true=Affiche uniquement les cours annulé, false=affiche les autres
     */
    public void setSeanceAAfficher(boolean AfficherLesAnnullees) {
        if(AfficherLesAnnullees)
            EtatAAfficher = "2";
        else {
            switch(this.getDroits()) {
                case 1:
                case 2: {
                    EtatAAfficher = "01";
                    break;
                }
                case 3:
                case 4: {
                    EtatAAfficher = "1";
                    break;
                }
            }
        }
            
        
    }
    
    /**
     * Créer la barre de navigation en fonction des droits et de l'utilisateur connecté.
     * @return JPanel de la barre de navigation
     */
    private JPanel createNavbar() {
        //JToolBar navbar = new JToolBar ();
        JPanel navbar = new JPanel();
        navbar.setLayout(new BoxLayout(navbar, BoxLayout.LINE_AXIS));
        
        JPanel left = new JPanel();
        left.setLayout(new FlowLayout( FlowLayout.LEFT ));
        
        JMenuBar leftBar = new JMenuBar();
        
        switch (connectedUser.getDroits()) {
            case 1:
            case 2:
            {
                JMenu menuGroupe = new JMenu("Groupes");
                JMenuItem itemEDT = new JMenuItem( new AbstractAction("Emplois du temps") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        ceControlleur.setSelectedEDT("groupe");
                        ceControlleur.setSeanceAAfficher(false);
                        ceControlleur.refreshTimetable();
                        ceControlleur.refreshControlPanel();
                    } 
                });
                
                JMenuItem itemRecap = new JMenuItem( new AbstractAction("Recapitulatif des cours") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        Controlleur.ShowError("Pas encore codé");
                    } 
                });
                
                JMenuItem itemCoursAnnules = new JMenuItem( new AbstractAction("Cours annulés") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        ceControlleur.setSelectedEDT("groupe");
                        ceControlleur.setSeanceAAfficher(true);
                        ceControlleur.refreshTimetable();
                        ceControlleur.refreshControlPanel();
                    } 
                });
                menuGroupe.add(itemEDT);
                menuGroupe.add(itemRecap);
                menuGroupe.add(itemCoursAnnules);
                leftBar.add(menuGroupe);
                
                //--------------------------------------------------------------------------------------------
                
                JMenu menuPromotion = new JMenu("Promotions");
                
                JMenuItem itemPromoRecap = new JMenuItem( new AbstractAction("Recapitulatif des cours") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        Controlleur.ShowError("Pas encore codé");
                    } 
                });
                menuPromotion.add(itemPromoRecap);
                leftBar.add(menuPromotion);
                
                //--------------------------------------------------------------------------------------------
                
                JMenu menuEnseignant = new JMenu("Enseignant");
                JMenuItem itemEnseignantEDT = new JMenuItem( new AbstractAction("Emplois du temps") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        ceControlleur.setSelectedEDT("enseignant");
                        ceControlleur.setSeanceAAfficher(false);
                        ceControlleur.refreshTimetable();
                        ceControlleur.refreshControlPanel();
                    } 
                });
                
                JMenuItem itemEnseignantRecap = new JMenuItem( new AbstractAction("Recapitulatif des cours") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        Controlleur.ShowError("Pas encore codé");
                    } 
                });
                
                JMenuItem itemEnseignantCoursAnnules = new JMenuItem( new AbstractAction("Cours annulés") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        ceControlleur.setSelectedEDT("enseignant");
                        ceControlleur.setSeanceAAfficher(true);
                        ceControlleur.refreshTimetable();
                        ceControlleur.refreshControlPanel();
                    } 
                });
                menuEnseignant.add(itemEnseignantEDT);
                menuEnseignant.add(itemEnseignantRecap);
                menuEnseignant.add(itemEnseignantCoursAnnules);
                leftBar.add(menuEnseignant);
                
                //--------------------------------------------------------------------------------------------
                
                JMenu menuSalles = new JMenu("Salles");
                JMenuItem itemSalleEDT = new JMenuItem( new AbstractAction("Emplois du temps") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        ceControlleur.setSelectedEDT("salle");
                        ceControlleur.setSeanceAAfficher(false);
                        ceControlleur.refreshTimetable();
                        ceControlleur.refreshControlPanel();
                    } 
                });
                JMenuItem itemSalleCoursAnnules = new JMenuItem( new AbstractAction("Cours annulés") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        ceControlleur.setSelectedEDT("salle");
                        ceControlleur.setSeanceAAfficher(true);
                        ceControlleur.refreshTimetable();
                        ceControlleur.refreshControlPanel();
                    } 
                });
                menuSalles.add(itemSalleEDT);
                menuSalles.add(itemSalleCoursAnnules);
                leftBar.add(menuSalles);
                
                
                //--------------------------------------------------------------------------------------------
                if(connectedUser.getDroits() == 1){
                    JMenuItem itemNouveauCours = new JMenuItem( new AbstractAction("Ajouter un nouveau cours") {
                        @Override
                        public void actionPerformed(ActionEvent e) { 
                            createNewSeance();
                        } 
                    });
                    leftBar.add(itemNouveauCours);
                }
                break;
            }
            case 3:
            case 4:
            default:
            {
                JMenu menuUtilisateur = new JMenu("Utilisateur");
                JMenuItem itemEDT = new JMenuItem( new AbstractAction("Emplois du temps") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        ceControlleur.setSelectedEDT("self");
                        ceControlleur.setSeanceAAfficher(false);
                        ceControlleur.refreshTimetable();
                        ceControlleur.refreshControlPanel();
                    } 
                });
                
                JMenuItem itemRecap = new JMenuItem( new AbstractAction("Recapitulatif des cours") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        Controlleur.ShowError("Pas encore codé");
                    } 
                });
                
                JMenuItem itemCoursAnnules = new JMenuItem( new AbstractAction("Cours annulés") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        ceControlleur.setSelectedEDT("self");
                        ceControlleur.setSeanceAAfficher(true);
                        ceControlleur.refreshTimetable();
                        ceControlleur.refreshControlPanel();
                    } 
                });
                
                menuUtilisateur.add(itemEDT);
                menuUtilisateur.add(itemRecap);
                menuUtilisateur.add(itemCoursAnnules);
                leftBar.add(menuUtilisateur);
                
                
                JMenu menuSalles = new JMenu("Salles");
                JMenuItem itemSalleEDT = new JMenuItem( new AbstractAction("Emplois du temps") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        ceControlleur.setSelectedEDT("salle");
                        ceControlleur.setSeanceAAfficher(false);
                        ceControlleur.refreshTimetable();
                        ceControlleur.refreshControlPanel();
                    } 
                });
                JMenuItem itemSalleCoursAnnules = new JMenuItem( new AbstractAction("Cours annulés") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        ceControlleur.setSelectedEDT("salle");
                        ceControlleur.setSeanceAAfficher(true);
                        ceControlleur.refreshTimetable();
                        ceControlleur.refreshControlPanel();
                    } 
                });
                menuSalles.add(itemSalleEDT);
                menuSalles.add(itemSalleCoursAnnules);
                leftBar.add(menuSalles);
                break;
            }
        }
        
        left.add(leftBar);
        
        JPanel right = new JPanel();
        right.setLayout(new FlowLayout( FlowLayout.RIGHT ));
        
        
        JMenuBar rightBar = new JMenuBar();
        JMenu NameMenu = new JMenu(connectedUser.getPrenom() + " " + connectedUser.getNom());
        JMenuItem quitter = new JMenuItem( new AbstractAction("Quitter") {
            @Override
            public void actionPerformed(ActionEvent e) { 
                System.exit(0);
            } 
        });
        
        NameMenu.add(quitter);
        rightBar.add(NameMenu);
        right.add(rightBar);
        
        navbar.add(left);
        navbar.add(Box.createHorizontalBox());
        navbar.add(right);
        
        return navbar;
    }
    
    
    
    
    
    
    
    //--------------------------------------------------------------------------
    
    /**
     * Transforme une heure passée en parametres (sous la forme "15:30:45") en heure décimale (15,575)
     * @param stringHeure (String)
     * @return (double)
     */
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
    
    /**
     * Vérifie si la séance passé en parametres est possible 
     * @param maSeance (seance) 
     * @return maSeance ArrayList(String) de chaques problèmes rencontrés (vide si aucuns)
     */
    public static ArrayList<String> isSeanceGood(seance maSeance) {
        ArrayList<String> returnArray = new ArrayList<String>();
        
        ArrayList<utilisateur> _enseignants = maSeance.getEnseignants();
        for(int i=0; i<_enseignants.size(); i++) {
            
            boolean Reponse = ModeleSQL.isUserAvailable( _enseignants.get(i).getID(), maSeance.getDate(), maSeance.getDebut(), maSeance.getFin(), maSeance.getID());
            System.out.println(Reponse);
            if(!Reponse) {
                returnArray.add("Enseignant: " + _enseignants.get(i).getPrenom() + " " + _enseignants.get(i).getNom() + " n'est pas disponible sur l'entieretée du cours.");
            }
            
            boolean EnseigneMatiere = false;
            ArrayList<cours> _matieres = ModeleSQL.EnseignantMatieres(_enseignants.get(i).getID());
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
            if(!ModeleSQL.isClassAvailable( _salles.get(i).getID(), maSeance.getDate(), maSeance.getDebut(), maSeance.getFin(), maSeance.getID())) {
                returnArray.add("Salle: " + _salles.get(i).getSite() + " " + _salles.get(i).getNom() + " n'est pas disponible sur l'entieretée du cours.");
            }
        }
        
        ArrayList<groupe> _groupes = maSeance.getGroupes();
        for(int i=0; i<_groupes.size(); i++) {
            if(!ModeleSQL.isGroupeAvailable( _groupes.get(i).getID(), maSeance.getDate(), maSeance.getDebut(), maSeance.getFin(), maSeance.getID())) {
                returnArray.add("Groupe: " + _groupes.get(i).getPromotion() + " " + _groupes.get(i).getNom() + " n'est pas disponible sur l'entieretée du cours.");
            }
        }
        
        
        return returnArray;
    }
    
    /**
     * Affiche un popup d'erreur avec l'erreur passé en parametre
     * @param error (String)
     */
    public static void ShowError (String error) {
        String ErrorMessage = "<html> <h2 style='text-align: center;'>Erreur(s):</h2>";
        ErrorMessage += error + "<br>";
        ErrorMessage += "</html>";
        JOptionPane.showMessageDialog(null, ErrorMessage);
    }
    
    /**
     * Affiche un popup avec les erreurs passés en parametres
     * @param error ArrayList(String)
     */
    public static void ShowError (ArrayList<String> error) {
        String ErrorMessage = "<html> <h2 style='text-align: center;'>Erreur(s):</h2>";
        for(int i=0; i<error.size(); i++ )
        {
            ErrorMessage += error.get(i) + "<br>";
        }
        ErrorMessage += "</html>";
        JOptionPane.showMessageDialog(null, ErrorMessage);
    }
}
