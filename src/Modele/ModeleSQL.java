package Modele;

import Modele.Type_cours;
import Modele.seance;
import Modele.groupe;
import Modele.cours;
import Modele.etudiant;
import Modele.salle;
import Modele.utilisateur;
import Controlleur.customDate;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO qui gere l'ingégralitée des connections avec la BDD.
 * @author KASYC Cyrille
 * @author LECOEUR Titouan
 * @author RASSOUW Clement
 */
public class ModeleSQL {
    private final Connection conn;
    private final Statement stmt;
    
    /**
     * Créer un objet capabel de requetes SQL sur la BDD 
     * @param hostDatabase (String)
     * @param portDatabase (String)
     * @param nameDatabase (String)
     * @param loginDatabase (String)
     * @param passwordDatabase (String)
     * @throws SQLException Exception SQL
     * @throws ClassNotFoundException Pas de classe trouvée
     */
    public ModeleSQL(String hostDatabase, String portDatabase, String nameDatabase, String loginDatabase, String passwordDatabase) throws SQLException, ClassNotFoundException{
        // chargement driver "com.mysql.jdbc.Driver"
        Class.forName("com.mysql.jdbc.Driver");
        
        String urlDatabase = "jdbc:mysql://"+ hostDatabase +":"+ portDatabase +"/" + nameDatabase;
        // String urlDatabase = "jdbc:mysql://localhost:3308/jps?characterEncoding=latin1";

        //création d'une connexion JDBC à la base 
        conn = DriverManager.getConnection(urlDatabase, loginDatabase, passwordDatabase);

        // création d'un ordre SQL (statement)
        stmt = conn.createStatement();
        System.out.println("CONNECTION BDD");
    }
    
    /**
     * Créer un objet capabel de requetes SQL sur la BDD sur le port 3306
     * @param hostDatabase (String)
     * @param nameDatabase (String)
     * @param loginDatabase (String)
     * @param passwordDatabase (String)
     * @throws SQLException Exception SQL
     * @throws ClassNotFoundException Pas de classe trouvé
     */
    public ModeleSQL(String hostDatabase, String nameDatabase, String loginDatabase, String passwordDatabase) throws SQLException, ClassNotFoundException{
        this(hostDatabase, "3306", nameDatabase, loginDatabase, passwordDatabase);
    }
    
    /**
     * Créer un objet capabel de requetes SQL sur la BDD sur le host localhost et le port 3306
     * @param nameDatabase (String)
     * @param loginDatabase (String)
     * @param passwordDatabase (String)
     * @throws SQLException Exception SQL
     * @throws ClassNotFoundException Pas de classe trouvée
     */
    public ModeleSQL(String nameDatabase, String loginDatabase, String passwordDatabase) throws SQLException, ClassNotFoundException{
        this("localhost", "3306", nameDatabase, loginDatabase, passwordDatabase);
    }
    
    /**
     * Créer un objet capabel de requetes SQL sur la BDD sur
     *  host  : "localhost"
     *  port  : 3306
     *  login : "root"
     *  psswd : ""
     *  table : "JavaING3"
     * 
     * @throws SQLException Exception SQL
     * @throws ClassNotFoundException Pas de classe trouvée
     */
    public ModeleSQL() throws SQLException, ClassNotFoundException{
        //this("51.77.145.11", "3306", "javaing3", "hyperplanning", "df6AktzpDmRtK9Aq");
        this("localhost", "3306", "javaing3", "root", "");
    }
    
    /**
     * Lance une query SQL SELECT recue en parametre et renvoie le résultat.
     * @param requete (String)
     * @return (ResultatSet)
     */
    public ResultSet query(String requete) {
        ResultSet result = null;
        try {
            result = stmt.executeQuery(requete);
            
        } catch (SQLException e) {
            System.out.println("Erreur dans la requete: "+ requete + "\nDétail: "+ e);
            System.exit(1);
        }
        return result;
    }
    
    /**
     * Lance une query SQL UPDATE/INSERT/DELETE recue en parametre
     * @param requete (String)
     */
    public void queryUpdate(String requete) {
        try {
            stmt.executeUpdate(requete);
            
        } catch (SQLException e) {
            System.out.println("Erreur dans la requete: " + requete + "\nDétail: "+ e);
            System.exit(1);
        }
    }
    
    
    /**
     * Renvoie le nombre de lignes dans les résultats recus en parametres
     * @param resultSet (ResultSet)
     * @return (int)
     */
    public int getRowCount(ResultSet resultSet) {
        //https://stackoverflow.com/questions/7886462/how-to-get-row-count-using-resultset-in-java
        int size = 0;
        try {
            resultSet.last();
            size = resultSet.getRow();
            resultSet.beforeFirst();
        }
        catch(Exception ex) {
            return 0;
        }
        return size;
    }
    
    /**
     * Récupere un utilisateur dans la BDD par l'id passé en parametre
     * @param id Id de l'utilisateur a rechercher
     * @return (utilisateurs)
     */
    public static utilisateur getUtilisateur(int id) {
        utilisateur _return = null;
        try {
            String sqlQuery = "SELECT * FROM `utilisateur` WHERE `id` = " + id + ";";
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
                
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
    
    /**
     * Récupere un utilisateur dans la BDD par l'email et le MDP passé en parametres
     * @param _email (String)
     * @param _password (String)
     * @return (utilisateurs)
     */
    public static utilisateur getUtilisateur(String _email, String _password) {
        utilisateur _return = null;
        try {
            String sqlQuery = "SELECT * FROM `utilisateur` WHERE `email` = '" + _email + "' AND `passwd` = '"+ _password +"';";
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) > 0)
            {
                result.next();
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
    
    /**
     * Récupere un étudiant dans la BDD par l'id passé en parametre
     * @param id Id de l'utilisateur a rechercher
     * @return (etudiant)
     */
    public static etudiant getEtudiant(int id) {
        etudiant _return = null;
        try {
            String sqlQuery = 
                    "SELECT * FROM `utilisateur` AS U\n" +
                    "JOIN `etudiant` AS E\n" +
                    "	ON E.`id_utilisateur` = U.`id`\n" +
                    "WHERE U.`id` = " + id + ";";
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
                
            }
            else
            {
                result.next();
                
                //(int _id, String _email, String _nom, String _prenom, String _password, int _droits
                groupe _tmpGroupe = ModeleSQL.getGroupe(result.getInt("id_groupe"));
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
    
    /**
     * Récupere un cours dans la BDD par l'id passé en parametre
     * @param id Id du cours a rechercher
     * @return (cours)
     */
    public static cours getCours(int id) {
        cours _return = null;
        try {
            String sqlQuery = "SELECT * FROM `cours` WHERE `id` = "+ id +";";
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            if(monModele.getRowCount(result) <= 0) {
                
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
    
    /**
     * Récupere un type de cours dans la BDD par l'id passé en parametre
     * @param id Id du type de cours a rechercher
     * @return (Type_cours)
     */
    public static Type_cours getTypeCours(int id) {
        Type_cours _return = null;
        try {
            String sqlQuery = "SELECT * FROM `type_cours` WHERE `id` = "+ id +";";
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            if(monModele.getRowCount(result) <= 0) {
                
            }
            else
            {
                result.next();
                
                _return = new Type_cours(
                        result.getInt("id"),
                        result.getString("nom")
                );
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        return _return;
    }
    
    /**
     * Récupere un groupe dans la BDD par l'id passé en parametre
     * @param id Id du groupe a rechercher
     * @return (groupe)
     */
    public static groupe getGroupe(int id) {
        groupe _return = null;
        try {
            String sqlQuery = 
                                "SELECT G.`id`, G.`nom`, P.`nom` AS \"promotion\" FROM `groupe` AS G\n" +
                                "JOIN `promotion` AS P\n" +
                                "	ON P.`id` = G.`id_promotion`\n" +
                                "WHERE G.`id` = "+ id +";";
            ModeleSQL monModele = new ModeleSQL();
            
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
                
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
    
    /**
     * Récupere une salle dans la BDD par l'id passé en parametre
     * @param id Id de la salle a rechercher
     * @return (salle)
     */
    public static salle getSalle(int id) {
        salle _return = null;
        try {
            String sqlQuery = 
                                "SELECT SA.`id`, SA.`nom`, SA.`capacite`, SI.`nom` AS \"site\" FROM `salle` AS SA\n" +
                                "JOIN `site` AS SI \n" +
                                "	ON SI.`id` = SA.`id_site`\n" +
                                "WHERE SA.`id` = "+ id +";";
            ModeleSQL monModele = new ModeleSQL();
            
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
                
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
    
    /**
     * Récupere une seance dans la BDD par l'id passé en parametre
     * @param id Id de la seance a rechercher
     * @return (seance)
     */
    public static seance getSeance(int id) {
        seance _return = null;
        try {
            String sqlQuery =   
                            "SELECT S.`id`, S.`semaine`, S.`date`, S.`heure_debut`, S.`heure_fin`, EC.`id` AS \"Etat\", C.`nom` AS \"CoursNom\", C.`id` AS \"CoursID\", TC.`nom` AS \"TypeNom\", TC.`id` AS \"TypeID\"\n" +
                            "FROM `seance` AS S\n" +
                            "JOIN `cours` AS C\n" +
                            "	ON C.`id` = S.`id_cours`\n" +
                            "JOIN `type_cours` AS TC\n" +
                            "	ON TC.`id` = S.`id_type`\n" +
                            "JOIN `etat_cours` AS EC\n" +
                            "	ON EC.`id` = S.`etat`\n" +
                            "WHERE S.`id` = " + id + ";";
            ModeleSQL modeleSeance = new ModeleSQL();
            
            ResultSet result = modeleSeance.query(sqlQuery);
            
            if(modeleSeance.getRowCount(result) <= 0) {
            }
            else
            {
                ArrayList<groupe> _groupesTable = new ArrayList<groupe>();
                try {
                    String sqlQueryGroupe = "SELECT * FROM `seance_groupes` WHERE `id_seance` = "+ id +";";
                    ModeleSQL modeleGroupe = new ModeleSQL();
                    ResultSet resultGroupes = modeleGroupe.query(sqlQueryGroupe);

                    if(modeleSeance.getRowCount(resultGroupes) <= 0) {
                    }
                    else
                    {
                        while(resultGroupes.next())
                        {
                            _groupesTable.add(0, ModeleSQL.getGroupe(resultGroupes.getInt("id_groupe")));
                        }
                    }
                } catch (SQLException | ClassNotFoundException e){
                    System.out.println("Erreur de connection à la BDD: " + e);
                }
                
                
                
                ArrayList<salle> _sallesTable = new ArrayList<salle>();
                try {
                    String sqlQuerySalle = "SELECT * FROM `seance_salles` WHERE `id_seance` = "+ id +";";
                    ModeleSQL modeleSalle = new ModeleSQL();
                    ResultSet resultSalles = modeleSalle.query(sqlQuerySalle);

                    if(modeleSeance.getRowCount(resultSalles) <= 0) {
                    }
                    else
                    {
                        while(resultSalles.next())
                        {
                            _sallesTable.add(0, ModeleSQL.getSalle(resultSalles.getInt("id_salle")));
                        }
                    }
                } catch (SQLException | ClassNotFoundException e){
                    System.out.println("Erreur de connection à la BDD: " + e);
                }
                
                
                
                ArrayList<utilisateur> _enseignantsTable = new ArrayList<utilisateur>();
                try {
                    String sqlQueryEnseignants = "SELECT * FROM `seance_enseignants` WHERE `id_seance` = "+ id +";";
                    ModeleSQL modeleEnseignants = new ModeleSQL();
                    ResultSet resultEnseignants = modeleEnseignants.query(sqlQueryEnseignants);

                    if(modeleSeance.getRowCount(resultEnseignants) <= 0) {
                    }
                    else
                    {
                        while(resultEnseignants.next())
                        {
                            _enseignantsTable.add(0, ModeleSQL.getUtilisateur(resultEnseignants.getInt("id_enseignant")));
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
                        result.getInt("Etat"),
                        new cours(result.getInt("CoursID"),result.getString("CoursNom")),
                        new Type_cours(result.getInt("TypeID"), result.getString("TypeNom")),
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
    
    /**
     * Récupère tous les groupes de la base de donnée
     * @return ArrayList(groupe) de tous les groupes
     */
    public static ArrayList<groupe> getAllGroupes() {
        ArrayList<groupe> mesGroupes = new ArrayList<groupe>();
        try {
            String sqlQuery =   "SELECT G.`id`, G.`nom`, P.`nom` AS \"promotion\" FROM `groupe` AS G\n" +
                                "JOIN `promotion` AS P\n" +
                                "	ON P.`id` = G.`id_promotion`\n" + 
                                "ORDER BY P.`nom` DESC, G.`nom` DESC";
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
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
    
    /**
     * Récupère tous les cours de la base de donnée
     * @return ArrayList(cours) de tous les cours
     */
    public static ArrayList<cours> getAllCours() {
        ArrayList<cours> mesCours = new ArrayList<cours>();
        try {
            String sqlQuery =   "SELECT * FROM `cours` ORDER BY `nom` DESC";
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
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
    
    /**
     * Récupère tous les type de cours de la base de donnée
     * @return ArrayList(Type_cours) de tous les type de cours
     */
    public static ArrayList<Type_cours> getAllTypeCours() {
        ArrayList<Type_cours> mesTypeCours = new ArrayList<Type_cours>();
        try {
            String sqlQuery =   "SELECT * FROM `type_cours` ORDER BY `id` DESC";
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
                Type_cours _tmp = new Type_cours(
                        0,
                        "Aucun cours trouvé"
                );
                mesTypeCours.add(0, _tmp);
            }
            else
            {
                while(result.next())
                {
                    Type_cours _tmp = new Type_cours(
                        result.getInt("id"),
                        result.getString("nom")
                    );
                    mesTypeCours.add(0, _tmp);
                }
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        
        return mesTypeCours;
    }
    
    /**
     * Récupère toute les salles de la base de donnée
     * @return ArrayList(salle) de toute les salles
     */
    public static ArrayList<salle> getAllSalles() {
        ArrayList<salle> mesSalles = new ArrayList<salle>();
        try {
            String sqlQuery =   "SELECT SA.`id`, SA.`nom`, SA.`capacite`, SI.`nom` AS \"site\" FROM `salle` AS SA\n" +
                                "JOIN `site` AS SI \n" +
                                "	ON SI.`id` = SA.`id_site`\n" +
                                "ORDER BY `capacite` DESC;";
            
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
                salle _tmp = new salle(
                    0,
                    "Pas de salle trouvées",
                    0,
                    "" 
                );
                mesSalles.add(0, _tmp);
            }
            else
            {
                while(result.next())
                {
                    salle _tmp =  new salle(
                        result.getInt("id"),
                        result.getString("nom"),
                        result.getInt("capacite"),
                        result.getString("site") 
                );
                    mesSalles.add(0, _tmp);
                }
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        
        return mesSalles;
    }
    
    /**
     * Récupère tous les enseignants de la base de donnée
     * @return ArrayList(groupe) de tous les enseignants
     */
    public static ArrayList<utilisateur> getAllProfs() {
        ArrayList<utilisateur> mesEnseignants = new ArrayList<utilisateur>();
        try {
            String sqlQuery =   "SELECT U.* FROM `utilisateur` AS U INNER JOIN `enseignant` AS E ON U.`id` = E.`id_utilisateur` GROUP BY U.`id` ORDER BY `nom` DESC";
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
                utilisateur _tmp = new utilisateur(
                        0,
                        "",
                        "PAS D'ENSEIGNANT TROUVE",
                        "",
                        "",
                        3
                );
                mesEnseignants.add(0, _tmp);
            }
            else
            {
                while(result.next())
                {
                    utilisateur _tmp =  new utilisateur(
                        result.getInt("id"),
                        result.getString("email"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getString("passwd"),
                        result.getInt("Droit")
                    );
                    mesEnseignants.add(0, _tmp);
                }
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        
        return mesEnseignants;
    }
    
    /**
     * Vérifie si un utilisateur est disponible entre des dates indiquées
     * @param id (int) ID de l'utilisateur dont on cherche les diponibilitees
     * @param _Date (cusomDate) date à laquelle on cherche
     * @param _Debut (customDate) créneaux horaire de début
     * @param _Fin (customDate) créneaux horaire de fin
     * @return (boolean)
     */
    public static boolean isUserAvailable(int id, customDate _Date, customDate _Debut, customDate _Fin) {
        return isUserAvailable(id, _Date.DBReady(), _Debut.DBReady(), _Fin.DBReady(), 0);
    }
    /**
    * Vérifie si un utilisateur est disponible entre des dates indiquées
    * @param id (int) ID de l'utilisateur dont on cherche les diponibilitees
    * @param _Date (String) date à laquelle on cherche sous format "2000-12-31"
    * @param _Debut (String) créneaux horaire de début sous format "23:59:59"
    * @param _Fin (String) créneaux horaire de fin sous format "23:59:29"
    * @return (boolean)
    */
    public static boolean isUserAvailable(int id, String _Date, String _Debut, String _Fin) {
        return isUserAvailable(id, _Date, _Debut, _Fin, 0);
    }
     /**
     * Vérifie si un utilisateur est disponible entre des dates indiquées sans prendre en compte une séance
     * @param id (int) ID de l'utilisateur dont on cherche les diponibilitees
     * @param _Date (cusomDate) date à laquelle on cherche
     * @param _Debut (customDate) créneaux horaire de début
     * @param _Fin (customDate) créneaux horaire de fin
     * @param _SeanceExceptionID (int) séance a ignorer
     * @return (boolean)
     */
    public static boolean isUserAvailable(int id, customDate _Date, customDate _Debut, customDate _Fin, int _SeanceExceptionID) {
        return isUserAvailable(id, _Date.DBReady(), _Debut.DBReady(), _Fin.DBReady(), _SeanceExceptionID);
    }
    /**
     * Vérifie si un utilisateur est disponible entre des dates indiquées sans prendre en compte une séance
     * @param id (int) ID de l'utilisateur dont on cherche les diponibilitees
     * @param _Date (String) date à laquelle on cherche sous format "2000-12-31"
     * @param _Debut (String) créneaux horaire de début sous format "23:59:59"
     * @param _Fin (String) créneaux horaire de fin sous format "23:59:29"
     * @param _SeanceExceptionID (int) séance a ignorer
     * @return (boolean)
     */
    public static boolean isUserAvailable(int id, String _Date, String _Debut, String _Fin, int _SeanceExceptionID) {
        boolean _return = false;
        try {
            String sqlQuery =   "SELECT * FROM `utilisateur` AS U\n" +
                                "LEFT JOIN `etudiant` AS E\n" +
                                "    ON U.`id` = E.`id_utilisateur`\n" +
                                "LEFT JOIN `seance_enseignants` AS Se\n" +
                                "    ON U.`id` = Se.`id_enseignant`\n" +
                                "LEFT JOIN `seance_groupes` AS Sg\n" +
                                "    ON E.`id_groupe` = Sg.`id_groupe`\n" +
                                "LEFT JOIN `seance` AS S\n" +
                                "    ON S.`id` = Sg.`id_seance` OR S.`id` = Se.`id_seance`\n" +
                                "WHERE \n" +
                                "    U.`ID` = "+ id +"\n" +
                                "    AND S.`date` = '"+ _Date +"'\n" +
                                "    AND (\n" +
                                "        ('"+ _Debut +"' >= S.`heure_debut` AND '"+ _Debut +"' <= S.`heure_fin`)\n" +
                                "        OR ('"+ _Fin +"' >= S.`heure_debut` AND '"+ _Fin +"' <= S.`heure_fin`)\n" +
                                "        OR ('"+ _Debut +"' <= S.`heure_debut` AND '"+ _Fin +"' >= S.`heure_fin`)\n" +
                                "    )" + 
                                "    AND S.`etat` = 1 ";
            
            if(_SeanceExceptionID > 0) {
                sqlQuery += "AND S.`id` != " + _SeanceExceptionID + ";"; 
            } else {
                sqlQuery += ";";
            }
            
            System.out.println(sqlQuery);
            
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            if(monModele.getRowCount(result) <= 0) {
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
    
    
    /**
     * Vérifie si un groupe est disponible entre des dates indiquées
     * @param id (int) ID du groupe dont on cherche les diponibilitees
     * @param _Date (customDate) date à laquelle on cherche
     * @param _Debut (customDate) créneaux horaire de début
     * @param _Fin (customDate) créneaux horaire de fin
     * @return (boolean)
     */
    public static boolean isGroupeAvailable(int id, customDate _Date, customDate _Debut, customDate _Fin) {
        return isGroupeAvailable(id, _Date.DBReady(), _Debut.DBReady(), _Fin.DBReady(), 0);
    }
    /**
     * Vérifie si un groupe est disponible entre des dates indiquées
     * @param id (int) ID du groupe dont on cherche les diponibilitees
     * @param _Date (String) date à laquelle on cherche sous format "2000-12-31"
     * @param _Debut (String) créneaux horaire de début sous format "23:59:59"
     * @param _Fin (String) créneaux horaire de fin sous format "23:59:29"
     * @return (boolean)
     */
    public static boolean isGroupeAvailable(int id, String _Date, String _Debut, String _Fin) {
        return isGroupeAvailable(id, _Date, _Debut, _Fin, 0);
    }
    /**
     * Vérifie si un groupe est disponible entre des dates indiquées sans prendre en compte une séance
     * @param id (int) ID du groupe dont on cherche les diponibilitees
     * @param _Date (customDate) date à laquelle on cherche
     * @param _Debut (customDate) créneaux horaire de début
     * @param _Fin (customDate) créneaux horaire de fin
     * @param _SeanceExceptionID (int) séance a ignorer
     * @return (boolean)
     */
    public static boolean isGroupeAvailable(int id, customDate _Date, customDate _Debut, customDate _Fin, int _SeanceExceptionID) {
        return isGroupeAvailable(id, _Date.DBReady(), _Debut.DBReady(), _Fin.DBReady(), _SeanceExceptionID);
    }
    /**
     * Vérifie si un groupe est disponible entre des dates indiquées sans prendre en compte une séance
     * @param id (int) ID du groupe dont on cherche les diponibilitees
     * @param _Date (String) date à laquelle on cherche sous format "2000-12-31"
     * @param _Debut (String) créneaux horaire de début sous format "23:59:59"
     * @param _Fin (String) créneaux horaire de fin sous format "23:59:29"
     * @param _SeanceExceptionID (int) séance a ignorer
     * @return (boolean)
     */
    public static boolean isGroupeAvailable(int id, String _Date, String _Debut, String _Fin, int _SeanceExceptionID) {
        boolean _return = false;
        try {
            String sqlQuery =   "SELECT * FROM `groupe` AS G\n" +
                                "LEFT JOIN `seance_groupes` AS Sg\n" +
                                "    ON G.`id` = Sg.`id_groupe`\n" +
                                "LEFT JOIN `seance` AS S\n" +
                                "    ON S.`id` = Sg.`id_seance`\n" +
                                "WHERE \n" +
                                "    G.`id` = "+ id +"\n" + 
                                "    AND S.`date` = '"+ _Date +"'\n" +
                                "    AND (\n" +
                                "        ('"+ _Debut +"' >= S.`heure_debut` AND '"+ _Debut +"' <= S.`heure_fin`)\n" +
                                "        OR ('"+ _Fin +"' >= S.`heure_debut` AND '"+ _Fin +"' <= S.`heure_fin`)\n" +
                                "        OR ('"+ _Debut +"' <= S.`heure_debut` AND '"+ _Fin +"' >= S.`heure_fin`)\n" +
                                "    )" + 
                                "    AND S.`etat` = 1 ";
            
            if(_SeanceExceptionID > 0) {
                sqlQuery += "AND S.`id` != " + _SeanceExceptionID + ";"; 
            } else {
                sqlQuery += ";";
            }
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
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
    
    
    /**
     * Vérifie si une classe est disponible entre des dates indiquées
     * @param id (int) ID de la classe dont on cherche les diponibilitees
     * @param _Date (customDate) date à laquelle on cherche
     * @param _Debut (customDate) créneaux horaire de début
     * @param _Fin (customDate) créneaux horaire de fin
     * @return (boolean)
     */
    public static boolean isClassAvailable(int id, customDate _Date, customDate _Debut, customDate _Fin) {
        return isClassAvailable(id, _Date.DBReady(), _Debut.DBReady(), _Fin.DBReady(), 0);
    }
    /**
     * Vérifie si une classe est disponible entre des dates indiquées
     * @param id (int) ID de la classe dont on cherche les diponibilitees
     * @param _Date (String) date à laquelle on cherche sous format "2000-12-31"
     * @param _Debut (String) créneaux horaire de début sous format "23:59:59"
     * @param _Fin (String) créneaux horaire de fin sous format "23:59:29"
     * @return (boolean)
     */
    public static boolean isClassAvailable(int id, String _Date, String _Debut, String _Fin) {
        return isClassAvailable(id, _Date, _Debut, _Fin, 0);
    }
    /**
     * Vérifie si une classe est disponible entre des dates indiquées sans prendre en compte une séance
     * @param id (int) ID de la classe dont on cherche les diponibilitees
     * @param _Date (customDate) date à laquelle on cherche sous format "2000-12-31"
     * @param _Debut (customDate) créneaux horaire de début sous format "23:59:59"
     * @param _Fin (customDate) créneaux horaire de fin sous format "23:59:29"
     * @param _SeanceExceptionID (int) séance a ignorer
     * @return (boolean)
     */
    public static boolean isClassAvailable(int id, customDate _Date, customDate _Debut, customDate _Fin, int _SeanceExceptionID) {
        return isClassAvailable(id, _Date.DBReady(), _Debut.DBReady(), _Fin.DBReady(), _SeanceExceptionID);
    }
    /**
     * Vérifie si une classe est disponible entre des dates indiquées sans prendre en compte une séance
     * @param id (int) ID de la classe dont on cherche les diponibilitees
     * @param _Date (String) date à laquelle on cherche sous format "2000-12-31"
     * @param _Debut (String) créneaux horaire de début sous format "23:59:59"
     * @param _Fin (String) créneaux horaire de fin sous format "23:59:29"
     * @param _SeanceExceptionID (int) séance a ignorer
     * @return (boolean)
     */
    public static boolean isClassAvailable(int id, String _Date, String _Debut ,String _Fin, int _SeanceExceptionID) {
        boolean _return = false;
        try {
            String sqlQuery =   "SELECT * FROM `seance` AS S\n" +
                                "JOIN `seance_salles` AS Se\n" +
                                "	ON S.`id` = Se.`id_seance`\n" +
                                "WHERE \n" +
                                "	Se.`id_salle` = "+ id + 
                                "    AND S.`date` = '"+ _Date +"'\n" +
                                "    AND (\n" +
                                "        ('"+ _Debut +"' >= S.`heure_debut` AND '"+ _Debut +"' <= S.`heure_fin`)\n" +
                                "        OR ('"+ _Fin +"' >= S.`heure_debut` AND '"+ _Fin +"' <= S.`heure_fin`)\n" +
                                "        OR ('"+ _Debut +"' <= S.`heure_debut` AND '"+ _Fin +"' >= S.`heure_fin`)\n" +
                                "    )" + 
                                "    AND S.`etat` = 1 ";
            if(_SeanceExceptionID > 0) {
                sqlQuery += "AND S.`id` != " + _SeanceExceptionID + ";"; 
            } else {
                sqlQuery += ";";
            }
            
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
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
    
    
    /**
     * Renvoie les cours (matières) d'un un enseignant
     * @param idProf (int) id de l'enseignant dont on cherche les matieres
     * @return ArrayList(cours) de toutes les matières dispensées par l'enseignants
     */
    public static ArrayList<cours> EnseignantMatieres(int idProf) {
        ArrayList<cours> CoursProf = new ArrayList<cours>();
        try {
            String sqlQuery =   "SELECT C.* FROM `enseignant` AS E\n" +
                                "JOIN `cours` AS C\n" +
                                "    ON C.`id` = E.`id_cours`\n" +
                                "WHERE `id_utilisateur` = " + idProf + ";";
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
            }
            else
            {
                while(result.next())
                {
                    cours _tmp = new cours(
                        result.getInt("id"),
                        result.getString("nom")
                    );
                    CoursProf.add(0, _tmp);
                }
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        
        return CoursProf;
    }
    
    /**
     * Récupere toutes les séances dans l'état "validé" d'un utilisateur
     * @param id (int) id de l'utilisateur
     * @return ArrayListe(seance) de tous les cours correspondant au critères, classé par ordre chronologique
     */
    public static ArrayList<seance> SeanceParUtilisateur(int id) {
        return SeanceParUtilisateur(id, -1, "1");
    }
    /**
     * Récupere toutes les séances dans l'état "validé" d'un utilisateur sur une semaine donnée
     * @param id (int) id de l'utilisateur
     * @param semaine (int) de l'année ou faire la recherche
     * @return ArrayListe(seance) de tous les cours correspondant au critères, classé par ordre chronologique
     */
    public static ArrayList<seance> SeanceParUtilisateur(int id, int semaine) {
        return SeanceParUtilisateur(id, -1, "1");
    }
    /**
     * Récupere toutes les séances d'un utilisateur sur une semaine et un (des) état(s) donné(s)
     * @param id (int) id de l'utilisateur
     * @param semaine (int) de l'année ou faire la recherche
     * @param etat (String) des états possibles acceptables. Choisir entre ["0", "1", "2", "01", "02", "12" ou n'importe quoi d'autre pour tous les etats]
     * @return ArrayListe(seance) de tous les cours correspondant au critères, classé par ordre chronologique
     */
    public static ArrayList<seance> SeanceParUtilisateur(int id, int semaine, String etat) {
        String sqlQuery =   "SELECT S.`id` FROM `utilisateur` AS U\n" +
                            "LEFT JOIN `etudiant` AS E \n" +
                            "	ON E.`id_utilisateur` = U.`id`\n" +
                            "LEFT JOIN `seance_groupes` AS Sg\n" +
                            "	ON Sg.`id_groupe` = E.`id_groupe`\n" +
                            "LEFT JOIN `seance_enseignants` AS Se\n" +
                            "	ON Se.`id_enseignant` = U.`id`\n" +
                            "LEFT JOIN `seance` AS S\n" +
                            "	ON Sg.`id_seance` = S.`id` OR Se.`id_seance` = S.`id`\n" +
                            "WHERE\n" +
                            "	U.`id` = "+ id + " \n";
        if(semaine >= 0) 
            sqlQuery += "   AND S.`semaine` = "+ semaine + " ";
        
        switch(etat) {
            case "0":  {
                sqlQuery += "AND S.`etat` = 0 ";
                break;
            }
            case "1": {
                sqlQuery += "AND S.`etat` = 1 ";
                break;
            }
            case "2": {
                sqlQuery += "AND S.`etat` = 2 ";
                break;
            }
            case "01":
            case "10": {
                sqlQuery += "AND (S.`etat` = 0 OR S.`etat` = 1) ";
                break;
            }
            case "02":
            case "20": {
                sqlQuery += "AND (S.`etat` = 0 OR S.`etat` = 2) ";
                break;
            }
            case "12":
            case "21": {
                sqlQuery += "AND (S.`etat` = 1 OR S.`etat` = 2) ";
                break;
            }
        }
        sqlQuery += " ORDER BY S.`date` ASC, S.`heure_debut` ASC;";
        return ModeleSQL.SeancePar(sqlQuery);
    }
    
    
    /**
     * Récupere toutes les séances dans l'état "validé" d'un groupe
     * @param id (int) id de du groupe
     * @return ArrayListe(seance) de tous les cours correspondant au critères, classé par ordre chronologique
     */
    public static ArrayList<seance> SeanceParGroupe(int id) {
        return SeanceParGroupe(id, -1, "1");
    }
    /**
     * Récupere toutes les séances dans l'état "validé" d'un groupe sur une semaine donnée
     * @param id (int) id de du groupe
     * @param semaine (int) de l'année ou faire la recherche
     * @return ArrayListe(seance) de tous les cours correspondant au critères, classé par ordre chronologique
     */
    public static ArrayList<seance> SeanceParGroupe(int id, int semaine) {
        return SeanceParGroupe(id, -1, "1");
    }
    /**
     * Récupere toutes les séances d'un groupe sur une semaine et un (des) état(s) donné(s)
     * @param id (int) id de du groupe
     * @param semaine (int) de l'année ou faire la recherche
     * @param etat (String) des états possibles acceptables. Choisir entre ["0", "1", "2", "01", "02", "12" ou n'importe quoi d'autre pour tous les etats]
     * @return ArrayListe(seance) de tous les cours correspondant au critères, classé par ordre chronologique
     */
    public static ArrayList<seance> SeanceParGroupe(int id, int semaine, String etat) {
        String sqlQuery =   "SELECT S.`id` FROM `seance` AS S\n" +
                            "JOIN `seance_groupes` AS Sg\n" +
                            "	ON S.`id` = Sg.`id_seance`\n" +
                            "WHERE \n" +
                            "	Sg.`id_groupe` = "+ id + " \n";

        if(semaine >= 0) 
            sqlQuery += "   AND S.`semaine` = "+ semaine + " ";
        
        switch(etat) {
            case "0":  {
                sqlQuery += "AND S.`etat` = 0 ";
                break;
            }
            case "1": {
                sqlQuery += "AND S.`etat` = 1 ";
                break;
            }
            case "2": {
                sqlQuery += "AND S.`etat` = 2 ";
                break;
            }
            case "01":
            case "10": {
                sqlQuery += "AND (S.`etat` = 0 OR S.`etat` = 1) ";
                break;
            }
            case "02":
            case "20": {
                sqlQuery += "AND (S.`etat` = 0 OR S.`etat` = 2) ";
                break;
            }
            case "12":
            case "21": {
                sqlQuery += "AND (S.`etat` = 1 OR S.`etat` = 2) ";
                break;
            }
        }
        sqlQuery += " ORDER BY S.`date` ASC, S.`heure_debut` ASC;";
        
        return ModeleSQL.SeancePar(sqlQuery);
    }
    
    /**
     * Récupere toutes les séances dans l'état "validé" qui ont lieu dans une salle
     * @param id (int) id de la salle
     * @return ArrayListe(seance) de tous les cours correspondant au critères, classé par ordre chronologique
     */
    public static ArrayList<seance> SeanceParSalle(int id) {
        return SeanceParSalle(id, -1, "1");
    }
    /**
     * Récupere toutes les séances dans l'état "validé" qui ont lieu dans une salle sur une semaine donnée
     * @param id (int) id de la salle
     * @param semaine (int) de l'année ou faire la recherche
     * @return ArrayListe(seance) de tous les cours correspondant au critères, classé par ordre chronologique
     */
    public static ArrayList<seance> SeanceParSalle(int id, int semaine) {
        return SeanceParSalle(id, -1, "1");
    }
    /**
     * Récupere toutes les séances qui ont lieu dans une salle sur une semaine et un (des) état(s) donné(s)
     * @param id (int) id de la salle
     * @param semaine (int) de l'année ou faire la recherche
     * @param etat (String) des états possibles acceptables. Choisir entre ["0", "1", "2", "01", "02", "12" ou n'importe quoi d'autre pour tous les etats]
     * @return ArrayListe(seance) de tous les cours correspondant au critères, classé par ordre chronologique
     */
    public static ArrayList<seance> SeanceParSalle(int id, int semaine, String etat) {
        String sqlQuery =   "SELECT S.`id` FROM `seance` AS S\n" +
                            "JOIN `seance_salles` AS Ss\n" +
                            "	ON S.`id` = Ss.`id_seance`\n" +
                            "WHERE \n" +
                            "	Ss.`id_salle` = "+ id + " \n";
        if(semaine >= 0) 
            sqlQuery += "   AND S.`semaine` = "+ semaine + " ";
        
        switch(etat) {
            case "0":  {
                sqlQuery += "AND S.`etat` = 0 ";
                break;
            }
            case "1": {
                sqlQuery += "AND S.`etat` = 1 ";
                break;
            }
            case "2": {
                sqlQuery += "AND S.`etat` = 2 ";
                break;
            }
            case "01":
            case "10": {
                sqlQuery += "AND (S.`etat` = 0 OR S.`etat` = 1) ";
                break;
            }
            case "02":
            case "20": {
                sqlQuery += "AND (S.`etat` = 0 OR S.`etat` = 2) ";
                break;
            }
            case "12":
            case "21": {
                sqlQuery += "AND (S.`etat` = 1 OR S.`etat` = 2) ";
                break;
            }
        }
        
        
        sqlQuery += " ORDER BY S.`date` ASC, S.`heure_debut` ASC;";
        
        return ModeleSQL.SeancePar(sqlQuery);
    }
    
    /**
     * Prend en parametres une requete SQL select sur la table Seance, et renvoie un Arraylist de toutes les séances renvoyées par la requete.
     * @param sqlQuery requete SQL de SELECT sur seance
     * @return ArrayList(seance) des seances renvoyees par la requete
     */
    private static ArrayList<seance> SeancePar(String sqlQuery) {
        ArrayList<seance> seanceList = new ArrayList<seance>();
        try {
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
            }
            else
            {
                while(result.next())
                {
                    seanceList.add(ModeleSQL.getSeance(result.getInt("id")));
                }
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        
        return seanceList;
    }
    
    /**
     * Insere une seance recue en parametres dans la BDD
     * @param s (seance) a inserer.
     */
    public static void InsererSeance (seance s) {
        
        int seanceID = 0;
        try {
            String sqlQuery =   "SELECT `id` FROM `seance` ORDER BY `id` DESC LIMIT 1;";
            
            //System.out.println(sqlQuery);
            
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) > 0) {
                result.next();
                seanceID = result.getInt("id");
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD - Récupération du dernier ID : " + e);
            System.exit(1);
        }
        seanceID += 1;
        
        try {
            String sqlQuery =   "INSERT INTO `seance`(`id`, `semaine`, `date`, `heure_debut`, `heure_fin`, `etat`, `id_cours`, `id_type`) " + 
                                "VALUES ("+ seanceID +", "+ s.getSemaine() +", '"+ s.getDate().DBReady() +"', '"+ s.getDebut().DBReady() +"', '" + 
                                s.getFin().DBReady() +"', "+ 0 +", "+ s.getCours().getID() +", "+ s.getType().getID() +");";
            
            //System.out.println(sqlQuery);
            
            ModeleSQL monModele = new ModeleSQL();
            monModele.queryUpdate(sqlQuery);
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD - Insertion de la séance : " + e);
        }
        
        try {
            String sqlQuery =   "INSERT INTO `seance_enseignants`(`id_seance`, `id_enseignant`) VALUES ";
            
            for(int i=0; i<s.getEnseignants().size(); i++) {
                if(i>0) sqlQuery += ", ";
                sqlQuery += "("+ seanceID +", "+ s.getEnseignants().get(i).getID() +")";
            }
            sqlQuery += ";";
            //System.out.println(sqlQuery);

            ModeleSQL monModele = new ModeleSQL();
            monModele.queryUpdate(sqlQuery);
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD - Insertion des enseignants: " + e);
        }
        
        try {
            String sqlQuery =   "INSERT INTO `seance_groupes`(`id_seance`, `id_groupe`) VALUES ";
            
            for(int i=0; i<s.getGroupes().size(); i++) {
                if(i>0) sqlQuery += ", ";
                sqlQuery += "("+ seanceID +", "+ s.getGroupes().get(i).getID() +")";
            }
            sqlQuery += ";";
            //System.out.println(sqlQuery);

            ModeleSQL monModele = new ModeleSQL();
            monModele.queryUpdate(sqlQuery);
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD - Insertion des groupes: " + e);
        }
        
        try {
            String sqlQuery =   "INSERT INTO `seance_salles`(`id_seance`, `id_salle`) VALUES ";
            
            for(int i=0; i<s.getSalles().size(); i++) {
                if(i>0) sqlQuery += ", ";
                sqlQuery += "("+ seanceID +", "+ s.getSalles().get(i).getID() +")";
            }
            sqlQuery += ";";
            //System.out.println(sqlQuery);

            ModeleSQL monModele = new ModeleSQL();
            monModele.queryUpdate(sqlQuery);
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD - Insertion des salles: " + e);
        }
    }
    
    /**
     * Update une séance recue en parametres dans la BDD
     * @param s (seance) à modifier 
     */
    public static void ChangerSeance (seance s) {
        
        if (s.getID() <= 0) {
            //throw erreur
        }
        else {
            try {
                String sqlQuery =   "UPDATE `seance` SET " + 
                                        "`semaine`="+ s.getSemaine() +",`date`='"+ s.getDate().DBReady() +"',`heure_debut`='"+ s.getDebut().DBReady() +"', " +
                                        "`heure_fin`='"+ s.getFin().DBReady() +"',`etat`="+ 0 +",`id_cours`="+ s.getCours().getID() +",`id_type`="+ s.getType().getID() +" " + 
                                    "WHERE `id`="+ s.getID() +";";

                System.out.println(sqlQuery);

                ModeleSQL monModele = new ModeleSQL();
                monModele.queryUpdate(sqlQuery);
            } catch (SQLException | ClassNotFoundException e){
                System.out.println("Erreur de connection à la BDD - Insertion de la séance : " + e);
            }
            
            
            
            try {
                String sqlQuery =   "DELETE FROM `seance_enseignants` WHERE `id_seance` = "+ s.getID() +";";

                ModeleSQL monModele = new ModeleSQL();
                monModele.queryUpdate(sqlQuery);
            } catch (SQLException | ClassNotFoundException e){
                System.out.println("Erreur de connection à la BDD - Insertion de la séance : " + e);
            }
            try {
                String sqlQuery =   "INSERT INTO `seance_enseignants`(`id_seance`, `id_enseignant`) VALUES ";

                for(int i=0; i<s.getEnseignants().size(); i++) {
                    if(i>0) sqlQuery += ", ";
                    sqlQuery += "("+ s.getID() +", "+ s.getEnseignants().get(i).getID() +")";
                }
                sqlQuery += ";";

                ModeleSQL monModele = new ModeleSQL();
                monModele.queryUpdate(sqlQuery);
            } catch (SQLException | ClassNotFoundException e){
                System.out.println("Erreur de connection à la BDD - Insertion des enseignants: " + e);
            }
            
            
            try {
                String sqlQuery =   "DELETE FROM `seance_groupes` WHERE `id_seance` = "+ s.getID() +";";

                ModeleSQL monModele = new ModeleSQL();
                monModele.queryUpdate(sqlQuery);
            } catch (SQLException | ClassNotFoundException e){
                System.out.println("Erreur de connection à la BDD - Insertion de la séance : " + e);
            }
            try {
                String sqlQuery =   "INSERT INTO `seance_groupes`(`id_seance`, `id_groupe`) VALUES ";

                for(int i=0; i<s.getGroupes().size(); i++) {
                    if(i>0) sqlQuery += ", ";
                    sqlQuery += "("+ s.getID() +", "+ s.getGroupes().get(i).getID() +")";
                }
                sqlQuery += ";";
                //System.out.println(sqlQuery);

                ModeleSQL monModele = new ModeleSQL();
                monModele.queryUpdate(sqlQuery);
            } catch (SQLException | ClassNotFoundException e){
                System.out.println("Erreur de connection à la BDD - Insertion des groupes: " + e);
            }
            
            
            try {
                String sqlQuery =   "DELETE FROM `seance_salles` WHERE `id_seance` = "+ s.getID() +";";

                ModeleSQL monModele = new ModeleSQL();
                monModele.queryUpdate(sqlQuery);
            } catch (SQLException | ClassNotFoundException e){
                System.out.println("Erreur de connection à la BDD - Insertion de la séance : " + e);
            }
            try {
                String sqlQuery =   "INSERT INTO `seance_salles`(`id_seance`, `id_salle`) VALUES ";

                for(int i=0; i<s.getSalles().size(); i++) {
                    if(i>0) sqlQuery += ", ";
                    sqlQuery += "("+ s.getID()+", "+ s.getSalles().get(i).getID() +")";
                }
                sqlQuery += ";";
                //System.out.println(sqlQuery);

                ModeleSQL monModele = new ModeleSQL();
                monModele.queryUpdate(sqlQuery);
            } catch (SQLException | ClassNotFoundException e){
                System.out.println("Erreur de connection à la BDD - Insertion des salles: " + e);
            }
        }
    }
    
    /**
     * Change l'état de la séance passée en parametres 
     * @param _id (int) id de la séance a update
     * @param _etat (int) etat a mettre
     */
    public static void updateEtatSeance(int _id, int _etat) {
        try {
            String sqlQuery =   "UPDATE `seance` SET `etat` = "+ _etat +" WHERE `id`= "+ _id +";";

            System.out.println(sqlQuery);

            ModeleSQL monModele = new ModeleSQL();
            monModele.queryUpdate(sqlQuery);
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD - Edition de l'état de la seance : " + e);
        }
    }
    
    
    public static ArrayList<recapitulatif> recapEnseignant(int idEnseignant) {
        ArrayList<recapitulatif> _return = new ArrayList<recapitulatif>();
        try {
            String sqlQuery = ""+
                "SELECT\n" +
                "C.`id` AS \"coursID\",\n" +
                "COUNT(*) AS \"NbrSeance\",\n" +
                "(\n" +
                "	SELECT COUNT(*) FROM `enseignant`\n" +
                "	JOIN (\n" +
                "		SELECT * FROM `seance` \n" +
                "		JOIN `seance_enseignants`\n" +
                "			ON `seance_enseignants`.`id_seance` = `seance` .`id`\n" +
                "		WHERE seance.`etat` = 1\n" +
                "	) AS tmpSeance\n" +
                "		ON tmpSeance.`id_enseignant` = `enseignant`.`id_utilisateur` AND tmpSeance.`id_cours` = `enseignant`.`id_cours`\n" +
                "	JOIN `cours`\n" +
                "		ON `cours`.`id` = tmpSeance.`id_cours`\n" +
                "	WHERE \n" +
                "		`enseignant`.`id_utilisateur` = E.`id_utilisateur`\n" +
                "		AND tmpSeance.`id_cours` = C.`id`\n" +
                "		AND (\n" +
                "			tmpSeance.`date` < CURRENT_DATE() \n" +
                "			OR (\n" +
                "				tmpSeance.`date` = CURRENT_DATE() \n" +
                "				AND tmpSeance.`heure_debut` < CURRENT_TIME()\n" +
                "			)\n" +
                "		)\n" +
                ") AS \"NbrPassee\",\n" +
                "(\n" +
                "	SELECT SEC_TO_TIME(SUM(\n" +
                "		CAST(SECOND(TIMEDIFF(`heure_fin`, `heure_debut`)) AS INT) + \n" +
                "		60*CAST(MINUTE(TIMEDIFF(`heure_fin`, `heure_debut`)) AS INT) + \n" +
                "		3600*CAST(HOUR(TIMEDIFF(`heure_fin`, `heure_debut`)) AS INT)\n" +
                "	)) AS \"DUREE\" \n" +
                "	FROM `seance` \n" +
                "	JOIN `seance_enseignants`\n" +
                "		ON `seance_enseignants`.`id_seance` = `seance`.`id`\n" +
                "	WHERE `seance`.`etat` = 1 AND`seance`.`id_cours` = E.`id_cours` AND `seance_enseignants`.`id_enseignant` = E.`id_utilisateur`\n" +
                ") AS \"Duree\",\n" +
                "(\n" +
                "	SELECT `seance`.`id` \n" +
                "	FROM `seance` \n" +
                "	JOIN `seance_enseignants` \n" +
                "		ON `seance_enseignants`.`id_seance` = `seance`.`id` \n" +
                "	WHERE \n" +
                "		`seance`.`id_cours` = E.`id_cours`\n" +
                "		AND `seance_enseignants`.`id_enseignant` = E.`id_utilisateur`\n" +
                "	ORDER BY \n" +
                "		`seance`.`semaine` ASC, \n" +
                "		`seance`.`heure_debut` ASC \n" +
                "	LIMIT 1\n" +
                ") AS \"PremiereSeanceID\",\n" +
                "(\n" +
                "	SELECT `seance`.`id` \n" +
                "	FROM `seance` \n" +
                "	JOIN `seance_enseignants` \n" +
                "		ON `seance_enseignants`.`id_seance` = `seance`.`id` \n" +
                "	WHERE \n" +
                "		`seance`.`id_cours` = E.`id_cours` \n" +
                "		AND `seance_enseignants`.`id_enseignant` = E.`id_utilisateur`\n" +
                "	ORDER BY \n" +
                "		`seance`.`semaine` DESC, \n" +
                "		`seance`.`heure_debut` DESC \n" +
                "	LIMIT 1\n" +
                ") AS \"DerniereSeanceID\"\n" +
                "FROM `enseignant` AS E\n" +
                "JOIN (\n" +
                "	SELECT * FROM `seance` AS S\n" +
                "	JOIN `seance_enseignants` AS Se\n" +
                "		ON Se.`id_seance` = S.`id`\n" +
                "	WHERE S.`etat` = 1\n" +
                ") AS S\n" +
                "	ON S.`id_enseignant` = E.`id_utilisateur` AND S.`id_cours` = E.`id_cours`\n" +
                "JOIN `cours` AS C\n" +
                "	ON C.`id` = S.`id_cours`\n" +
                "\n" +
                "WHERE E.`id_utilisateur` = "+ idEnseignant +"\n" +
                "GROUP BY E.`id_cours`\n";
                  
                    
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
                
            }
            else
            {
                while(result.next()){
                    cours _tmpCours = getCours(result.getInt("coursID"));
                    seance _tmpPremiere = getSeance(result.getInt("PremiereSeanceID"));
                    seance _tmpDerniere = getSeance(result.getInt("DerniereSeanceID"));
                    
                    recapitulatif _recap = new recapitulatif(
                        _tmpCours,
                        result.getInt("NbrSeance"),
                        result.getInt("NbrPassee"),
                        result.getString("Duree"),
                        _tmpPremiere,
                        _tmpDerniere
                    );
                     _return.add(_recap);
                }
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        return _return;
    }
    
    
    
    public static ArrayList<recapitulatif> recapEtudiant(int idGroupe) {
        ArrayList<recapitulatif> _return = new ArrayList<recapitulatif>();
        try {
            String sqlQuery = ""+
                "SELECT\n" +
                "C.`id` AS \"coursID\",\n" +
                "COUNT(*) AS \"NbrSeance\",\n" +
                "(\n" +
                "	SELECT SEC_TO_TIME(SUM(\n" +
                "		CAST(SECOND(TIMEDIFF(`heure_fin`, `heure_debut`)) AS INT) + \n" +
                "		60*CAST(MINUTE(TIMEDIFF(`heure_fin`, `heure_debut`)) AS INT) + \n" +
                "		3600*CAST(HOUR(TIMEDIFF(`heure_fin`, `heure_debut`)) AS INT)\n" +
                "	)) AS \"DUREE\" \n" +
                "	FROM `seance` \n" +
                "	JOIN `seance_groupes`\n" +
                "		ON `seance_groupes`.`id_seance` = `seance`.`id`\n" +
                "	WHERE `seance`.`etat` = 1 AND`seance`.`id_cours` = G.`id_cours` AND `seance_groupes`.`id_groupe` = G.`id_groupe`\n" +
                ") AS \"Duree\",\n" +
                "(\n" +
                "	SELECT `seance`.`id` \n" +
                "	FROM `seance` \n" +
                "	JOIN `seance_groupes` \n" +
                "		ON `seance_groupes`.`id_seance` = `seance`.`id` \n" +
                "	WHERE \n" +
                "		`seance`.`id_cours` = G.`id_cours`\n" +
                "		AND `seance_groupes`.`id_groupe` = G.`id_groupe`\n" +
                "	ORDER BY \n" +
                "		`seance`.`semaine` ASC, \n" +
                "		`seance`.`heure_debut` ASC \n" +
                "	LIMIT 1\n" +
                ") AS \"PremiereSeanceID\",\n" +
                "(\n" +
                "	SELECT `seance`.`id` \n" +
                "	FROM `seance` \n" +
                "	JOIN `seance_groupes` \n" +
                "		ON `seance_groupes`.`id_seance` = `seance`.`id` \n" +
                "	WHERE \n" +
                "		`seance`.`id_cours` = G.`id_cours` \n" +
                "		AND `seance_groupes`.`id_groupe` = G.`id_groupe`\n" +
                "	ORDER BY \n" +
                "		`seance`.`semaine` DESC, \n" +
                "		`seance`.`heure_debut` DESC \n" +
                "	LIMIT 1\n" +
                ") AS \"DerniereSeanceID\"\n" +
                "FROM (\n" +
                "	SELECT DISTINCT `seance_groupes`.`id_groupe`, `seance`.`id_cours` FROM `seance_groupes`\n" +
                "	JOIN `seance`\n" +
                "		ON `seance`.`id` = `seance_groupes`.`id_seance`\n" +
                "	WHERE `seance`.`etat` = 1\n" +
                ") AS G\n" +
                "JOIN (\n" +
                "	SELECT * FROM `seance` AS S\n" +
                "	JOIN `seance_groupes` AS Se\n" +
                "		ON Se.`id_seance` = S.`id`\n" +
                ") AS S\n" +
                "	ON S.`id_groupe` = G.`id_groupe` AND S.`id_cours` = G.`id_cours`\n" +
                "JOIN `cours` AS C\n" +
                "	ON C.`id` = S.`id_cours`\n" +
                "\n" +
                "WHERE G.`id_groupe` = "+ idGroupe +"\n" +
                "GROUP BY G.`id_cours`";
                    
                    
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
                
            }
            else
            {
                while(result.next()){
                    cours _tmpCours = getCours(result.getInt("coursID"));
                    seance _tmpPremiere = getSeance(result.getInt("PremiereSeanceID"));
                    seance _tmpDerniere = getSeance(result.getInt("DerniereSeanceID"));
                    
                    recapitulatif _recap = new recapitulatif(
                        _tmpCours,
                        result.getInt("NbrSeance"),
                        -1,
                        result.getString("Duree"),
                        _tmpPremiere,
                        _tmpDerniere
                    );
                     _return.add(_recap);
                }
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        return _return;
    }
    
    
    
    
    //----------------------------------------------------------------------------------------------------------------------------------
    
    /**
     * Récupere toutes les salles d'une capacitee minimum sur une plage de temps donnée et les renvoie dans un ArrayList(salle)
     * @deprecated 
     * @param _capaciteMin (int) capacitee minimum de la classe
     * @param _Date (customDate) date à laquelle on cherche
     * @param _Debut (customDate) créneaux horaire de début
     * @param _Fin (customDate) créneaux horaire de fin
     * @return ArrayList(salle)
     */
    public static ArrayList<salle> AvailableClass(int _capaciteMin, customDate _Date, customDate _Debut, customDate _Fin) {
        return AvailableClass(_capaciteMin, _Date.DBReady(), _Debut.DBReady(), _Fin.DBReady());
    }
    /**
     * Récupere toutes les salles d'une capacitee minimum sur une plage de temps donnée et les renvoie dans un ArrayList(salle)
     * @deprecated 
     * @param _capaciteMin (int) capacitee minimum de la classe
     * @param _Date (String) date à laquelle on cherche sous format "2000-12-31"
     * @param _Debut (String) créneaux horaire de début sous format "23:59:59"
     * @param _Fin (String) créneaux horaire de fin sous format "23:59:29"
     * @return ArrayList(salle)
     */
    public static ArrayList<salle> AvailableClass(int _capaciteMin, String _Date, String _Debut, String _Fin) {
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
                                "	S.`date` = '"+ _Date +"'\n" +
                                "	AND (\n" +
                                "		('"+ _Debut +"' >= S.`heure_debut` AND '"+ _Debut +"' <= S.`heure_fin`)\n" +
                                "		OR ('"+ _Fin +"' >= S.`heure_debut` AND '"+ _Fin +"' <= S.`heure_fin`)\n" +
                                "		OR ('"+ _Debut +"' <= S.`heure_debut` AND '"+ _Fin +"' >= S.`heure_fin`)\n" +
                                "	)\n" +
                                ")\n" +
                                "AND `capacite` > "+ _capaciteMin +"\n" +
                                "ORDER BY `capacite` DESC;";
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
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
    
    /**
     * Récupere toutes les enseignants qui enseignent une matiere sur une plage de temps donnée et les renvoie dans un ArrayList(utilisateur)
     * @deprecated 
     * @param _matiere (int) id du cours (matiere) enseigné
     * @param _Date (String) date à laquelle on cherche
     * @param _Debut (String) créneaux horaire de début
     * @param _Fin (String) créneaux horaire de fin
     * @return ArrayList(utilisateur)
     */
    public static ArrayList<utilisateur> AvailableProf(int _matiere, customDate _Date, customDate _Debut, customDate _Fin) {
        return AvailableProf(_matiere, _Date.DBReady(), _Debut.DBReady(), _Fin.DBReady());
    }
    /**
     * Récupere toutes les enseignants qui enseignent une matiere sur une plage de temps donnée et les renvoie dans un ArrayList(utilisateur)
     * @deprecated 
     * @param _matiere (int) id du cours (matiere) enseigné
     * @param _Date (String) date à laquelle on cherche sous format "2000-12-31"
     * @param _Debut (String) créneaux horaire de début sous format "23:59:59"
     * @param _Fin (String) créneaux horaire de fin sous format "23:59:29"
     * @return ArrayList(utilisateur)
     */
    public static ArrayList<utilisateur> AvailableProf(int _matiere, String _Date, String _Debut, String _Fin) {
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
                                "    S.`date` = '"+ _Date +"'\n" +
                                "    AND (\n" +
                                "        ('"+ _Debut +"' >= S.`heure_debut` AND '"+ _Debut +"' <= S.`heure_fin`)\n" +
                                "        OR ('"+ _Fin +"' >= S.`heure_debut` AND '"+ _Fin +"' <= S.`heure_fin`)\n" +
                                "        OR ('"+ _Debut +"' <= S.`heure_debut` AND '"+ _Fin +"' >= S.`heure_fin`)\n" +
                                "    )\n" +
                                ")\n" +
                                "AND ES.`id_cours` = "+ _matiere +";";
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) <= 0) {
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
    
    /**
     * Recupere le nombre totals d'étudiants dans une ArrayList(groupe) passé en parametres
     * @deprecated 
     * @param groupes ArrayList(groupe)
     * @return (int)
     */
    public static int GroupeTotalEtudiant(ArrayList<groupe> groupes) {

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
            
            ModeleSQL monModele = new ModeleSQL();
            ResultSet result = monModele.query(sqlQuery);
            
            if(monModele.getRowCount(result) >= 0) {
                result.next();
                _return = result.getInt("Total");
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Erreur de connection à la BDD: " + e);
        }
        return _return;
    }
}
