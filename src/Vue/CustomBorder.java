package Vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.Border;

/**
 * Permet de créer des bordures facilement pour mes panels
 * SOURCE http://www.java2s.com/Tutorial/Java/0240__Swing/implementsBorderinterface.htm
 * @author KASYC Cyrille
 * @author LECOEUR Titouan
 * @author RASSOUW Clement
 */
public class CustomBorder implements Border {  
    int top;  
    int left;   
    int bottom;   
    int right;  
    Color color = null;   
    
    /**
     * Créer un border complet d'1px d'épaisseur et de contours noirs
     */
    public CustomBorder() {  
        this.top = 1;   
        this.left = 1;   
        this.bottom = 1;   
        this.right = 1;   
        this.color = Color.BLACK;   
    }
    
    /**
     * Créer un border d'épaisseur pour chaque cotés du parametre correspondant en pixels. Contours noirs
     * @param _top (int)
     * @param _left (int)
     * @param _bot (int)
     * @param _right (int)
     */
    public CustomBorder(int _top, int _left, int _bot, int _right) {  
        this.top = _top;   
        this.left = _left;   
        this.bottom = _bot;   
        this.right = _right;   
        this.color = Color.BLACK;   
    }
    
    /**
     * Créer un border d'épaisseur pour chaque cotés du parametre correspondant en pixels. Contours de la couleurs en parametre
     * @param _top (int)
     * @param _left (int)
     * @param _bot (int)
     * @param _right (int)
     * @param _col (java.awt.Color)
     */
    public CustomBorder(int _top, int _left, int _bot, int _right, Color _col) {  
        this.top = _top;   
        this.left = _left;   
        this.bottom = _bot;   
        this.right = _right;   
        this.color = _col;   
    } 
    

    @Override
    public void paintBorder(Component c, 
                            Graphics g,  
                            int x, int y,
                            int width, int height) {


        Insets insets = getBorderInsets(c);   
        if (color != null)  
            g.setColor(color);  

        if(this.top > 0)
        {
            g.fill3DRect(0, 
                         0, 
                         width-insets.right, 
                         insets.top, 
                         true); 
        }
        
        if(this.left > 0)
        {
            g.fill3DRect(0, insets.top, insets.left,  
                            height-insets.top, true); 
        }
        
        if(this.bottom > 0)
        {
            g.fill3DRect(insets.left, height-insets.bottom,   
                         width-insets.left, insets.bottom, true);   
        }
        
        if(this.right > 0)
        {
            g.fill3DRect(width-insets.right, 0, insets.right,   
                         height-insets.bottom, true);
        }
    }   

    @Override
    public Insets getBorderInsets(Component c) {  
        return new Insets(top, left, bottom, right);  
    }   

    @Override
    public boolean isBorderOpaque() {   
        return true;  
    }   
}
