/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning.Vue;

import DB_class.*;
import hyperplanning.Controlleur;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Cyrille
 */
public class SearchPanel<T> extends JPanel {
    private Controlleur monControlleur;
    private String type;
    private ArrayList<T> mesObjets;
    private int semaineNumber, yearNumber;
    
    private JButton leftArrow, rightArrow, valider;
    private JTextField SemaineSelector, yearSelector;
    private JLabel TypeIndicator;
    private JComboBox objectSelection, affichageType;
    
    private boolean advancedPanel = false;
    
    public SearchPanel(Controlleur _monControlleur, int _semaine, int _year) {
        super(new FlowLayout());
        semaineNumber = _semaine;
        monControlleur = _monControlleur;
        yearNumber = _year;
        buildBar();
    }
    
    public SearchPanel(Controlleur _monControlleur, ArrayList<T> _mesObjets, String _type, int _semaine, int _year) {
        super(new FlowLayout());
        
        advancedPanel = true;
        
        monControlleur = _monControlleur;
        mesObjets = _mesObjets;
        type = _type;
        semaineNumber = _semaine;
        yearNumber = _year;
        
        objectSelection = new JComboBox(mesObjets.toArray(new Object[0]));
        switch (type){
            case "enseignant":
            {
                objectSelection.setRenderer(new EnseignantCellRenderer());
                
                TypeIndicator = new JLabel("Selectionnez un enseignant");
                break;
            }
            
            case "groupe":
            {
                objectSelection.setRenderer(new GroupeCellRenderer());
                
                TypeIndicator = new JLabel("Selectionnez un groupe");
                break;
            }
            
            case "salle":
            {
                objectSelection.setRenderer(new SalleCellRenderer());
                
                TypeIndicator = new JLabel("Selectionnez une salle");
                break;
            }
        }
        objectSelection.setSelectedIndex(-1);
        
        buildBar();
    }
    
    
    private void buildBar() {
        
        String[] stringStyleAffichage = {"Afficher en grille", "Afficher en liste"};
        affichageType = new JComboBox(stringStyleAffichage);
        if(monControlleur.getAffichageType())
            affichageType.setSelectedIndex(0);
        else
            affichageType.setSelectedIndex(1);
        
        leftArrow = new JButton("<");
        leftArrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(semaineNumber > 0) 
                    semaineNumber--;
                
                SemaineSelector.setText("" + semaineNumber);
            }
        });
        
        rightArrow = new JButton(">");
        rightArrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(semaineNumber < 52) 
                    semaineNumber++;
                
                SemaineSelector.setText("" + semaineNumber);
            }
        });
        
        
        valider = new JButton("Valider");
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(advancedPanel) {
                    System.out.println("ON RENTRE DEDANS!");
                    switch (type){
                        case "enseignant":
                        {
                            utilisateur _tmp = (utilisateur) objectSelection.getItemAt(objectSelection.getSelectedIndex());
                            monControlleur.setEnseignantID(_tmp.getID());
                            break;
                        }

                        case "groupe":
                        {
                            groupe _tmp = (groupe) objectSelection.getItemAt(objectSelection.getSelectedIndex());
                            monControlleur.setGroupeID(_tmp.getID());
                            break;
                        }

                        case "salle":
                        {
                            salle _tmp = (salle) objectSelection.getItemAt(objectSelection.getSelectedIndex());
                            monControlleur.setSalleID(_tmp.getID());
                            break;
                        }
                    }
                }
                monControlleur.setSelectedSemaine(semaineNumber);
                monControlleur.setSelectedYear(yearNumber);
                
                if(affichageType.getSelectedIndex() == 0)
                    monControlleur.setAffichageType(true);
                else
                    monControlleur.setAffichageType(false);

                
                monControlleur.refreshTimetable();
            }
        });
        
        
        SemaineSelector = new JTextField();
        SemaineSelector.setText("" + semaineNumber);
        SemaineSelector.setColumns(3);
        SemaineSelector.setPreferredSize(new Dimension(0,26));

        //https://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield
        SemaineSelector.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performAction(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performAction(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performAction(e);
            }
            
            private void performAction(DocumentEvent e) {
                String texte = SemaineSelector.getText();
                int numero = 0;
                if(!texte.isEmpty()) {
                    try {
                       numero = (int) Integer.parseInt(texte);
                       
                       if(numero > 0 && numero < 53) {
                           semaineNumber = numero;
                       }
                    } catch (Exception ex) {
                        
                    }
                }
            }
            
        });
        //new JComboBox(tousLesTypeCours.toArray(new Type_cours[0]));
        //TypeIndicator = new JLabel(type);
        
        
        
        yearSelector = new JTextField();
        yearSelector.setText("" + yearNumber);
        yearSelector.setColumns(5);
        yearSelector.setPreferredSize(new Dimension(0,26));

        //https://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield
        yearSelector.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performAction(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performAction(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performAction(e);
            }
            
            private void performAction(DocumentEvent e) {
                String texte = yearSelector.getText();
                int numero = 0;
                if(!texte.isEmpty()) {
                    try {
                       numero = (int) Integer.parseInt(texte);
                       
                       if(numero > 2015 && numero < 2025) {
                           yearNumber = numero;
                       }
                    } catch (Exception ex) {
                        
                    }
                }
            }
            
        });
        //new JComboBox(tousLesTypeCours.toArray(new Type_cours[0]));
        //TypeIndicator = new JLabel(type);
        
        if(advancedPanel) {
            this.add(TypeIndicator);
            this.add(objectSelection);
        }
        this.add(leftArrow);
        this.add(SemaineSelector);
        this.add(rightArrow);
        //this.add(yearSelector);
        this.add(affichageType);
        this.add(valider);
    }
    
    
    private static class SalleCellRenderer implements ListCellRenderer {
        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String aAfficher;
            if (index == -1 && value == null) aAfficher = "SALLE";
            else {
                salle _tmp = (salle) value;
                aAfficher = _tmp.getSite() + " - " + _tmp.getNom();
            }
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, aAfficher, index, isSelected, cellHasFocus);
            
            //renderer.setEnabled(false);
            
            return renderer;
        }
    }
    
    private static class GroupeCellRenderer implements ListCellRenderer {
        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String aAfficher;
            if (index == -1 && value == null) aAfficher = "GROUPE";
            else {
                groupe _tmp = (groupe) value;
                aAfficher = _tmp.getPromotion() + " - " + _tmp.getNom();
            }
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, aAfficher, index, isSelected, cellHasFocus);
            
            //renderer.setEnabled(false);
            
            return renderer;
        }
    }
    
    private static class EnseignantCellRenderer implements ListCellRenderer {
        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String aAfficher;
            if (index == -1 && value == null) aAfficher = "ENSEIGNANT";
            else {
                utilisateur _tmp = (utilisateur) value;
                aAfficher = _tmp.getPrenom() + " " + _tmp.getNom();
            }
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, aAfficher, index, isSelected, cellHasFocus);
            
            //renderer.setEnabled(false);
            
            return renderer;
        }
    }
    
}
