/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning.Vue;


import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Cyrille
 */
public class Vue extends JFrame {
    
    JPanel contentPane, Navbar;
    JScrollPane centerPanel, leftPanel, rightPanel;
    boolean rightPanelHidden = true;
    boolean leftPanelHidden = false;
    boolean centerPanelHidden = false;

    int dayStart = 8;
    int dayEnd = 21;
    int splitEvery = 15;
    
    private int NUM = 5;
    private JTextField[] fields = new JTextField[NUM];
    private JLabel[] labels = new JLabel[NUM];
    GroupLayout.ParallelGroup parallel;
    GroupLayout.SequentialGroup sequential;
    GroupLayout layout;
    
    
    public Vue () {
        //https://www.youtube.com/watch?v=-UvxwleNA20
        
        super("Hyperplanning - Projet JAVA ING3 2020");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(960, 720);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        
        
        contentPane = (JPanel) this.getContentPane();
        
        Navbar = createNavbar();
        contentPane.add(Navbar, BorderLayout.NORTH);
        
        leftPanel = new JScrollPane(new SeanceCreation(this));
        
        
        contentPane.add(leftPanel, BorderLayout.WEST);
        
        centerPanel = new JScrollPane(new Timetable(this));
        contentPane.add(centerPanel, BorderLayout.CENTER);
        
        this.setVisible(true);
    }
    
    private JPanel createNavbar() {
        //JToolBar navbar = new JToolBar ();
        JPanel navbar = new JPanel();
        navbar.setLayout(new BoxLayout(navbar, BoxLayout.LINE_AXIS));
        
        JPanel left = new JPanel();
        left.setLayout(new FlowLayout( FlowLayout.LEFT ));
        left.add(new JLabel("Message 1"));
        left.add(new JLabel("Message 2"));
        left.add(new JLabel("Message 3"));
        
        JPanel right = new JPanel();
        right.setLayout(new FlowLayout( FlowLayout.RIGHT ));
        right.add(new JLabel("Message 1"));
        
        navbar.add(left);
        navbar.add(Box.createHorizontalBox());
        navbar.add(right);
        
        return navbar;
    }
    
    public void refresh() {
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    public void changeCenter(JPanel _tmpPannel) {
        if(!centerPanelHidden)
            contentPane.remove(centerPanel);
        
        centerPanelHidden = false;
        centerPanel = new JScrollPane(_tmpPannel);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        refresh();
    }
    
    public void hideLeft() {
        if(leftPanelHidden) {
            NUM++;
            
            fields = new JTextField[NUM];
            labels = new JLabel[NUM];
            
            contentPane.remove(leftPanel);
            leftPanel = new JScrollPane(createDynLayout());
            contentPane.add(leftPanel, BorderLayout.WEST);
        }
        else {
            contentPane.remove(leftPanel);
            leftPanelHidden = !leftPanelHidden;
        }
        //leftPanelHidden = !leftPanelHidden;
        refresh();
    }
    
    public void UpdateLeft(SeanceCreation _tmp) {
        contentPane.remove(leftPanel);
        leftPanel = new JScrollPane(_tmp);
        contentPane.add(leftPanel, BorderLayout.WEST);
        refresh();
    }
    
    public void changeRightPanel(JPanel _tmpPannel) {
        if(!rightPanelHidden)
            contentPane.remove(rightPanel);
        
        rightPanelHidden = false;
        rightPanel = new JScrollPane(_tmpPannel);
        contentPane.add(rightPanel, BorderLayout.EAST);

        refresh();
    }
    
    public void closeRightPanel() {
        if(!rightPanelHidden)
            contentPane.remove(rightPanel);
        rightPanelHidden = true;
        refresh();
    }
    
    private JPanel createDynLayout() {
        JPanel panel = new JPanel();
        layout = new GroupLayout(panel);
        parallel = layout.createParallelGroup();
        sequential = layout.createSequentialGroup();
        
        
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(parallel));
        layout.setVerticalGroup(sequential);
        for (int i = 0; i < NUM; i++) {
            labels[i] = new JLabel(String.valueOf(i + 1), JLabel.RIGHT);
            fields[i] = new JTextField(String.valueOf("String " + (i + 1)));
            labels[i].setLabelFor(fields[i]);
            parallel.addGroup(layout.createSequentialGroup().
                addComponent(labels[i]).addComponent(fields[i]));
            sequential.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(labels[i]).addComponent(fields[i]));
            layout.linkSize(SwingConstants.HORIZONTAL, labels[i], labels[0]);
        }
        return panel;
    }
}


