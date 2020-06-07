package Modele;

/**
 * Classe cours
 * @author KASYC Cyrille
 * @author LECOEUR Titouan
 * @author RASSOUW Clement
 */
public class cours {
    private int id;
    private String nom;
    
    /**
     * Créer un cours (Informatique, Electronique...)
     * @param _id id du type cours dans la BDD
     * @param _type String désignant le type de cours
     */
    public cours(int _id, String _type) {
        this.id = _id;
        this.nom = _type;
    }
    
    /**
     * Créer un cours (Informatique, Electronique...)
     * @param id id du type cours dans la BDD
     */
    public cours(int id) {
        this(ModeleSQL.getCours(id));
    }
    
    /**
     * Copie le cours passé en parametre dans celui là
     * @param _tmpCours Type cours
     */
    public cours (cours _tmpCours) {
        this.id = _tmpCours.getID();
        this.nom = _tmpCours.getNom();
    }
    
    /**
     * 
     * @return id du cours (int)
     */
    public int getID () { return this.id; }
    /**
     * 
     * @return Nom du cours (String)
     */
    public String getNom () { return this.nom; }
    
    /**
     * 
     * @return Transforme la classe en string pour l'afficher
     */
    @Override
    public String toString() {
        return "[" + id + ", " + nom + "]";
    }
}
