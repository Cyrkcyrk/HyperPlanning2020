package Vue;

import Controlleur.Controlleur;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author KASYC Cyrille
 * @author LECOEUR Titouan
 * @author RASSOUW Clement
 */
public class NavbarPanel extends JPanel {
    Controlleur monControlleur;
    /**
     * Créer la barre de navigation principale
     * @param _ctrleur  (controlleur)
     */
    public NavbarPanel(Controlleur _ctrleur) {
        
        monControlleur = _ctrleur;
        
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        
        JPanel left = new JPanel();
        left.setLayout(new FlowLayout( FlowLayout.LEFT ));
        
        JMenuBar leftBar = new JMenuBar();
        
        switch (monControlleur.getDroits()) {
            case 1:
            case 2:
            {
                JMenu menuGroupe = new JMenu("Groupes");
                JMenuItem itemEDT = new JMenuItem( new AbstractAction("Emplois du temps") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        monControlleur.setSelectedEDT("groupe");
                        monControlleur.setSeanceAAfficher(false);
                        monControlleur.refreshTimetable();
                        monControlleur.refreshControlPanel();
                    } 
                });
                
                JMenuItem itemRecap = new JMenuItem( new AbstractAction("Recapitulatif des cours") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        monControlleur.setSelectedEDT("recapGroupe");
                        monControlleur.refreshTimetable();
                        monControlleur.refreshControlPanel();
                    } 
                });
                
                JMenuItem itemCoursAnnules = new JMenuItem( new AbstractAction("Cours annulés") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        monControlleur.setSelectedEDT("groupe");
                        monControlleur.setSeanceAAfficher(true);
                        monControlleur.refreshTimetable();
                        monControlleur.refreshControlPanel();
                    } 
                });
                menuGroupe.add(itemEDT);
                menuGroupe.add(itemRecap);
                menuGroupe.add(itemCoursAnnules);
                leftBar.add(menuGroupe);
                
                //--------------------------------------------------------------------------------------------
                
                JMenu menuPromotion = new JMenu("Promotions");
                
                JMenuItem itemPromoRecap = new JMenuItem( new AbstractAction("Recapitulatif des cours") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        Controlleur.ShowError("Pas encore codé");
                    } 
                });
                menuPromotion.add(itemPromoRecap);
                //leftBar.add(menuPromotion);
                
                //--------------------------------------------------------------------------------------------
                
                JMenu menuEnseignant = new JMenu("Enseignant");
                JMenuItem itemEnseignantEDT = new JMenuItem( new AbstractAction("Emplois du temps") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        monControlleur.setSelectedEDT("enseignant");
                        monControlleur.setSeanceAAfficher(false);
                        monControlleur.refreshTimetable();
                        monControlleur.refreshControlPanel();
                    } 
                });
                
                JMenuItem itemEnseignantRecap = new JMenuItem( new AbstractAction("Recapitulatif des cours") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        monControlleur.setSelectedEDT("recapEnseignant");
                        monControlleur.refreshTimetable();
                        monControlleur.refreshControlPanel();
                    } 
                });
                
                JMenuItem itemEnseignantCoursAnnules = new JMenuItem( new AbstractAction("Cours annulés") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        monControlleur.setSelectedEDT("enseignant");
                        monControlleur.setSeanceAAfficher(true);
                        monControlleur.refreshTimetable();
                        monControlleur.refreshControlPanel();
                    } 
                });
                menuEnseignant.add(itemEnseignantEDT);
                menuEnseignant.add(itemEnseignantRecap);
                menuEnseignant.add(itemEnseignantCoursAnnules);
                leftBar.add(menuEnseignant);
                
                //--------------------------------------------------------------------------------------------
                
                JMenu menuSalles = new JMenu("Salles");
                JMenuItem itemSalleEDT = new JMenuItem( new AbstractAction("Emplois du temps") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        monControlleur.setSelectedEDT("salle");
                        monControlleur.setSeanceAAfficher(false);
                        monControlleur.refreshTimetable();
                        monControlleur.refreshControlPanel();
                    } 
                });
                JMenuItem itemSalleCoursAnnules = new JMenuItem( new AbstractAction("Cours annulés") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        monControlleur.setSelectedEDT("salle");
                        monControlleur.setSeanceAAfficher(true);
                        monControlleur.refreshTimetable();
                        monControlleur.refreshControlPanel();
                    } 
                });
                menuSalles.add(itemSalleEDT);
                menuSalles.add(itemSalleCoursAnnules);
                leftBar.add(menuSalles);
                
                
                //--------------------------------------------------------------------------------------------
                if(monControlleur.getDroits() == 1){
                    JMenuItem itemNouveauCours = new JMenuItem( new AbstractAction("Ajouter un nouveau cours") {
                        @Override
                        public void actionPerformed(ActionEvent e) { 
                            monControlleur.createNewSeance();
                        } 
                    });
                    leftBar.add(itemNouveauCours);
                }
                break;
            }
            case 3:
            case 4:
            default:
            {
                JMenu menuUtilisateur = new JMenu("Utilisateur");
                JMenuItem itemEDT = new JMenuItem( new AbstractAction("Emplois du temps") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        monControlleur.setSelectedEDT("self");
                        monControlleur.setSeanceAAfficher(false);
                        monControlleur.refreshTimetable();
                        monControlleur.refreshControlPanel();
                    } 
                });
                
                JMenuItem itemRecap = new JMenuItem( new AbstractAction("Recapitulatif des cours") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        monControlleur.setSelectedEDT("recapSelf");
                        monControlleur.refreshTimetable();
                        monControlleur.refreshControlPanel();
                    } 
                });
                
                JMenuItem itemCoursAnnules = new JMenuItem( new AbstractAction("Cours annulés") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        monControlleur.setSelectedEDT("self");
                        monControlleur.setSeanceAAfficher(true);
                        monControlleur.refreshTimetable();
                        monControlleur.refreshControlPanel();
                    } 
                });
                
                menuUtilisateur.add(itemEDT);
                menuUtilisateur.add(itemRecap);
                menuUtilisateur.add(itemCoursAnnules);
                leftBar.add(menuUtilisateur);
                
                
                JMenu menuSalles = new JMenu("Salles");
                JMenuItem itemSalleEDT = new JMenuItem( new AbstractAction("Emplois du temps") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        monControlleur.setSelectedEDT("salle");
                        monControlleur.setSeanceAAfficher(false);
                        monControlleur.refreshTimetable();
                        monControlleur.refreshControlPanel();
                    } 
                });
                JMenuItem itemSalleCoursAnnules = new JMenuItem( new AbstractAction("Cours annulés") {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        monControlleur.setSelectedEDT("salle");
                        monControlleur.setSeanceAAfficher(true);
                        monControlleur.refreshTimetable();
                        monControlleur.refreshControlPanel();
                    } 
                });
                menuSalles.add(itemSalleEDT);
                menuSalles.add(itemSalleCoursAnnules);
                leftBar.add(menuSalles);
                break;
            }
        }
        
        left.add(leftBar);
        
        JPanel right = new JPanel();
        right.setLayout(new FlowLayout( FlowLayout.RIGHT ));
        
        
        JMenuBar rightBar = new JMenuBar();
        JMenu NameMenu = new JMenu(monControlleur.getConnectedUser().getPrenom() + " " + monControlleur.getConnectedUser().getNom());
        JMenuItem quitter = new JMenuItem( new AbstractAction("Quitter") {
            @Override
            public void actionPerformed(ActionEvent e) { 
                System.exit(0);
            } 
        });
        
        NameMenu.add(quitter);
        rightBar.add(NameMenu);
        right.add(rightBar);
        
        this.add(left);
        this.add(Box.createHorizontalBox());
        this.add(right);
    }
}
