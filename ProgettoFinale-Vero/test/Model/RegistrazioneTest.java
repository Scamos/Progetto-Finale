package Model;

import Vista.VistaRegistrazione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.*;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
class RegistrazioneTest {

    private Registrazione registrazione;
    private VistaRegistrazione vista;

    @BeforeEach
    void setUp() {
        vista = new VistaRegistrazione();
        registrazione = Registrazione.getInstance(vista);
    }

    @Test
    void getInstance() {
        //Arrange
        VistaRegistrazione nuovaVista = new VistaRegistrazione();

        //Act
        Registrazione instance1 = Registrazione.getInstance(vista);
        Registrazione instance2 = Registrazione.getInstance(nuovaVista);

        //Assert
        assertSame(instance1, instance2);
    }

    @Test
    void verificaEmailNuova() {
        //Arrange
        String email = "nuovaemail@esempio.com";
        String password = "password123";

        //Act
        boolean result = registrazione.verificaEmail(email, password);

        //Assert
        assertTrue(result);
    }

    @Test
    void verificaEmailEsistente() {
        //Arrange
        String email = "mariorossi@gmail.com";
        String password = "1234";

        //Act
        boolean result = registrazione.verificaEmail(email, password);

        //Assert
        assertFalse(result);
    }

    @Test
    void verificaPasswordCorretta() {
        //Arrange
        String email = "mariorossi@gmail.com";
        String password = "1234";
        String input = "8\n"; //Simula l'input dell'utente per il logout
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //Act & Assert
        assertDoesNotThrow(() -> registrazione.verificaPassword(email, password));
    }

    @Test
    void verificaPasswordErrata() {
        //Arrange
        String email = "mariorossi@gmail.com";
        String password = "password_errata";
        String input = ""; //Non serve l'input simulato per questo caso
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //Act & Assert
        assertDoesNotThrow(() -> registrazione.verificaPassword(email, password));
    }

    @Test
    void mostraErroreRegistrazione() {
        //Act
        registrazione.mostraErroreRegistrazione();

        //Assert
        assertDoesNotThrow(() -> registrazione.mostraErroreRegistrazione());
    }

    @Test
    void creaNuovoUtenteRegistrato() {
        //Arrange
        /*String email = "nuovoutente@esempio.com";
        String password = "nuovapassword";
        String input = "n\n8\n"; //Simula la scelta di non inserire i dati della carta e poi il logout
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //Act & Assert
        assertDoesNotThrow(() -> registrazione.creaNuovoUtenteRegistrato(email, password));*/
    }

    @Test
    void chiamaUtenteRegistrato() {
        //Arrange
        String email = "mariorossi@gmail.com";
        String password = "1234";
        String input = "8\n"; //Simula l'input dell'utente per il logout
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //Act & Assert
        assertDoesNotThrow(() -> registrazione.chiamaUtenteRegistrato(email, password));
    }
}