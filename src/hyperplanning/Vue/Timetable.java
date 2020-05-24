/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning.Vue;

import DB_class.*;
import hyperplanning.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
/**
 *
 * @author Cyrille
 */
public class Timetable extends JPanel {
    
    Vue VuePanel;
    
    int dayStart = 8;
    int dayEnd = 21;
    int splitEvery = 15;
    
    int daySplit = (60/splitEvery)*24;
    int numberOfHours = dayEnd - dayStart;
    int Separation = (daySplit/24)*numberOfHours;
    
    ArrayList<seance> mesSeances;
    
    public Timetable(Vue _vuePanel)
    {
        super(new GridBagLayout());
        this.VuePanel = _vuePanel;
        
        mesSeances = new ArrayList<seance>();
        mesSeances.add(0, createSeance());
        mesSeances.add(0, createSeance());
        mesSeances.add(0, createSeance());
        mesSeances.add(0, createSeance());
        
        
        for (int i=0; i<mesSeances.size(); i++)
        {
            System.out.println(mesSeances.get(i).toString());
        }
        
        this.createTimetable();
    }
    
    public Timetable(Vue _vuePanel, ArrayList<seance> _seances)
    {
        super(new GridBagLayout());
        this.VuePanel = _vuePanel;
        this.mesSeances = _seances;
        this.createTimetable();
    }
     public Timetable(Vue _vuePanel, ArrayList<seance> _seances, int _splitEvery)
    {
        super(new GridBagLayout());
        this.VuePanel = _vuePanel;
        this.mesSeances = _seances;
        this.splitEvery = _splitEvery;
        this.createTimetable();
    }
    public Timetable(Vue _vuePanel, ArrayList<seance> _seances, int _splitEvery, int _dayStart, int _dayEnd)
    {
        super(new GridBagLayout());
        this.VuePanel = _vuePanel;
        this.mesSeances = _seances;
        this.splitEvery = _splitEvery;
        this.dayStart = _dayStart;
        this.dayEnd = _dayEnd;
        this.createTimetable();
    }
    
    private void createTimetable() {
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        
        ActionListener changeTT = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VuePanel.changeCenter(new Timetable(VuePanel));
                VuePanel.closeRightPanel(); 
                
            }
        };
        
        ActionListener hideLeft = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VuePanel.hideLeft();
            }
        };
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 9;
        c.weightx = 1;
        JButton EDTButton = new JButton("EDT");
        EDTButton.addActionListener(hideLeft);
        this.add(EDTButton, c);
        c.gridwidth = 1;

        
        
        
        
        c.gridy = 1;
        String[] _jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        for (int i=0; i<7; i++) {
            c.gridx = 2+i;
            JPanel panel = new JPanel();
            panel.setBorder(new CustomBorder(0, 0, 0, 0));
            JLabel label = new JLabel(_jours[i]);
            panel.add(label);
            
            this.add(panel, c);
            
        }
        
        c.weightx = 0.05;
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = Separation;
        JButton TempsButton = new JButton("Temps");
        TempsButton.addActionListener(changeTT);
        this.add(TempsButton, c);
        c.gridheight = 1; 

        
        String[] _heures = {"00h00", "01h00", "02h00", "03h00", "04h00", "05h00", 
                           "06h00", "07h00", "08h00", "09h00", "10h00", "11h00", 
                           "12h00", "13h00", "14h00", "15h00", "16h00", "17h00", 
                           "18h00", "19h00", "20h00", "21h00", "22h00", "23h00"};
        c.weightx = 0.01;
        for (int i=0; i<numberOfHours; i++) {
            c.gridx = 1;
            c.gridy = 2+(i*(Separation/numberOfHours));
            c.gridheight = (Separation/numberOfHours);
            JPanel panel = new JPanel();
            panel.setBorder(new CustomBorder(2, 0, 0, 0));
            JLabel label = new JLabel(_heures[dayStart + i]);
            panel.add(label);
            
            this.add(panel, c);
            c.gridheight = 1;
            
        }
        
        c.weightx = 0.125;
        c.gridwidth = 1;
        c.gridheight = 1;
        
        
        

        int changement = 0;
        for (int j=0; j<7; j++) {
            for (int i=0; i<Separation; i++) {
                c.gridx = j+2;
                c.gridy = i+2;
                JPanel panel = new JPanel();
                
                changement = 0;
                
                for (int k=0; k< mesSeances.size(); k++ ) {
                    seance s = mesSeances.get(k);
                    int eventDay = s.getDate().getDateInt(); 
                    double eventStart = Controlleur.heureToDouble(s.getDebut().toString());
                    double eventEnd = Controlleur.heureToDouble(s.getFin().toString());
                    int duration = (int) Math.round((daySplit/24)*(eventEnd - eventStart));
                    int begin = (int) Math.round((daySplit/24)*(eventStart - dayStart));
                    
                    
                    if(j == eventDay && i == begin)
                    {
                        changement = 1;
                        c.gridheight = duration;
                        i+=duration-1;
                        
                        if(s.duration() <=  ((double) splitEvery)/60)
                        {
                            panel = new SeancePanel(VuePanel, s, "vide");
                        }
                        else if(s.duration() <= (((double)splitEvery)/60)*3)
                        {
                            panel = new SeancePanel(VuePanel, s, "petit");
                        }
                        else
                        {
                            panel = new SeancePanel(VuePanel, s, "moyen");
                        }
                        //panel.setBorder(new CustomBorder(1, 1, 1, 0));
                        //panel.setBackground(Color.green);
                    }
                }
                
                if(changement == 0)
                {
                    c.gridheight = 1;
                    
                    int _top = 0,
                        _left = 1,
                        _bot = 0,
                        _right = 0;
                    
                    if(i%(60/splitEvery) == 0 )
                    {
                        _top=1;
                    }
                    if(j == 6)
                    {
                        _right=1;
                    }
                    if(i == Separation-1)
                    {
                        _bot = 1;
                    }
                    
                    
                    
                    panel.setBorder(new CustomBorder(_top, _left, _bot, _right));
                }
                this.add(panel, c);
            }
        }
    }
    
    
    private seance createSeance() {
        seance _return = null;
        
        ArrayList<groupe> _groupesTable = new ArrayList<groupe>();
        
        int NbrGroupe = (int) (Math.random()*100)%5 +1;
        for (int i=0; i<NbrGroupe; i++) {
            int groupeID = (int) (Math.random()*100)%5 +1;
            _groupesTable.add(0, Modele.getGroupe(groupeID));
        }
        
        ArrayList<salle> _sallesTable = new ArrayList<salle>();
        int NbrSalle = (int) (Math.random()*100)%3 +1;
        for (int i=0; i<NbrSalle; i++) {
            int salleID = (int) (Math.random()*100)%5 +1;
            _sallesTable.add(0, Modele.getSalle(salleID));
        }
        
        ArrayList<utilisateur> _enseignantsTable = new ArrayList<utilisateur>();
        int NbrProf = (int) (Math.random()*100)%2 +1;
        for (int i=0; i<NbrProf; i++) {
            int profID = (int) (Math.random()*100)%3 +13;
            _enseignantsTable.add(0, Modele.getUtilisateur(profID));
        }
        
        
        int heureDebut = (int) (Math.random()*100)%12  + 8 ;
        int minuteDebut = (int) ((Math.random()*100)%4)*15  ;
        int heureFin =  (int) (Math.random()*100)%(21-heureDebut) + heureDebut+1;
        int minuteFin = (int) ((Math.random()*100)%4)*15;
        if(heureFin >= 21)
            minuteFin = 0;
        
        /*heureDebut = 15;
        minuteDebut = 0;
        heureFin = 15;
        minuteFin = 15;*/
        
        String DebutString = "";
        if(heureDebut < 10)
            DebutString += "0";
        DebutString += heureDebut + ":";
        if(minuteDebut < 10)
            DebutString += "0";
        DebutString += minuteDebut + ":00";
        
        
        String FinString = "";
        if(heureFin < 10)
            FinString += "0";
        FinString += heureFin + ":";
        if(minuteFin < 10)
            FinString += "0";
        FinString += minuteFin + ":00";
        
        
        int semaine = (int) (Math.random()*100);
        semaine = semaine%52 + 1;
        int jour = (int) (Math.random()*100);
        jour = 18 + (jour%7);
        
        String jourString = "2020-05-" + jour;
        
        _return = new seance(
                (int) (Math.random()*100)%100 +1,           //ID
                semaine,                                    //semaine
                jourString,                                  //date (jour)
                DebutString,                                //heure debut
                FinString,                                  //heure fin
                "valide",
                "POO Java",
                "TP",
                _groupesTable,
                _sallesTable,
                _enseignantsTable
        );
        return _return;
    }
}
