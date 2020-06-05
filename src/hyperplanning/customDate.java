/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Classe créée dans le besoins de gérer des dates en java, dnas le but de simplifier le code. 
 * Peu recommandée pour utiliser, mais bien pratique pour des besoins personnels.
 * Tres mal sécurisée.
 * 
 * @author Cyrille
 */
public class customDate {
    boolean dateFormat, hoursFormat;
    Date maDate;
    
    /**
     * Prend en parametre un type ("heure" ou "date"), et un input correspondant au type, et vas le transformer en date.
     * @param type (string) soit "heure" soit "date"
     * @param input (string) doit être respectivement dans le format "23:59:59" ou "2020:12:31"
     */
    public customDate(String type, String input) {
        dateFormat = false;
        hoursFormat = false;
        
        switch(type){
            case "heure":
            {
                try {
                    maDate = new SimpleDateFormat("HH:mm:ss", new Locale("FR", "fr")).parse(input);
                    hoursFormat = true;
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            }
            case "jour":
            {
                try {
                    maDate = new SimpleDateFormat("yyyy-MM-dd").parse(input);
                    dateFormat = true;
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            }
        }
    }
    
    /**
     * Prend un pattern en parametres et renvoie la date correspondant a ce pattern
     * @param pattern (String), sous la forme trouvable sur la javadoc de SimpleDateFormat
     * @return (String)
     */
    public String getByPattern(String pattern) {
        SimpleDateFormat monDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));

        return (String) monDateFormat.format(maDate);
    }
    
    @Override
    public String toString() {
        String returnString;
        
        if(dateFormat && !hoursFormat)
        {
            //returnString = DateFormat.getDateInstance(DateFormat.FULL, new Locale("fr","FR")).format(maDate);
            
            returnString = getByPattern("dd/MM/yyyy");
        }
        else if(!dateFormat && hoursFormat)
        {
            returnString = getByPattern("HH:mm");
        }
        else
        {
            returnString = "Error with the date";
        }
        
        return returnString;
    }
    
    /**
     * Renvoie la date sous format "manuscrit" (Lundi 20 avril 2020)
     * @return (String)
     */
    public String getDateManuscrite() {
        return getByPattern("EEEEEEEE dd MMMM YYYY");
    }
    
    /**
     * Renvoie le numéro du jour dans la semaine (lundi=0, mardi=1, etc)
     * @return (int)
     */
    public int getDayInt() {
        return Integer.parseInt(getByPattern("u"))-1;
    }
    
    /**
     * Retourne le numéro de la semaine dans l'année
     * @return  (int)
     */
    public int getWeekNumber() {
        return Integer.parseInt(getByPattern("w"));
    }
    
    /**
     * Return un String contenant la date ou l'heure qui correspond a un format pret pour etre géré par la BDD
     * @return 
     */
    public String DBReady() {
        String returnString;
        
        if(dateFormat && !hoursFormat)
        {
            //returnString = DateFormat.getDateInstance(DateFormat.FULL, new Locale("fr","FR")).format(maDate);
            
            returnString = getByPattern("yyyy-MM-dd");
        }
        else if(!dateFormat && hoursFormat)
        {
            returnString = getByPattern("HH:mm:ss");
        }
        else
        {
            returnString = "Error with the date";
        }
        return returnString;
    }
}
