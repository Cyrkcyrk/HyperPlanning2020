/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning;


import DB_class.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Math;


/**
 *
 * @author Cyrille
 */
public class Vue extends JFrame {
    
    JPanel contentPane;
    JScrollPane leftPanel, timeTable;
    int hideLeftPanel;
    
    int dayStart = 8;
    int dayEnd = 21;
    int splitEvery = 15;
    
    ArrayList<seance> mesSeances = new ArrayList<seance>();
    
    
    public Vue () {
        //https://www.youtube.com/watch?v=-UvxwleNA20
        
        super("Hyperplanning - Projet JAVA ING3 2020");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(960, 720);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        
        hideLeftPanel = 1;
        
        mesSeances.add(0, createSeance());
        mesSeances.add(0, createSeance());
        mesSeances.add(0, createSeance());
        mesSeances.add(0, createSeance());
        
        contentPane = (JPanel) this.getContentPane();
        contentPane.add(createNavbar(), BorderLayout.NORTH);
        
        
        leftPanel = new JScrollPane( new JTree() );
        leftPanel.setPreferredSize(new Dimension (200, 0));
        contentPane.add(leftPanel, BorderLayout.WEST);
        
        timeTable = new JScrollPane(createTable());
        contentPane.add(timeTable, BorderLayout.CENTER);
        
        this.setVisible(true);
    }
    
    private JPanel createNavbar() {
        //JToolBar navbar = new JToolBar ();
        JPanel navbar = new JPanel();
        navbar.setLayout(new BoxLayout(navbar, BoxLayout.LINE_AXIS));
        
        JPanel left = new JPanel();
        left.setLayout(new FlowLayout( FlowLayout.LEFT ));
        left.add(new JLabel("Message 1"));
        left.add(new JLabel("Message 2"));
        left.add(new JLabel("Message 3"));
        
        JPanel right = new JPanel();
        right.setLayout(new FlowLayout( FlowLayout.RIGHT ));
        right.add(new JLabel("Message 1"));
        
        navbar.add(left);
        navbar.add(Box.createHorizontalBox());
        navbar.add(right);
        
        return navbar;
    }
    
    private JPanel createTable() {
        
        
        
        int daySplit = (60/splitEvery)*24;
        int numberOfHours = dayEnd - dayStart;
        int Separation = (daySplit/24)*numberOfHours;

        
        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        
        
        
        ActionListener hideLeft = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("bouton cliqué");
                if(hideLeftPanel == 1)
                {
                    contentPane.remove(leftPanel);
                    hideLeftPanel = 0;
                }
                else
                {
                    contentPane.add(leftPanel, BorderLayout.WEST);
                    hideLeftPanel = 1;
                }
                contentPane.revalidate();
                contentPane.repaint();
            }
        };
        
        ActionListener changeTT = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mesSeances = new ArrayList<seance>();
                mesSeances.add(0, createSeance());
                mesSeances.add(0, createSeance());
                mesSeances.add(0, createSeance());
                mesSeances.add(0, createSeance());
                refreshTimetable();
            }
        };
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 9;
        c.weightx = 1;
        JButton EDTButton = new JButton("EDT");
        EDTButton.addActionListener(hideLeft);
        container.add(EDTButton, c);
        c.gridwidth = 1;

        
        
        
        
        c.gridy = 1;
        String[] _jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        for (int i=0; i<7; i++) {
            c.gridx = 2+i;
            JPanel panel = new JPanel();
            panel.setBorder(new CustomBorder(0, 0, 0, 0));
            JLabel label = new JLabel(_jours[i]);
            panel.add(label);
            
            container.add(panel, c);
            
        }
        
        c.weightx = 0.05;
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = Separation;
        JButton TempsButton = new JButton("Temps");
        TempsButton.addActionListener(changeTT);
        container.add(TempsButton, c);
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
            
            container.add(panel, c);
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
                    int eventDay = Integer.parseInt(s.getDate()); 
                    double eventStart = Controlleur.heureToDouble(s.getDebut());
                    double eventEnd = Controlleur.heureToDouble(s.getFin());
                    int duration = (int) Math.round((daySplit/24)*(eventEnd - eventStart));
                    int begin = (int) Math.round((daySplit/24)*(eventStart - dayStart));
                    
                    if(j == eventDay && i == begin)
                    {
                        changement = 1;
                        c.gridheight = duration;
                        i+=duration-1;
                        
                        
                        System.out.println( ((double) splitEvery)/60);
                        System.out.println(s.duration());
                        
                        if(s.duration() <=  ((double) splitEvery)/60)
                        {
                            panel = panelSeanceMinuscule(s);
                        }
                        else if(s.duration() <= (((double)splitEvery)/60)*5)
                        {
                            panel = panelSeancePetit(s);
                        }
                        else
                        {
                            panel = panelSeanceMoyen(s);
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
                container.add(panel, c);
            }
        }
        return container;
    }
    
    private void refreshTimetable() {
        contentPane.remove(timeTable);
        timeTable = new JScrollPane(createTable());
        contentPane.add(timeTable, BorderLayout.CENTER);

        contentPane.revalidate();
        contentPane.repaint();
    }
    
    private JPanel panelSeance2(seance s) {
        JPanel pRetour = new JPanel();
        
        
        
        
        
        JPanel pHeureDebut = new JPanel();
        pHeureDebut.setLayout(new BoxLayout(pHeureDebut, BoxLayout.LINE_AXIS));
        pHeureDebut.add(new JLabel(s.getDebut()));
        pHeureDebut.add(Box.createHorizontalBox());
        pHeureDebut.add(new JLabel(""));
        
        
        
        JPanel pHeureFin = new JPanel();
        pHeureFin.setLayout(new BoxLayout(pHeureFin, BoxLayout.LINE_AXIS));
        pHeureFin.add(new JLabel(s.getFin()));
        pHeureFin.add(Box.createHorizontalBox());
        
        JLabel Matiere = new JLabel(s.getCours());
        
        
        
        
        pRetour.setBorder(new CustomBorder(1, 1, 1, 0));
        pRetour.setBackground(Color.green);
        
        return pRetour;
    }
    
    private JPanel panelSeanceMinuscule(seance s)
    {
        JPanel jPanel1 = new javax.swing.JPanel();
        
        jPanel1.setBorder(new CustomBorder(1, 1, 1, 0));
        jPanel1.setBackground(Color.gray);
        return jPanel1;
    }
    
    private JPanel panelSeancePetit(seance s)
    {
        JPanel jPanel1 = new javax.swing.JPanel();
        JLabel jLabel1 = new javax.swing.JLabel();
        JLabel jLabel2 = new javax.swing.JLabel();
        JPanel jPanel2 = new javax.swing.JPanel();
        JLabel jLabel3 = new javax.swing.JLabel();
        JLabel jLabel4 = new javax.swing.JLabel();
        JLabel jLabel5 = new javax.swing.JLabel();
        JLabel jLabel6 = new javax.swing.JLabel();
        JLabel jLabel7 = new javax.swing.JLabel();
        JLabel jLabel8 = new javax.swing.JLabel();
        
        
        jLabel3.setText(s.getCours());
        
        jLabel1.setText("");
        jLabel2.setText("");
        jLabel4.setText("");
        jLabel5.setText("");
        jLabel6.setText("");
        jLabel7.setText("");
        jLabel8.setText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                )
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 0, Short.MAX_VALUE)
                .addComponent(jLabel8)
            )
        );
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                )
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap()
            )
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2)
            )
        );

 
        jPanel1.setBorder(new CustomBorder(1, 1, 1, 0));
        jPanel1.setBackground(Color.gray);
        jPanel2.setBackground(Color.gray);
        return jPanel1;
    }
    
    
    private JPanel panelSeanceMoyen(seance s)
    {
        JPanel jPanel1 = new javax.swing.JPanel();
        JLabel jLabel1 = new javax.swing.JLabel();
        JLabel jLabel2 = new javax.swing.JLabel();
        JPanel jPanel2 = new javax.swing.JPanel();
        JLabel jLabel3 = new javax.swing.JLabel();

        jLabel1.setText(s.getDebut());
        jLabel2.setText(s.getFin());
        jLabel3.setText(s.getCours());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                )
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
            )
        );
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                )
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addComponent(jPanel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
            )
        );

 
        jPanel1.setBorder(new CustomBorder(1, 1, 1, 0));
        jPanel1.setBackground(Color.gray);
        jPanel2.setBackground(Color.gray);
        return jPanel1;
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
        //int heureFin =  (int) (Math.random()*100)%(21-heureDebut) + heureDebut+1;
        int heureFin = heureDebut+1;
        //int minuteFin = (int) ((Math.random()*100)%4)*15;
        int minuteFin = 30;
        if(heureFin >= 21)
            minuteFin = 0;
        
        heureDebut = 15;
        minuteDebut = 0;
        heureFin = 15;
        minuteFin = 30;
        
        int semaine = (int) (Math.random()*100);
        semaine = semaine%52 + 1;
        int jour = (int) (Math.random()*100);
        jour = jour%7;
        
        _return = new seance(
                (int) (Math.random()*100)%100 +1,           //ID
                semaine,                                    //semaine
                "" + jour,                                  //date (jour)
                heureDebut + ":" + minuteDebut + ":00",     //heure debut
                heureFin + ":" + minuteFin + ":00",         //heure fin
                "valide",
                "info",
                "TP",
                _groupesTable,
                _sallesTable,
                _enseignantsTable
        );
        return _return;
    }
}
