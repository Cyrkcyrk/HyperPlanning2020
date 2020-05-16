/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning;

import java.sql.ResultSet;
import java.sql.SQLException;
import DB_class.*;

/**
 *
 * @author Cyrille
 */
public class Hyperplanning {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Vue maVue = new Vue();
        
        try {
            Modele monModele = new Modele("51.77.145.11", "JavaING3", "hyperplanning", "df6AktzpDmRtK9Aq");
            
            ResultSet result = monModele.query("SELECT * FROM `utilisateur`");
            
            
            int columnCount = result.getMetaData().getColumnCount();
            
            while (result.next()) {
                for (int i = 0; i < columnCount; i++) {
                    System.out.print(result.getString(i+1) + ", ");
                }
                System.out.println("");
                System.out.println("--------------");
            }
            
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        
        seance tmp_seance = new seance(1);
        System.out.println(tmp_seance);
        
        etudiant tmp_user = new etudiant(10);
        System.out.println(tmp_user);
        
        
        
    }
}
