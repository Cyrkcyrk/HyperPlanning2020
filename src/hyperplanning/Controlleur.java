/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning;
import DB_class.*;
import hyperplanning.Vue.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;

/**
 *
 * @author Cyrille
 */
public class Controlleur {
    private Controlleur ceControlleur = this;
    private Vue maVue;
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
                
    
    public Controlleur() {
        
        SelectedSemaine = (int) Integer.parseInt(new SimpleDateFormat("w").format(new Date()));
        SelectedYear = (int) Integer.parseInt(new SimpleDateFormat("YYYY").format(new Date()));
        
        maVue = new Vue(this);
        maVue.changeMainPanel(new JScrollPane(ConnectionPanel()));
    }
    
    public Vue getVue() { return maVue; }
    public int getDroits() { 
        if(connectedUser == null) 
            return 0;
        else
            return connectedUser.getDroits();
    }
    
    public void ShowSeanceInformations(seance s) {
         maVue.changeRightPanel(new SeancePanel(this, s, "rightPanel"));
    }
    
    public void closeRightPanel() {
        maVue.closeRightPanel();
    }
    public void closeLeftPanel() {
        maVue.closeLeftPanel();
    }
    
    public void editSeance(seance s) {
        JScrollPane _tmp = new JScrollPane(new SeanceCreation(this, 200, s), 
                                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        maVue.changeLeftPanel(_tmp);
        maVue.closeRightPanel();
    }
    public void createNewSeance() {
        JScrollPane _tmp = new JScrollPane(new SeanceCreation(this, 200), 
                                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        maVue.changeLeftPanel(_tmp);
    }
    public void saveSeance(seance s) {
        
        ArrayList<String> erreur = Controlleur.isSeanceGood(s);
        if(erreur.size() <= 0) {
            System.out.println("Pas d'erreurs");
            if(s.getID() <= 0) {
                Modele.InsererSeance(s);
            }
            else{
                Modele.ChangerSeance(s);
            }
            
            refreshTimetable();
            maVue.closeLeftPanel();
            
            
        }
        else {
            ShowError(erreur);
        }
    }
    public void changerEtatSeance(seance s, int _etat) {
        
        if(_etat == 1) {
            ArrayList<String> erreur = Controlleur.isSeanceGood(s);
            if(erreur.size() <= 0) {
                System.out.println("Pas d'erreurs");
                
                Modele.updateEtatSeance(s.getID(), _etat);
                refreshTimetable();
                maVue.closeLeftPanel();
                maVue.closeRightPanel();
            }
            else {
                ShowError(erreur);
            }
        }
        else if(_etat == 0 || _etat == 2) {
            Modele.updateEtatSeance(s.getID(), _etat);
            refreshTimetable();
            maVue.closeLeftPanel();
            maVue.closeRightPanel();
        }
        else {
            ShowError("Etat '"+ _etat + "' incorrecte");
        }
    }
    
    private JPanel ConnectionPanel() {
        
        JPanel returnPanel = new JPanel();
        
        JLabel lblNewLabel = new JLabel("Login");
        returnPanel.add(lblNewLabel);

        JTextField textField = new JTextField();
        returnPanel.add(textField);
        textField.setColumns(10);

        JPasswordField passwordField = new JPasswordField();
        returnPanel.add(passwordField);
        passwordField.setColumns(10);

        JLabel lblUsername = new JLabel("Username");
        returnPanel.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        returnPanel.add(lblPassword);

        JButton btnNewButton = new JButton("Login");
        btnNewButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String userName = textField.getText();
                String password = passwordField.getText();
                
                System.out.println("Username: " + userName + " - password: " + password);
                if(password.isEmpty()) {
                    try{
                        int id = (int) Integer.parseInt(userName);
                        connectedUser = Modele.getUtilisateur(id);
                        
                    } catch (NumberFormatException ex) {
                    }
                }
                else {
                    connectedUser = Modele.getUtilisateur(userName, password);
                }
                if(connectedUser == null) {
                    ShowError("Email ou mot de passe incorrect!");
                }
                else {
                    connected();
                }
            }
        });

        returnPanel.add(btnNewButton);
        
        return returnPanel;
    }
    
    public void connected() {
        
        //maVue.changeMainControlPanel(new SearchPanel(Modele.getAllProfs(), "enseignant", 21));
        //maVue.changeMainControlPanel(new SearchPanel(Modele.getAllSalles(), "salle", SelectedSemaine));
        //maVue.changeMainControlPanel(new SearchPanel(Modele.getAllGroupes(), "groupe", 21));
        //maVue.changeMainControlPanel(new SearchPanel(21));
        
        maVue.changeNavbar(createNavbar());
        refreshControlPanel();
        refreshTimetable();
    }
    
    public void refreshControlPanel() {
        switch(SelectedEDT) {
            case "self":{
                controlPanel = new SearchPanel(this, SelectedSemaine, SelectedYear);
                break;
            }
            case "salle": {
                controlPanel = new SearchPanel(this, Modele.getAllSalles(), "salle", selectedSalleID, SelectedSemaine, SelectedYear);
                break;
            }
            case "groupe": {
                controlPanel = new SearchPanel(this, Modele.getAllGroupes(), "groupe", selectedGroupeID, SelectedSemaine, SelectedYear);
                break;
            }
            case "enseignant": {
                controlPanel = new SearchPanel(this, Modele.getAllProfs(), "enseignant", selectedEnseignantID, SelectedSemaine, SelectedYear);
                break;
            }
        }
        maVue.changeMainControlPanel(controlPanel);
    }
    
    public void refreshTimetable() {
        if(affichageGrille)
            refreshTimetableGrille();
        else
            refreshTimetableListe();
            
    }
    private void refreshTimetableGrille() {
        switch(SelectedEDT) {
            case "self":{
                monEDT = new Timetable(ceControlleur, Modele.SeanceParUtilisateur(connectedUser.getID(), SelectedSemaine, EtatAAfficher));
                break;
            }
            case "salle": {
                monEDT = new Timetable(ceControlleur, Modele.SeanceParSalle(selectedSalleID, SelectedSemaine, EtatAAfficher));
                break;
            }
            case "groupe": {
                monEDT = new Timetable(ceControlleur, Modele.SeanceParGroupe(selectedGroupeID, SelectedSemaine, EtatAAfficher));
                break;
            }
            case "enseignant": {
                monEDT = new Timetable(ceControlleur, Modele.SeanceParUtilisateur(selectedEnseignantID, SelectedSemaine, EtatAAfficher));
                break;
            }
        }
        maVue.changeMainPanel(new JScrollPane(monEDT));
    }
    private void refreshTimetableListe() {
        switch(SelectedEDT) {
            case "self":{
                monEDT = new TimetableListe(ceControlleur, Modele.SeanceParUtilisateur(connectedUser.getID(), SelectedSemaine, EtatAAfficher));
                break;
            }
            case "salle": {
                monEDT = new TimetableListe(ceControlleur, Modele.SeanceParSalle(selectedSalleID, SelectedSemaine, EtatAAfficher));
                break;
            }
            case "groupe": {
                monEDT = new TimetableListe(ceControlleur, Modele.SeanceParGroupe(selectedGroupeID, SelectedSemaine, EtatAAfficher));
                break;
            }
            case "enseignant": {
                monEDT = new TimetableListe(ceControlleur, Modele.SeanceParUtilisateur(selectedEnseignantID, SelectedSemaine, EtatAAfficher));
                break;
            }
        }
        /*String SelectedEDT = "self";
        int SelectedSemaine,
                selectedSalleID = 0,
                selectedEnseignantID = 0,
                selectedGroupeID = 0;*/
            
        //monEDT = new Timetable(ceControlleur, Modele.SeanceParUtilisateur(13));
        maVue.changeMainPanel(new JScrollPane(monEDT));
    }
    
    public void setSalleID(int _id) { selectedSalleID = _id;}
    public void setEnseignantID(int _id) { selectedEnseignantID = _id;}
    public void setGroupeID(int _id) { selectedGroupeID = _id;}
    public void setSelectedEDT(String choix) { SelectedEDT = choix; }
    public void setSelectedSemaine(int _semaine) { SelectedSemaine = _semaine;}
    public int getSelectedSemaine() { return SelectedSemaine; }
    public void setSelectedYear(int _year) { SelectedYear = _year;}
    public int getSelectedYear() { return SelectedYear; }
    public void setAffichageType(boolean _type) { affichageGrille = _type; }
    public boolean getAffichageType() {return affichageGrille; }
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
    
    public static void ShowError (String error) {
        String ErrorMessage = "<html> <h2 style='text-align: center;'>Erreur(s):</h2>";
        ErrorMessage += error + "<br>";
        ErrorMessage += "</html>";
        JOptionPane.showMessageDialog(null, ErrorMessage);
    }
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
