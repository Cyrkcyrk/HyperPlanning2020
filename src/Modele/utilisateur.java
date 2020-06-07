package Modele;

/**
 * Classe utilisateur
 * @author KASYC Cyrille
 * @author LECOEUR Titouan
 * @author RASSOUW Clement
 */
public class utilisateur {
    
    protected int id, droits;
    protected String email, password, nom, prenom;
    
    /**
     * Créer un utilisateur avec ce qui est passé en parametre
     * @param _id (int)
     * @param _email (String)
     * @param _nom (String)
     * @param _prenom (String)
     * @param _password (String)
     * @param _droits (int)
     */
    public utilisateur (int _id, String _email, String _nom, String _prenom, String _password, int _droits) {
        this.id = _id;
        this.nom = _nom;
        this.prenom = _prenom;
        this.email = _email;
        this.password = _password;
        this.droits = _droits;
    }
    
    /**
     * Copie l'utilisateur passé en parametres dans celui actuel
     * @param _tmpUtilisateur (utilisateur)
     */
    public utilisateur(utilisateur _tmpUtilisateur) {
        this.id = _tmpUtilisateur.getID();
        this.nom = _tmpUtilisateur.getNom();
        this.prenom = _tmpUtilisateur.getPrenom();
        this.email = _tmpUtilisateur.getEMail();
        this.password = _tmpUtilisateur.getPassword();
        this.droits = _tmpUtilisateur.getDroits();
    }
    
    /**
     * Créer un utilisateur a partir de son ID en allant chercher les informations dans la BDD
     * @param id (int)
     */
    public utilisateur (int id)
    {
        this(ModeleSQL.getUtilisateur(id)); 
    }
    
    /**
     * 
     * @return id de l'utilisateur (int)
     */
    public int getID () { return this.id; }
    
    /**
     * 
     * @return Nom (String)
     */
    public String getNom () { return this.nom; }
    
    /**
     * 
     * @return Prénom (String)
     */
    public String getPrenom () { return this.prenom; }
    
    /**
     * 
     * @return Mail (String)
     */
    public String getEMail () { return this.email; }
    
    /**
     * 
     * @return Mot de passe (String)
     */
    public String getPassword () { return this.password; }
    
    /**
     * 
     * @return Droits d'affichage (int)
     */
    public int getDroits () { return this.droits; }
    
    /**
     * 
     * @return Transforme la classe en string pour l'afficher
     */
    @Override
    public String toString() {
        return "[" + id + ", " + nom + ", " + prenom + ", " + email + ", " + droits + "]";
    }
    
}
