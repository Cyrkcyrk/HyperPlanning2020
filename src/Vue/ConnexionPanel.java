package Vue;

import Controlleur.Controlleur;
import Modele.ModeleSQL;
import Modele.utilisateur;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Créer le pannel qui permet de s'identifier
 * @author KASYC Cyrille
 * @author LECOEUR Titouan
 * @author RASSOUW Clement
 */
public class ConnexionPanel extends JPanel{
    
    Controlleur monControlleur;
    /**
     * Créer un JPanel pour se connecter
     * @param _ctrleur (Controlleur)
     */
    public ConnexionPanel(Controlleur _ctrleur) {
        
        monControlleur = _ctrleur;

        JTextField UserNameTextField = new JTextField();
        UserNameTextField.setColumns(10);
        UserNameTextField.setMaximumSize(new Dimension(300,26));

        JPasswordField PasswordField = new JPasswordField();
        PasswordField.setColumns(10);
        PasswordField.setMaximumSize(new Dimension(300,26));

        JLabel lblUsername = new JLabel("Username");
        JLabel lblPassword = new JLabel("Password");
        
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = UserNameTextField.getText();
                String password = PasswordField.getText();
                
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
        
        
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        
        //layout.createParallelGroup()
        //layout.createSequentialGroup()
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGap(10)
            .addGroup(layout.createParallelGroup()
                .addComponent(lblUsername)
                .addComponent(lblPassword)
                .addComponent(UserNameTextField)
                .addComponent(PasswordField)
                .addComponent(loginButton)
            )
            
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGap(10)
            .addComponent(lblUsername)
            .addComponent(UserNameTextField)
            .addGap(5)
            .addComponent(lblPassword)
            .addComponent(PasswordField)
            .addGap(10)
            .addComponent(loginButton)
        );
        
    }
}
