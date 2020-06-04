/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning.Vue;

import DB_class.*;
import hyperplanning.Controlleur;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author Cyrille
 */
public class SeancePanel extends JPanel {
    private seance s;
    private Controlleur monControlleur;

    public SeancePanel(Controlleur _ctrlr, seance _s, String Param) {
        s = _s;
        monControlleur = _ctrlr;
        
        switch (Param) {
            case "petit":
            case "small":
            case "s":
            {
                petit();
                break;
            }
            case "moyen":
            {
                moyen();
                break;
            }
            case "minuscule":
            case "XS":
            case "vide":
            {
                vide();
                break;
            }
            case "horizontal": 
            {
                horizontal();
                this.setBorder(new CustomBorder(1,1,1,1));
                break;
            }
            case "rightPanel":
            {
                this.setPreferredSize(new Dimension(200, 0));
                
                if(monControlleur.getDroits() == 1 || monControlleur.getDroits() == 2) {
                    RightPanelAdmin();
                }
                else {
                    RightPanel();
                }
                    
                
                
                break;
            }
            
            case "leftPanel":
            {
                LeftPanel();
                break;
            }
            default: 
            {
                
                break;
            } 
        }
        if(Param != "rightPanel" && Param != "leftPanel")
        {
            addMouseListener(new MouseAdapter() { 
                @Override
                public void mouseClicked(MouseEvent me) { 
                    //System.out.println(me); 
                    monControlleur.ShowSeanceInformations(s);
                } 
            });
        }
    }
    
    private void moyen()
    {
        JLabel jLabel1 = new javax.swing.JLabel();
        JLabel jLabel2 = new javax.swing.JLabel();
        JPanel jPanel2 = new javax.swing.JPanel();
        JLabel jLabel3 = new javax.swing.JLabel();

        jLabel1.setText(s.getDebut().toString());
        jLabel2.setText(s.getFin().toString());
        jLabel3.setText(s.getCours().getNom());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                )
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
            )
        );
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(this);
        this.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                )
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addComponent(jPanel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
            )
        );

 
        this.setBorder(new CustomBorder(1, 1, 1, 0));
        this.setBackground(Color.gray);
        jPanel2.setBackground(Color.gray);
    }
    
    private void petit()
    {
        JLabel jLabel1 = new javax.swing.JLabel();
        JLabel jLabel2 = new javax.swing.JLabel();
        JPanel jPanel2 = new javax.swing.JPanel();
        JLabel jLabel3 = new javax.swing.JLabel();
        JLabel jLabel4 = new javax.swing.JLabel();
        JLabel jLabel5 = new javax.swing.JLabel();
        JLabel jLabel6 = new javax.swing.JLabel();
        JLabel jLabel7 = new javax.swing.JLabel();
        JLabel jLabel8 = new javax.swing.JLabel();
        
        
        jLabel3.setText(s.getCours().getNom());
        
        jLabel1.setText("");
        jLabel2.setText("");
        jLabel4.setText("");
        jLabel5.setText("");
        jLabel6.setText("");
        jLabel7.setText("");
        jLabel8.setText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                )
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 0, Short.MAX_VALUE)
                .addComponent(jLabel8)
            )
        );
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(this);
        this.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                )
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap()
            )
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2)
            )
        );

 
        this.setBorder(new CustomBorder(1, 1, 1, 0));
        this.setBackground(Color.gray);
        jPanel2.setBackground(Color.gray);
    }
    
    private void vide()
    {
        this.setBorder(new CustomBorder(1, 1, 1, 0));
        this.setBackground(Color.gray);
    }
    
    private void horizontal() {
        
        JLabel LabelDate = new javax.swing.JLabel();
        JLabel LabelHeure = new javax.swing.JLabel();
        JLabel LabelMatiere = new javax.swing.JLabel();
       
        LabelDate.setText("<html>"+ s.getDate().getDateManuscrite() + "</html>");
        LabelHeure.setText("<html>"+ s.getDebut() +" - "+ s.getFin() +"</html>");
        LabelMatiere.setText(s.getCours().getNom());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelHeure, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelMatiere)
                .addContainerGap(335, Short.MAX_VALUE)
            )
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelDate)
                    .addComponent(LabelHeure)
                    .addComponent(LabelMatiere)
                )
                .addContainerGap()
            )
        );
    }
    
    private void RightPanel() {
        JLabel MatiereLabel = new javax.swing.JLabel();
        JLabel DateLabel = new javax.swing.JLabel();
        JLabel sallesLabel = new javax.swing.JLabel();
        JLabel ProfLabel = new javax.swing.JLabel();
        JLabel TypeLabel = new javax.swing.JLabel();
        JLabel TDLabel = new javax.swing.JLabel();
        JLabel CloseLabel = new javax.swing.JLabel();

        MatiereLabel.setFont(new java.awt.Font("Tahoma", 1, 18));
        MatiereLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MatiereLabel.setText(s.getCours().getNom());

        DateLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        DateLabel.setText("<html>"+ s.getDate().getDateManuscrite() +" :<br> "+ s.getDebut() +" - "+ s.getFin() +"</html>");
        
        
        String SallesTexte = "<html><b>Salle";
        if(s.getSalles().size() > 1)
            SallesTexte += "s";
        SallesTexte += " :</b>";
        
        for (int i=0; i<s.getSalles().size(); i++){
            salle _salle = s.getSalles().get(i);
            
            SallesTexte += "<br>" + _salle.getSite() + " - " + _salle.getNom();
        }
        SallesTexte += "</html>";
        sallesLabel.setText(SallesTexte);
        sallesLabel.setFont(new java.awt.Font("Tahoma", 0, 11));
        
        
        String ProfTexte = "<html><b>Enseignant";
        if(s.getSalles().size() > 1)
            ProfTexte += "s";
        ProfTexte += " :</b>";
        
        for (int i=0; i<s.getEnseignants().size(); i++){
            utilisateur _prof = s.getEnseignants().get(i);
            
            ProfTexte += "<br>" + _prof.getPrenom() + " " + _prof.getNom();
        }
        ProfTexte += "</html>";
        ProfLabel.setText(ProfTexte);
        ProfLabel.setFont(new java.awt.Font("Tahoma", 0, 11));
        
        TypeLabel.setText("<html><b>Type : </b>" + s.getType().getType() + "</html>");
        TypeLabel.setFont(new java.awt.Font("Tahoma", 0, 11));
        
        
        String TDTexte = "<html><b>TD";
        if(s.getGroupes().size() > 1)
            TDTexte += "s";
        TDTexte += " :</b>";
        
        for (int i=0; i<s.getGroupes().size(); i++){
            groupe _TD = s.getGroupes().get(i);
            
            TDTexte += "<br>" + _TD.getPromotion() + " " + _TD.getNom();
        }
        TDTexte += "</html>";
        TDLabel.setText(TDTexte);
        TDLabel.setFont(new java.awt.Font("Tahoma", 0, 11));
        
        CloseLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CloseLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CloseLabel.setText("X");
        CloseLabel.setToolTipText("Fermer");
        CloseLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        CloseLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));


        CloseLabel.addMouseListener(new MouseAdapter() { 
            @Override
            public void mouseClicked(MouseEvent me) { 
                //System.out.println(me); 
                monControlleur.closeRightPanel();
            } 
        });
        
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        
        
        
        GroupLayout.ParallelGroup HorizontalGroupe = layout.createParallelGroup()
            .addGroup(layout.createParallelGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(TypeLabel)
                    .addComponent(ProfLabel)
                    .addComponent(sallesLabel)
                    .addComponent(DateLabel)
                    .addComponent(MatiereLabel)
                    .addComponent(TDLabel)
                )
            );
        
        HorizontalGroupe.addGroup(layout.createSequentialGroup()
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 0, Integer.MAX_VALUE)
            .addComponent(CloseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
                    

        
        layout.setHorizontalGroup(
            layout.createParallelGroup()
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HorizontalGroupe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            )
        );
        
        
        GroupLayout.SequentialGroup VerticalGroupe = layout.createSequentialGroup()
            .addContainerGap();
        
        VerticalGroupe
            .addComponent(CloseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(4, 4, 4);
        
        VerticalGroupe.addComponent(MatiereLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(DateLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(sallesLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(ProfLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(TypeLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(TDLabel)
            .addContainerGap();
        

        
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VerticalGroupe)
        );
    }
    private void RightPanelAdmin() {
        JLabel MatiereLabel = new javax.swing.JLabel();
        JLabel DateLabel = new javax.swing.JLabel();
        JLabel sallesLabel = new javax.swing.JLabel();
        JLabel ProfLabel = new javax.swing.JLabel();
        JLabel TypeLabel = new javax.swing.JLabel();
        JLabel TDLabel = new javax.swing.JLabel();
        JLabel CloseLabel = new javax.swing.JLabel();
        JButton EditBtn = new JButton();

        MatiereLabel.setFont(new java.awt.Font("Tahoma", 1, 18));
        MatiereLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MatiereLabel.setText(s.getCours().getNom());

        DateLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        DateLabel.setText("<html>"+ s.getDate().getDateManuscrite() +" :<br> "+ s.getDebut() +" - "+ s.getFin() +"</html>");
        
        
        String SallesTexte = "<html><b>Salle";
        if(s.getSalles().size() > 1)
            SallesTexte += "s";
        SallesTexte += " :</b>";
        
        for (int i=0; i<s.getSalles().size(); i++){
            salle _salle = s.getSalles().get(i);
            
            SallesTexte += "<br>" + _salle.getSite() + " - " + _salle.getNom();
        }
        SallesTexte += "</html>";
        sallesLabel.setText(SallesTexte);
        sallesLabel.setFont(new java.awt.Font("Tahoma", 0, 11));
        
        
        String ProfTexte = "<html><b>Enseignant";
        if(s.getSalles().size() > 1)
            ProfTexte += "s";
        ProfTexte += " :</b>";
        
        for (int i=0; i<s.getEnseignants().size(); i++){
            utilisateur _prof = s.getEnseignants().get(i);
            
            ProfTexte += "<br>" + _prof.getPrenom() + " " + _prof.getNom();
        }
        ProfTexte += "</html>";
        ProfLabel.setText(ProfTexte);
        ProfLabel.setFont(new java.awt.Font("Tahoma", 0, 11));
        
        TypeLabel.setText("<html><b>Type : </b>" + s.getType().getType() + "</html>");
        TypeLabel.setFont(new java.awt.Font("Tahoma", 0, 11));
        
        
        String TDTexte = "<html><b>TD";
        if(s.getGroupes().size() > 1)
            TDTexte += "s";
        TDTexte += " :</b>";
        
        for (int i=0; i<s.getGroupes().size(); i++){
            groupe _TD = s.getGroupes().get(i);
            
            TDTexte += "<br>" + _TD.getPromotion() + " " + _TD.getNom();
        }
        TDTexte += "</html>";
        TDLabel.setText(TDTexte);
        TDLabel.setFont(new java.awt.Font("Tahoma", 0, 11));
        
        CloseLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CloseLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CloseLabel.setText("X");
        CloseLabel.setToolTipText("Fermer");
        CloseLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        CloseLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));


        CloseLabel.addMouseListener(new MouseAdapter() { 
            @Override
            public void mouseClicked(MouseEvent me) { 
                //System.out.println(me); 
                monControlleur.closeRightPanel();
            } 
        });
        
        
        
        EditBtn.setText("Modifier");
        EditBtn.setToolTipText("Changer la seance");
        EditBtn.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monControlleur.editSeance(s);
            }
        });
        
        
        
        
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        
        
        
        GroupLayout.ParallelGroup HorizontalGroupe = layout.createParallelGroup()
            .addGroup(layout.createParallelGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(TypeLabel)
                    .addComponent(ProfLabel)
                    .addComponent(sallesLabel)
                    .addComponent(DateLabel)
                    .addComponent(MatiereLabel)
                    .addComponent(TDLabel)
                )
            );
        
        HorizontalGroupe.addGroup(layout.createSequentialGroup()
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 0, Integer.MAX_VALUE)
            .addComponent(CloseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(EditBtn)
        );
                    

        
        layout.setHorizontalGroup(
            layout.createParallelGroup()
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HorizontalGroupe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            )
        );
        
        
        GroupLayout.SequentialGroup VerticalGroupe = layout.createSequentialGroup()
            .addContainerGap();
        
        VerticalGroupe
            .addComponent(CloseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(4, 4, 4);
        
        VerticalGroupe.addComponent(MatiereLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(DateLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(sallesLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(ProfLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(TypeLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(TDLabel)
            .addContainerGap();
        
        VerticalGroupe.addComponent(EditBtn);

        
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VerticalGroupe)
        );
    }
    private void LeftPanel() {
        JLabel MatiereLabel = new javax.swing.JLabel();
        JLabel DateLabel = new javax.swing.JLabel();
        JLabel sallesLabel = new javax.swing.JLabel();
        JLabel ProfLabel = new javax.swing.JLabel();
        JLabel TypeLabel = new javax.swing.JLabel();
        JLabel TDLabel = new javax.swing.JLabel();
        JLabel CloseLabel = new javax.swing.JLabel();

        MatiereLabel.setFont(new java.awt.Font("Tahoma", 1, 18));
        MatiereLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MatiereLabel.setText(s.getCours().getNom());

        DateLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        DateLabel.setText("<html>"+ s.getDate().getDateManuscrite() +" :<br> "+ s.getDebut() +" - "+ s.getFin() +"</html>");
        
        
        String SallesTexte = "<html><b>Salle";
        if(s.getSalles().size() > 1)
            SallesTexte += "s";
        SallesTexte += " :</b>";
        
        for (int i=0; i<s.getSalles().size(); i++){
            salle _salle = s.getSalles().get(i);
            
            SallesTexte += "<br>" + _salle.getSite() + " - " + _salle.getNom();
        }
        SallesTexte += "</html>";
        sallesLabel.setText(SallesTexte);
        sallesLabel.setFont(new java.awt.Font("Tahoma", 0, 11));
        
        
        String ProfTexte = "<html><b>Enseignant";
        if(s.getSalles().size() > 1)
            ProfTexte += "s";
        ProfTexte += " :</b>";
        
        for (int i=0; i<s.getEnseignants().size(); i++){
            utilisateur _prof = s.getEnseignants().get(i);
            
            ProfTexte += "<br>" + _prof.getPrenom() + " " + _prof.getNom();
        }
        ProfTexte += "</html>";
        ProfLabel.setText(ProfTexte);
        ProfLabel.setFont(new java.awt.Font("Tahoma", 0, 11));
        
        TypeLabel.setText("<html><b>Type : </b>" + s.getType().getType() + "</html>");
        TypeLabel.setFont(new java.awt.Font("Tahoma", 0, 11));
        
        
        String TDTexte = "<html><b>TD";
        if(s.getGroupes().size() > 1)
            TDTexte += "s";
        TDTexte += " :</b>";
        
        for (int i=0; i<s.getGroupes().size(); i++){
            groupe _TD = s.getGroupes().get(i);
            
            TDTexte += "<br>" + _TD.getPromotion() + " " + _TD.getNom();
        }
        TDTexte += "</html>";
        TDLabel.setText(TDTexte);
        TDLabel.setFont(new java.awt.Font("Tahoma", 0, 11));
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        
        
        
        GroupLayout.ParallelGroup HorizontalGroupe = layout.createParallelGroup()
            .addGroup(layout.createParallelGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(TypeLabel)
                    .addComponent(ProfLabel)
                    .addComponent(sallesLabel)
                    .addComponent(DateLabel)
                    .addComponent(MatiereLabel)
                    .addComponent(TDLabel)
                )
            );
        
       
        
        layout.setHorizontalGroup(
            layout.createParallelGroup()
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HorizontalGroupe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            )
        );
        
        
        GroupLayout.SequentialGroup VerticalGroupe = layout.createSequentialGroup()
            .addContainerGap();
        
        
        VerticalGroupe.addComponent(MatiereLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(DateLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(sallesLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(ProfLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(TypeLabel)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(TDLabel)
            .addContainerGap();

        
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VerticalGroupe)
        );
    }
}
