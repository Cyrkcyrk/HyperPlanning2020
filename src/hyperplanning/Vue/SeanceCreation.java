/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning.Vue;

import DB_class.*;
import hyperplanning.*;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.optionalusertools.TimeChangeListener;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Cyrille
 */
public class SeanceCreation extends JPanel {
    private final int width;
    private ArrayList<LabelAndDelete> SelectedGroupe = new ArrayList<>();
    private ArrayList<LabelAndDelete> SelectedEnseignant = new ArrayList<>();
    private ArrayList<LabelAndDelete> SelectedSalle = new ArrayList<>();
    private cours selectedCours = null;
    private int selectedCoursComboID = -1, selectedTypeCoursComboID=-1;
    private Type_cours selectedTypeCours = null;
    private final Controlleur monControlleur;
    final private SeanceCreation monPanel = this;
    
    //private ArrayList<utilisateur> enseignantsArray = new ArrayList<utilisateur>();
    //private ArrayList<salle> sallesArray = new ArrayList<salle>();
    
    private ArrayList<utilisateur> enseignantsArray = Modele.getAllProfs();
    private ArrayList<salle> sallesArray = Modele.getAllSalles();
    
    private ArrayList<groupe> tousLesGroupes = Modele.getAllGroupes();
    private final ArrayList<cours> tousLesCours = Modele.getAllCours();
    private final ArrayList<Type_cours> tousLesTypeCours = Modele.getAllTypeCours();
    
    private customDate date = null, heureDebut = null, heureFin = null;
    
    private seance maSeance;
    private int seanceID = 0;
    
    public SeanceCreation(Controlleur _ctrlr, int _width)
    {
        monControlleur = _ctrlr;
        width = _width;
        
        creerPanel();
    }
    
    public SeanceCreation(Controlleur _ctrlr, int _width, seance _tmpSeance)
    {
        monControlleur = _ctrlr;
        width = _width;
        
        ArrayList<groupe> _groupes = _tmpSeance.getGroupes();
        for(int i=0; i< _groupes.size(); i++) {
            
            for (int j=0; j< tousLesGroupes.size(); j++) {
                if (tousLesGroupes.get(j).getID() == _groupes.get(i).getID()) {
                    tousLesGroupes.remove(j);
                    j=tousLesGroupes.size();
                }
            }
            
            LabelAndDelete _tmp = new LabelAndDelete(
                _groupes.get(i), 
                new JLabel(_groupes.get(i).getPromotion() + " " + _groupes.get(i).getNom()),
                SelectedGroupe.size(),
                SelectedGroupe,
                monPanel,
                tousLesGroupes
            );
            SelectedGroupe.add(SelectedGroupe.size(), _tmp);
        }
        
        ArrayList<utilisateur> _enseignants = _tmpSeance.getEnseignants();
        for(int i=0; i< _enseignants.size(); i++) {
            for (int j=0; j< enseignantsArray.size(); j++) {
                if (enseignantsArray.get(j).getID() == _enseignants.get(i).getID()) {
                    enseignantsArray.remove(j);
                    j=enseignantsArray.size();
                }
            }
            LabelAndDelete _tmp = new LabelAndDelete(
                _enseignants.get(i), 
                new JLabel(_enseignants.get(i).getNom() + " " + _enseignants.get(i).getPrenom()),
                SelectedEnseignant.size(),
                SelectedEnseignant,
                monPanel,
                enseignantsArray
            );
            SelectedEnseignant.add(SelectedEnseignant.size(), _tmp);
        }
        
        ArrayList<salle> _salles = _tmpSeance.getSalles();
        for(int i=0; i< _salles.size(); i++) {
            for (int j=0; j< sallesArray.size(); j++) {
                if (sallesArray.get(j).getID() == _salles.get(i).getID()) {
                    sallesArray.remove(j);
                    j=sallesArray.size();
                }
            }
            LabelAndDelete _tmp = new LabelAndDelete(
                _salles.get(i), 
                new JLabel(_salles.get(i).getNom() + " " + _salles.get(i).getSite()+ " - " + _salles.get(i).getCapacite()),
                SelectedSalle.size(),
                SelectedSalle,
                monPanel,
                sallesArray
            );
            SelectedSalle.add(SelectedSalle.size(), _tmp);
        }
        
        date = _tmpSeance.getDate();
        heureDebut = _tmpSeance.getDebut();
        heureFin = _tmpSeance.getFin();
        
        selectedCours = _tmpSeance.getCours();
        selectedTypeCours = _tmpSeance.getType();
        
        for (int i=0; i< tousLesCours.size(); i++) {
            if(tousLesCours.get(i).getID() == _tmpSeance.getCours().getID()) {
                selectedCoursComboID = i;
                i = tousLesCours.size();
            }
        }
        for (int i=0; i< tousLesTypeCours.size(); i++) {
            if(tousLesTypeCours.get(i).getID() == _tmpSeance.getType().getID()) {
                selectedTypeCoursComboID = i;
                i = tousLesTypeCours.size();
            }
        }
        seanceID = _tmpSeance.getID();
        //private int selectedCoursComboID = -1, selectedTypeCoursComboID=-1;
        
        
        creerPanel();
    }
    
    public SeanceCreation(Controlleur _ctrlr)
    {
        monControlleur = _ctrlr;
        width = 200;
        
        maSeance = new seance(
            0, 
            0,
            new customDate("jour", "2000-01-01"),
            new customDate("heure", "00:00:00"),
            new customDate("heure", "00:00:00"),
            "Inexistant",
            new cours(0, "Cours non définis"),
            new Type_cours(0, "N'existe pas"),
            new ArrayList<groupe>(),
            new ArrayList<salle>(),
            new ArrayList<utilisateur>()
        );
        
        creerPanel();
    }
    
    public SeanceCreation(
            Controlleur _ctrlr, 
            int _width, 
            int _seanceID,
            ArrayList<LabelAndDelete> _SelectedGroupe,
            ArrayList<groupe> _tousLesGroupes,
            ArrayList<LabelAndDelete> _SelectedEnseignant,
            ArrayList<utilisateur> _enseignantsArray,
            ArrayList<LabelAndDelete> _SelectedSalle,
            ArrayList<salle> _sallesArray,
            
            cours _selectedCours,
            int _selectedCoursComboID,
            Type_cours _selectedTypeCours,
            int _selectedTypeCoursComboID,
            
            customDate _date,
            customDate _HDebut,
            customDate _HFin
        ) {
        monControlleur = _ctrlr;
        width = _width;
        seanceID = _seanceID;
        
        SelectedGroupe = _SelectedGroupe;
        tousLesGroupes = _tousLesGroupes;
        SelectedEnseignant = _SelectedEnseignant;
        enseignantsArray = _enseignantsArray;
        SelectedSalle = _SelectedSalle;
        sallesArray = _sallesArray;
        
        selectedCours = _selectedCours;
        selectedCoursComboID = _selectedCoursComboID;
        selectedTypeCours = _selectedTypeCours;
        selectedTypeCoursComboID = _selectedTypeCoursComboID;
        
        date = _date;
        heureDebut = _HDebut;
        heureFin = _HFin;
        
        creerPanel();
    }
    
    private void creerPanel()
    {
        GroupLayout layout = new GroupLayout(this); 
        this.setLayout(layout);
        this.setBorder(new CustomBorder(1, 0, 0, 0));
        //this.setPreferredSize(new Dimension(width, 0));
        
        Dimension verticalMaxSize = new Dimension(
            Integer.MAX_VALUE,
            26
        );
        transformToSeance();
            
        JPanel resumeSeance = new SeancePanel(monControlleur, maSeance, "leftPanel");
        resumeSeance.setMaximumSize(new Dimension(width-20,0));
        resumeSeance.setPreferredSize(new Dimension(width-20,0));
        
        
        JLabel DateLabel = new JLabel("Choisissez une date");
        DatePickerSettings dateSettings = new DatePickerSettings();
        DatePicker datePicker = new DatePicker(dateSettings);
        dateSettings.setVetoPolicy(new SampleDateVetoPolicy());
        dateSettings.setHighlightPolicy(new SampleHighlightPolicy());
        datePicker.setMaximumSize(verticalMaxSize);
        datePicker.addDateChangeListener(new JourChangeListener(this));
        try {
            if(date != null)
                datePicker.setDate(LocalDate.of(
                    (int) Integer.parseInt(date.getByPattern("yyyy")), 
                    (int) Integer.parseInt(date.getByPattern("MM")), 
                    (int) Integer.parseInt(date.getByPattern("dd"))
                ));
        } catch (Exception e) {
            System.out.println("Problème avec l'initialisation de 'date' : " + e);
        }
        
        JLabel TimeDebutLabel = new JLabel("Choisissez une heure de début");
        
        TimePickerSettings timeSettingsD = new TimePickerSettings();
        try {
            if(heureDebut != null)
                timeSettingsD.initialTime = LocalTime.of(
                    (int) Integer.parseInt(heureDebut.getByPattern("HH")),
                    (int) Integer.parseInt(heureDebut.getByPattern("mm"))
                );
        } catch (Exception e) {
            System.out.println("Problème avec l'initialisation de 'heureDebut' : " + e);
        }
           
        TimePicker timePickerD = new TimePicker(timeSettingsD);
        timePickerD.setMaximumSize(verticalMaxSize);
        timePickerD.addTimeChangeListener(new TimeDebutChangeListener(this));
        timeSettingsD.setVetoPolicy(new SampleTimeVetoPolicy());
        timeSettingsD.generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.FifteenMinutes, null, null);
        
        
        JLabel TimeFinLabel = new JLabel("Choisissez une heure de Fin");
        
        TimePickerSettings timeSettingsF = new TimePickerSettings();
        try {
            if(heureFin != null)
                timeSettingsF.initialTime = LocalTime.of(
                    (int) Integer.parseInt(heureFin.getByPattern("HH")),
                    (int) Integer.parseInt(heureFin.getByPattern("mm"))
                );
        } catch (Exception e) {
            System.out.println("Problème avec l'initialisation de 'heureFin' : " + e);
        }
        
        TimePicker timePickerF = new TimePicker(timeSettingsF);
        timePickerF.setMaximumSize(verticalMaxSize);
        timePickerF.addTimeChangeListener(new TimeFinChangeListener(this));
        timeSettingsF.setVetoPolicy(new SampleTimeVetoPolicy());
        timeSettingsF.generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.FifteenMinutes, null, null);
        
        
        
        JLabel GroupeLabel = new JLabel("Selectionnez un groupe");  
        JComboBox GroupesSelection = new JComboBox(tousLesGroupes.toArray(new groupe[0]));    
        
        GroupesSelection.setMaximumSize(verticalMaxSize);
        GroupesSelection.setSelectedIndex(-1);
        GroupesSelection.setRenderer(new GroupeCellRenderer());
        
        ActionListener selectGroupe = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                groupe _tmpSelectedGroupe = (groupe) GroupesSelection.getItemAt(GroupesSelection.getSelectedIndex());
                
                boolean AlreadyLoaded = false;
                for (int i=0; i<SelectedGroupe.size(); i++) {
                    if(((groupe) SelectedGroupe.get(i).getObject()).getID() == _tmpSelectedGroupe.getID())
                    {
                        AlreadyLoaded = true;
                        i=SelectedGroupe.size();
                    }
                }
                if(AlreadyLoaded == false) {
                    tousLesGroupes.remove(_tmpSelectedGroupe);
                    LabelAndDelete _tmp = new LabelAndDelete(
                        _tmpSelectedGroupe, 
                        new JLabel(_tmpSelectedGroupe.getPromotion() + " " + _tmpSelectedGroupe.getNom()),
                        SelectedGroupe.size(),
                        SelectedGroupe,
                        monPanel,
                        tousLesGroupes
                    );
                    SelectedGroupe.add(SelectedGroupe.size(), _tmp);
                    refresh();
                }
            }
        };
        GroupesSelection.addActionListener(selectGroupe);
        
        
        JLabel CoursLabel = new JLabel("Selectionnez un cours");
        JComboBox CoursSelection = new JComboBox(tousLesCours.toArray(new cours[0]));
        
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
                
                boolean AlreadyLoaded = false;
                for (int i=0; i<SelectedEnseignant.size(); i++) {
                    if(((utilisateur) SelectedEnseignant.get(i).getObject()).getID() == _tmpSelectedEnseignant.getID())
                    {
                        AlreadyLoaded = true;
                        i=SelectedEnseignant.size();
                    }
                }
                if(AlreadyLoaded == false) {
                    enseignantsArray.remove(_tmpSelectedEnseignant);
                    LabelAndDelete _tmp = new LabelAndDelete(
                        _tmpSelectedEnseignant, 
                        new JLabel(_tmpSelectedEnseignant.getNom() + " " + _tmpSelectedEnseignant.getPrenom()),
                        SelectedEnseignant.size(),
                        SelectedEnseignant,
                        monPanel,
                        enseignantsArray
                    );
                    SelectedEnseignant.add(SelectedEnseignant.size(), _tmp);
                    refresh();
                }
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
                
                boolean AlreadyLoaded = false;
                for (int i=0; i<SelectedSalle.size(); i++) {
                    if(((salle) SelectedSalle.get(i).getObject()).getID() == _tmpSelectedSalle.getID())
                    {
                        AlreadyLoaded = true;
                        i=SelectedSalle.size();
                    }
                }
                if(AlreadyLoaded == false) {
                    sallesArray.remove(_tmpSelectedSalle);
                    LabelAndDelete _tmp = new LabelAndDelete(
                        _tmpSelectedSalle, 
                        new JLabel(_tmpSelectedSalle.getNom() + " " + _tmpSelectedSalle.getSite()+ " - " + _tmpSelectedSalle.getCapacite()),
                        SelectedSalle.size(),
                        SelectedSalle,
                        monPanel,
                        sallesArray
                    );                
                    SelectedSalle.add(SelectedSalle.size(), _tmp);
                    refresh();
                }
            }
        };
        SalleSelection.addActionListener(selectSalle);
        
        
        
        JLabel TypeCoursLabel = new JLabel("Selectionnez un type de cours");
        JComboBox TypeCoursSelection = new JComboBox(tousLesTypeCours.toArray(new Type_cours[0]));
        
        TypeCoursSelection.setMaximumSize(verticalMaxSize);
        try {
            if(selectedTypeCoursComboID != -1) {
                
                TypeCoursSelection.setSelectedIndex(selectedTypeCoursComboID);
            }
            else {
                TypeCoursSelection.setSelectedIndex(-1);
            }
        } catch (Exception e) {
            
        }
        
        TypeCoursSelection.setRenderer(new TypeCoursCellRenderer());
        
        ActionListener selectTypeCours = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTypeCours = (Type_cours) TypeCoursSelection.getItemAt(TypeCoursSelection.getSelectedIndex());
                selectedTypeCoursComboID = TypeCoursSelection.getSelectedIndex();
                refresh();
            }
        };
        TypeCoursSelection.addActionListener(selectTypeCours);
        
        
        
        
        JButton btnConfirmation = new JButton("Confirmer");
        ActionListener confirmPressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> erreur = transformToSeance();
                if(erreur.size()<=0) {
                    monControlleur.saveSeance(maSeance);
                }
                else {
                    Controlleur.ShowError(erreur);
                }
            }
        };
        btnConfirmation.addActionListener(confirmPressed);
        
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
        
        layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                    .addComponent(resumeSeance)
                    .addComponent(CoursLabel)
                    .addComponent(CoursSelection)
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
                    .addComponent(TypeCoursLabel)
                    .addComponent(TypeCoursSelection)
                    
                )
                .addGap(5)
            )
            .addComponent(btnConfirmation)
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup() 
            .addGap(10)
            .addComponent(resumeSeance)
            .addGap(10)
            .addComponent(CoursLabel)
            .addComponent(CoursSelection)
            .addGap(5)
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
            .addComponent(EnseignantLabel)
            .addComponent(EnseignantSelection)
            .addGroup(VerticalLabelEnseignant)
            .addGap(5)
            .addComponent(SalleLabel)
            .addComponent(SalleSelection)
            .addGroup(VerticalLabelSalle)
            .addGap(5)
            .addComponent(TypeCoursLabel)
            .addComponent(TypeCoursSelection)
            .addGap(5)
            .addComponent(btnConfirmation)
            .addGap(10)
        );
    }
    
    private void refresh() {
        monControlleur.getVue().changeLeftPanel(new JScrollPane(new SeanceCreation(
                monControlleur, 
                width, 
                seanceID,
                SelectedGroupe,
                tousLesGroupes,
                SelectedEnseignant, 
                enseignantsArray,
                SelectedSalle, 
                sallesArray,
                selectedCours, 
                selectedCoursComboID, 
                selectedTypeCours,
                selectedTypeCoursComboID,
                date, 
                heureDebut, 
                heureFin
        ), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
           JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        ));
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
    private static class SampleDateVetoPolicy implements DateVetoPolicy {
        @Override
        public boolean isDateAllowed(LocalDate date) {
            //LocalDate now = LocalDate.now(); 
            /*if (date.compareTo(now) < 0) {
                return false;
            }*/
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                return false;
            }
            return true;
        }
    }
    private static class SampleHighlightPolicy implements DateHighlightPolicy {
        @Override
        public HighlightInformation getHighlightInformationOrNull(LocalDate date) {
            LocalDate now = LocalDate.now(); 
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                return new HighlightInformation(new Color(200, 150, 150, 255), null, "");
            }
            if (date.compareTo(now) < 0) {
                return new HighlightInformation(new Color(150, 150, 150, 255), null, "");
            }
            
            return null;
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
                aAfficher = _tmp.getNom() + " " + _tmp.getSite() + " - " + _tmp.getCapacite();
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
    private static class TypeCoursCellRenderer implements ListCellRenderer {
        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String aAfficher;
            if (index == -1 && value == null) aAfficher = "TYPE COURS";
            else {
                Type_cours _tmp = (Type_cours) value;
                aAfficher = "" + _tmp.getType();
            }
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, aAfficher, index, isSelected, cellHasFocus);
            
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
        ArrayList<T> tableauObjectGlobal;
        
        LabelAndDelete(T _monObjet, JLabel _text, int _pos, ArrayList<LabelAndDelete> _monArray, SeanceCreation _parent, ArrayList<T> _tableauObjectGlobal)
        {
            monObjet = _monObjet;
            text = _text;
            position = _pos;
            monArray = _monArray;
            Parent = _parent;
            tableauObjectGlobal = _tableauObjectGlobal;
            
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
                    tableauObjectGlobal.add(0, monObjet);
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
        }
        
        public JLabel getLabel() {return text;}
        public JLabel getClose() {return CloseLabel;}
        public T getObject() { return monObjet;}
    }

    private ArrayList<String> transformToSeance() {
        ArrayList<String> _return = new ArrayList<>();
        
        if(SelectedGroupe.size() <= 0)
            _return.add("Aucun groupe séléctionné.");
        ArrayList<groupe> _groupes = new ArrayList<groupe>();
        for(int i=0; i < SelectedGroupe.size(); i++) {
            groupe _tmp = (groupe) SelectedGroupe.get(i).getObject();
            _groupes.add(_tmp);
        }
        
        if(SelectedEnseignant.size() <= 0)
            _return.add("Aucun enseignant séléctionné.");
        ArrayList<utilisateur> _enseignants = new ArrayList<utilisateur>();
        for(int i=0; i<SelectedEnseignant.size(); i++) {
            utilisateur _tmp = (utilisateur) SelectedEnseignant.get(i).getObject();
            _enseignants.add(_tmp);
        }
        
        if(SelectedSalle.size() <= 0)
            _return.add("Aucune salle séléctionnée.");
        ArrayList<salle> _salles = new ArrayList<salle>();
        for(int i=0; i<SelectedSalle.size(); i++) {
            salle _tmp = (salle) SelectedSalle.get(i).getObject();
            _salles.add(_tmp);
        }
        
        customDate _tmpDate = date, _tmpDebut = heureDebut, _tmpFin = heureFin;
        if(_tmpDate == null) {
            _tmpDate = new customDate("jour","2010-01-01");
            _return.add("Aucun jour séléctionné");
        }
        
        if(_tmpDebut == null) {
            _tmpDebut = new customDate("heure","00:00:00");
            _return.add("Aucune heure de début séléctionné");
        }
        
        if(_tmpFin == null) {
            _tmpFin = new customDate("heure","00:00:00");
            _return.add("Aucune heure de fin séléctionné");
        }
        
        cours _tmpCours = selectedCours;
        if(_tmpCours == null) {
            _tmpCours = new cours(0, "Pas selectionné");
            _return.add("Aucun cours séléctionné");
        }
        
        Type_cours _tmpTypeCours = selectedTypeCours;
        if(_tmpTypeCours == null) {
            _tmpTypeCours = new Type_cours(0, "Pas selectionné");
            _return.add("Aucun type de cours séléctionné");
        }
        
        maSeance = new seance(
            seanceID,
            _tmpDate.getWeekNumber(),
            _tmpDate,
            _tmpDebut,
            _tmpFin,
            "En cours de création",
            _tmpCours,
            _tmpTypeCours,
            _groupes,
            _salles,
            _enseignants
        );
        
        return _return;
    }
}
