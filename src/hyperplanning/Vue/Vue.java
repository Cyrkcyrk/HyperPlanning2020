/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning.Vue;


import DB_class.*;
import hyperplanning.Controlleur;
import hyperplanning.Modele;
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
    JPanel timeTable, rightPanel;
    JScrollPane timeTableScroll, leftPanelScroll, rightPanelScroll;
    JTree leftPanel;
    
    boolean rightPanelHidden = true;
    boolean leftPanelHidden = false;

    int dayStart = 8;
    int dayEnd = 21;
    int splitEvery = 15;
    
    
    
    
    public Vue () {
        //https://www.youtube.com/watch?v=-UvxwleNA20
        
        super("Hyperplanning - Projet JAVA ING3 2020");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(960, 720);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        
        
        contentPane = (JPanel) this.getContentPane();
        contentPane.add(createNavbar(), BorderLayout.NORTH);
        
        
        leftPanel = new JTree();
        leftPanelScroll = new JScrollPane(leftPanel);
        leftPanelScroll.setPreferredSize(new Dimension (200, 0));
        contentPane.add(leftPanelScroll, BorderLayout.WEST);
        
        timeTable = new Timetable(this);
        timeTableScroll = new JScrollPane(timeTable);
        contentPane.add(timeTableScroll, BorderLayout.CENTER);
        
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
    
    public void changeTimetable(Timetable _EDT) {
        contentPane.remove(timeTableScroll);
        timeTable = _EDT;
        timeTableScroll = new JScrollPane(timeTable);
        contentPane.add(timeTableScroll, BorderLayout.CENTER);

        contentPane.revalidate();
        contentPane.repaint();
    }
    
    public void hideLeft() {
        if(leftPanelHidden) {
            contentPane.add(leftPanelScroll, BorderLayout.WEST);
        }
        else {
            contentPane.remove(leftPanelScroll);
        }
        leftPanelHidden = !leftPanelHidden;
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    public void changeRightPanel(JPanel _tmpPannel) {
        if(!rightPanelHidden)
            contentPane.remove(rightPanelScroll);
        
        rightPanelHidden = false;
        rightPanel = _tmpPannel;
        rightPanelScroll = new JScrollPane(_tmpPannel);
        contentPane.add(rightPanelScroll, BorderLayout.EAST);

        contentPane.revalidate();
        contentPane.repaint();
    }
    
    public void closeRightPanel() {
        if(!rightPanelHidden)
            contentPane.remove(rightPanelScroll);
        rightPanelHidden = true;
        contentPane.revalidate();
        contentPane.repaint();
    }
}
