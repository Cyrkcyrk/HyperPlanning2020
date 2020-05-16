/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperplanning;

import java.sql.*;
import java.util.ArrayList;
import DB_class.*;

/**
 *
 * @author Cyrille
 */
public class Modele {
    private final Connection conn;
    private final Statement stmt;
    
    public Modele(String hostDatabase, String portDatabase, String nameDatabase, String loginDatabase, String passwordDatabase) throws SQLException, ClassNotFoundException{
        // chargement driver "com.mysql.jdbc.Driver"
        Class.forName("com.mysql.jdbc.Driver");
        
        String urlDatabase = "jdbc:mysql://"+ hostDatabase +":"+ portDatabase +"/" + nameDatabase;
        // String urlDatabase = "jdbc:mysql://localhost:3308/jps?characterEncoding=latin1";

        //création d'une connexion JDBC à la base 
        conn = DriverManager.getConnection(urlDatabase, loginDatabase, passwordDatabase);

        // création d'un ordre SQL (statement)
        stmt = conn.createStatement();
    }
    public Modele(String hostDatabase, String nameDatabase, String loginDatabase, String passwordDatabase) throws SQLException, ClassNotFoundException{
        this(hostDatabase, "3306", nameDatabase, loginDatabase, passwordDatabase);
    }
    public Modele(String nameDatabase, String loginDatabase, String passwordDatabase) throws SQLException, ClassNotFoundException{
        this("localhost", "3306", nameDatabase, loginDatabase, passwordDatabase);
    }
    public Modele() throws SQLException, ClassNotFoundException{
        this("51.77.145.11", "3306", "JavaING3", "hyperplanning", "df6AktzpDmRtK9Aq");
    }
    
    public ResultSet query(String requete)
    {
        ResultSet result = null;
        try {
            result = stmt.executeQuery(requete);
            
        } catch (SQLException e) {
            System.out.println("Erreur dans la requete: " + e);
            System.exit(1);
        }
        return result;
    }
    
    public static utilisateur getUtilisateur(int id)
    {
        utilisateur _return = null;
        try {
            String sqlQuery = "SELECT * FROM `utilisateur` WHERE `id` = " + id + ";";
            Modele monModele = new Modele();
            ResultSet result = monModele.query(sqlQuery);
            
            if(result.getMetaData().getColumnCount() <= 0) {
                
            }
            else
            {
                result.next();
                
                //(int _id, String _email, String _nom, String _prenom, String _password, int _droits
                _return = new utilisateur(
                        result.getInt("id"),
                        result.getString("email"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getString("passwd"),
                        result.getInt("Droit")
                );
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        return _return;
    }
    
    public static groupe getGroupe(int id)
    {
        groupe _return = null;
        try {
            String sqlQuery = 
                                "SELECT G.`id`, G.`nom`, P.`nom` AS \"promotion\" FROM `groupe` AS G\n" +
                                "JOIN `promotion` AS P\n" +
                                "	ON P.`id` = G.`id_promotion`\n" +
                                "WHERE G.`id` = "+ id +";";
            Modele monModele = new Modele();
            
            ResultSet result = monModele.query(sqlQuery);
            
            if(result.getMetaData().getColumnCount() <= 0) {
                
            }
            else
            {
                result.next();
                
                _return = new groupe(
                        result.getInt("id"),
                        result.getString("nom"),
                        result.getString("promotion") 
                );
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        return _return;
    }
    
    
    public static salle getSalle(int id)
    {
        salle _return = null;
        try {
            String sqlQuery = 
                                "SELECT SA.`id`, SA.`nom`, SA.`capacite`, SI.`nom` AS \"site\" FROM `salle` AS SA\n" +
                                "JOIN `site` AS SI \n" +
                                "	ON SI.`id` = SA.`id_site`\n" +
                                "WHERE SA.`id` = "+ id +";";
            Modele monModele = new Modele();
            
            ResultSet result = monModele.query(sqlQuery);
            
            if(result.getMetaData().getColumnCount() <= 0) {
                
            }
            else
            {
                result.next();
                
                _return = new salle(
                        result.getInt("id"),
                        result.getString("nom"),
                        result.getInt("capacite"),
                        result.getString("site") 
                );
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        return _return;
    }
    
    
    public static seance getSeance(int id)
    {
        seance _return = null;
        try {
            String sqlQuery =   
                            "SELECT S.`id`, S.`semaine`, S.`date`, S.`heure_debut`, S.`heure_fin`, EC.`nom` AS \"Etat\", C.`nom` AS \"Cours\", TC.`nom` AS \"Type\"\n" +
                            "FROM `seance` AS S\n" +
                            "JOIN `cours` AS C\n" +
                            "	ON C.`id` = S.`id_cours`\n" +
                            "JOIN `type_cours` AS TC\n" +
                            "	ON TC.`id` = S.`id_type`\n" +
                            "JOIN `etat_cours` AS EC\n" +
                            "	ON EC.`id` = S.`etat`\n" +
                            "WHERE S.`id` = " + id + ";";
            Modele modeleSeance = new Modele();
            
            ResultSet result = modeleSeance.query(sqlQuery);
            
            if(result.getMetaData().getColumnCount() <= 0) {
            }
            else
            {
                ArrayList<groupe> _groupesTable = new ArrayList<groupe>();
                try {
                    String sqlQueryGroupe = "SELECT * FROM `seance_groupes` WHERE `id_seance` = "+ id +";";
                    Modele modeleGroupe = new Modele();
                    ResultSet resultGroupes = modeleGroupe.query(sqlQueryGroupe);

                    if(resultGroupes.getMetaData().getColumnCount() <= 0) {
                    }
                    else
                    {
                        while(resultGroupes.next())
                        {
                            _groupesTable.add(0, Modele.getGroupe(resultGroupes.getInt("id_groupe")));
                        }
                    }
                } catch (SQLException | ClassNotFoundException e){
                    System.out.println("Erreur de connection à la BDD: " + e);
                }
                
                
                
                ArrayList<salle> _sallesTable = new ArrayList<salle>();
                try {
                    String sqlQuerySalle = "SELECT * FROM `seance_salles` WHERE `id_seance` = "+ id +";";
                    Modele modeleSalle = new Modele();
                    ResultSet resultSalles = modeleSalle.query(sqlQuerySalle);

                    if(resultSalles.getMetaData().getColumnCount() <= 0) {
                    }
                    else
                    {
                        while(resultSalles.next())
                        {
                            _sallesTable.add(0, Modele.getSalle(resultSalles.getInt("id_salle")));
                        }
                    }
                } catch (SQLException | ClassNotFoundException e){
                    System.out.println("Erreur de connection à la BDD: " + e);
                }
                
                result.next();
                _return = new seance(
                        result.getInt("id"), 
                        result.getInt("semaine"),
                        result.getString("date"),
                        result.getString("heure_debut"),
                        result.getString("heure_fin"),
                        result.getString("Etat"),
                        result.getString("Cours"),
                        result.getString("Type"),
                        _groupesTable,
                        _sallesTable
                );
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        return _return;
    }
}
