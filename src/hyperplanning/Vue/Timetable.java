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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.*;
/**
 *
 * @author Cyrille
 */
public class Timetable extends JPanel {
    
    Controlleur monControlleur;
    
    int dayStart = 8;
    int dayEnd = 21;
    int splitEvery = 15;
    
    int daySplit = (60/splitEvery)*24;
    int numberOfHours = dayEnd - dayStart;
    int Separation = (daySplit/24)*numberOfHours;
    
    Date firstDay = new Date();
    
    ArrayList<seance> mesSeances;

    
    public Timetable(Controlleur _ctrlr, ArrayList<seance> _seances)
    {
        super(new GridBagLayout());
        this.monControlleur = _ctrlr;
        this.mesSeances = _seances;
        this.createTimetable();
    }
    public Timetable(Controlleur _ctrlr, ArrayList<seance> _seances, int _splitEvery)
    {
        super(new GridBagLayout());
        this.monControlleur = _ctrlr;
        this.mesSeances = _seances;
        this.splitEvery = _splitEvery;
        this.createTimetable();
    }
    
    public Timetable(Controlleur _ctrlr, ArrayList<seance> _seances, int _splitEvery, int _dayStart, int _dayEnd)
    {
        super(new GridBagLayout());
        this.monControlleur = _ctrlr;
        this.mesSeances = _seances;
        this.splitEvery = _splitEvery;
        this.dayStart = _dayStart;
        this.dayEnd = _dayEnd;
        this.createTimetable();
    }
    
    private void createTimetable()  {
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 9;
        c.weightx = 1;
        //JButton EDTButton = new JButton("EDT");
        //this.add(EDTButton, c);
        c.gridwidth = 1;
        
        
        
        c.gridy = 1;
        try {
            firstDay = new SimpleDateFormat("w YYYY").parse(monControlleur.getSelectedSemaine() + " " + monControlleur.getSelectedYear());
            int NumeroJourSemaine = Integer.parseInt(new SimpleDateFormat("u", new Locale("fr", "FR")).format(firstDay))-1;
            firstDay = new Date(firstDay.getTime() - (1000*3600*24* NumeroJourSemaine));
            
            //String[] _jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
            for (int i=0; i<7; i++) {
                c.gridx = 2+i;
                JPanel panel = new JPanel();
                panel.setBorder(new CustomBorder(0, 0, 0, 0));
                
                //https://stackoverflow.com/questions/1005523/how-to-add-one-day-to-a-date
                String dateChiffres = new SimpleDateFormat("EEEEEEE dd/MM", new Locale("fr", "FR")).format(new Date(firstDay.getTime() + (1000 * 60 * 60 * 24 * i)));
                JLabel label = new JLabel(dateChiffres);
                
                panel.add(label);
                this.add(panel, c);
            }
            
        } catch (ParseException ex) {
            System.out.println(ex);
            String[] _jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
            for (int i=0; i<7; i++) {
                c.gridx = 2+i;
                JPanel panel = new JPanel();
                panel.setBorder(new CustomBorder(0, 0, 0, 0));
                
                JLabel label = new JLabel(_jours[i]);
                panel.add(label);

                this.add(panel, c);

            }
        }
        
        
        //String[] _jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        for (int i=0; i<7; i++) {
            c.gridx = 2+i;
            JPanel panel = new JPanel();
            panel.setBorder(new CustomBorder(0, 0, 0, 0));
            
            //https://stackoverflow.com/questions/1005523/how-to-add-one-day-to-a-date
            String dateChiffres = new SimpleDateFormat("EEEEEEE dd/MM", new Locale("fr", "FR")).format(new Date(firstDay.getTime() + (1000 * 60 * 60 * 24 * i)));
            JLabel label = new JLabel(dateChiffres);
            panel.add(label);
            
            this.add(panel, c);
            
        }
        
        c.weightx = 0.05;
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = Separation;
        //JButton TempsButton = new JButton("Temps");
        //this.add(TempsButton, c);
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
                    int eventDay = s.getDate().getDayInt(); 
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
                            panel = new SeancePanel(monControlleur, s, "vide");
                        }
                        else if(s.duration() <= (((double)splitEvery)/60)*3)
                        {
                            panel = new SeancePanel(monControlleur, s, "petit");
                        }
                        else
                        {
                            panel = new SeancePanel(monControlleur, s, "moyen");
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

}
