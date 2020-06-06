package Vue;

import Controlleur.Controlleur;
import Modele.ModeleSQL;
import Modele.utilisateur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Cyrille
 */
public class ConnexionPanel extends JPanel{
    
    Controlleur monControlleur;
    /**
     * Créer un JPanel pour se connecter
     * @param _ctrleur (Controlleur)
     */
    public ConnexionPanel(Controlleur _ctrleur) {
        
        monControlleur = _ctrleur;
        
        JLabel lblNewLabel = new JLabel("Login");
        this.add(lblNewLabel);

        JTextField textField = new JTextField();
        this.add(textField);
        textField.setColumns(10);

        JPasswordField passwordField = new JPasswordField();
        this.add(passwordField);
        passwordField.setColumns(10);

        JLabel lblUsername = new JLabel("Username");
        this.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        this.add(lblPassword);

        JButton btnNewButton = new JButton("Login");
        btnNewButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String userName = textField.getText();
                String password = passwordField.getText();
                
                utilisateur connectedUser = null;
                
                System.out.println("Username: " + userName + " - password: " + password);
                if(password.isEmpty()) {
                    try{
                        int id = (int) Integer.parseInt(userName);
                        connectedUser = ModeleSQL.getUtilisateur(id);
                        monControlleur.setConnectedUser(connectedUser);
                        
                    } catch (NumberFormatException ex) {
                    }
                }
                else {
                    connectedUser = ModeleSQL.getUtilisateur(userName, password);
                    monControlleur.setConnectedUser(connectedUser);
                }
                if(connectedUser == null) {
                    Controlleur.ShowError("Email ou mot de passe incorrect!");
                }
                else {
                    monControlleur.connected();
                }
            }
        });

        this.add(btnNewButton);
    }
}
