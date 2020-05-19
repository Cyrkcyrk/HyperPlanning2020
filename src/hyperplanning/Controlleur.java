/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning;
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
}
