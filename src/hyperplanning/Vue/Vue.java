/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning.Vue;


import DB_class.seance;
import hyperplanning.Modele;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Cyrille
 */
public class Vue extends JFrame {
    
    JPanel contentPane, Navbar;
    JScrollPane centerPanel, leftPanel, rightPanel;
    boolean rightPanelHidden = true;
    boolean leftPanelHidden = false;
    boolean centerPanelHidden = false;


    GroupLayout layout;
    
    
    
    private Timetable monEDT;
    
    
    public Vue () {
        //https://www.youtube.com/watch?v=-UvxwleNA20
        
        super("Hyperplanning - Projet JAVA ING3 2020");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(960, 720);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        
        
        contentPane = (JPanel) this.getContentPane();
        
        Navbar = createNavbar();
        contentPane.add(Navbar, BorderLayout.NORTH);
        monEDT = new Timetable(this, Modele.SeanceParSalle(3));
        leftPanel = new JScrollPane(new SeanceCreation(this, 200, Modele.getSeance(1)),//monEDT.createSeance()),
                                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        
        contentPane.add(leftPanel, BorderLayout.WEST);
        
        centerPanel = new JScrollPane(monEDT);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        
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
    
    public void refresh() {
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    public void changeCenter(JPanel _tmpPannel) {
        if(!centerPanelHidden)
            contentPane.remove(centerPanel);
        
        centerPanelHidden = false;
        centerPanel = new JScrollPane(_tmpPannel);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        refresh();
    }
    
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
    
    public void UpdateLeft(SeanceCreation _tmp) {
        if(!leftPanelHidden)
            contentPane.remove(leftPanel);
        leftPanel = new JScrollPane(_tmp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(leftPanel, BorderLayout.WEST);
        leftPanelHidden = false;
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
    
    public void closeLeftPanel() {
        if(!leftPanelHidden)
            contentPane.remove(leftPanel);
        leftPanelHidden = true;
        refresh();
    }
    
    public void EditSeancePanel(seance s) {
        if(!leftPanelHidden)
            contentPane.remove(leftPanel);
        
        leftPanelHidden = false;
        leftPanel = new JScrollPane(new SeanceCreation(this, 200, s), 
                                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(leftPanel, BorderLayout.WEST);

        refresh();
    }
   
}


