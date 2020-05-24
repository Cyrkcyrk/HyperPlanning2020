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
    
    
    public static etudiant getEtudiant(int id)
    {
        etudiant _return = null;
        try {
            String sqlQuery = 
                    "SELECT * FROM `utilisateur` AS U\n" +
                    "JOIN `etudiant` AS E\n" +
                    "	ON E.`id_utilisateur` = U.`id`\n" +
                    "WHERE U.`id` = " + id + ";";
            Modele monModele = new Modele();
            ResultSet result = monModele.query(sqlQuery);
            
            if(result.getMetaData().getColumnCount() <= 0) {
                
            }
            else
            {
                result.next();
                
                //(int _id, String _email, String _nom, String _prenom, String _password, int _droits
                groupe _tmpGroupe = Modele.getGroupe(result.getInt("id_groupe"));
                _return = new etudiant(
                        result.getInt("id"),
                        result.getString("email"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getString("passwd"),
                        result.getInt("Droit"),
                        result.getInt("numero"),
                        _tmpGroupe
                );
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        return _return;
    }
    
    public static cours getCours(int id)
    {
        cours _return = null;
        try {
            String sqlQuery = "SELECT * FROM `cours` WHERE `id` = "+ id +";";
            Modele monModele = new Modele();
            ResultSet result = monModele.query(sqlQuery);
            if(result.getMetaData().getColumnCount() <= 0) {
                
            }
            else
            {
                result.next();
                
                _return = new cours(
                        result.getInt("id"),
                        result.getString("nom")
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
                
                
                
                ArrayList<utilisateur> _enseignantsTable = new ArrayList<utilisateur>();
                try {
                    String sqlQueryEnseignants = "SELECT * FROM `seance_enseignants` WHERE `id_seance` = "+ id +";";
                    Modele modeleEnseignants = new Modele();
                    ResultSet resultEnseignants = modeleEnseignants.query(sqlQueryEnseignants);

                    if(resultEnseignants.getMetaData().getColumnCount() <= 0) {
                    }
                    else
                    {
                        while(resultEnseignants.next())
                        {
                            _enseignantsTable.add(0, Modele.getUtilisateur(resultEnseignants.getInt("id_enseignant")));
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
                        _sallesTable,
                        _enseignantsTable
                );
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        return _return;
    }
    
    public static ArrayList<groupe> getAllGroupes()
    {
        ArrayList<groupe> mesGroupes = new ArrayList<groupe>();
        try {
            String sqlQuery =   "SELECT G.`id`, G.`nom`, P.`nom` AS \"promotion\" FROM `groupe` AS G\n" +
                                "JOIN `promotion` AS P\n" +
                                "	ON P.`id` = G.`id_promotion`\n" + 
                                "ORDER BY P.`nom` DESC, G.`nom` DESC";
            Modele monModele = new Modele();
            ResultSet result = monModele.query(sqlQuery);
            
            if(result.getMetaData().getColumnCount() <= 0) {
                groupe _tmp = new groupe(
                        0,
                        "Aucun groupe trouvé",
                        ""
                );
                mesGroupes.add(0, _tmp);
            }
            else
            {
                while(result.next())
                {
                    groupe _tmp = new groupe(
                        result.getInt("id"),
                        result.getString("nom"),
                        result.getString("promotion") 
                );
                    mesGroupes.add(0, _tmp);
                }
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        
        return mesGroupes;
    }
    
    
    public static ArrayList<cours> getAllCours()
    {
        ArrayList<cours> mesCours = new ArrayList<cours>();
        try {
            String sqlQuery =   "SELECT * FROM `cours` ORDER BY `nom` DESC";
            Modele monModele = new Modele();
            ResultSet result = monModele.query(sqlQuery);
            
            if(result.getMetaData().getColumnCount() <= 0) {
                cours _tmp = new cours(
                        0,
                        "Aucun cours trouvé"
                );
                mesCours.add(0, _tmp);
            }
            else
            {
                while(result.next())
                {
                    cours _tmp = new cours(
                        result.getInt("id"),
                        result.getString("nom")
                    );
                    mesCours.add(0, _tmp);
                }
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        
        return mesCours;
    }
    
    
    
    
    public static boolean isUserAvailable(int id, customDate _Date, customDate _Debut, customDate _Fin)
    {
        return isUserAvailable(id, _Date.DBReady(), _Debut.DBReady(), _Fin.DBReady());
    }
    
    public static boolean isUserAvailable(int id, String _stringDate, String _stringDebut, String _stringFin)
    {
        boolean _return = false;
        try {
            String sqlQuery =   "SELECT * FROM `utilisateur` AS U\n" +
                                "LEFT JOIN `etudiant` AS E\n" +
                                "    ON U.`id` = E.`id_utilisateur`\n" +
                                "LEFT JOIN `seance_enseignants` AS Se\n" +
                                "    ON U.`id` = Se.`id_enseignant`\n" +
                                "LEFT JOIN `seance_groupes` AS Sg\n" +
                                "    ON U.`id` = Sg.`id_groupe`\n" +
                                "LEFT JOIN `seance` AS S\n" +
                                "    ON S.`id` = Sg.`id_seance` OR S.`id` = Se.`id_seance`\n" +
                                "WHERE \n" +
                                "    U.`ID` = "+ id +"\n" +
                                "    AND S.`date` = "+ _stringDate +"\n" +
                                "    AND (\n" +
                                "        ("+ _stringDebut +" >= S.`heure_debut` AND "+ _stringDebut +" <= S.`heure_fin`)\n" +
                                "        OR ("+ _stringFin +" >= S.`heure_debut` AND "+ _stringFin +" <= S.`heure_fin`)\n" +
                                "        OR ("+ _stringDebut +" <= S.`heure_debut` AND "+ _stringFin +" >= S.`heure_fin`)\n" +
                                "    )";
            Modele monModele = new Modele();
            ResultSet result = monModele.query(sqlQuery);
            
            if(result.getMetaData().getColumnCount() <= 0) {
                _return = true;
            }
            else
            {
                _return = false;
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        return _return;
    }
    
    
    public boolean isClassAvailable(int id, customDate _Date, customDate _Debut, customDate _Fin)
    {
        return isClassAvailable(id, _Date.DBReady(), _Debut.DBReady(), _Fin.DBReady());
    }
    
    public static boolean isClassAvailable(int id, String _stringDate, String _stringDebut, String _stringFin)
    {
        boolean _return = false;
        try {
            String sqlQuery =   "SELECT * FROM `seance` AS S\n" +
                                "JOIN `seance_salles` AS Se\n" +
                                "	ON S.`id` = Se.`id_seance`\n" +
                                "WHERE \n" +
                                "	Se.`id_salle` = "+ id + 
                                "    AND S.`date` = "+ _stringDate +"\n" +
                                "    AND (\n" +
                                "        ("+ _stringDebut +" >= S.`heure_debut` AND "+ _stringDebut +" <= S.`heure_fin`)\n" +
                                "        OR ("+ _stringFin +" >= S.`heure_debut` AND "+ _stringFin +" <= S.`heure_fin`)\n" +
                                "        OR ("+ _stringDebut +" <= S.`heure_debut` AND "+ _stringFin +" >= S.`heure_fin`)\n" +
                                "    )";
            Modele monModele = new Modele();
            ResultSet result = monModele.query(sqlQuery);
            
            if(result.getMetaData().getColumnCount() <= 0) {
                _return = true;
            }
            else
            {
                _return = false;
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        return _return;
    }
    
    public static ArrayList<salle> AvailableClass(int _capaciteMin, customDate _Date, customDate _Debut, customDate _Fin)
    {
        return AvailableClass(_capaciteMin, _Date.DBReady(), _Debut.DBReady(), _Fin.DBReady());
    }
    
    public static ArrayList<salle> AvailableClass(int _capaciteMin, String _stringDate, String _stringDebut, String _stringFin)
    {
        ArrayList<salle> _return = new ArrayList<salle>();
        try {
            String sqlQuery =   "SELECT `salle`.*, `site`.`nom` AS 'site' FROM `salle`\n" +
                                "JOIN `site`\n" + 
                                "       ON `site`.`id` = `salle`.`id_site`\n" +
                                "WHERE `salle`.`id` NOT IN (\n" +
                                "	SELECT S.`id` FROM `seance` AS S\n" +
                                "	JOIN `seance_salles` AS Se\n" +
                                "		ON S.`id` = Se.`id_seance`\n" +
                                "	WHERE \n" +
                                "	S.`date` = '"+ _stringDate +"'\n" +
                                "	AND (\n" +
                                "		('"+ _stringDebut +"' >= S.`heure_debut` AND '"+ _stringDebut +"' <= S.`heure_fin`)\n" +
                                "		OR ('"+ _stringFin +"' >= S.`heure_debut` AND '"+ _stringFin +"' <= S.`heure_fin`)\n" +
                                "		OR ('"+ _stringDebut +"' <= S.`heure_debut` AND '"+ _stringFin +"' >= S.`heure_fin`)\n" +
                                "	)\n" +
                                ")\n" +
                                "AND `capacite` > "+ _capaciteMin +"\n" +
                                "ORDER BY `capacite` DESC;";
            Modele monModele = new Modele();
            ResultSet result = monModele.query(sqlQuery);
            
            if(result.getMetaData().getColumnCount() <= 0) {
                salle _tmp = new salle(
                        0,
                        "Aucune salle disponible",
                        0,
                        "" 
                    );
                    _return.add(0, _tmp);
            }
            else
            {
                while(result.next())
                {
                    salle _tmp = new salle(
                        result.getInt("id"),
                        result.getString("nom"),
                        result.getInt("capacite"),
                        result.getString("site") 
                    );
                    _return.add(0, _tmp);
                }
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        return _return;
    }
    
    
    public static ArrayList<utilisateur> AvailableProf(int _matiere, customDate _Date, customDate _Debut, customDate _Fin)
    {
        return AvailableProf(_matiere, _Date.DBReady(), _Debut.DBReady(), _Fin.DBReady());
    }
    
    public static ArrayList<utilisateur> AvailableProf(int _matiere, String _stringDate, String _stringDebut, String _stringFin)
    {
        ArrayList<utilisateur> _return = new ArrayList<utilisateur>();
        try {
            String sqlQuery =   "SELECT * FROM `utilisateur` AS U\n" +
                                "JOIN `enseignant` AS ES\n" +
                                "	ON U.`id` = ES.`id_utilisateur`\n" +
                                "WHERE \n" +
                                "U.`id` NOT IN (\n" +
                                "    SELECT U.`id` FROM `utilisateur` AS U\n" +
                                "    LEFT JOIN `etudiant` AS E\n" +
                                "        ON U.`id` = E.`id_utilisateur`\n" +
                                "    LEFT JOIN `seance_enseignants` AS Se\n" +
                                "        ON U.`id` = Se.`id_enseignant`\n" +
                                "    LEFT JOIN `seance_groupes` AS Sg\n" +
                                "        ON U.`id` = Sg.`id_groupe`\n" +
                                "    LEFT JOIN `seance` AS S\n" +
                                "        ON S.`id` = Sg.`id_seance` OR S.`id` = Se.`id_seance`\n" +
                                "    WHERE \n" +
                                "    S.`date` = '"+ _stringDate +"'\n" +
                                "    AND (\n" +
                                "        ('"+ _stringDebut +"' >= S.`heure_debut` AND '"+ _stringDebut +"' <= S.`heure_fin`)\n" +
                                "        OR ('"+ _stringFin +"' >= S.`heure_debut` AND '"+ _stringFin +"' <= S.`heure_fin`)\n" +
                                "        OR ('"+ _stringDebut +"' <= S.`heure_debut` AND '"+ _stringFin +"' >= S.`heure_fin`)\n" +
                                "    )\n" +
                                ")\n" +
                                "AND ES.`id_cours` = "+ _matiere +";";
            Modele monModele = new Modele();
            ResultSet result = monModele.query(sqlQuery);
            
            if(result.getMetaData().getColumnCount() <= 0) {
                utilisateur _tmp = new utilisateur(
                    0,
                    "",
                    "Aucun enseignant disponible",
                    "",
                    "",
                    0
                );
                _return.add(0, _tmp);
            }
            else
            {
                while(result.next())
                {
                    utilisateur _tmp = new utilisateur(
                        result.getInt("id"),
                        result.getString("email"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getString("passwd"),
                        result.getInt("Droit")
                    );
                    _return.add(0, _tmp);
                }
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        return _return;
    }
    

    public static int GroupeTotalEtudiant(ArrayList<groupe> groupes)
    {

        int _return = 0;
        try {
            String sqlQuery =   "SELECT COUNT(*) AS \"Total\" FROM `etudiant`";
            
            for (int i=0; i<groupes.size(); i++) {
                if(i >= 1)
                    sqlQuery += " OR ";
                else
                    sqlQuery += " WHERE ";
                sqlQuery += "`id_groupe` = " + groupes.get(i).getID();
            }
            sqlQuery += ";";
            
            Modele monModele = new Modele();
            ResultSet result = monModele.query(sqlQuery);
            
            if(result.getMetaData().getColumnCount() >= 0) {
                result.next();
                _return = result.getInt("Total");
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        return _return;
    }
    
}
