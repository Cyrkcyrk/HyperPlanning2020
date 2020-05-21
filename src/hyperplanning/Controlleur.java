/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning;
import java.text.*;
import java.util.*;
import java.util.regex.*;

/**
 *
 * @author Cyrille
 */
public class Controlleur {
    public static double heureToDouble(String stringHeure)
    {
        String pattern = "(\\d+):(\\d+)(:\\d+)?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(stringHeure);
        
        double retour = 0;
        if(m.find()){
            double dec = Integer.parseInt(m.group(2));
            dec = dec/60;
            retour = Integer.parseInt(m.group(1)) + dec;
        }
        else
        {
            System.out.println("Pas trouvé");
        }
        return retour;
    }
    
    public static String heureToString(Date heure) {
        String h = new SimpleDateFormat("HH:mm").format(heure);
        return h;
    }
    
    public static String dateToString (Date jour) {
        String d = DateFormat.getDateInstance(DateFormat.FULL, new Locale("fr","FR")).format(jour);
        return d;
    }
    
    public static Date stringToHeure(String inputHours) {
        Date heure;
        
        try {
            heure = new SimpleDateFormat("HH:mm:ss", new Locale("FR", "fr")).parse(inputHours);
        } catch (Exception e) {
            System.out.println(e);
            heure = new Date();
        }
        return heure;
    }
    
    public static Date stringToDate(String inputDate) {
        Date jour;
        try {
            jour = new SimpleDateFormat("yyyy/MM/dd").parse(inputDate);
        } catch (Exception e) {
            System.out.println(e);
            jour = new Date();
        }
        return jour;
    }
}
