/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning.Vue;

import DB_class.cours;
import DB_class.groupe;
import DB_class.salle;
import DB_class.utilisateur;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.optionalusertools.TimeChangeListener;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;
import hyperplanning.Modele;
import hyperplanning.customDate;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Cyrille
 */
public class SeanceCreation extends JPanel {
    private int width;
    private ArrayList<LabelAndDelete> SelectedGroupe = new ArrayList<LabelAndDelete>();
    private ArrayList<LabelAndDelete> SelectedEnseignant = new ArrayList<LabelAndDelete>();
    private ArrayList<LabelAndDelete> SelectedSalle = new ArrayList<LabelAndDelete>();
    private cours selectedCours = null;
    private int selectedCoursComboID = -1;
    private Vue maVue;
    private SeanceCreation monPanel = this;
    
    private ArrayList<utilisateur> enseignantsArray = new ArrayList<utilisateur>();
    private ArrayList<salle> sallesArray = new ArrayList<salle>();
    
    private customDate date = null, heureDebut = null, heureFin = null;
    
    public SeanceCreation(Vue _maVue, int _width)
    {
        maVue = _maVue;
        width = _width;
        
        creerPanel();
    }
    public SeanceCreation(Vue _maVue)
    {
        maVue = _maVue;
        width = 200;
        creerPanel();
    }
    
    public SeanceCreation(
            Vue _maVue, 
            int _width, 
            ArrayList<LabelAndDelete> _SelectedGroupe,
            ArrayList<LabelAndDelete> _SelectedEnseignant,
            ArrayList<LabelAndDelete> _SelectedSalle,
            cours _selectedCours,
            int _selectedCoursComboID,
            customDate _date,
            customDate _HDebut,
            customDate _HFin
        ) {
        maVue = _maVue;
        width = 200;
        SelectedGroupe = _SelectedGroupe;
        SelectedEnseignant = _SelectedEnseignant;
        SelectedSalle = _SelectedSalle;
        selectedCours = _selectedCours;
        selectedCoursComboID = _selectedCoursComboID;
        date = _date;
        heureDebut = _HDebut;
        heureFin = _HFin;
        
        getAvailableEnseignants();
        getAvailableSalles();
        creerPanel();
    }
    
    private void getAvailableEnseignants() {
        try {
            if(selectedCours != null && date != null && heureDebut != null && heureFin != null)
                enseignantsArray = Modele.AvailableProf(selectedCours.getID(), date, heureDebut, heureFin);
        } catch (Exception e) {
            System.out.println("CAN'T GET PROF! " + e);
        }
    }
    
    private void getAvailableSalles() {
        try {
            if(SelectedGroupe != null && date != null && heureDebut != null && heureFin != null)
            {
                ArrayList<groupe> _tmpList = new ArrayList<groupe>();
                for(int i=0; i< SelectedGroupe.size(); i++)
                {
                    _tmpList.add(i, (groupe) SelectedGroupe.get(i).getObject());
                }
                sallesArray = Modele.AvailableClass(Modele.GroupeTotalEtudiant(_tmpList), date, heureDebut, heureFin);
            }
        } catch (Exception e) {
            System.out.println("CAN'T GET CLASS! " + e);
        }
    }
    
    
    
    public void creerPanel()
    {
        GroupLayout layout = new GroupLayout(this); 
        this.setLayout(layout);
        this.setBorder(new CustomBorder(1, 0, 0, 0));
        
        Dimension verticalMaxSize = new Dimension(
            Integer.MAX_VALUE,
            26
        );

        JLabel DateLabel = new JLabel("Choisissez une date");
        DatePicker datePicker = new DatePicker();
        datePicker.setMaximumSize(verticalMaxSize);
        datePicker.addDateChangeListener(new JourChangeListener(this));
        try {
            datePicker.setDate(LocalDate.of(
                (int) Integer.parseInt(date.getByPattern("yyyy")), 
                (int) Integer.parseInt(date.getByPattern("MM")), 
                (int) Integer.parseInt(date.getByPattern("dd"))
            ));
        } catch (Exception e) {
            System.out.println(e);
        }
        
        JLabel TimeDebutLabel = new JLabel("Choisissez une heure de début");
        
        TimePickerSettings timeSettingsD = new TimePickerSettings();
        try {
            timeSettingsD.initialTime = LocalTime.of(
                (int) Integer.parseInt(heureDebut.getByPattern("HH")),
                (int) Integer.parseInt(heureDebut.getByPattern("mm"))
            );
        } catch (Exception e) {
            System.out.println(e);
        }
           
        TimePicker timePickerD = new TimePicker(timeSettingsD);
        timePickerD.setMaximumSize(verticalMaxSize);
        timePickerD.addTimeChangeListener(new TimeDebutChangeListener(this));
        timeSettingsD.setVetoPolicy(new SampleTimeVetoPolicy());
        timeSettingsD.generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.FifteenMinutes, null, null);
        
        
        JLabel TimeFinLabel = new JLabel("Choisissez une heure de Fin");
        
        TimePickerSettings timeSettingsF = new TimePickerSettings();
        try {
            timeSettingsF.initialTime = LocalTime.of(
                (int) Integer.parseInt(heureFin.getByPattern("HH")),
                (int) Integer.parseInt(heureFin.getByPattern("mm"))
            );
        } catch (Exception e) {
            System.out.println(e);
        }
        
        TimePicker timePickerF = new TimePicker(timeSettingsF);
        timePickerF.setMaximumSize(verticalMaxSize);
        timePickerF.addTimeChangeListener(new TimeFinChangeListener(this));
        timeSettingsF.setVetoPolicy(new SampleTimeVetoPolicy());
        timeSettingsF.generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.FifteenMinutes, null, null);
        
        
        
        JLabel GroupeLabel = new JLabel("Selectionnez un groupe");  
        JComboBox GroupesSelection = new JComboBox(Modele.getAllGroupes().toArray(new groupe[0]));    
        
        GroupesSelection.setMaximumSize(verticalMaxSize);
        GroupesSelection.setSelectedIndex(-1);
        GroupesSelection.setRenderer(new GroupeCellRenderer());
        
        ActionListener selectGroupe = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                groupe _tmpSelectedGroupe = (groupe) GroupesSelection.getItemAt(GroupesSelection.getSelectedIndex());
                LabelAndDelete _tmp = new LabelAndDelete(
                    _tmpSelectedGroupe, 
                    new JLabel(_tmpSelectedGroupe.getPromotion() + " " + _tmpSelectedGroupe.getNom()),
                    SelectedGroupe.size(),
                    SelectedGroupe,
                    monPanel
                );                
                SelectedGroupe.add(SelectedGroupe.size(), _tmp);
                refresh();
            }
        };
        GroupesSelection.addActionListener(selectGroupe);
        
        
        JLabel CoursLabel = new JLabel("Selectionnez un cours");
        JComboBox CoursSelection = new JComboBox(Modele.getAllCours().toArray(new cours[0]));
        
        CoursSelection.setMaximumSize(verticalMaxSize);
        try {
            if(selectedCoursComboID != -1) {
                
                CoursSelection.setSelectedIndex(selectedCoursComboID);
            }
            else {
                CoursSelection.setSelectedIndex(-1);
            }
        } catch (Exception e) {
            
        }
        
        CoursSelection.setRenderer(new CoursCellRenderer());
        
        ActionListener selectCours = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCours = (cours) CoursSelection.getItemAt(CoursSelection.getSelectedIndex());
                selectedCoursComboID = CoursSelection.getSelectedIndex();
                refresh();
            }
        };
        CoursSelection.addActionListener(selectCours);
        
        
        
        
        
        JLabel EnseignantLabel = new JLabel("Selectionnez un enseignant");  
        JComboBox EnseignantSelection = new JComboBox(enseignantsArray.toArray(new utilisateur[0]));    
        
        EnseignantSelection.setMaximumSize(verticalMaxSize);
        EnseignantSelection.setSelectedIndex(-1);
        EnseignantSelection.setRenderer(new EnseignantCellRenderer());
        
        ActionListener selectEnseignant = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utilisateur _tmpSelectedEnseignant = (utilisateur) EnseignantSelection.getItemAt(EnseignantSelection.getSelectedIndex());
                System.out.println("Enseignant: " + _tmpSelectedEnseignant);
                
                LabelAndDelete _tmp = new LabelAndDelete(
                    _tmpSelectedEnseignant, 
                    new JLabel(_tmpSelectedEnseignant.getNom() + " " + _tmpSelectedEnseignant.getPrenom()),
                    SelectedEnseignant.size(),
                    SelectedEnseignant,
                    monPanel
                );                
                SelectedEnseignant.add(SelectedEnseignant.size(), _tmp);
                refresh();
            }
        };
        EnseignantSelection.addActionListener(selectEnseignant);
        
        
        JLabel SalleLabel = new JLabel("Selectionnez une salle");  
        JComboBox SalleSelection = new JComboBox(sallesArray.toArray(new salle[0]));    
        
        SalleSelection.setMaximumSize(verticalMaxSize);
        SalleSelection.setSelectedIndex(-1);
        SalleSelection.setRenderer(new SalleCellRenderer());
        
        ActionListener selectSalle = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salle _tmpSelectedSalle = (salle) SalleSelection.getItemAt(SalleSelection.getSelectedIndex());
                System.out.println("Salle: " + _tmpSelectedSalle);
                
                LabelAndDelete _tmp = new LabelAndDelete(
                    _tmpSelectedSalle, 
                    new JLabel(_tmpSelectedSalle.getNom() + " " + _tmpSelectedSalle.getSite()),
                    SelectedSalle.size(),
                    SelectedSalle,
                    monPanel
                );                
                SelectedSalle.add(SelectedSalle.size(), _tmp);
                refresh();
            }
        };
        SalleSelection.addActionListener(selectSalle);
        
        
        
        
        
        
        
        
        
        JButton btn2 = new JButton("Two");
        JLabel btn3 = new JLabel("Three");
        JButton btn4 = new JButton("Four");
        JButton btn5 = new JButton("Five");
        
        
        GroupLayout.ParallelGroup HorizontalLabelGroupe = layout.createParallelGroup();
        GroupLayout.SequentialGroup VerticalLabelGroupe = layout.createSequentialGroup();
        for (int i=0; i<SelectedGroupe.size(); i++) {
            
            HorizontalLabelGroupe.addGroup(layout.createSequentialGroup()
                .addComponent(SelectedGroupe.get(i).getLabel())
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 0, Integer.MAX_VALUE)
                .addComponent(SelectedGroupe.get(i).getClose())
            );
            
            VerticalLabelGroupe.addGroup(layout.createParallelGroup()
                .addComponent(SelectedGroupe.get(i).getLabel())
                .addComponent(SelectedGroupe.get(i).getClose())
            );
        }
        
        GroupLayout.ParallelGroup HorizontalLabelEnseignant = layout.createParallelGroup();
        GroupLayout.SequentialGroup VerticalLabelEnseignant = layout.createSequentialGroup();
        for (int i=0; i<SelectedEnseignant.size(); i++) {
            
            HorizontalLabelEnseignant.addGroup(layout.createSequentialGroup()
                .addComponent(SelectedEnseignant.get(i).getLabel())
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 0, Integer.MAX_VALUE)
                .addComponent(SelectedEnseignant.get(i).getClose())
            );
            
            VerticalLabelEnseignant.addGroup(layout.createParallelGroup()
                .addComponent(SelectedEnseignant.get(i).getLabel())
                .addComponent(SelectedEnseignant.get(i).getClose())
            );
        }
        
        
        GroupLayout.ParallelGroup HorizontalLabelSalle = layout.createParallelGroup();
        GroupLayout.SequentialGroup VerticalLabelSalle = layout.createSequentialGroup();
        for (int i=0; i<SelectedSalle.size(); i++) {
            
            HorizontalLabelSalle.addGroup(layout.createSequentialGroup()
                .addComponent(SelectedSalle.get(i).getLabel())
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 0, Integer.MAX_VALUE)
                .addComponent(SelectedSalle.get(i).getClose())
            );
            
            VerticalLabelSalle.addGroup(layout.createParallelGroup()
                .addComponent(SelectedSalle.get(i).getLabel())
                .addComponent(SelectedSalle.get(i).getClose())
            );
        }
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGap(5)
            .addGroup(layout.createParallelGroup()
                .addComponent(DateLabel)
                .addComponent(datePicker)
                .addComponent(TimeDebutLabel)
                .addComponent(timePickerD)
                .addComponent(TimeFinLabel)
                .addComponent(timePickerF)
                .addComponent(GroupeLabel)
                .addComponent(GroupesSelection)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10)
                    .addGroup(HorizontalLabelGroupe)
                )
                .addComponent(CoursLabel)
                .addComponent(CoursSelection)
                .addComponent(EnseignantLabel)
                .addComponent(EnseignantSelection)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10)
                    .addGroup(HorizontalLabelEnseignant)
                )
                .addComponent(SalleLabel)
                .addComponent(SalleSelection)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10)
                    .addGroup(HorizontalLabelSalle)
                )
                .addComponent(btn2)
                .addComponent(btn3)
                .addComponent(btn4)
            )
            .addGap(5)
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup() 
            .addGap(10)
            .addComponent(DateLabel)
            .addComponent(datePicker)
            .addGap(5)
            .addComponent(TimeDebutLabel)
            .addComponent(timePickerD)
            .addGap(5)
            .addComponent(TimeFinLabel)
            .addComponent(timePickerF)
            .addGap(5)
            .addComponent(GroupeLabel)
            .addComponent(GroupesSelection)
            .addGroup(VerticalLabelGroupe)
            .addGap(5)
            .addComponent(CoursLabel)
            .addComponent(CoursSelection)
            .addGap(5)
            .addComponent(EnseignantLabel)
            .addComponent(EnseignantSelection)
            .addGroup(VerticalLabelEnseignant)
            .addGap(5)
            .addComponent(SalleLabel)
            .addComponent(SalleSelection)
            .addGroup(VerticalLabelSalle)
            .addGap(5)
            .addComponent(btn2)
            
            .addComponent(btn3)
            .addComponent(btn4)
            .addGap(10)
        );
    }
    
    private void refresh() {
        maVue.UpdateLeft(new SeanceCreation(maVue, width, SelectedGroupe, SelectedEnseignant, SelectedSalle, selectedCours, selectedCoursComboID, date, heureDebut, heureFin));
    }
    
    private void setDate(customDate _date) { this.date = _date;}
    private void setHeureDebut(customDate _date) { this.heureDebut = _date;}
    private void setHeureFin(customDate _date) { this.heureFin = _date;}
    
    private static class SampleTimeVetoPolicy implements TimeVetoPolicy {

        /**
         * isTimeAllowed, Return true if a time should be allowed, or false if a time should be
         * vetoed.
         */
        @Override
        public boolean isTimeAllowed(LocalTime time) {
            // Only allow times from 9a to 5p, inclusive.
            return PickerUtilities.isLocalTimeInRange(
                time, LocalTime.of(8, 00), LocalTime.of(21, 00), true);
        }
    }
    
    private static class TimeDebutChangeListener implements TimeChangeListener {
        SeanceCreation parent;
        private TimeDebutChangeListener(SeanceCreation _parent) {
		this.parent = _parent;
	}
        
        @Override
        public void timeChanged(TimeChangeEvent event) {
                LocalTime newTime = event.getNewTime();
                String newTimeString = PickerUtilities.localTimeToString(newTime, "(null)") + ":00";
                
                parent.setHeureDebut(new customDate("heure", newTimeString));
                parent.refresh();
        }
    }
    private static class TimeFinChangeListener implements TimeChangeListener {
        SeanceCreation parent;
        private TimeFinChangeListener(SeanceCreation _parent) {
		this.parent = _parent;
	}
        
        @Override
        public void timeChanged(TimeChangeEvent event) {
                LocalTime newTime = event.getNewTime();
                String newTimeString = PickerUtilities.localTimeToString(newTime, "(null)") + ":00";
                
                parent.setHeureFin(new customDate("heure", newTimeString));
                parent.refresh();
        }
    }
    private static class JourChangeListener implements DateChangeListener {
	SeanceCreation parent;
	private JourChangeListener(SeanceCreation _parent) {
		this.parent = _parent;
	}

	@Override
	public void dateChanged(DateChangeEvent event) {
            LocalDate newDate = event.getNewDate();
            String newDateString = PickerUtilities.localDateToString(newDate, "(null)");
            parent.setDate(new customDate("jour", newDateString));
	}
    }
    
    
    private static class GroupeCellRenderer implements ListCellRenderer {
        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String aAfficher;
            if (index == -1 && value == null) aAfficher = "GROUPES";
            else {
                groupe _tmp = (groupe) value;
                aAfficher = _tmp.getPromotion() + " " + _tmp.getNom();
            }
            
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, aAfficher, index, isSelected, cellHasFocus);
            return renderer;
        }
    }
    private static class EnseignantCellRenderer implements ListCellRenderer {
        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String aAfficher;
            if (index == -1 && value == null) aAfficher = "ENSEIGNANTS";
            else {
                utilisateur _tmp = (utilisateur) value;
                aAfficher = _tmp.getPrenom() + " " + _tmp.getNom();
            }
            
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, aAfficher, index, isSelected, cellHasFocus);
            return renderer;
        }
    }
    private static class SalleCellRenderer implements ListCellRenderer {
        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String aAfficher;
            if (index == -1 && value == null) aAfficher = "SALLE";
            else {
                salle _tmp = (salle) value;
                aAfficher = _tmp.getNom() + " " + _tmp.getSite();
            }
            
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, aAfficher, index, isSelected, cellHasFocus);
            return renderer;
        }
    }
    private static class CoursCellRenderer implements ListCellRenderer {
        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String aAfficher;
            if (index == -1 && value == null) aAfficher = "COURS";
            else {
                cours _tmp = (cours) value;
                aAfficher = "" + _tmp.getNom();
            }
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, aAfficher, index, isSelected, cellHasFocus);
            
            //renderer.setEnabled(false);
            
            return renderer;
        }
    }
    
    private static class LabelAndDelete<T> {
        
        private T monObjet;
        private JLabel text, CloseLabel;
        private int position;
        private ArrayList<LabelAndDelete> monArray;
        private LabelAndDelete cetObjet = this;
        private SeanceCreation Parent;
        
        LabelAndDelete(T _monObjet, JLabel _text, int _pos, ArrayList<LabelAndDelete> _monArray, SeanceCreation _parent)
        {
            monObjet = _monObjet;
            text = _text;
            position = _pos;
            monArray = _monArray;
            Parent = _parent;
            
            CloseLabel = new JLabel();
            CloseLabel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
            CloseLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            CloseLabel.setText("X");
            CloseLabel.setToolTipText("Supprimer");
            CloseLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
            CloseLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));


            CloseLabel.addMouseListener(new MouseAdapter() { 
                @Override
                public void mouseClicked(MouseEvent me) { 
                    updatePosition();
                    monArray.remove(position+1);
                    Parent.refresh();
                    
                }
            });
        }
        
        public void updatePosition() {
            
            if(position < monArray.size()-1)
            {
                monArray.get(position+1).updatePosition();
            }
            position--;
            System.out.println(position + " " + monObjet);
        }
        
        public JLabel getLabel() {return text;}
        public JLabel getClose() {return CloseLabel;}
        public T getObject() { return monObjet;}
    }
}
