package Vue;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author KASYC Cyrille
 * @author LECOEUR Titouan
 * @author RASSOUW Clement
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
    
    
    /**
     * Créer la vue
     */
    public Vue () {
        //https://www.youtube.com/watch?v=-UvxwleNA20
        
        super("Hyperplanning - Projet JAVA ING3 2020");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(960, 720);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        contentPane = (JPanel) this.getContentPane();
        centerPanel = new JPanel(new BorderLayout());
        contentPane.add(centerPanel, BorderLayout.CENTER);
        
        this.setVisible(true);
    }
    
    /**
     * Revalide et repaint la vue
     */
    public void refresh() {
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    /**
     * Cache le panneau ouest
     */
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
    
    /**
     * Change le panneau de gauche par celui passé en parametres
     * @param _tmp (JScrollePane) 
     */
    public void changeLeftPanel(JScrollPane _tmp) {
        if(!leftPanelHidden)
            contentPane.remove(leftPanel);
        leftPanel = _tmp;
        contentPane.add(leftPanel, BorderLayout.WEST);
        leftPanelHidden = false;
        refresh();
    }
    /**
     * Fermer (cacher) le panneau de gauche
     */
    public void closeLeftPanel() {
        if(!leftPanelHidden)
            contentPane.remove(leftPanel);
        leftPanelHidden = true;
        refresh();
    }
    
    /**
     * Change le panneau de droite par celui passé en parametres
     * @param _tmpPannel (JScrollePane) 
     */
    public void changeRightPanel(JPanel _tmpPannel) {
        if(!rightPanelHidden)
            contentPane.remove(rightPanel);
        
        rightPanelHidden = false;
        rightPanel = new JScrollPane(_tmpPannel);
        contentPane.add(rightPanel, BorderLayout.EAST);

        refresh();
    }
    /**
     * Fermer le panneau de droite.
     */
    public void closeRightPanel() {
        if(!rightPanelHidden)
            contentPane.remove(rightPanel);
        rightPanelHidden = true;
        refresh();
    }
    
    /**
     * Change le panneau principal
     * @param _tmp (JScrollPane)
     */
    public void changeMainPanel(JScrollPane _tmp) {
        if(!mainPanelHidden)
            centerPanel.remove(mainPanel);
        
        mainPanelHidden = false;
        mainPanel = _tmp;
        centerPanel.add(mainPanel, BorderLayout.CENTER);

        refresh();
    }
    /**
     * Change la barre de controle au dessus du panneau principal.
     * @param _tmp JPanel
     */
    public void changeMainControlPanel(JPanel _tmp) {
        if(!mainControlPanelHidden)
            centerPanel.remove(controlMainPanel);
        
        mainControlPanelHidden = false;
        controlMainPanel = _tmp;
        centerPanel.add(controlMainPanel, BorderLayout.NORTH);

        refresh();
    }
    
    /**
     * Change la barre de navigation
     * @param _tmp JPanel
     */
    public void changeNavbar(JPanel _tmp) {
        if(!navbarHidden)
            contentPane.remove(Navbar);
        
        navbarHidden = false;
        Navbar = _tmp;
        contentPane.add(Navbar, BorderLayout.NORTH);

        refresh();
    }
   
}


