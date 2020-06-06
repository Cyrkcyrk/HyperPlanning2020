package Modele;

/**
 * 
 * @author KASYC Cyrille
 * @author LECOEUR Titouan
 * @author RASSOUW Clement
 */
public class Type_cours {
    private int id;
    private String type;
    
    /**
     * Créer un type de cours (CM, TD, TP...)
     * @param _id id du type cours dans la BDD
     * @param _type String désignant le type de cours
     */
    public Type_cours(int _id, String _type) {
        this.id = _id;
        this.type = _type;
    }
    
    /**
     * Créer un type de cours (CM, TD, TP...)
     * @param id id du type cours dans la BDD=
     */
    public Type_cours(int id) {
        this(ModeleSQL.getTypeCours(id)); 
    }
    
    /**
     * Copie le type cours passé en parametre dans celui actuel.
     * @param _tmpTypeCours (Type_Cours)
     */
    public Type_cours(Type_cours _tmpTypeCours) {
        this.id = _tmpTypeCours.getID();
        this.type = _tmpTypeCours.getType();
    }
    /**
     * @return l'id du type cours
     */
    public int getID() { return this.id; }
    /**
     * @return String du nom
     */
    public String getType() { return this.type; }
    
    /**
     * 
     * @return Transforme la classe en string pour l'afficher
     */
    @Override
    public String toString() {
        return "[" + id + ", " + type + "]";
    }
}
