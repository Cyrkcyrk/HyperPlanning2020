/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controlleur.Controlleur;
import Modele.recapitulatif;
import Modele.seance;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Cyrille
 */
public class RecapPanel extends JPanel{
    Controlleur monControlleur;
    ArrayList<recapitulatif> mesRecaps;
    GroupLayout layout;
    
    public RecapPanel(Controlleur _ctrlr, ArrayList<recapitulatif> _recaps) {
        this.monControlleur = _ctrlr;
        this.mesRecaps = _recaps;
        
        layout = new GroupLayout(this);
        this.setLayout(layout);

        this.createPanel();
    }
    
    /**
     * Méthode appellée à la fin de chaques constructeurs pour créer et remplir la liste.
     */
    private void createPanel(){
        GroupLayout.ParallelGroup HorizontalGroupe = layout.createParallelGroup();
        GroupLayout.SequentialGroup VerticalGroupe = layout.createSequentialGroup();
        
        JPanel legende = legendeColonnes();
        HorizontalGroupe.addComponent(legende);
        VerticalGroupe.addComponent(legende).addGap(5);
            
            
        for(int i=0; i<mesRecaps.size(); i++) {
            JPanel _tmp = new recap(mesRecaps.get(i));
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
    
    private JPanel legendeColonnes() {
        JPanel _return = new JPanel();
        
        JLabel LabelCours = new javax.swing.JLabel();
        JLabel LabelNombreHeure = new javax.swing.JLabel();
        JLabel LabelDureeTotale = new javax.swing.JLabel();
        JLabel LabelPremiereSeance = new javax.swing.JLabel();
        JLabel LabelDerniereSeance = new javax.swing.JLabel();

        LabelCours.setText("<html><b><u>Cours</u></b></html>");
        LabelNombreHeure.setText("<html><b><u>Nb</u></b></html>");
        LabelDureeTotale.setText("<html><b><u>Durée totale</u></b></html>");
        LabelPremiereSeance.setText("<html><b><u>Premiere séance</u></b></html>");
        LabelDerniereSeance.setText("<html><b><u>Derniere Séance</u></b></html>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(_return);
        _return.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelCours, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelPremiereSeance, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelDerniereSeance, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelNombreHeure, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelDureeTotale)
                .addContainerGap(335, Short.MAX_VALUE)
            )
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelCours)
                    .addComponent(LabelPremiereSeance)
                    .addComponent(LabelDerniereSeance)
                    .addComponent(LabelNombreHeure)
                    .addComponent(LabelDureeTotale)
                )
                .addContainerGap()
            )
        );
        return _return;
    }
    
    
    private class recap extends JPanel {
        
        public recap(recapitulatif monRecap) {
            this.setBorder(new CustomBorder(1,1,1,1));
            
            JLabel LabelCours = new javax.swing.JLabel();
            JLabel LabelNombreHeure = new javax.swing.JLabel();
            JLabel LabelDureeTotale = new javax.swing.JLabel();
            JLabel LabelPremiereSeance = new javax.swing.JLabel();
            JLabel LabelDerniereSeance = new javax.swing.JLabel();
            
            LabelCours.setText("<html>"+ monRecap.getCours().getNom() + "</html>");
            LabelNombreHeure.setText("<html>"+ monRecap.getNbrSeance() +"</html>");
            LabelDureeTotale.setText("<html>" + monRecap.getDuree() + "</html>");
            LabelPremiereSeance.setText("<html>" + monRecap.getPremiere().getDate().getDateManuscrite() + "</html>");
            LabelDerniereSeance.setText("<html>" + monRecap.getDerniere().getDate().getDateManuscrite()  + "</html>");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(LabelCours, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(LabelPremiereSeance, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(LabelDerniereSeance, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(LabelNombreHeure, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(LabelDureeTotale)
                    .addContainerGap(335, Short.MAX_VALUE)
                )
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LabelCours)
                        .addComponent(LabelPremiereSeance)
                        .addComponent(LabelDerniereSeance)
                        .addComponent(LabelNombreHeure)
                        .addComponent(LabelDureeTotale)
                    )
                    .addContainerGap()
                )
            );
        }
        
        
    }
}


