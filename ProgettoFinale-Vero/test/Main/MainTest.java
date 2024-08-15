package Main;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
/*class MainTest {

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private InputStream originalIn;
    private PrintStream originalOut;
    private ByteArrayInputStream inContent;

    @BeforeEach
    public void setUpStreams() {
        //Salva gli originali System.in e System.out
        originalOut = System.out;
        originalIn = System.in;

        //Ridireziona System.out e System.in
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        inContent = new ByteArrayInputStream("".getBytes());
        System.setIn(inContent);
    }

    @AfterEach
    public void restoreStreams() {
        //Ripristina gli originali System.in e System.out
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void main() {
        //Arrange
        String simulatedInput = "mariorossi@gmail.com\n1234\nn\n"; //Simula input dell'utente
        ByteArrayInputStream newInContent = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(newInContent);

        //Act
        Main.main(new String[]{});

        //Debugging
        System.out.println("Output del test:\n" + outContent.toString());

        //Assert
        String output = outContent.toString();
        assertTrue(output.contains("Benvenuto!"), "Output should contain 'Benvenuto!'");
        assertTrue(output.contains("Inserisci email:"), "Output should contain 'Inserisci email:'");
        assertTrue(output.contains("Inserisci password:"), "Output should contain 'Inserisci password:'");
        assertTrue(output.contains("Vuoi continuare? (s/n)"), "Output should contain 'Vuoi continuare? (s/n)'");
        assertTrue(output.contains("Registrazione avvenuta con successo"), "Output should contain 'Registrazione avvenuta con successo'");
    }
}*/
public class MainTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalSystemOut = System.out;
    private final ByteArrayInputStream inputStreamCaptor = new ByteArrayInputStream("".getBytes());

    @BeforeEach
    public void setUp() {
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void mainRiuscito() {
        // Simulate user input
        String simulatedUserInput = "mariorossi@gmail.com\n1234\nn\n";
        setSystemIn(simulatedUserInput);

        // Execute main method
        Main.main(new String[0]);

        // Capture output
        String output = outputStreamCaptor.toString();

        // Verify output contains expected strings (e.g., registration success message, and "Logout effettuato.")
        assertTrue(output.contains("Benvenuto!"));
        assertTrue(output.contains("Registrazione avvenuta con successo, esegui il Log in"));
        assertTrue(output.contains("Logout effettuato."));
    }

    @Test
    public void mainEmailNonValida() {
        // Simulate user input with invalid email
        String simulatedUserInput = "invalidemail\n1234\nn\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        // Execute main method
        Main.main(new String[0]);

        // Capture output
        String output = outputStreamCaptor.toString();

        // Verify output contains expected strings (e.g., invalid email message)
        assertTrue(output.contains("Email non valida. Registrazione annullata."));
    }

    @Test
    public void mainSceltaNonValida() {
        // Simulate user input with invalid choice
        String simulatedUserInput = "mariorossi@gmail.com\n1234\ns\nx\nn\n";
        setSystemIn(simulatedUserInput);

        // Execute main method
        Main.main(new String[0]);

        // Capture output
        String output = outputStreamCaptor.toString();

        // Verify output contains expected strings (e.g., invalid choice message)
        assertTrue(output.contains("Scelta non valida\nInserisci un valore valido"));
    }

    @AfterEach
    public void tearDown() {
        // Reset System.out to its original
        System.setOut(originalSystemOut);
    }

    //Helper method to set the System.in stream
    private void setSystemIn(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }
}