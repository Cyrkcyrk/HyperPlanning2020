package Vue;

import Controlleur.Controlleur;
import Modele.recapitulatif;
import Modele.seance;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


import org.jfree.chart.*; 
import org.jfree.chart.plot.*; 
import org.jfree.data.*; 
import org.jfree.data.general.DefaultPieDataset;

/**
 * Créer un panel qui contient le récapitulatif des cours
 * @author KASYC Cyrille
 * @author LECOEUR Titouan
 * @author RASSOUW Clement
 */
public class RecapPanel extends JPanel{
    Controlleur monControlleur;
    ArrayList<recapitulatif> mesRecaps;
    GroupLayout layout;
    
    /**
     * Créer le pannel de récapitulatif de séances.
     * @param _ctrlr (Controlleur)
     * @param _recaps ArrayList(recapitulatif)
     */
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
    
    /**
     * Créer un panel de description des colonnes
     * @return (JPanel)
     */
    private JPanel legendeColonnes() {
        JPanel _return = new JPanel();
        
        JLabel LabelCours = new JLabel();
        JLabel LabelNombreHeure = new JLabel();
        JLabel LabelDureeTotale = new JLabel();
        JLabel LabelPremiereSeance = new JLabel();
        JLabel LabelDerniereSeance = new JLabel();
        JLabel LabelChart = new JLabel("");

        LabelCours.setText("<html><b><u>Cours</u></b></html>");
        LabelNombreHeure.setText("<html><b><u>Nb</u></b></html>");
        LabelDureeTotale.setText("<html><b><u>Durée totale</u></b></html>");
        LabelPremiereSeance.setText("<html><b><u>Premiere séance</u></b></html>");
        LabelDerniereSeance.setText("<html><b><u>Derniere Séance</u></b></html>");
        
        if(mesRecaps.size()>0 && mesRecaps.get(0).getNbrSeancePassee() > -1)
            LabelChart.setText("<html><b><u>Avancement</u></b></html>");

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
                .addComponent(LabelDureeTotale, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelChart)
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
                    .addComponent(LabelChart)
                )
                .addContainerGap()
            )
        );
        return _return;
    }
    
    /**
     * Classe qui génère un JPanel horizontal avec les informations du récapitulatif.
     */
    private class recap extends JPanel {
        /**
         * Créer le panel de recap 
         * @param monRecap (recapitulatif)
         */
        public recap(recapitulatif monRecap) {
            this.setBorder(new CustomBorder(1,1,1,1));
            
            JLabel LabelCours = new javax.swing.JLabel();
            JLabel LabelNombreHeure = new JLabel();
            JLabel LabelDureeTotale = new JLabel();
            JLabel LabelPremiereSeance = new JLabel();
            JLabel LabelDerniereSeance = new JLabel();
            //JLabel LabelNombrePassee = new JLabel();
            
            JPanel chart = new JPanel();
            chart.setMaximumSize(new Dimension(110,110));
            chart.setBorder(new CustomBorder(1,1,1,1));
            
            
            DefaultPieDataset dpd = new DefaultPieDataset();
            int slice = monRecap.getNbrSeancePassee();
            int total = monRecap.getNbrSeance();
            dpd.setValue("0", slice);
            dpd.setValue("1", total - slice);
            JFreeChart pie = ChartFactory.createPieChart("", dpd, false, false, false);
            PiePlot plot = (PiePlot) pie.getPlot();
            plot.setLabelGenerator(null);
            
            plot.setSectionPaint("1", new Color(232, 223, 162));
            plot.setSectionPaint("0", new Color(158, 217, 160));
            
            
            
            ChartPanel cPanel = new ChartPanel(pie); 
            cPanel.setPreferredSize(new Dimension(100,100));
            
            chart.add(cPanel); 
            
            LabelCours.setText("<html>"+ monRecap.getCours().getNom() + "</html>");
            LabelNombreHeure.setText("<html>"+ monRecap.getNbrSeance() +"</html>");
            //LabelNombrePassee.setText("<html>" + monRecap.getNbrSeancePassee() + "</html>");
            LabelDureeTotale.setText("<html>" + monRecap.getDuree() + "</html>");
            LabelPremiereSeance.setText("<html>" + monRecap.getPremiere().getDate().getDateManuscrite() + "</html>");
            LabelDerniereSeance.setText("<html>" + monRecap.getDerniere().getDate().getDateManuscrite()  + "</html>");
            
            GroupLayout layout = new GroupLayout(this);
            
            GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelCours, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelPremiereSeance, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelDerniereSeance, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelNombreHeure, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelDureeTotale, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            
            if(monRecap.getNbrSeancePassee()>-1)
                horizontal.addComponent(chart);
            
            horizontal.addContainerGap(0, Integer.MAX_VALUE);
            
            GroupLayout.ParallelGroup vertical = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(LabelCours)
                .addComponent(LabelPremiereSeance)
                .addComponent(LabelDerniereSeance)
                .addComponent(LabelNombreHeure)
                .addComponent(LabelDureeTotale);
            
            if(monRecap.getNbrSeancePassee()>-1)
                vertical.addComponent(chart);

            
            this.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(horizontal)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(vertical)
                    .addContainerGap()
                )
            );
        }
        
        
    }
}


