/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning.Vue;


import DB_class.seance;
import hyperplanning.*;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Cyrille
 */
public class Vue extends JFrame {
    
    private JPanel contentPane, Navbar, controlMainPanel, centerPanel;
    private JScrollPane mainPanel, leftPanel, rightPanel;
    private boolean rightPanelHidden = true;
    private boolean leftPanelHidden = true;
    private boolean centerPanelHidden = true;
    private boolean mainPanelHidden = true;
    private boolean mainControlPanelHidden = true;
    private boolean navbarHidden = true;
    
    
    private Controlleur monControlleur;
    
    private GroupLayout layout;
    
    
    
    private Timetable monEDT;
    
    
    public Vue (Controlleur _controlleur) {
        //https://www.youtube.com/watch?v=-UvxwleNA20
        
        super("Hyperplanning - Projet JAVA ING3 2020");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(960, 720);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        contentPane = (JPanel) this.getContentPane();
        centerPanel = new JPanel(new BorderLayout());
        contentPane.add(centerPanel, BorderLayout.CENTER);
        monControlleur = _controlleur;
        
        this.setVisible(true);
    }
    
    
    
    public void refresh() {
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    /*public void changeCenter(JPanel _tmpPannel) {
        if(!centerPanelHidden)
            centerPanel.remove(mainPanel);
        
        centerPanelHidden = false;
        mainPanel = new JScrollPane(_tmpPannel);
        centerPanel.add(mainPanel, BorderLayout.CENTER);

        refresh();
    }*/
    
    public void hideLeft() {
        if(leftPanelHidden) {
            contentPane.add(leftPanel, BorderLayout.WEST);
        }
        else {
            contentPane.remove(leftPanel);
        }
        leftPanelHidden = !leftPanelHidden;
        refresh();
    }
    
    public void changeLeftPanel(JScrollPane _tmp) {
        if(!leftPanelHidden)
            contentPane.remove(leftPanel);
        leftPanel = _tmp;
        contentPane.add(leftPanel, BorderLayout.WEST);
        leftPanelHidden = false;
        refresh();
    }
    public void closeLeftPanel() {
        if(!leftPanelHidden)
            contentPane.remove(leftPanel);
        leftPanelHidden = true;
        refresh();
    }
    
    public void changeRightPanel(JPanel _tmpPannel) {
        if(!rightPanelHidden)
            contentPane.remove(rightPanel);
        
        rightPanelHidden = false;
        rightPanel = new JScrollPane(_tmpPannel);
        contentPane.add(rightPanel, BorderLayout.EAST);

        refresh();
    }
    public void closeRightPanel() {
        if(!rightPanelHidden)
            contentPane.remove(rightPanel);
        rightPanelHidden = true;
        refresh();
    }
    
    public void changeMainPanel(JScrollPane _tmp) {
        if(!mainPanelHidden)
            centerPanel.remove(mainPanel);
        
        mainPanelHidden = false;
        mainPanel = _tmp;
        centerPanel.add(mainPanel, BorderLayout.CENTER);

        refresh();
    }
    public void changeMainControlPanel(JPanel _tmp) {
        if(!mainControlPanelHidden)
            centerPanel.remove(controlMainPanel);
        
        mainControlPanelHidden = false;
        controlMainPanel = _tmp;
        centerPanel.add(controlMainPanel, BorderLayout.NORTH);

        refresh();
    }
    
    public void changeNavbar(JPanel _tmp) {
        if(!navbarHidden)
            contentPane.remove(Navbar);
        
        navbarHidden = false;
        Navbar = _tmp;
        contentPane.add(Navbar, BorderLayout.NORTH);

        refresh();
    }
    
    /*public void EditSeancePanel(seance s) {
        if(!leftPanelHidden)
            contentPane.remove(leftPanel);
        
        leftPanelHidden = false;
        leftPanel = new JScrollPane(new SeanceCreation(monControlleur, 200, s), 
                                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(leftPanel, BorderLayout.WEST);

        refresh();
    }*/
   
}


