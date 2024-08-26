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
public class MainTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalSystemOut = System.out;
    //private final ByteArrayInputStream inputStreamCaptor = new ByteArrayInputStream("".getBytes());
    private final InputStream originalSystemIn = System.in;

    @BeforeEach
    public void setUp() {
        //Reindirizza System.out per catturare l'output
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /*@Test
    public void mainRiuscito() {
        //Simula l'input
        String simulatedUserInput = "mariorossi@gmail.com\n1234\n8\nn\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(inputStream);

        //Esegui il main
        Main.main(new String[]{});

        //Cattura l'output
        String output = outputStreamCaptor.toString();

        //Verifica che l'output contenga le stringhe aspettate
        assertTrue(output.contains("Benvenuto!"));
        assertTrue(output.contains("Utente autenticato: mariorossi@gmail.com"));
        assertTrue(output.contains("Logout effettuato."));
        assertTrue(output.contains("Vuoi continuare? (s/n)"));
    }*/

    @Test
    public void mainEmailNonValida() {
        //Simula l'input
        String simulatedUserInput = "emailinvalida\n1234\nn\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        //Esegui il main
        Main.main(new String[0]);

        //Cattura l'output
        String output = outputStreamCaptor.toString();

        //Verifica che l'output contenga le stringhe aspettate
        assertTrue(output.contains("Email non valida. Registrazione annullata."));
    }

    /*@Test
    public void mainSceltaNonValida() {
        //Simula l'input
        String simulatedUserInput = "mariorossi@gmail.com\n1234\n8\ninvalido\nn\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(inputStream);

        //Esegui il main
        Main.main(new String[]{});

        //Cattura l'output
        String output = outputStreamCaptor.toString();

        //Verifica che l'output contenga le stringhe aspettate
        assertTrue(output.contains("Utente autenticato: mariorossi@gmail.com"));
        assertTrue(output.contains("Logout effettuato."));
        assertTrue(output.contains("Scelta non valida\nInserisci un valore valido"));
        assertTrue(output.contains("Vuoi continuare? (s/n)"));
    }*/

    @Test
    public void mainPasswordNonValida() {
        //Arrange
        String simulatedUserInput = "mariorossi@gmail.com\npassworderrata\nn\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(inputStream);

        //Act
        Main.main(new String[]{});

        //Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Errore: Password incorretta"));
        assertTrue(output.contains("Vuoi continuare? (s/n)"));
    }

    @AfterEach
    public void tearDown() {
        //Resetta System.out e System.in agli originali
        System.setOut(originalSystemOut);
        System.setIn(originalSystemIn);
    }
}