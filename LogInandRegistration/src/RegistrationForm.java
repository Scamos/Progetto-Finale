import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RegistrationForm extends JDialog {
    private JTextField tfName;
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirmPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel registerPanel;

    //Inizializza il Panel Registration
    public RegistrationForm(JFrame parent) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //Creo un listener sul bottone Register che aspetta e controlla se qualcuno lo preme
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser(); //Chiama questo metodo
            }
        });
        //Creo un listener sul bottone Cancel che aspetta e controlla se qualcuno lo preme
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); //"Pulisco" ciò che ho scritto
            }
        });

        setVisible(true);
    }

    //Creo il metodo per registrare un nuovo user
    private void registerUser() {
        //Prendo ciò che è stato scritto
        String name = tfName.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirmPassword = String.valueOf(pfConfirmPassword.getPassword());

        //Controllo se è stato lasciato un campo vuoto
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Controllo se il campo password è uguale alla conferma password
        if (!password.equals(confirmPassword)){
            JOptionPane.showMessageDialog(this,
                    "Confirm Password does not match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Inserisco user al database chiamando questo metodo
        user = addUserToDatabase(name, email, password);
        //Controllo se non sono vuoti
        if (user != null){
            dispose(); //"Pulisco" ciò che ho scritto
        }
        else{
            //Nel caso di errori in fase di registrazione
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public User user;
    //Creo il metodo per aggiungere un user al database
    private User addUserToDatabase(String name, String email, String password){
        User user = null;

        //Mi connetto al database
        //Informazioni del Database
        final String DB_URL = "jdbc:mysql://localhost:3306/tebellautenti";
        final String USERNAME = "samu";
        final String PASSWORD = "samu123";

        //Provo la connessione
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //Connesso al database con successo...

            //Seleziono dalla tabella users il nome, la email e la password
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (name, email, password)" + "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            //Inserisco una nuova row nella table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                //Lo faccio nel caso in cui ne sto effettivamente inserendo almeno 1
                user = new User();
                user.name = name;
                user.email = email;
                user.password = password;
            }

            //Chiudo le connessioni
            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        //Ritorno lo user
        return user;
    }

    //Metodo principale che genera il Registration Form, prende user,
    //controlla se non è vuoto e dà un risultato a seconda
    //di come va questo controllo
    public static void main(String[] args) {
        RegistrationForm myForm = new RegistrationForm(null);
        User user = myForm.user;
        if (user != null){
            System.out.println("Successful registration of: " + user.name);
        }
        else{
            System.out.println("Registration canceled");
        }
    }
}
