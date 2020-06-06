package Modele;

/**
 *
 * @author KASYC Cyrille
 * @author LECOEUR Titouan
 * @author RASSOUW Clement
 */
public class groupe {
    private String Nom, Promotion;
    private int id;
    
    /**
     * Créer un groupe a partir des informations passées en parametres
     * @param _id (int)
     * @param _Nom (String)
     * @param _Promotion (String)
     */
    public groupe (int _id, String _Nom, String _Promotion) {
        this.id = _id;
        this.Nom = _Nom;
        this.Promotion = _Promotion;
    }
    
    /**
     * Copie le groupe passé en parametre dans celui actuel.
     * @param _tmpGroupe (groupe)
     */
    public groupe(groupe _tmpGroupe) {
        this.id = _tmpGroupe.getID();
        this.Nom = _tmpGroupe.getNom();
        this.Promotion = _tmpGroupe.getPromotion();
    }
    
    /**
     * Créer un groupe a partir de son ID en allant chercher les informations dans la BDD
     * @param id (int)
     */
    public groupe (int id)
    {
        this(ModeleSQL.getGroupe(id));
    }
    
    /**
     * 
     * @return ID du groupe (int)
     */
    public int getID () { return this.id; }
    
    /**
     * 
     * @return Nom du groupe (TD1, TD2, etc) (String)
     */
    public String getNom () { return this.Nom; }
    
    /**
     * 
     * @return Promotion du groupe (ING1, ING2, etc) (String)
     */
    public String getPromotion () { return this.Promotion; }
    
    /**
     * 
     * @return Transforme la classe en string pour l'afficher
     */
    @Override
    public String toString() {
        return "[" + this.id + ", " + this.Nom + ", " + this.Promotion + "]";
    }
}
