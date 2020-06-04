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
 *
 * @author Cyrille
 */
public class customDate {
    boolean dateFormat, hoursFormat;
    Date maDate;
    
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
    public String getByPattern(String pattern)
    {
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
    
    
    
    public String getDateString() {
        return getByPattern("EEEEEEEE");
    }
    
    public String getDateManuscrite() {
        return getByPattern("EEEEEEEE dd MMMM YYYY");
    }
    
    public int getDayInt() {
        return Integer.parseInt(getByPattern("u"))-1;
    }
    
    public int getWeekNumber() {
        return Integer.parseInt(getByPattern("w"));
    }
    
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
