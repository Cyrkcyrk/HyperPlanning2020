/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning;


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
    
    public Vue () {
        //https://www.youtube.com/watch?v=-UvxwleNA20
        
        super("Hyperplanning - Projet JAVA ING3 2020");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(960, 720);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        
        
        
        JPanel contentPane = (JPanel) this.getContentPane();
        
        
        
        contentPane.add(createNavbar(), BorderLayout.NORTH);
        
        
        JScrollPane leftColumn = new JScrollPane( new JTree() );
        leftColumn.setPreferredSize(new Dimension (200, 0));
        contentPane.add(leftColumn, BorderLayout.WEST);
        
        
        contentPane.add(new JScrollPane(createTable()), BorderLayout.CENTER);
        
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
        JPanel timeTable = new JPanel ();
        
        int dayStart = 8;
        int dayEnd   = 21;
        int splitEvery = 15;
        
        
        
        int daySplit = (60/splitEvery)*24;
        int numberOfHours = dayEnd - dayStart;
        int Separation = (daySplit/24)*numberOfHours;
        
        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        
        
        
        
        /**/
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 9;
        c.weightx = 1;
        container.add(new JButton("EDT"), c);
        c.gridwidth = 1;

        
        
        c.gridy = 1;
        c.gridx = 2;
        container.add(new JButton("Lundi"), c);
        c.gridx = 3;
        container.add(new JButton("Mardi"), c);
        c.gridx = 4;
        container.add(new JButton("Mercredi"), c);
        c.gridx = 5;
        container.add(new JButton("Jeudi"), c);
        c.gridx = 6;
        container.add(new JButton("Vendredi"), c);
        c.gridx = 7;
        container.add(new JButton("Samedi"), c);
        c.gridx = 8;
        container.add(new JButton("Dimanche"), c);
        
        
        c.weightx = 0.05;
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = Separation;
        container.add(new JButton("TEMPS"), c);
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
        
        
        int eventDay = 0;
        double eventStart = 8.5;
        double eventEnd = 9.5;
        
        
        int duration = (int) Math.round((daySplit/24)*(eventEnd - eventStart));
        int begin = (int) Math.round((daySplit/24)*(eventStart - dayStart));
        
        for (int j=0; j<7; j++) {
            for (int i=0; i<Separation; i++) {
                c.gridx = j+2;
                c.gridy = i+2;
                JPanel panel = new JPanel();
                
                if(j==eventDay && i == begin)
                {
                    c.gridheight = duration;
                    i+=duration-1;
                    panel.setBorder(new CustomBorder(1, 1, 1, 1));
                    panel.setBackground(Color.green);
                    
                }
                else
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
}
