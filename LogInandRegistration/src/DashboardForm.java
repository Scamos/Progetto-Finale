import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DashboardForm extends JFrame {
    private JPanel dashboardPanel;
    private JLabel lbAdmin;

    //Inizializza il Panel Dashboard
    public DashboardForm(){
        setTitle("Dashboard");
        setContentPane(dashboardPanel);
        setMinimumSize(new Dimension(500, 429));
        setSize(1200, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Controllo la presenza di User nel Database
        boolean hasRegistredUsers = connectToDatabase();
        if(hasRegistredUsers){ //Nel caso ho User nel database
            //Mostro il Login form
            LoginForm loginForm = new LoginForm(this);
            User user = loginForm.user;

            //Controllo se ho inserito qualcosa dentro user
            if(user != null){
                lbAdmin.setText("User: " + user.name); //La scritta nella schermata mostrerà il nome dell'utente
                setLocationRelativeTo(null);
                setVisible(true);
            }
            else{
                dispose(); //"Pulisco" ciò che ho scritto
            }
        }
        else{ //Nel caso non ho user nel Database
            //Mostro il Registration form
            RegistrationForm registrationForm = new RegistrationForm(this);
            User user = registrationForm.user;

            //Controllo se ho inserito qualcosa dentro user
            if(user != null){
                lbAdmin.setText("User: " + user.name); //La scritta nella schermata mostrerà il nome dell'utente
                setLocationRelativeTo(null);
                setVisible(true);
            }
            else{
                dispose();//"Pulisco" ciò che ho scritto
            }
        }
    }

    //Creo il metodo per connettermi al database
    private boolean connectToDatabase(){
        //Set del boolean
        boolean hasRegistredUsers = false;

        //Informazioni del Database
        final String MYSQL_SERVER_URL = "jdbc:mysql://localhost:3306/";
        final String DB_URL = "jdbc:mysql://localhost:3306/tebellautenti";
        final String USERNAME = "samu";
        final String PASSWORD = "samu123";

        //Provo la connessione
        try{
            //Primo, mi connetto a MySQL server e creo il Database, se non è già stato creato
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS tebellautenti");
            statement.close();
            conn.close();

            //Secondo, mi connetto al Database e creo la table "users" se non è già stata creata
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT ( 10 ) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(200) NOT NULL,"
                    + "email VARCHAR(200) NOT NULL UNIQUE,"
                    + "password VARCHAR(200) NOT NULL"
                    + ")";
            statement.executeUpdate(sql);

            //Controllo se sono presenti users nella table users
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");

            if(resultSet.next()){
                int numUsers = resultSet.getInt(1);
                if(numUsers > 0){
                    hasRegistredUsers = true;
                }
            }

            //Chiudo le connessioni
            statement.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        //Ritorno il boolean
        return hasRegistredUsers;
    }

    //Metodo Main da cui parte tutto, che vuole solamente generare il Dashboard Form
    //Che so già mostrerà il Login Form se ho user nel Database
    //Oppure mostrerà il Registration Form se non ho user nel Database
    public static void main(String[] args) {
        DashboardForm dashboardForm = new DashboardForm();
    }
}
