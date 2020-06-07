package Modele;

/**
 * Classe etudiant
 * @author KASYC Cyrille
 * @author LECOEUR Titouan
 * @author RASSOUW Clement
 */
public class etudiant extends utilisateur {
    private int numeroEtudiant;
    private groupe Groupe;
    
    /**
     * Créer un étudiant a partir d'un utilisateur, un groupe et un numéro d'étudiant
     * @param _tmpUtilisateur utilisateur
     * @param _numeroEtudiant int
     * @param _Groupe groupe
     */
    public etudiant(utilisateur _tmpUtilisateur, int _numeroEtudiant, groupe _Groupe) {
        super(_tmpUtilisateur);
        this.numeroEtudiant = _numeroEtudiant;
    }
    
    /**
     * Créer un étudiant a partir des informations passées en parametres
     * @param _id (int)
     * @param _email (String)
     * @param _nom (String)
     * @param _prenom (String)
     * @param _password (String)
     * @param _droits (int)
     * @param _numeroEtudiant (int)
     * @param _Groupe (groupe)
     */
    public etudiant(int _id, String _email, String _nom, String _prenom, String _password, int _droits, int _numeroEtudiant, groupe _Groupe) {
        super(_id, _email, _nom, _prenom, _password, _droits);
        this.numeroEtudiant = _numeroEtudiant;
        this.Groupe = _Groupe;
    }
    
    /**
     * Créer un étudiant a partir de son ID en allant récupérer les informations dans la BDD
     * @param id (int)
     */
    public etudiant(int id) {
        this(ModeleSQL.getEtudiant(id));
    }
    
    /**
     * Copie l'étudiant passé en parametre dans celui actuel
     * @param _tmpEtudiant (etudiant)
     */
    public etudiant (etudiant _tmpEtudiant) {
        super(_tmpEtudiant.getID(),
              _tmpEtudiant.getEMail(),
              _tmpEtudiant.getNom(),
              _tmpEtudiant.getPrenom(),
              _tmpEtudiant.getPassword(),
              _tmpEtudiant.getDroits());
        
        this.numeroEtudiant = _tmpEtudiant.getNumeroEtudiant();
        this.Groupe = _tmpEtudiant.getGroupe();
    }
    
    /**
     * 
     * @return numéro d'étudiant
     */
    public int getNumeroEtudiant () { return this.numeroEtudiant; }
    /**
     * 
     * @return groupe de l'étudiant
     */
    public groupe getGroupe () { return this.Groupe; }
    
    /**
     * 
     * @return Transforme la classe en string pour l'afficher
     */
    @Override
    public String toString() {
        return "[" + id + ", " + nom + ", " + prenom + ", " + email + ", " + droits + ", " + numeroEtudiant + ", " + Groupe + "]";
    }
}
