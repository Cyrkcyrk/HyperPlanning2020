/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning.Vue;

import DB_class.*;
import hyperplanning.*;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Cyrille
 */
public class TimetableListe extends JPanel {
    ArrayList<seance> mesSeances;
    Controlleur monControlleur;
    GroupLayout layout;
    
    
    public TimetableListe(Controlleur _ctrlr, ArrayList<seance> _seances) {
        this.monControlleur = _ctrlr;
        this.mesSeances = _seances;
        
        layout = new GroupLayout(this);
        this.setLayout(layout);

        this.createTimetable();
    }
    
    private void createTimetable(){
        GroupLayout.ParallelGroup HorizontalGroupe = layout.createParallelGroup();
        GroupLayout.SequentialGroup VerticalGroupe = layout.createSequentialGroup();
        
        for(int i=0; i<mesSeances.size(); i++) {
            JPanel _tmp = new SeancePanel(monControlleur, mesSeances.get(i), "horizontal");
            HorizontalGroupe.addComponent(_tmp);
            VerticalGroupe.addComponent(_tmp).addGap(5);
        }

        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGap(5)
            .addGroup(HorizontalGroupe)
            .addGap(5)
        );
        layout.setVerticalGroup(VerticalGroupe);
        
    }
    
    
}
