import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DashboardForm extends JFrame {
    private JPanel dashboardPanel;
    private JLabel lbAdmin;

    public DashboardForm(){
        setTitle("Dashboard");
        setContentPane(dashboardPanel);
        setMinimumSize(new Dimension(500, 429));
        setSize(1200, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        boolean hasRegistredUsers = connectToDatabase();
        if(hasRegistredUsers){
            //show Login form
            LoginForm loginForm = new LoginForm(this);
            User user = loginForm.user;

            if(user != null){
                lbAdmin.setText("User: " + user.name);
                setLocationRelativeTo(null);
                setVisible(true);
            }
            else{
                dispose();
            }
        }
        else{
            //show Registration form
            RegistrationForm registrationForm = new RegistrationForm(this);
            User user = registrationForm.user;

            if(user != null){
                lbAdmin.setText("User: " + user.name);
                setLocationRelativeTo(null);
                setVisible(true);
            }
            else{
                dispose();
            }
        }
    }

    private boolean connectToDatabase(){
        boolean hasRegistredUsers = false;

        final String MYSQL_SERVER_URL = "jdbc:mysql://localhost:3306/";
        final String DB_URL = "jdbc:mysql://localhost:3306/tebellautenti";
        final String USERNAME = "samu";
        final String PASSWORD = "samu123";

        try{
            //First, connect to MySQL server and create the database if not created
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS tebellautenti");
            statement.close();
            conn.close();

            //Second, connect to the database and create the table "users" if not created
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT ( 10 ) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(200) NOT NULL,"
                    + "email VARCHAR(200) NOT NULL UNIQUE,"
                    + "password VARCHAR(200) NOT NULL"
                    + ")";
            statement.executeUpdate(sql);

            //Check if we have users in the table users
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");

            if(resultSet.next()){
                int numUsers = resultSet.getInt(1);
                if(numUsers > 0){
                    hasRegistredUsers = true;
                }
            }

            statement.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return hasRegistredUsers;
    }

    public static void main(String[] args) {
        DashboardForm dashboardForm = new DashboardForm();
    }
}
