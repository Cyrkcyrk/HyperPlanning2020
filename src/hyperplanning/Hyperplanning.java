/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning;

import hyperplanning.Vue.Vue;
import java.sql.ResultSet;
import java.sql.SQLException;
import DB_class.*;
import java.text.*;
import java.util.*;

/**
 *
 * @author Cyrille
 */
public class Hyperplanning {
    
    
    public static void main(String[] args) {
        Vue maVue = new Vue();
        String inputTimeStamp = "16/08/2011";
        String inputHours = "01:08:00";
        try {
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(inputTimeStamp);
            String d = DateFormat.getDateInstance(DateFormat.FULL, new Locale("fr","FR")).format(date1);
            
            System.out.println(d);
            
            Date heure = new SimpleDateFormat("HH:mm:ss", new Locale("FR", "fr")).parse(inputHours);
            String h = new SimpleDateFormat("HH:mm").format(heure);
            System.out.println(h);
            
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
