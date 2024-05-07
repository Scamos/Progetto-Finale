import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog {
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnOK;
    private JButton btnCancel;
    private JPanel loginPanel;
    private JButton btnRegister;

    //Inizializza il Panel Login
    public LoginForm(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //Creo un listener sul bottone OK che aspetta e controlla se qualcuno lo preme
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Prende i valori inseriti nei campi email e password
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                //Prende i valori e li passa per il metodo che controlla se user è autenticato
                user = getAuthenticatedUser(email, password);

                //Controllo se non sono vuoti
                if (user != null){
                    dispose(); //"Pulisco" ciò che ho scritto
                }
                else{
                    //Questo avviene ad un errore nel inserimento della email o della password
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or Password invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //Creo un listener sul bottone Cancel che aspetta e controlla se qualcuno lo preme
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); //"Pulisco" ciò che ho scritto
            }
        });
        //Creo un listener sul bottone Register che aspetta e controlla se qualcuno lo preme
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Creo una variabile per passare un RegistrationForm al click del pulsante
                RegistrationForm registrationForm = new RegistrationForm(DashboardForm.this,
                        "New user: " + user.name,
                        "Successful Registration",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        setVisible(true);
    }

    public User user;
    //Creo il metodo per controllare se l'user è autenticato
    private User getAuthenticatedUser(String email, String password){
        User user = null;

        //Mi connetto al database
        //Informazioni del Database
        final String DB_URL = "jdbc:mysql://localhost:3306/tebellautenti";
        final String USERNAME = "samu";
        final String PASSWORD = "samu123";

        //Provo la connessione
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //Connesso al database con successo...

            //Seleziono dalla tabella users le email e password
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            //Lo faccio per ogni user presente
            if(resultSet.next()){
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.password = resultSet.getString("password");
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

    //Metodo principale che genera il Login Form, prende user,
    //controlla se non è vuoto e dà un risultato a seconda
    //di come va questo controllo
    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm (null);
        User user = loginForm.user;
        if(user != null){
            System.out.println("Successful Authentication of: " + user.name);
            System.out.println("Email: " + user.email);
        }
        else{
            System.out.println("Authentication canceled");
        }
    }
}
