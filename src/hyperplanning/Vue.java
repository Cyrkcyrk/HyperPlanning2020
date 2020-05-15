/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning;


import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Cyrille
 */
public class Vue extends JFrame {
    
    public Vue () {
        //https://openclassrooms.com/forum/sujet/jframe-exitonclose-33355
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        setLayout(null);
        setBounds(0, 0, 960, 720);
        setResizable(false);
        setVisible(true);
        
       
        JButton testButton = new JButton();
        testButton.setBounds(new Rectangle(50, 50, 200, 25));
        testButton.setText("GNEUUUUUU");
        this.add(testButton);
        
    }
}
