package Vue;

import Controlleur.Controlleur;
import Modele.seance;
import java.util.ArrayList;
import javax.swing.*;

/**
 *  Affichage de l'EDT sous format de liste
 * @author KASYC Cyrille
 * @author LECOEUR Titouan
 * @author RASSOUW Clement
 */
public class TimetableListe extends JPanel {
    ArrayList<seance> mesSeances;
    Controlleur monControlleur;
    GroupLayout layout;
    
    /**
     * Créer un EDT sous format liste
     * @param _ctrlr (Controlleur)
     * @param _seances ArrayList(seance) Liste de toutes les séances a afficher
     */
    public TimetableListe(Controlleur _ctrlr, ArrayList<seance> _seances) {
        this.monControlleur = _ctrlr;
        this.mesSeances = _seances;
        
        layout = new GroupLayout(this);
        this.setLayout(layout);

        this.createTimetable();
    }
    
    /**
     * Méthode appellée à la fin de chaques constructeurs pour créer et remplir la liste.
     */
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
